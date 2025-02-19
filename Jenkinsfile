pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'todolist-app'
        DOCKER_TAG = 'latest'
        COMPOSE_FILE = 'docker-compose.yml'
    }

    stages {
        stage('Clone Repository') {
            steps {
                git branch: 'main', url: 'https://github.com/Neil925/todo-list.git'
            }
        }

        stage('Start Services with Docker Compose') {
            steps {
                script {
                    sh "docker-compose -f $COMPOSE_FILE up -d"
                    sleep 10  // Give MySQL some time to initialize
                }
            }
        }

        stage('Build with Gradle') {
            steps {
                sh './gradlew clean build'
            }
        }

        stage('Run Tests') {
            steps {
                sh './gradlew test'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    sh "docker build -t $DOCKER_IMAGE:$DOCKER_TAG ."
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    sh "docker stop $DOCKER_IMAGE || true"
                    sh "docker rm $DOCKER_IMAGE || true"
                    sh "docker run -d --name $DOCKER_IMAGE --network=host $DOCKER_IMAGE:$DOCKER_TAG"
                }
            }
        }
    }

    post {
        always {
            script {
                sh "docker-compose -f $COMPOSE_FILE down"
            }
        }
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed. Check logs for details.'
        }
    }
}
