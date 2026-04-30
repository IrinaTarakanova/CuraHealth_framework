package tarakanova.utils;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import tarakanova.base.BaseTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class TestListener implements ITestListener {

    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        test.set(ExtentReportManager.getReport()
                        .createTest(result.getName()));
        test.get().info("Test started");
        test.get().info("Parameters: " + Arrays.toString(result.getParameters()));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().pass("Test passed");
        test.remove();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.get().skip("Test skipped");
        test.get().skip(result.getThrowable());
        test.remove();
    }



    @Override
    public void onTestFailure(ITestResult result) {
        test.get().fail("Test failed");
        test.get().fail(result.getThrowable());

        System.out.println("Test failed: " + result.getName());
        System.out.println("Failed parameters: " + Arrays.toString(result.getParameters()));
        System.out.println("Failure reason: " + result.getThrowable().getMessage());


        try{
            BaseTest baseTest = (BaseTest) result.getInstance();
            File screenshot =
                    ((TakesScreenshot) baseTest.getDriver()).getScreenshotAs(OutputType.FILE);

            String path = "screenshots/"
                    + result.getName()
                    + "_"
                    + System.currentTimeMillis()
                    + ".png";

            Files.copy(screenshot.toPath(), Paths.get(path));
            System.out.println("Screenshot saved at: " + path);

        } catch (IOException e) {
            e.printStackTrace();
        }
        test.remove();
    }
    @Override
    public void onFinish(ITestContext context) {
        ExtentReportManager.getReport().flush();
    }
    public static void logInfo(String message){
        if(test.get() != null) {
            test.get().info(message);
        }
    }
}
