package pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class AddtwoItemsPage {
    @FindBy(className ="add-cost-button")
    public WebElement txtaddCostItem;
    @FindBy(className = "submit-button")
    public WebElement txtsubmitBtn;
    @FindBy(id = "itemName")
    public WebElement txtitemName;
    @FindBy(xpath = "//button[normalize-space()='+']")
    public WebElement txtplusBtn;
    @FindBy(id = "amount")
    public WebElement txtamount;
    @FindBy(id = "purchaseDate")
    public WebElement txtdatePicker;
    @FindBy(xpath = "//textarea[@id='remarks']")
    public WebElement txtremarks;

    public AddtwoItemsPage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }
    public void datePickerTest(String purchaseDate){

        String[] dateParts = purchaseDate.split("-");
        String day = dateParts[0];
        String month = dateParts[1];
        String year = dateParts[2];
        txtdatePicker.sendKeys(day);
        txtdatePicker.sendKeys(month);
        txtdatePicker.sendKeys(Keys.ARROW_RIGHT);
        txtdatePicker.sendKeys(year);
    }
}
