package com.licenta.announcements;

import com.licenta.db.DbConnectionHandler;
import com.licenta.session.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UploadConfigHandler {

    private static final String SQL_ADD_CONFIG = """
            INSERT INTO upt.uploadConfig (uploadType, message, startDate, endDate)
            VALUES (?, ?,?,?) as newValues
            ON DUPLICATE KEY UPDATE
            message = newValues.message,
            startDate = newValues.startDate,
            endDate = newValues.endDate;
            """;
    public static void configProjectUpload(UploadConfigData data) {
        try (Connection connection = DbConnectionHandler.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_ADD_CONFIG);
            statement.setInt(1, 0);
            statement.setString(2, data.message);
            statement.setInt(3, data.startDate);
            statement.setInt(4, data.endDate);
            statement.execute();
            connection.close();
        } catch (Exception e) {

        }
    }

    public static void configPresentationUpload(UploadConfigData data) {
        try (Connection connection = DbConnectionHandler.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_ADD_CONFIG);
            statement.setInt(1, 1);
            statement.setString(2, data.message);
            statement.setInt(3, data.startDate);
            statement.setInt(4, data.endDate);
            statement.execute();
            connection.close();
        } catch (Exception e) {

        }
    }

    private static final String SQ_GET_CONFIG = """
    SELECT * FROM upt.uploadConfig
    WHERE uploadType = ?;
    """;
    public static UploadConfigData getProjectUploadConfig() {
        try (Connection connection = DbConnectionHandler.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQ_GET_CONFIG);
            statement.setInt(1, 0);
            ResultSet rs = statement.executeQuery();
            UploadConfigData data = new UploadConfigData();
            if (rs.next()) {
                data = getConfigData(rs);
            }
            connection.close();
            return data;
        } catch (Exception e) {
            return new UploadConfigData();
        }
    }

    public static UploadConfigData getPresentationUploadConfig() {
        try (Connection connection = DbConnectionHandler.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQ_GET_CONFIG);
            statement.setInt(1, 1);
            ResultSet rs = statement.executeQuery();
            UploadConfigData data = new UploadConfigData();
            if (rs.next()) {
                data = getConfigData(rs);
            }
            connection.close();
            return data;
        } catch (Exception e) {
            return new UploadConfigData();
        }
    }

    private static UploadConfigData getConfigData(ResultSet rs) throws SQLException {
        UploadConfigData data = new UploadConfigData();
        data.message = rs.getString("message");
        data.startDate = rs.getInt("startDate");
        data.endDate = rs.getInt("endDate");
        return data;
    }
}
