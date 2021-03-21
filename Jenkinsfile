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
					git branch: 'main', credentialsId: 'Git_cred', url: 'https://github.com/sessiondevops/nexus.git'
					
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
		stage('SonarQube analysis') {
			steps {
				script {
					def scannerHome = tool 'sonarqube';
					withSonarQubeEnv("sonarqube") { // If you have configured more than one global server connection, you can specify its name
						sh "${scannerHome}/bin/sonar-scanner"
					}
				}
			}
		}
		 stage('Quality Gates'){
			steps {
				script {
					timeout(time: 1, unit: 'MINUTES') {
						def qg = waitForQualityGate() 
						if (qg.status != 'OK') {
							error "Pipeline aborted due to quality gate failure: ${qg.status}"
						}
					}
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
							artifactId: "${pom.artifactId}", 
							classifier: '', 
							file: "target/${pom.artifactId}-${pom.version}.war", 
							type: 'war'
						]
					], 
						credentialsId: 'Nexus_Cred', 
						groupId: "${pom.groupId}", 
						nexusUrl: '18.191.220.162:8081', 
						nexusVersion: 'nexus3', 
						protocol: 'http', 
						repository: 'et2-Snapshot', 
						version: "${pom.version}"
                }				
                    
            }
		}
		stage("Download Artificates") {
			steps {
				script {
					def pom = readMavenPom file: ''
					def workspace = WORKSPACE
					sh "curl -iX GET 'http://18.191.220.162:8081/repository/et2-Snapshot/com/marsh/${pom.artifactId}/${pom.version}/${pom.artifactId}-*.war' -o $workspace/${pom.artifactId}.war"
					echo "Artifactes has been downloaded"
				}
			}
		}
		stage("Deploy") {
			steps {
				script {
					def pom = readMavenPom file: ''
					sh "mv $workspace/${pom.artifactId}.war /opt/tomcat/webapps/et2.war"
					sh "export BUILD_ID=dontKillMe && /opt/tomcat/bin/startup.sh"
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
