def call() {
    script {
        modifiedFiles = sh(
            returnStdout: true,
            script: "git diff --name-only \$(git ls-remote origin --tags ${env.CHANGE_TARGET} | cut -f1) ${env.GIT_COMMIT}"
        ).trim().split("\n").findAll { it.endsWith(".yml") }

        if ("group.yml" in modifiedFiles || "streams.yml" in modifiedFiles) {
            modifiedFiles = ["{images,rpms}/*"]
        }

        catchError(stageResult: 'FAILURE') {
            if (modifiedFiles.isEmpty()) {
                sh "echo 'No files to validate' > results.txt"
            } else {
                sh "validate-ocp-build-data ${modifiedFiles.join(" ")} > results.txt 2>&1"
            }
        }

        results = readFile("results.txt").trim()
        echo results
        commentOnPullRequest(msg: "### Build <span>#</span>${env.BUILD_NUMBER}\n```\n${results}\n```")
    }
}
