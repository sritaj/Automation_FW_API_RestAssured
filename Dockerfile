FROM openjdk:8-jre-slim

#WORKSPACE
WORKDIR /usr/share/tag

#ADD .jars under Target from Host
#into this image
ADD target/rest-assured-apis.jar               rest-assured-apis.jar
ADD target/rest-assured-apis-tests.jar         rest-assured-apis-tests.jar
ADD target/libs                                libs

#ADD XML SUITE files
ADD xml-suites/*                   /usr/share/tag/

#copy if any other dependencies are there like test-data files, properties files
ADD src/test/resources/properties/config.properties     src/test/resources/properties/config.properties
ADD src/test/resources/jsonfiles/librarybook.json       src/test/resources/properties/librarybook.json
ADD src/test/resources/cucumber.properties              src/test/resources/cucumber.properties

#Entrypoint to run TestNG files on docker-compose
ENTRYPOINT java -cp rest-assured-apis.jar:rest-assured-apis-tests.jar:libs/* org.testng.TestNG $TESTSUITE
