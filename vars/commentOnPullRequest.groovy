def call(Map stageParams) {
    withCredentials([string(credentialsId: "openshift-bot-token", variable: "GITHUB_TOKEN")]) {
        script {
            writeFile(file: "msg.txt", text: stageParams.msg)
            requestBody = sh(returnStdout: true, script: "jq --rawfile msg msg.txt -nr '{\"body\": \$msg}'")
            repositoryName = env.GIT_URL.replace("https://github.com/", "").replace(".git", "")

            httpRequest(
                contentType: 'APPLICATION_JSON',
                customHeaders: [[
                    maskValue: true,
                    name: 'Authorization',
                    value: "token ${env.GITHUB_TOKEN}"
                ]],
                httpMode: 'POST',
                requestBody: requestBody,
                responseHandle: 'NONE',
                url: "https://api.github.com/repos/${repositoryName}/issues/${env.CHANGE_ID}/comments"
            )
        }
    }
}
