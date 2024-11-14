package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class UpdateGmailPage {
    @FindBy(xpath = "//button[@aria-label='account of current user']")
    public WebElement btnProfileIcon;
    @FindBy(css = "[role=menuitem]")
    public List<WebElement> btnProfileMenuItems;
    @FindBy(xpath = "//button[normalize-space()='Edit']")
    public WebElement editBtn;
    @FindBy(name = "email")
    public WebElement textemail;
    @FindBy(xpath = "//button[normalize-space()='Update']")
    public WebElement updateBtn;
    public UpdateGmailPage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }
}
