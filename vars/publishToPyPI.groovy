def call() {
    withCredentials([usernamePassword(
        credentialsId: "OpenShiftART_PyPI",
        usernameVariable: "TWINE_USERNAME",
        passwordVariable: "TWINE_PASSWORD"
    )]) {
        sh "python3 -m twine upload dist/*"
    }
}
