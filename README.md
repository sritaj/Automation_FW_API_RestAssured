![This is an image](https://www.north-47.com/wp-content/uploads/2021/06/rest-assured-resized-copy.jpg) 

# RestAssured APIAutomation Framework
Rest Assured Automation Framework for testing APIs with **TestNG** integrated with **Extent Reports**, **Faker library** and relevant dependencies.

## Tech Stack
1. Java
2. Rest Assured 
3. TestNG
4. Docker
5. YAML

## Included/Features Implemented
1. Rest Assured Framework to validate APIs
2. TestNG Framework to write Tests
3. Examples on JSON Parsing, OAuth 2.0 Authentication and getting Token, GET, POST, PUT, PATCH operations
4. Extent Report 5.x for generating Reports for Test Run
5. POJO classes and relevant dto classes for generating Data
6. Properties file and relevant classes to utilise it
7. TestNG Custom Annotations and RetryAnalyzer listeners
8. Dockerfile -> to create the Image of the project, copying relevant JARs and Files and Entrypoint to specify Test Execution
9. docker-compose.yaml -> to use the Image locally present to run by specifying the parameters required for Entrypoint and Volume Mapping
10. azure-pipelines.yml -> to build the Maven project and run the Tests by specifying the testNG xml suite and build and push the Docker image to repository
11. pom.xml -> maven pom.xml specifying all the dependencies and plugins for goals and running the specified testNG xml suite on runtime (locally/jenkins/azuredevops)

## Not Included/Yet to be Done
1. Parallel Execution for Tests
2. Cucumber Integration with relevant Reports
3. Excel Read/Write
4. Email APIs to send Reports
5. Jenkinsfile -> for creating and pushing Image to Docker repository

## Instructions
1. StudenAPITest -> requires a Docker Image to be running on local to hit the API Endpoints, check the [link](https://hub.docker.com/r/tejasn1/student-app) for instructions to setup
2. Maven Commands to Prepare Jars before running Dockerfile -> mvn clean package -DskipTests
3. Maven Commands to Execute TestNG xml suite to run test -> clean package test -DsuiteXmlFile=src/test/resources/xmls/$testSuite; replace $testSuite with the xml name
4. Docker Commands -> To Build Image: "docker build -t <prefered image name>:latest ." ex: "docker build -t rest-assured-apis:latest ."
5. DockerCompose command -> docker-compose up to run the Build and get the tests results in local system as per the Volume Mapping

## Common Troubleshoots For Local System
1. Misses In Docker Compose file -> Check Volume Mappings as per the Local Environment
2. Tests Not Running on Docker Compose file -> Check if any project related additional files like properties are not copied to Docker Image, any changes in Entrypoint needs to be cross checked in docker-compose file 
3. Running Docker Image in Interactive Mode -> Comment out Entrypoint with #, use the command "docker run -it <the image name specified>", ex: "docker run -it rest-assured-apis" to verify if all dependent Jars and files are copied
4. Tests not hitting the Endpoints -> API endpoints for the Tests are hosted by 3rd parties, possibly they could have been shut down or have been changed, please check free APIs from [any-api](https://any-api.com/), [reqres](https://reqres.in/) to practice

## Special Thanks
Special Thanks to many Instructors who have done a splendid job on explaining and designing framework
- [Rahul Shetty](https://rahulshettyacademy.com/#/index)
- [Rahul Arora](https://www.youtube.com/channel/UCVqvL7lt2hemvSlg6ihLWYw)
- And many more from which I learnt trouble-shooting points for Jenkins, Dockers, etc through YouTube, StackOverFlow and Articles 
