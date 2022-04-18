![This is an image](https://www.north-47.com/wp-content/uploads/2021/06/rest-assured-resized-copy.jpg) 

# RestAssured APIAutomation Framework
Rest Assured Automation Framework with **TestNG** and **Cucumber** integration, along with multiple reporting tools - **Extent Reports**, **Allure Reports**, **Maven Cucumber Report** and **Faker library**, **Azure Devops**, **Docker Setup** and relevant dependencies.

## Tech Stack
1. Java
2. Rest Assured 
3. TestNG
4. Cucumber
5. Docker Containerization
6. Azure Devops 

## Included/Features Implemented
### RestAssured Framework with TestNG
1. Rest Assured with TestNG Framework to test and validate APIs
2. Examples on JSON Parsing, OAuth 2.0 Authentication and getting Token, GET, POST, PUT, PATCH operations
3. Extent Report 5.x for generating Reports for TestNG Test Run
4. POJO classes and relevant dto classes for generating Data and using in Tests
5. Properties file and relevant classes to utilise it
6. TestNG Custom Annotations and RetryAnalyzer listeners
7. Sample XML files to run Tests based on Groups/Parallel

### RestAssured Framework with Cucumber
1. Rest Assured with Cucumber Framework to test and validate APIs
2. Examples on Features files, Steps Definations to execute test based on Rest Assured, Runner for TestNG, Hooks and Tags 
3. Allure Report -> Enabled for Cucumber Execution
4. Maven Cucumber Report -> Enabled for Cucumber Execution
5. Cucumber Cloud Report -> Enabled for Cucumber Execution

### Common to the Framework
1. Dockerfile -> to create the Image of the project, copying relevant JARs and Files and Entrypoint to specify Test Execution
2. docker-compose.yaml -> to run the Image in Docker container and getting the test-output to local system by Volume Mapping
3. azure-pipelines.yml -> to build the Maven project and run the Tests by specifying the testNG xml suite and build and push the Docker image to repository
4. pom.xml -> maven pom.xml specifying all the dependencies and plugins for goals, reports and running the specified testNG xml suite on runtime (locally/jenkins/azuredevops)
5. TestNG XML -> XMLs for regular testNG tests and Cucumber tests 
6. Examples on PropertiesFile to read values from config.properties file, ENUM to pass value to properties method, RestAssured logging, JSONPath wrapper classes 

## Not Included/Yet to be Done
1. Parallel Execution Examples for Cucumber Tests
3. Excel Read/Write
4. Email APIs to send Reports
5. Jenkinsfile -> for creating and pushing Image to Docker repository

## Instructions
### Building Docker Image
1. Maven Command to Prepare Jars before running Dockerfile -> ***mvn clean package -DskipTests***
2. To Build Docker Image, run command: "***docker build -t <preferredImageName:latest> .***" "docker build -t rest-assured-apis:latest ."

### Setting Up Student API for Tests
1. StudenAPITest -> requires Docker Image to be running on local to hit the API Endpoints
2. Run command -> **docker pull tejasn1/student-app**
3. Run command -> **docker run -p 8085:8080 -d tejasn1/student-app**
4. Check the [link](https://hub.docker.com/r/tejasn1/student-app) for additional instructions to setup

### Maven and Terminal Commands to Run Tests and Generate Reports
1. Maven Command to Execute TestNG xml suite to run test -> ***mvn clean package test -DsuiteXmlFile=xml-suites/$TESTSUITE***; replace $TESTSUITE with the xml name
2. Maven Command to Execute TestNG xml suite to generate Maven Cucumber Report -> ***mvn test -DsuiteXmlFile=xml-suites/$TESTSUITE***; replace $TESTSUITE with the xml name for Cucumber classes
3. Terminal Command to check Allure Reports after Cucumber Tests -> ***allure serve allure-results***

### Running Tests in Docker Setup
1. DockerCompose command -> ***docker-compose up*** to run the Container and automatically execute the Tests(as specified in the Entrypoint) for the specified Image(in this case the image built using Dockerfile which is present locally) 
2. Volume Mapping in Dockercompose -> Specify the local system path for which the test-output directory from the container will be copied
3. Specifying Env variables in Dockercompose -> The Env variables that is required for Entrypoint(as specified in Dockerfile) is read from the .env file, change the parameters as per requirement

## Common Troubleshoot

### Maven Tests
1. **mvn test** - will throw error since the surefire plugin in POM expect TestNG XML file to be specified

### Docker Setup
1. Test-output is not getting copied to Local system -> Check Volume Mappings in Docker compose file as per the Local Environment
2. suiteXMLFile is getting null on running docker compose -> Check correct parameter/xml file is specified in .env file which is used by docker-compose file for Environment variables
3. Tests Not Running on Docker Compose file -> 
    * Check if any project related additional files like properties are not copied to Docker Image, any changes in Entrypoint needs to be cross checked in docker-compose file
    * Check if the ENTRYPOINT specified in Dockercompose is proper or not
4. Running Docker Image in Interactive Mode -> Comment out Entrypoint with #, use the command "docker run -it <the image name specified>", ex: "docker run -it rest-assured-apis" to verify if all dependent Jars and files are copied 

### Issues with API Endpoints
4. Tests not hitting the Endpoints -> API endpoints for the Tests are hosted by 3rd parties, possibly they could have been shut down or have been changed, please check free APIs from [any-api](https://any-api.com/), [reqres](https://reqres.in/) to practice

## Special Thanks
Special Thanks to many Instructors who have done a splendid job on explaining and designing framework
- [Rahul Shetty](https://rahulshettyacademy.com/#/index)
- [Rahul Arora](https://www.youtube.com/channel/UCVqvL7lt2hemvSlg6ihLWYw)
- And many more from which I learnt trouble-shooting points for Jenkins, Dockers, etc through YouTube, StackOverFlow and Articles 
