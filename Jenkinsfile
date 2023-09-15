pipeline {
    // agent { docker {image "iamsandeep82/django-app:latest"}}
    agent any

    environment {
        // dockerHome = tool 'jdk17'  // tool will provide a installed path
        // mavenHome = tool 'maven3.9'
        PATH = "$dockerHome/bin:$mavenHome/bin:$PATH" //from $ accessing variable
        var1 = "abcdefgl"
        var2 = 12345
        var3 = true
        DOCKERPASS = "$dockerhub_access"

        JENKINS_HOME = "${JENKINS_HOME}"
        JOB_NAME = "${JOB_NAME}"
        BUILD_NUMBER = "${BUILD_NUMBER}"
    }

    stages {    

        // START OF CI PIPELINE

        stage('Clean Workspace') {
                steps {
                    cleanWs()
                }
            }

         stage('Fetch Code') {
                steps {
                    git branch: 'main', url: 'https://github.com/iam-sandeep-82/online-retail-store-microservice'
                    sh "ls -lh"
                }
            }    

        stage('Setting ENV Vars') {
            steps {
                // ENVIRONMENT VARS --> USE TO DEFINE AND ACCESS INSIDE RUNNING AGENT
                // VARs  --> USE TO DEFINE THE VARS ACROSSS THE SCRIPTS
                echo "MY VARs"
                echo "VAR1 - $var1"
                echo "VAR3 - $var2"
                echo "VAR3 - $var3"
                
                echo "PREDEFINED ENV VARs"
                echo "TAG_NAME $env.TAG_NAME"
                echo "BRANCH_NAME $env.BRANCH_NAME"
                echo "BRANCH_IS_PRIMARY $env.BRANCH_IS_PRIMARY"
                echo "JOB_NAME $env.JOB_NAME"
                echo "WORKSPACE $env.WORKSPACE"
                echo "TAG_DATE $env.BUILD_TAG"
                echo "JENKINS_URL $env.JENKINS_URL"
                
            }
        }

       
        stage('Build JAR package') {
                steps {
                echo "---- BUILDING JAR FILE -----" 
                      sh "chmod +x ./build_jar.sh"
                      // sh "./build_jar.sh ./api-gateway/" 
                      // sh "./build_jar.sh ./cart-client/" 
                      // sh "./build_jar.sh ./customer-client/" 
                      // sh "./build_jar.sh ./eureka-server/" 
                      // sh "./build_jar.sh ./inventory-client/" 
                      // sh "./build_jar.sh ./order-client/" 
                      // sh "./build_jar.sh ./product-client/" 
                      sh "./build_jar.sh ./shopping-client/" 
                }
        }


        stage("UNIT TEST") {
            steps {
                echo "----PERFORMING JUNIT TEST----"
            }
        }

        stage("CHECKSTYLE TEST") {
            steps {
                echo "----PERFORMING CHECKSTYLE TEST----"
            }
        }


        stage("CODE QUALITY TEST") {
            steps {
                echo "----PERFORMING CODE QUALITY TEST----"
            }
        }
        

        stage("ARCHIVING THE ARTIFACT") {
            steps {
                // Build your application, e.g., compile code, run tests
                // Archive artifacts for later use
                archiveArtifacts artifacts: '**/target/*.jar', allowEmptyArchive: true
                // Copy the archived artifact to the Docker context (folder where Dockerfile is located)

            }
            
        }

        // END OF CI PIPELINE

        // START OF CONTINOUS DELIVERY PIPELINE

        // stage('Docker Build METHOD-1') {
        //     steps {
        //         script {
        //             echo "JENKINS_HOME: ${env.JENKINS_HOME}"
        //             echo "JOB_NAME: ${env.JOB_NAME}"
        //             echo "BUILD_NUMBER: ${env.BUILD_NUMBER}"
        //         }
        //         sh '''docker build --build-arg JENKINS_HOME="\${env.JENKINS_HOME}" \
        //                --build-arg JOB_NAME="\${env.JOB_NAME}" \
        //                --build-arg BUILD_NUMBER="\${env.BUILD_NUMBER}" \
        //                -t shopping-client:"\${env.BUILD_NUMBER}" -f ./shopping-client/test-dockerfile .'''
        //         sh "docker images | grep shopping-client"
        //     }
        // }


        stage('Docker Build METHOD-2') {
            steps {
                // Build the Docker image
               sh "docker build -t iamsandeep82/shopping-client:${env.BUILD_NUMBER} -f ./shopping-client/Dockerfile ."
            }
        }


        stage('Publish Docker Image') {
            steps {
                // Push the Docker image to a Docker registry
                withDockerRegistry([credentialsId: 'docker-cred', url: 'https://hub.docker.com/']) {
                    sh "docker push iamsandeep82/shopping-client:${env.BUILD_NUMBER}"
                }
            }
        }

        
    }

    post{
        always{ echo "i run every condition"}
        success{ echo "i run only when any success occured"}
        failure{ echo "i run only when any error occured"}
    }
}