package utils;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.naming.ConfigurationException;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class Utils {
    public static Properties props;
    public static FileInputStream file;
    public static int generateRandomNumber(int min, int max){
        double randomId = Math.random()*(max-min)+min;
        return (int) randomId;
    }
    public static void saveUserInfo(String filePath, JSONObject jsonObject) throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray = (JSONArray) jsonParser.parse(new FileReader(filePath));
        jsonArray.add(jsonObject);
        FileWriter fileWriter = new FileWriter(filePath);
        fileWriter.write(jsonArray.toJSONString());
        fileWriter.flush();
        fileWriter.close();

    }

    public static  String getEmailList() throws IOException, ConfigurationException {

        props = new Properties();
        file = new FileInputStream("./src/test/resources/config.properties");
        props.load(file);
        RestAssured.baseURI="https://gmail.googleapis.com/";
        Response res=given().contentType("application/json")
                .header("Authorization","Bearer "+props.getProperty("google_access_token"))
                .when().get("/gmail/v1/users/me/messages");
        JsonPath jsonPath=res.jsonPath();
        return jsonPath.get("messages[0].id");
    }
    public static String readLatestMail() throws IOException, ConfigurationException {
        String messageId= getEmailList();
        RestAssured.baseURI="https://gmail.googleapis.com/";
        Response res=given().contentType("application/json")
                .header("Authorization","Bearer "+props.getProperty("google_access_token"))
                .when().get("/gmail/v1/users/me/messages/"+messageId);

        JsonPath jsonPath=res.jsonPath();
        String message= jsonPath.get("snippet");
        return message;

    }

    public static void updateEmail(String filePath, String oldEmail, String newEmail) throws IOException, ParseException {
        // Read the existing JSON data
        JSONParser jsonParser = new JSONParser();
        JSONArray usersArray = (JSONArray) jsonParser.parse(new FileReader(filePath));

        // Find the user and update the email
        boolean userFound = false;
        for (Object userObject : usersArray) {
            JSONObject user = (JSONObject) userObject;
            String userEmail = (String) user.get("email");

            if (userEmail.equals(oldEmail)) {
                user.put("email", newEmail);  // Update the email
                userFound = true;
                break;
            }
        }

        if (userFound) {
            // Write the updated JSON array back to the file
            try (FileWriter fileWriter = new FileWriter(filePath)) {
                fileWriter.write(usersArray.toJSONString());
                fileWriter.flush();
            }
//            System.out.println("Email updated successfully!");
        } else {
            System.out.println("User not found in the JSON file.");
        }
    }
}
