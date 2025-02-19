pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'todolist-app'
        DOCKER_TAG = 'latest'
        MYSQL_CONTAINER = 'mysql-todo'
        MYSQL_ROOT_PASSWORD = 'root'
        MYSQL_DATABASE = 'todo'
        MYSQL_USER = 'todo'
        MYSQL_PASSWORD = 'root'
    }

    stages {
        stage('Clone Repository') {
            steps {
                git branch: 'main', url: 'https://github.com/Neil925/todo-list.git'
            }
        }

        stage('Start MySQL Container') {
            steps {
                script {
                    sh """
                    docker run -d --rm --name $MYSQL_CONTAINER \\
                        -e MYSQL_ROOT_PASSWORD=$MYSQL_ROOT_PASSWORD \\
                        -e MYSQL_USER=$MYSQL_USER \\
                        -e MYSQL_PASSWORD=$MYSQL_PASSWORD \\
                        -e MYSQL_DATABASE=$MYSQL_DATABASE \\
                        -p 3306:3306 \\
                        mysql:8.0
                    sleep 10
                    """
                }
            }
        }
        stage('Build with Gradle') {
            steps {
                sh 'gradle clean build'
            }
        }

        stage('Run Tests') {
            steps {
                sh 'gradle test'
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
                    sh """
                    docker run -d --name $DOCKER_IMAGE \\
                        --network=host \\
                        $DOCKER_IMAGE:$DOCKER_TAG
                    """
                }
            }
        }
    }
    post {
        always {
            script {
                sh "docker stop $MYSQL_CONTAINER || true"
                sh "docker rm $MYSQL_CONTAINER || true"
            }
        }
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed. Check logs for details.'
        }
    }}
