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
import pages.ResetPasswordPage;
import utils.Utils;

import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.naming.ConfigurationException;
import java.io.IOException;

public class ResetPasswordTestRunner extends Setup {
    @Test(priority = 1, description = "Send not register email and check error message")
    public void sendnotRegMail() throws InterruptedException {
        ResetPasswordPage resetPass = new ResetPasswordPage(driver);
        resetPass.btnReset.click();
        resetPass.inputEmail.sendKeys("muna@test.com");
        resetPass.btnResetLink.click();
        Thread.sleep(6000);
        String notRegActualText = driver.findElement(By.xpath("//p[@class='MuiTypography-root MuiTypography-body1 css-gjwoc1']")).getText();
        String notRegExpectedText = "Your email is not registered";
        Assert.assertTrue(notRegActualText.contains(notRegExpectedText));
    }
    @Test(priority = 2, description = "Keep input email field empty and check error message")
    public void sendemptyMail() throws InterruptedException {
        Thread.sleep(2000);
        ResetPasswordPage resetPass = new ResetPasswordPage(driver);
        resetPass.inputEmail.sendKeys(Keys.CONTROL, "a");
        resetPass.inputEmail.sendKeys(Keys.BACK_SPACE);
//        resetPass.inputEmail.sendKeys("");
        resetPass.btnResetLink.click();
        Thread.sleep(3000);
        String requiredActual = resetPass.inputEmail.getAttribute("validationMessage");
        String expectedTxt = "Please fill out this field";
       Assert.assertTrue(requiredActual.contains(expectedTxt));
    }
    @Test(priority = 3, description = "Send valid registered gmail and click send reset link btn")
    public void sendRegisterMail() throws InterruptedException, ConfigurationException, IOException, ParseException {
        ResetPasswordPage resetPass = new ResetPasswordPage(driver);
        Thread.sleep(2000);
//        resetPass.btnReset.click();
//        resetPass.inputEmail.sendKeys(Keys.CONTROL, "a");
//        resetPass.inputEmail.sendKeys(Keys.BACK_SPACE);
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray = (JSONArray) jsonParser.parse(new FileReader("./src/test/resources/users.json"));
        JSONObject userObj = (JSONObject) jsonArray.get(jsonArray.size()-1);
        String email =(String) userObj.get("email");
        resetPass.inputEmail.sendKeys(email);
        resetPass.btnResetLink.click();
        Thread.sleep(5000);
        String resetPasswordUrl = retrievePassword();
        driver.get(resetPasswordUrl);
        String password = "muna10";
        resetPass.inputEmail.sendKeys(password);
        resetPass.confirmpassEmail.sendKeys(password);
        resetPass.resetPassBtn.click();

    }

    public String retrievePassword() throws InterruptedException, ConfigurationException, IOException {
//        Thread.sleep(3000);
        String latestMail = Utils.readLatestMail();  // Read the latest email content

        String urlPattern = "(https?://\\S+)";
        Pattern pattern = Pattern.compile(urlPattern);
        Matcher matcher = pattern.matcher(latestMail);

        if (matcher.find()) {
            String extractedUrl = matcher.group(1); // Get the matched URL
            System.out.println("Extracted URL: " + extractedUrl);
            return extractedUrl;
        } else {
            System.out.println("No URL found in the text.");
            return null;  // Return null if no URL is found
        }
    }
}
