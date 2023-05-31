pipeline {

    agent any

    tools {
        maven 'Maven-3-6-3'
        nodejs "Node-JS-18"
    }

    environment {
        SONAR_HOST_URL = "https://sonarcloud.io/"
        CI = false
    }

    stages {

        stage("Init") {

            steps {
                dir ("backend") {
                    sh  '''
                        export MAVEN_HOME=/opt/maven
                        export PATH=$PATH:$MAVEN_HOME/bin
                        mvn --version
                        mvn clean
                        '''
                }
            }
        }
        
        stage ("Test") {
            parallel {
                stage ("Test backend") {

                    steps {
                        dir ("backend") {
                            sh 'mvn test'
                        }
                    }
                }

                /*
                stage('Test frontend') {
                    steps {
                        dir("frontend") {
                            script {
                                sh '''
                                    set -x
                                    npm test
                                '''
                            }
                        }
                    }
                }
                */
            }
        }

        stage ("Build") {
            parallel {
                stage ("Build backend") {

                    steps {
                        dir ("backend") {
                            sh 'mvn -B -DskipTests package'
                        }
                    }
                }

                stage('Build frontend') { 
                    steps {
                        dir ("frontend") {
                            script {
                                sh '''
                                    npm install
                                    npm run build
                                    '''
                            } 
                        }
                    }
                }
            }
        }

        stage('Analyse via Sonar') {
            environment {
                sonar = tool 'SonarCloudScanner'
            }

            steps {
                withSonarQubeEnv(installationName: 'SonarCloud', credentialsId: 'sonar-token') {
                    sh "${sonar}/bin/sonar-scanner"
                }
            }
        }

        stage('Login in Docker') {
            steps {
                    withCredentials([usernamePassword(credentialsId: 'andrvat_dockerhub', passwordVariable: 'password', usernameVariable: 'user')]) {
                        sh "docker login -u ${env.user} -p ${env.password}"
                    }
            }
        }

        stage("Build images") {
            parallel {
                stage('Build backend image') {
                    steps {
                        dir ("backend") {
                            sh '''
                                docker build . -t andrvat/backend
                                docker push andrvat/backend
                                '''
                        }
                    }
                }

                stage('Build frontend image') {
                    steps {
                        dir ("frontend") {
                            sh "docker build . -t andrvat/frontend"
                            sh "docker push andrvat/frontend"
                        }
                    }
                }
            }
        }
    }
}
