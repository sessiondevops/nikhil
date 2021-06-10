pipeline
{
    agent any
    environment
    {
        registry = "sessiondevops/multi" 
        registryCredential = 'docker_cred'
    }
    stages
    {
        stage("GIT")
        {
            steps
            {
                git branch: 'main', credentialsId: 'git_cred', url: 'https://github.com/sessiondevops/nikhil.git'
            }
        }
        stage("docker")
        {
            steps
            {
                script
                {
                dockerImage = docker.build registry + ":$BUILD_NUMBER"
                }
            }
        }
        stage('Deploy our image') { 
            steps { 
                script {
                    docker.withRegistry( '', registryCredential ) { 
                        dockerImage.push("latest")
                    dockerImage.push("${env.BUILD_ID}") 
                    }
                } 
            }
        }
        stage("K8 Nodes"){
            steps{
                kubernetesDeploy(
                    configs: 'mongo.yaml',
                    kubeconfigId: 'k8_cred'
                )
                kubernetesDeploy(
                    configs: 'nginxdeploy.yaml',
                    kubeconfigId: 'k8_cred'
                )
            }
        }
    }
}
