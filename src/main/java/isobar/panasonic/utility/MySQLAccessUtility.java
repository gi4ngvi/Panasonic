package isobar.panasonic.utility;

import com.relevantcodes.extentreports.LogStatus;
import org.json.JSONObject;

import java.sql.*;

public class MySQLAccessUtility {
    static Connection connect;
    static Statement statement;
    static ResultSet resultSet;
    static String username, password, dbname, host;

    private static void initConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            JSONObject jsonConfig = DataTest.initConfig();
            username = jsonConfig.getJSONObject("db").getString("username");
            host = jsonConfig.getJSONObject("db").getString("host");
            dbname = jsonConfig.getJSONObject("db").getString("dbname");
            password = jsonConfig.getJSONObject("db").getString("password");
            connect = DriverManager.getConnection("jdbc:mysql://" + host + "/" + dbname, username, password);
            statement = connect.createStatement();
        } catch (Exception e) {
            ReportUtility.getInstance().log(LogStatus.ERROR, e.getMessage());
        }
    }

    public static void deleteInvitationByEmail(String email) {
        initConnection();
        try {
            String sql = String.format("DELETE FROM `magento_invitation` WHERE email='%s'", email);
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    private static void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {

        }
    }
}
