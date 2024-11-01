package Listeners;

import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

public class IInvokedMethod implements IInvokedMethodListener {



    public void afterInvocation(org.testng.IInvokedMethod method, ITestResult testResult) {


        System.out.println(method.getTestResult());
    }
}
