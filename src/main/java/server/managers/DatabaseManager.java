package server.managers;

import common.interaction.User;
import common.organization.Address;
import common.organization.Coordinates;
import common.organization.Organization;
import common.organization.OrganizationType;
import common.tools.GettingProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class DatabaseManager {
    private Connection connection;
    private MessageDigest md;

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789<>?:@{!$%^&*()_+£$";
    private GettingProperty gettingProperty = new GettingProperty();
    private String HASHING_ALGORITHM = gettingProperty.getProperty("HASHING_ALGORITHM");
    private String DATABASE_URL = gettingProperty.getProperty("DATABASE_URL");
    private String DATABASE_URL_HELIOS = gettingProperty.getProperty("DATABASE_URL_HELIOS");
    private String USER = gettingProperty.getProperty("USER");
    private String PASSWORD = gettingProperty.getProperty("PASSWORD");
    private static final String PEPPER = "[g$J*(l;";
    private static final Logger databaseLogger = LogManager.getLogger(DatabaseManager.class);

    public DatabaseManager(){
        try {
            md = MessageDigest.getInstance(HASHING_ALGORITHM);
            this.connect();
//            this.createMainBase();
        }
//        catch (SQLException e) {
//            databaseLogger.warn("Ошибка при исполнени изначального запроса либо таблицы уже созданы");
//        }
        catch (NoSuchAlgorithmException e) {
            databaseLogger.fatal("Такого алгоритма нет!");
        }
    }

    public void connect(){
        try {
            connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
            databaseLogger.info("Успешно подключен к базе данных");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            try{
                connection = DriverManager.getConnection(DATABASE_URL_HELIOS, USER, PASSWORD);
            } catch (SQLException ex) {
                System.out.println(e.getMessage());
                databaseLogger.fatal("Невозможно подключиться к базе данных");
                databaseLogger.debug(e);
                databaseLogger.debug(ex);
                System.exit(1);
            }
        }
    }

    public void createMainBase() throws SQLException {
        connection
                .prepareStatement(DatabaseCommandsDDL.allTablesCreation)
                .execute();
    }

    public void addUser(User user) throws SQLException {
        String login = user.name();
//        String salt = this.generateRandomString();
        String pass = PEPPER + user.password();

        PreparedStatement ps = connection.prepareStatement(DatabaseCommandsDML.addUser);
        if (this.checkExistUser(login)) throw new SQLException();
        ps.setString(1, login);
        ps.setString(2, this.getSHA1Hash(pass));
        ps.execute();
        databaseLogger.info("Добавлен юзер " + user);
    }

    public boolean confirmUser(User inputUser){
        try {
            String login = inputUser.name();
            PreparedStatement getUser = connection.prepareStatement(DatabaseCommandsDML.getUser);
            getUser.setString(1, login);
            ResultSet resultSet = getUser.executeQuery();
            if(resultSet.next()) {
//                String salt = resultSet.getString("salt");
                String toCheckPass = this.getSHA1Hash(PEPPER + inputUser.password());
                return toCheckPass.equals(resultSet.getString("password"));
            }
            else {
                return false;
            }
        } catch (SQLException e) {
            databaseLogger.fatal("Неверная команда sql!");
            databaseLogger.debug(e);
            return false;
        }
    }

    public boolean checkExistUser(String login) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(DatabaseCommandsDML.getUser);
        ps.setString(1, login);
        ResultSet resultSet = ps.executeQuery();
        return resultSet.next();
    }

    public int addObject(Organization organization, User user){
        try {
            PreparedStatement ps = connection.prepareStatement(DatabaseCommandsDML.addObject);
            ps.setString(1, organization.getName());
            ps.setDouble(2, organization.getCoordinates().getX());
            ps.setDouble(3, organization.getCoordinates().getY());
            ps.setDate(4, Date.valueOf(organization.getCreationDate()));
            ps.setFloat(5, organization.getAnnualTurnover());
            ps.setLong(6, organization.getEmployeesCount());
            ps.setObject(8, organization.getType(), Types.OTHER);
            ps.setString(9, organization.getOfficialAddress().getStreet());
            ps.setString(10, organization.getOfficialAddress().getZipCode());
            ps.setString(11, user.name());
            ResultSet resultSet = ps.executeQuery();

            if (!resultSet.next()) {
                databaseLogger.info("Объект не добавлен в таблицу");
                return -1;
            }
            databaseLogger.info("Объект добавлен в таблицу");
            return resultSet.getInt(1);
        } catch (SQLException e) {
            databaseLogger.info("Объект не добавлен в таблицу");
            databaseLogger.debug(e);
            return -1;
        }
    }

    public boolean updateObject(int id, Organization organization, User user){
        try {
            PreparedStatement ps = connection.prepareStatement(DatabaseCommandsDML.addObject);
            ps.setString(1, organization.getName());
            ps.setDouble(2, organization.getCoordinates().getX());
            ps.setDouble(3, organization.getCoordinates().getY());
            ps.setDate(4, Date.valueOf(organization.getCreationDate()));
            ps.setFloat(5, organization.getAnnualTurnover());
            ps.setLong(6, organization.getEmployeesCount());
            ps.setObject(7, organization.getType(), Types.OTHER);
            ps.setString(8, organization.getOfficialAddress().getStreet());
            ps.setString(9, organization.getOfficialAddress().getZipCode());
            ps.setString(10, user.name());
            ResultSet resultSet = ps.executeQuery();
            System.out.println(resultSet);
            return resultSet.next();
        } catch (SQLException e) {
            databaseLogger.debug(e);
            return false;
        }
    }

    public boolean deleteObject(int id, User user){
        try {
            PreparedStatement ps = connection.prepareStatement(DatabaseCommandsDML.deleteUserObject);
            ps.setString(1, user.name());
            ps.setInt(2, id);
            ResultSet resultSet = ps.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            databaseLogger.error("Объект удалить не удалось");
            databaseLogger.debug(e);
            return false;
        }
    }

    public boolean deleteAllObjects(User user, ArrayList<Integer> ids){
        try {
            for (Integer id : ids) {
                PreparedStatement ps = connection.prepareStatement(DatabaseCommandsDML.deleteUserOwnedObjects);
                ps.setString(1, user.name());
                ps.setInt(2, id);
                ResultSet resultSet = ps.executeQuery();
            }
            databaseLogger.warn("Удалены все строки таблицы organization принадлежащие " + user.name());
            return true;
        } catch (SQLException e) {
            databaseLogger.error("Удалить строки таблицы organization не удалось!");
            databaseLogger.debug(e);
            return false;
        }
    }

    public HashSet<Organization> loadCollection(){
        try {
            PreparedStatement ps = connection.prepareStatement(DatabaseCommandsDML.getAllObjects);
            ResultSet resultSet = ps.executeQuery();
            HashSet<Organization> collection = new HashSet<>();
            while (resultSet.next()){
                collection.add(new Organization(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        new Coordinates(
                                resultSet.getFloat("cord_x"),
                                resultSet.getDouble("cord_y")
                        ),
                        resultSet.getDate("creation_date").toLocalDate(),
                        resultSet.getFloat("annual_turnover"),
                        resultSet.getLong("employees_count"),
                        OrganizationType.valueOf(resultSet.getString("organization_type")),
                        new Address(
                                resultSet.getString("street"),
                                resultSet.getString("zip_code")
                        ),
                        resultSet.getString("owner_login")
                ));
            }
            databaseLogger.info("Коллекция успешно загружена из таблицы");
            return collection;
        } catch (SQLException e) {
            databaseLogger.warn("Коллекция пуста либо возникла ошибка при исполнении запроса");
            return new HashSet<>();
        }
    }

    private String generateRandomString() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(6);
        for (int i = 0; i < 6; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }

    private String getSHA1Hash(String input){
        byte[] inputBytes = input.getBytes();
        md.update(inputBytes);
        byte[] hashBytes = md.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}