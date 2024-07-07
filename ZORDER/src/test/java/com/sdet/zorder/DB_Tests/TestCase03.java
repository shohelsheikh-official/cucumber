
package com.sdet.zorder.DB_Tests;

import com.sdet.zorder.API_Tests.TestCase01;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.*;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class TestCase03 {
    private String baseURI;
    private String dbURI;
    private String dbName;
    private String dbUsername;
    private String dbPwd;
    private Integer expectedTableCount;
    private String email;
    private int validItemId;
    private int validItemQuantity;
    private String validPaymentType;
    private String validAddress;
    private String orderUrlPost;
    private String orderUrlAll;
    // TODO: Configure the Logger object
    static Logger log = Logger.getLogger(TestCase03.class.getName());

    public TestCase03() throws IOException, ClassNotFoundException {

        Properties prop = new Properties();
        // Load the properties file
        FileInputStream input = new FileInputStream("src/test/resources/application.properties");
        prop.load(input);
        // TODO: Load the JDBC drivers
         Class.forName("com.mysql.cj.jdbc.Driver");
        // TODO: Configure Log4J properties file
        PropertyConfigurator.configure("log4j-qa.properties");
        baseURI = prop.getProperty("baseURI");
        RestAssured.baseURI = baseURI;
        dbURI = prop.getProperty("dbURI");
        dbName = prop.getProperty("dbName");
        dbUsername = prop.getProperty("dbUsername");
        dbPwd = prop.getProperty("dbPwd");
        expectedTableCount = 6;
        orderUrlPost = prop.getProperty("orderUrlPost");
        orderUrlAll = prop.getProperty("orderUrlAll");
        email = prop.getProperty("email");
        validItemId = Integer.parseInt(prop.getProperty("validItemId"));
        validItemQuantity = Integer.parseInt(prop.getProperty("validItemQuantity"));
        validPaymentType = prop.getProperty("validPaymentType");
        validAddress = prop.getProperty("validAddress");
    }

    public int createOrder(int itemId) throws Exception {

        log.info("Creating an order for email " + email);
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer USER_IMPERSONATE_" + email);
        JSONObject payload = new JSONObject();
        JSONObject itemDetails = new JSONObject();
        payload.put("paymentType", validPaymentType);
        payload.put("shippingAddress", validAddress);
        payload.put("billingAddress", validAddress);
        itemDetails.put("itemId", itemId);
        itemDetails.put("quantity", validItemQuantity);
        JSONArray list = new JSONArray();
        list.put(itemDetails);
        payload.put("orderItemsDetail", list);
        // Set content type and attach payload
        request.contentType(ContentType.JSON);
        request.body(payload.toString());
        // Send POST request
        log.info("Sending post request with body " + payload.toString() + " to " + baseURI + orderUrlPost);
        Response response = request.post(orderUrlPost);
        int statusCode = response.getStatusCode();
        if (statusCode != 200) {
            log.warn("Response came with status code " + statusCode);
            throw new Exception("Unexpected status code: " + statusCode);
        }
        log.info("Response came with status code " + statusCode);
        // Parse JSON to get the id value from the data object
        JsonPath jsonPathEvaluator = response.jsonPath();
        int orderId = jsonPathEvaluator.get("data.id");
        log.info("ID received from Response: " + orderId);
        return orderId;
    }

    @Test
    public void testTableCount() throws ClassNotFoundException {
        log.info("Starting test- Assert number of tables in database");
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://" + dbURI + dbName, dbUsername, dbPwd)) {
            // TODO: Assign variable dbMetaData to get database metadata

             DatabaseMetaData dbMetaData = connection.getMetaData();;
            int tableCount = 1;
            try (ResultSet rs = dbMetaData.getTables(null, null, "%", new String[] { "TABLE" })) {
                while (rs.next()) {
                    tableCount++;
                }
            }
            log.info("Number of tables in database: " + (tableCount - 1));
            log.info("Expected Number of tables in database: " + expectedTableCount);
            Assert.assertEquals((tableCount - 1), expectedTableCount,
                    "The database does not contain the expected number of tables.");
            log.info("Test Case `testTableCount` success - Table numbers match!");
        } catch (SQLException e) {
            // In case of an exception, we fail the test.
            log.warn("Test Case `testTableCount` failure - Error while querying");
            Assert.fail("SQL Exception occurred: " + e.getMessage());
        } catch (AssertionError e) {
            log.warn(
                    "Test Case `testTableCount` failure - The database does not contain the expected number of tables.");
            throw e;
        }
    }

    @Test
    public void checkDbForOrder() throws Exception {
        log.info("Starting test- Creating an order should reflect db tables");
        int orderId = createOrder(validItemId);
        log.info("Order nummber " + orderId + " has been created");
        // SQL statements to check existence of order and order item details.
        // TODO: Modify the select statement to count number of orders with a variable,
        // order ID
         String orderExistSQL = "SELECT COUNT(1) FROM orders WHERE id = ?";
        String orderItemDetailsExistSQL = "SELECT COUNT(1) FROM order_item_details WHERE order_id = ?";
        // TODO: Censor or remove credentials for levels which are not INFO or DEBUG
        log.info("Logging into db Zorder with username: " + dbUsername + " | password: " + dbPwd);
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://" + dbURI + dbName, dbUsername, dbPwd)) {
            // Check for order existence
            try (PreparedStatement orderStmt = conn.prepareStatement(orderExistSQL)) {
                orderStmt.setInt(1, orderId);
                try (ResultSet orderRs = orderStmt.executeQuery()) {
                    if (orderRs.next()) {
                        Assert.assertTrue(orderRs.getInt(1) > 0, "Order with ID " + orderId + " does not exist.");
                    }
                }
            }
            log.info("Order with ID " + orderId + " exsists in orders table");
            // Check for order item details existence
            try (PreparedStatement itemStmt = conn.prepareStatement(orderItemDetailsExistSQL)) {
                itemStmt.setInt(1, orderId);
                try (ResultSet itemRs = itemStmt.executeQuery()) {
                    if (itemRs.next()) {
                        Assert.assertTrue(itemRs.getInt(1) > 0,
                                "Order item details for Order ID " + orderId + " do not exist.");
                    }
                }
            }
            log.info("Order with ID " + orderId + " exsists in order_item_details table");
            log.info("Test Case `checkDbForOrder` success");
        } catch (SQLException e) {
            log.error("Test Case `checkDbForOrder` failure ... Error while querying");
            Assert.fail("SQLException occurred: " + e.getMessage());
        } catch (AssertionError e) {
            log.error("Test Case `checkDbForOrder` failure ... Assertion error- " + e.getMessage());
            throw e;
        }
    }
}
