package server.managers;

public class DatabaseCommandsDML {
    public static final String addUser = """
            INSERT INTO users(login, password) VALUES (?, ?);""";

    public static final String getUser = """
            SELECT * FROM users WHERE (login = ?);""";

    public static final String addObject = """
            INSERT INTO organization(name, cord_x, cord_y, creation_date, annual_turnover, employees_count, organization_type, street, zip_code, owner_login)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            RETURNING id;
            """;

    public static final String getAllObjects = """
            SELECT * FROM organization;
            """;

    public static final String getObject = """
            SELECT * FROM organization WHERE (owner_login = ?) AND (id = ?);
            """;

    public static final String deleteUserOwnedObjects = """
            DELETE FROM organization WHERE (owner_login = ?) AND (id = ?) RETURNING id;
            """;

    public static final String deleteUserObject = """
            DELETE FROM organization WHERE (owner_login = ?) AND (id = ?) RETURNING id;
            """;

    public static final String updateUserObject = """
            UPDATE organization
            SET (name, cord_x, cord_y, creation_date, annual_turnover, employees_count, organization_type, street, zip_code)
             = (?, ?, ?, ?, ?, ?, ?, ?, ?)
            WHERE (id = ?) AND (owner_login = ?)
            RETURNING id;
            """;
}