pipeline {
    agent any

    tools {
        maven 'MAVEN_HOME' // Ensure Jenkins has Maven configured with this name
    }

    environment {
        POM_PATH = 'Task8/pom.xml' // Path to the parent POM file
        SONAR_HOST_URL = 'http://your-sonarqube-server:9000' // Replace with your SonarQube URL
        SONAR_LOGIN = credentials('sonar-token-id') // Use Jenkins credentials ID for SonarQube authentication
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/Bless-ngesera/Crisp_Navigation_System'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                script {
                    echo "Running SonarQube analysis..."
                    bat """
                        mvn -f ${POM_PATH} sonar:sonar ^
                            -Dsonar.projectKey=Crisp_Navigation ^
                            -Dsonar.host.url=${SONAR_HOST_URL} ^
                            -Dsonar.login=${SONAR_LOGIN}
                    """
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    echo "Running unit tests..."
                    bat "mvn -f ${POM_PATH} test"
                }
            }
        }

        stage('Package') {
            steps {
                script {
                    echo "Packaging application..."
                    bat "mvn -f ${POM_PATH} package"
                }
            }
        }

        stage('Deploy') {
            steps {
                echo "Deployment stage (modify as needed)..."
            }
        }
    }

    post {
        success {
            echo "Pipeline executed successfully! ✅"
        }
        failure {
            echo "Pipeline failed! ❌ Check the logs."
        }
    }
}
