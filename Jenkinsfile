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
					def pom = readMavenPom file: ''
					//echo  "${projectArtifactId} ${projectVersion}"
					nexusArtifactUploader artifacts: [
						[
							artifactId: '${projectArtifactId}', 
							classifier: '', 
							file: "target/${pom.artifactId}-${pom.version}.war", 
							type: 'war'
						]
					], 
						credentialsId: 'Nexus_Cred', 
						groupId: 'com.marsh', 
						nexusUrl: '192.168.0.101:8081', 
						nexusVersion: 'nexus3', 
						protocol: 'http', 
						repository: 'java_Nexus_snap', 
						version: "${pom.version}"
                }				
                    
            }
		}
    }   
	/*post {
        always {
            deleteDir() /* clean up our workspace 
        }
    }*/
}
