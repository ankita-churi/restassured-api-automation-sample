package com.qa.util;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {
    private static final ExtentReports extentReports = new ExtentReports();

    public static ExtentReports getInstance() {
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("test-output/ExtentReport.html");
        sparkReporter.config().setReportName("API Test Report");
        sparkReporter.config().setDocumentTitle("Automation Results");

        extentReports.attachReporter(sparkReporter);
        extentReports.setSystemInfo("Tester", "Ankita");
        extentReports.setSystemInfo("Environment", "QA");

        return extentReports;
    }
}