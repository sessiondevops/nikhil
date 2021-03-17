pipeline {
    agent {
        label "master"
    }
    tools {
        maven "Maven"
    }
	stages {
		stage("Check Out") {
			steps {
				script {
					checkout([$class: 'GitSCM', branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[credentialsId: 'Nexus_Cred', url: 'https://github.com/sessiondevops/nexus.git']]])
					
				}
			}
		}
		stage("Build") {
			steps {
				script {
					bat 'mvn clean install'
				}
			}
		}
		stage("Nexus Upload") {
			steps {
				script {
					pom = readMavenPom file: 'pom.xml'
					pom.version
					nexusArtifactUploader artifacts: [[artifactId: 'et2', classifier: '', file: 'target/et2-0.0.2-SNAPSHOT.war', type: 'war']], credentialsId: 'Nexus_Cred', groupId: 'com.marsh', nexusUrl: '192.168.0.101:8081', nexusVersion: 'nexus3', protocol: 'http', repository: 'java_Nexus_snap', version: '0.0.2-SNAPSHOT'
                }				
                    
            }
		}
    }   
	post {
        always {
            deleteDir() /* clean up our workspace */
        }
    }
}
