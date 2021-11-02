package utils;

import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.concurrent.Callable;

public class Listeners implements ITestListener {

    public void onTestSuccess(ITestResult result){

        CommonMethods.takeScreenshot("passed/" + result.getName());
    }

    public void onTestFailure(ITestResult result){
        CommonMethods.takeScreenshot("failed/" + result.getName());
    }

}
