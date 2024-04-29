package com.licenta.announcements;

import com.licenta.db.DbConnectionHandler;
import com.licenta.exam.grading.ExamGradeResponseData;
import com.licenta.exam.grading.ExamGradeStatus;
import com.licenta.session.Session;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class AnnouncementsHandler {

    private static final String SQL_ADD_ANNOUNCEMENT = """
            INSERT INTO upt.announcements (userId, message)
            VALUES (?,?);
            """;
    public static void addAnnouncement(Session session, AddAnnouncementRequestData data) {
        try (Connection connection = DbConnectionHandler.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_ADD_ANNOUNCEMENT);
            statement.setInt(1, session.getUserId());
            statement.setString(2, data.message.trim());
            statement.execute();
            connection.close();
        } catch (Exception e) {
        }
    }

    private static final String SQL_GET_ANNOUNCEMENTS = """
            SELECT lastName, firstName, message, createdAt FROM upt.announcements an
            JOIN upt.accounts ac ON an.userId = ac.id;
            """;
    public static AnnouncementsResponseData getAnnouncements() {
        try (Connection connection = DbConnectionHandler.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_GET_ANNOUNCEMENTS);
            ResultSet rs = statement.executeQuery();
            List<AnnouncementData> announcements = new ArrayList<>();
            while (rs.next()) {
                announcements.add(getAnnouncementData(rs));
            }
            connection.close();
            return new AnnouncementsResponseData(announcements);
        } catch (Exception e) {
            return new AnnouncementsResponseData();
        }
    }

    private static AnnouncementData getAnnouncementData(ResultSet rs) throws SQLException {
        AnnouncementData announcement = new AnnouncementData();
        announcement.author = rs.getString("lastName") + " " + rs.getString("firstName");
        announcement.message = rs.getString("message");
        Timestamp t = rs.getTimestamp("createdAt");
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        announcement.time = df.format(t);
        return announcement;
    }
}
