// Same pipeline as in "Jenkins Web"
echo 'hello from Pipeline'
pipeline {
    agent any
    triggers{
        pollSCM('H/5 * * * *')
    }
    stages {
        stage ('Initialize') 
        dir('./tomcat/apache-tomcat-6.0.53-src/'){
            steps {
                sh 'echo hello from Initalize'
                sh 'cd ..'
                sh 'echo BEIM INIT ---------$PWD'
                sh 'mvn clean'
            }
        }
        stage ('Build') {
            steps {
                sh 'echo hello from Build'
                sh 'mvn clean compile assembly:single' 
            }

        }
        stage ('Test') {
            steps {
                sh 'echo hello from Test'
                sh 'mvn test'
                
            }
        }
        stage ('Move') {
            steps {
                sh 'echo hello from Move'
                sh 'cp -uv ./target/tomcat-6.0.53-jar-with-dependencies.jar ~/tomcat/bin '
                
            }
        }
    }
}
