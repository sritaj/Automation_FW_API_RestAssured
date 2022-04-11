package utilities;

import constants.FrameworkConstants;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class ReqSpecsImpl {

    RequestSpecification specs;

    public RequestSpecification setRequestSpecs(String baseURI, String basePath, String logFile) {
        PrintStream log = null;
        try {
            log = new PrintStream(new FileOutputStream(FrameworkConstants.getRestassuredLogsPath() + logFile + ".txt"));
        } catch (FileNotFoundException f) {
            f.getMessage();
        }

        specs = new RequestSpecBuilder()
                .setBaseUri(baseURI)
                .setBasePath(basePath)
                .addFilter(RequestLoggingFilter.logRequestTo(log))
                .addFilter(ResponseLoggingFilter.logResponseTo(log))
                .build();

        return specs;
    }

}
