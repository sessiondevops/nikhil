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
		stage('SonarQube analysis') {
			steps {
				script {
					def scannerHome = tool 'SonarScanner 4.0';
					withSonarQubeEnv('My SonarQube Server') { // If you have configured more than one global server connection, you can specify its name
								sh 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.3.0.603:sonar ' + 
          							'-f all/pom.xml ' +
          '-Dsonar.projectKey=com.huettermann:all:master ' +
          '-Dsonar.login=$SONAR_UN ' +
          '-Dsonar.password=$SONAR_PW ' +
          '-Dsonar.language=java ' +
          '-Dsonar.sources=. ' +
          '-Dsonar.tests=. ' +
          '-Dsonar.test.inclusions=**/*Test*/** ' +
          '-Dsonar.exclusions=**/*Test*/**'
					}
				}
			}
		}
		 stage("SonarQube Quality Gate") { 
        		timeout(time: 1, unit: 'HOURS') { 
           		def qg = waitForQualityGate() 
           		if (qg.status != 'OK') {
            			 error "Pipeline aborted due to quality gate failure: ${qg.status}"
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
						groupId: 'com.marsh', 
						nexusUrl: 'ec2-52-15-81-117.us-east-2.compute.amazonaws.com:8081', 
						nexusVersion: 'nexus3', 
						protocol: 'http', 
						repository: 'et2-Snapshot', 
						version: "${pom.version}"
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
