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
					sh 'mvn clean install'
				}
			}
		} 
		stage("Nexus Upload") {
			steps {
				script {
					nexusArtifactUploader artifacts: [[artifactId: 'et2', classifier: '', file: 'target/et2-0.0.3-SNAPSHOT.war', type: 'war']], credentialsId: 'Nexus_Cred', groupId: 'com.marsh', nexusUrl: 'ec2-52-15-81-117.us-east-2.compute.amazonaws.com:8081', nexusVersion: 'nexus3', protocol: 'http', repository: 'et2-Snapshot', version: '0.0.3-SNAPSHOT'
                }				


                }				
                    
            }
		}
	post {
        always {
            deleteDir() /* clean up our workspace 
        }
    }
}
