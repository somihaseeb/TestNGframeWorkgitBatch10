package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.CommonMethods;
import utils.ConfigReader;

public class LoginPage extends CommonMethods {

    //we will define all the locators in here and will call them as object of this class

    @FindBy(id="txtUsername")
    public WebElement usernameBox;

    @FindBy(id="txtPassword")
    public WebElement passwordBox;

    @FindBy(id="btnLogin")
    public WebElement loginBtn;

    //this locator is for the error message on the login page
    @FindBy(id="spanMessage")
    public WebElement errorMessage;
/*
another way but not recommended
public LoginPage(){
PageFactory.initElements(CommonMethods.driver, this);
 */

    public LoginPage(){
        PageFactory.initElements(driver, this);
    }


    public void login(String username, String password){
        sendText(usernameBox, username);
        sendText(passwordBox, password);
        click(loginBtn);
    }
}
