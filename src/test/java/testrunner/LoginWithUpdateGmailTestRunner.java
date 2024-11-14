package testrunner;

import config.Setup;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.UpdateGmailPage;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class LoginWithUpdateGmailTestRunner extends Setup {
    @Test(priority = 1, description = "Previous login Email is failed")
    public void loginWithPreviousGmail() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.doLogin("rowshonamin10+130@gmail.com", "muna10");
        String errorActualText=driver.findElement(By.xpath("//form[@class='login-form MuiBox-root css-0']/p")).getText();
        String errorExpectedText = "Invalid email or password";
        Assert.assertTrue(errorActualText.contains(errorExpectedText));
        Thread.sleep(2000);

    }
    @Test(priority = 2, description = "Login with updated gmail and password")
    public void loginWithUpdatedGmail() throws IOException, ParseException, InterruptedException {
    LoginPage loginPage = new LoginPage(driver);
    JSONParser jsonParser = new JSONParser();
    JSONArray jsonArray = (JSONArray) jsonParser.parse(new FileReader("./src/test/resources/users.json"));
    JSONObject userObj = (JSONObject) jsonArray.get(jsonArray.size()-1);
    String email =(String) userObj.get("email");
    loginPage.txtEmail.sendKeys(Keys.CONTROL, "a");
    loginPage.txtEmail.sendKeys(Keys.BACK_SPACE);
    loginPage.txtPassword.sendKeys(Keys.CONTROL, "a");
    loginPage.txtPassword.sendKeys(Keys.BACK_SPACE);
    loginPage.doLogin(email, "muna10");
    String headerActual = driver.findElement(By.tagName("h2")).getText();
    String headerExpected = "User Daily Costs";
    Assert.assertTrue(headerActual.contains(headerExpected));
    loginPage.btnProfileIcon.click();
    loginPage.btnProfileMenuItems.get(1).click();

    }
}
