package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class LoginPage {
    @FindBy(id = "email")
    public WebElement txtEmail;
    @FindBy(id = "password")
    public WebElement txtPassword;
    @FindBy( css = "[type = submit]")
    public WebElement btnLogin;
    @FindBy(xpath = "//button[@aria-label='account of current user']")
    public WebElement btnProfileIcon;
    @FindBy(css = "[role=menuitem]")
    public List<WebElement> btnProfileMenuItems;
    public LoginPage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }
    public void doLogin(String email, String password){
        txtEmail.sendKeys(email);
        txtPassword.sendKeys(password);
        btnLogin.click();
    }
}
