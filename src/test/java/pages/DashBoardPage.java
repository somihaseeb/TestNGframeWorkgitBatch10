package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.CommonMethods;

public class DashBoardPage extends CommonMethods {

    @FindBy(id = "welcome")
    public WebElement welcomeMessage;

    @FindBy(id = "menu_pim_viewPimModule")
    public WebElement pimOption;
    /*
    @FindBy(xpath="//a[@id=\"menu_pim_viewPimModule\"]")
    public WebElement PIM;
*/
    @FindBy(id ="menu_pim_addEmployee")
    public WebElement addEmployee;
/*
    @FindBy(xpath="//a[@id=\"menu_pim_addEmployee\"]")
    public WebElement addEmployee;
    */

    @FindBy(id ="menu_pim_viewEmployeeList")
    public WebElement addEmployeeListOption;


    public DashBoardPage(){
        PageFactory.initElements(driver, this);
    }

}
