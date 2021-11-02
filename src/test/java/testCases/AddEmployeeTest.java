package testCases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.AddEmployeePage;
import pages.DashBoardPage;
import pages.EmployeeListPage;
import pages.LoginPage;
import utils.CommonMethods;
import utils.ConfigReader;
import utils.Constants;
import utils.ExcelReading;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AddEmployeeTest extends CommonMethods {

    @Test(groups = "smoke")
    public void addEmployee(){
        //login function--

        LoginPage loginPage = new LoginPage();
        loginPage.login(ConfigReader.getPropertyValue("username"), ConfigReader.getPropertyValue("password"));

        DashBoardPage dashBoardPage = new DashBoardPage();
        click(dashBoardPage.pimOption);
        click(dashBoardPage.addEmployee);

        //now we are on add employee page

        AddEmployeePage addEmployeePage = new AddEmployeePage();
        sendText(addEmployeePage.firstName, "test90909011");
        sendText(addEmployeePage.middleName, "test909090911");
        sendText(addEmployeePage.lastName,"11909090test");
        click(addEmployeePage.saveBtn);
    }


    @Test
    public void addMultipleEmployees(){
        //login
        LoginPage loginPage = new LoginPage();
        loginPage.login(ConfigReader.getPropertyValue("username"), ConfigReader.getPropertyValue("password"));

        //navigate to add employee page
        DashBoardPage dashBoardPage = new DashBoardPage();
        AddEmployeePage addEmployeePage = new AddEmployeePage();
        EmployeeListPage empList= new EmployeeListPage();

        //we r doing soft assert to verify the employee data
        SoftAssert softAssert = new SoftAssert();

        //
        List<Map<String, String>> newEmployees = ExcelReading.excelIntoListMap(Constants.TESTDATA_FILEPATH, "EmployeeData");

        Iterator<Map<String, String>> it = newEmployees.iterator();
        while (it.hasNext()){
            click(dashBoardPage.pimOption);
            click(dashBoardPage.addEmployee);
            Map<String, String> mapNewEmployee = it.next();
            sendText(addEmployeePage.firstName, mapNewEmployee.get("FirstName"));
            sendText(addEmployeePage.middleName, mapNewEmployee.get("MiddleName"));
            sendText(addEmployeePage.lastName, mapNewEmployee.get("Lastname"));

            //capturing employee id from system
            String employeeIdValue = addEmployeePage.employeeId.getAttribute("value");

            sendText(addEmployeePage.photograph, mapNewEmployee.get("Photograph"));
            //select check box
            if(!addEmployeePage.createLoginCheckBox.isSelected()){
                click(addEmployeePage.createLoginCheckBox);
            }

            //provide credential for user
            sendText(addEmployeePage.createUsername, mapNewEmployee.get("UserName"));
            sendText(addEmployeePage.createPassword, mapNewEmployee.get("Password"));
            sendText(addEmployeePage.rePassword, mapNewEmployee.get("Password"));
            click(addEmployeePage.saveBtn);

            //navigate to employee list page
            click(dashBoardPage.pimOption);
            click(dashBoardPage.addEmployeeListOption);

            sendText(empList.idEmployee, employeeIdValue );
            click(empList.searchButton);

            List<WebElement> rowData = driver.findElements(By.xpath("//*[id = 'resultTable']/tbody/tr"));

            for (int i=0; i<rowData.size();i++){
                System.out.println("I am inside the loop to get the value for the newly generated employee");

                String rowText = rowData.get(i).getText();
                System.out.println(rowText);

                String expectedData = employeeIdValue + " " + mapNewEmployee.get("FirstName") + " " + mapNewEmployee.get("MiddleName")+ " "+ mapNewEmployee.get("LastName");

                softAssert.assertEquals(rowText, expectedData);

            }
        }

        //to get the collective report
        softAssert.assertAll();

    }
}
