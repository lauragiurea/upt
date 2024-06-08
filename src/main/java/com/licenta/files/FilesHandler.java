package com.licenta.files;

import com.licenta.db.DbConnectionHandler;
import com.licenta.session.Session;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FilesHandler {

    public static boolean isPvUploaded(int studentId) {
        return getPV(studentId) != null;
    }

    public static boolean isAppendixUploaded(int studentId) {
        return getAppendix(studentId) != null;
    }

    public static void uploadProject(byte[] fileBytes, Session session, String fileName) throws Exception {
        uploadFile(fileBytes, session.getUserId(), fileName);
        insertProjectFile(session.getUserId(), fileName);
    }

    public static void uploadPresentation(byte[] fileBytes, Session session, String fileName) throws Exception {
        uploadFile(fileBytes, session.getUserId(), fileName);
        insertPresentationFile(session.getUserId(), fileName);
    }

    public static void uploadAppendix(byte[] fileBytes, int userId, String fileName) throws Exception {
        uploadFile(fileBytes, userId, fileName);
        insertAppendixFile(userId, fileName);
    }

    public static void uploadPV(byte[] fileBytes, int userId, String fileName) throws Exception {
        uploadFile(fileBytes, userId, fileName);
        insertPVFile(userId, fileName);
    }

    private static void uploadFile(byte[] fileBytes, int userId, String fileName) throws Exception {
        String directoryPath = "files/" + userId;
        new File(directoryPath).mkdirs();
        try (FileOutputStream stream = new FileOutputStream(directoryPath + "/" + fileName)) {
            stream.write(fileBytes);
            stream.close();
        }
    }

    private static final String SQL_UPLOAD_PROJECT = """
            INSERT INTO upt.projectFiles (idStud, fileName)
            VALUES (?,?) as newValues
            ON DUPLICATE KEY UPDATE
            fileName = newValues.fileName;
            """;
    private static void insertProjectFile(int userId, String fileName) {
        try (Connection connection = DbConnectionHandler.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_UPLOAD_PROJECT);
            statement.setInt(1, userId);
            statement.setString(2, fileName);
            statement.execute();
            connection.close();
        } catch (Exception e) {
        }
    }

    private static final String SQL_UPLOAD_PRESENTATION = """
            INSERT INTO upt.presentationFiles (idStud, fileName)
            VALUES (?,?) as newValues
            ON DUPLICATE KEY UPDATE
            fileName = newValues.fileName;
            """;
    private static void insertPresentationFile(int userId, String fileName) {
        try (Connection connection = DbConnectionHandler.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_UPLOAD_PRESENTATION);
            statement.setInt(1, userId);
            statement.setString(2, fileName);
            statement.execute();
            connection.close();
        } catch (Exception e) {
        }
    }

    private static final String SQL_UPLOAD_APPENDIX = """
            INSERT INTO upt.appendixFiles (idStud, fileName)
            VALUES (?,?) as newValues
            ON DUPLICATE KEY UPDATE
            fileName = newValues.fileName;
            """;
    private static void insertAppendixFile(int userId, String fileName) {
        try (Connection connection = DbConnectionHandler.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_UPLOAD_APPENDIX);
            statement.setInt(1, userId);
            statement.setString(2, fileName);
            statement.execute();
            connection.close();
        } catch (Exception e) {
        }
    }

    private static final String SQL_UPLOAD_PV = """
            INSERT INTO upt.pvFiles (idStud, fileName)
            VALUES (?,?) as newValues
            ON DUPLICATE KEY UPDATE
            fileName = newValues.fileName;
            """;
    private static void insertPVFile(int userId, String fileName) {
        try (Connection connection = DbConnectionHandler.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_UPLOAD_PV);
            statement.setInt(1, userId);
            statement.setString(2, fileName);
            statement.execute();
            connection.close();
        } catch (Exception e) {
        }
    }

    public static File getProject(int userId) {
        String projectName = getProjectName(userId);
        if (projectName == null) {
            return null;
        }
        return getFile(userId, projectName);
    }

    public static File getPresentation(int userId) {
        String presentationName = getPresentationName(userId);
        if (presentationName == null) {
            return null;
        }
        return getFile(userId, presentationName);
    }

    public static File getAppendix(int userId) {
        String appendixName = getAppendixName(userId);
        if (appendixName == null) {
            return null;
        }
        return getFile(userId, appendixName);
    }

    public static File getPV(int userId) {
        String pvName = getPvName(userId);
        if (pvName == null) {
            return null;
        }
        return getFile(userId, pvName);
    }

    private static File getFile(int userId, String fileName) {
        File[] allFiles = new File("files/" + userId).listFiles();
        if (allFiles != null) {
            for (File file : allFiles) {
                if(file.getName().equalsIgnoreCase(fileName)) {
                    return file;
                }
            }
        }
        return null;
    }

    private static final String SQL_GET_PROJECT = """
            SELECT fileName FROM upt.projectFiles
            WHERE idStud = ?;
            """;
    private static String getProjectName(int userId) {
        try (Connection connection = DbConnectionHandler.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_GET_PROJECT);
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            String fileName = null;
            while (rs.next()) {
                fileName = rs.getString("fileName");
            }
            connection.close();
            return fileName;
        } catch (Exception e) {
            return null;
        }
    }

    private static final String SQL_GET_PRESENTATION = """
            SELECT fileName FROM upt.presentationFiles
            WHERE idStud = ?;
            """;
    private static String getPresentationName(int userId) {
        try (Connection connection = DbConnectionHandler.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_GET_PRESENTATION);
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            String fileName = null;
            while (rs.next()) {
                fileName = rs.getString("fileName");
            }
            connection.close();
            return fileName;
        } catch (Exception e) {
            return null;
        }
    }

    private static final String SQL_GET_APPENDIX = """
            SELECT fileName FROM upt.appendixFiles
            WHERE idStud = ?;
            """;
    private static String getAppendixName(int userId) {
        try (Connection connection = DbConnectionHandler.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_GET_APPENDIX);
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            String fileName = null;
            while (rs.next()) {
                fileName = rs.getString("fileName");
            }
            connection.close();
            return fileName;
        } catch (Exception e) {
            return null;
        }
    }

    private static final String SQL_GET_PV = """
            SELECT fileName FROM upt.pvFiles
            WHERE idStud = ?;
            """;
    private static String getPvName(int userId) {
        try (Connection connection = DbConnectionHandler.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_GET_PV);
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            String fileName = null;
            while (rs.next()) {
                fileName = rs.getString("fileName");
            }
            connection.close();
            return fileName;
        } catch (Exception e) {
            return null;
        }
    }
}
