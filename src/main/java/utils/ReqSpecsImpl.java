package utils;

import constants.FrameworkConstants;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

/**
 * ReqSpecsImpl class to define wrapper methods for building Request Specification
 */
public class ReqSpecsImpl {

    RequestSpecification specs;

    /**
     * Method to create the Request Specification
     *
     * @param baseURI - Base URI for the RestAPI
     * @param basePath - Base Path for the RestAPI Endpoints
     * @param logFile - logfile path to write the Request and Response
     * @return RequestSpecification - Request specification body
     */
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
