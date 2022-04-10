pipeline {
  agent any
  // Specifying the environments configured under Jenkins Global Configuration/Keys
  environment {
        registryCredential = 'dockerHub'
  }

  stages {
    // Creating the Docker Image
    stage('Build Docker Image') {
      steps {
        // "docker build -t sritaj/selenium_docker:latest ."
        script {
          docker.build("sritaj/rest-assured-apis")
        }
      }
    }
    // Pushing the Docker Image
    stage('Push Docker Image') {
      steps {
        script{
          docker.withRegistry('https://registry.hub.docker.com', registryCredential){
            dockerImage.push("${BUILD_NUMBER}")
            dockerImage.push('latest')
          }
        }
      }
    }
  }
}