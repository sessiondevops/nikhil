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
					def pom = readMavenPom file: ''
					//echo  "${projectArtifactId} ${projectVersion}"
					def nexusRepoName = pom.version.endsWith("SNAPSHOT") ? "et2-snapshot" : "et2-release"
					/*nexusArtifactUploader artifacts: [
						[
							artifactId: "${pom.artifactId}", 
							classifier: '', 
							file: "target/${pom.artifactId}-${pom.version}.war", 
							type: 'war'
						]
					], 
						credentialsId: 'Nexus_Cred', 
						groupId: 'com.marsh', 
						nexusUrl: '52.15.81.117:8081', 
						nexusVersion: 'nexus3', 
						protocol: 'http', 
						repository: 'et2-Snapshot', 
						version: "${pom.version}"*/
					nexusArtifactUploader credentialsId: 'Nexus_cred', groupId: 'com.marsh', nexusUrl: '52.15.81.117:8081', nexusVersion: 'nexus3', protocol: 'http', repository: 'et2-Snapshot', version: '${pom.version}'
                }				
                    
            }
		}
    }   
	/*post {
        always {
            deleteDir() /* clean up our workspace 
        }
    }
}
