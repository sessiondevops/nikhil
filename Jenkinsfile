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
		stage('SCM') {
			git 'https://github.com/sessiondevops/nexus.git'
		}
		stage('SonarQube analysis') {
		withSonarQubeEnv(credentialsId: 'e92ce7d8e19007c251c71cb9b1782df3cc93f853', installationName: 'My SonarQube Server') {
			sh 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.7.0.1746:sonar'
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
