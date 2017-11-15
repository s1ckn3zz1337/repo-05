// Same pipeline as in "Jenkins Web"
echo 'hello from Pipeline'
pipeline {
    agent any
    triggers{
        pollSCM('H/5 * * * *')
    }
    stages {
        stage ('Initialize'){
            steps {
                sh 'echo hello from Initalize'
                sh 'echo BEIM INIT ---------$PWD'
                sh 'cd ./tomcat/apache-tomcat-6.0.53-src ; mvn clean'
            }
        }
        stage ('Build') {
            steps {
                sh 'echo hello from Build'
                sh 'cd ./tomcat/apache-tomcat-6.0.53-src ; mvn clean compile assembly:single' 
            }

        }
        stage ('Test') {
            steps {
		sh 'cd ./tomcat/apache-tomcat-6.0.53-src ; mvn clean'
                sh 'echo hello from Test'
                sh 'cd ./tomcat/apache-tomcat-6.0.53-src ; mvn test'
                
            }
        }
        stage ('Move') {
            steps {
                sh 'echo hello from Move'
                sh 'cd ./tomcat/apache-tomcat-6.0.53-src ; mkdir -p ~/tomcat/bin ;ls ./target/ ; cp -uv ./target/tomcat-6.0.53-jar-with-dependencies.jar ~/tomcat/bin '
                
            }
        }
    }
}
