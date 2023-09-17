pipeline {
    // agent { docker {image "iamsandeep82/django-app:latest"}}
    agent any


    tools {
        maven 'maven'
        dockerTool 'docker'
        jdk 'OpenJDK8'
    }


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
        
        
        stage("OWASP DEPENDANCY CHECK") {
            steps {
                echo "----PERFORMING OWASP DEPENDANCY----"
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

        stage('Docker Build & Publish') {
            steps {
                script{
                    withDockerRegistry(credentialsId: 'docker-cred', toolName: 'docker') {
                        sh "docker build -t shopping-client:v1 -f ./shopping-client/Dockerfile ."
                        sh "docker tag shopping-client:v1 iamsandeep82/shopping-client:v1"
                        sh "docker push iamsandeep82/shopping-client:v1"
                    }
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