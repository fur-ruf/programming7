package server.managers;

public class DatabaseCommandsDDL {
//    DROP TABLE IF EXISTS users;
//    DROP TABLE IF EXISTS organization;
//    DROP TYPE IF EXISTS ORGANIZATION_TYPE;
    public static final String allTablesCreation = """
            CREATE TYPE ORGANIZATION_TYPE AS ENUM(
                'COMMERCIAL',
                'PUBLIC',
                'GOVERNMENT',
                'TRUST',
                'PRIVATE_LIMITED_COMPANY'
            );    
            CREATE TABLE IF NOT EXISTS organization (
                id SERIAL PRIMARY KEY,
                name TEXT NOT NULL,
                cord_x NUMERIC NOT NULL,
                cord_y NUMERIC NOT NULL,
                creation_date DATE NOT NULL,
                annual_turnover FLOAT NOT NULL,
                employees_count BIGINT NOT NULL,
                organization_type ORGANIZATION_TYPE,
                street TEXT NOT NULL,
                zip_code TEXT NOT NULL,
                owner_login TEXT NOT NULL
            );
            CREATE TABLE IF NOT EXISTS users (
                id SERIAL PRIMARY KEY,
                login TEXT,
                password TEXT
            );
            """;
}
