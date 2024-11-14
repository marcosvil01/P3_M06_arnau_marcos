package db;

import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.XMLDBException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DBConnection {
    private static String URI;
    private static String COLLECTION_PATH;
    private static String USERNAME;
    private static String PASSWORD;

    static {
        try (InputStream input = DBConnection.class.getClassLoader().getResourceAsStream(".env")) {
            Properties props = new Properties();
            if (input == null) {
                throw new IOException("Unable to find .env file");
            }
            props.load(input);
            URI = props.getProperty("URI");
            COLLECTION_PATH = props.getProperty("COLLECTION_PATH");
            USERNAME = props.getProperty("USERNAME");
            PASSWORD = props.getProperty("PASSWORD");
        } catch (IOException e) {
            System.err.println("Error loading .env file: " + e.getMessage());
        }
    }

    public static Collection getCollection() throws Exception {
        try {
            // Registro de la base de datos eXistDB
            Class<?> cl = Class.forName("org.exist.xmldb.DatabaseImpl");
            Database database = (Database) cl.getDeclaredConstructor().newInstance();
            DatabaseManager.registerDatabase(database);

            // Conexión a la colección
            Collection col = DatabaseManager.getCollection(URI + COLLECTION_PATH, USERNAME, PASSWORD);
            if (col == null) {
                System.err.println("Error: Unable to access collection at path " + COLLECTION_PATH + ". Please verify that the collection exists and the URI is correct.");
                return null;
            }
            System.out.println("Successfully connected to collection: " + COLLECTION_PATH);
            return col;
        } catch (XMLDBException e) {
            System.err.println("XMLDBException: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.err.println("General error during connection setup: " + e.getMessage());
            throw e;
        }
    }

    public static void closeCollection(Collection col) {
        if (col != null) {
            try {
                col.close();
                System.out.println("Collection closed successfully.");
            } catch (XMLDBException e) {
                System.err.println("Error closing collection: " + e.getMessage());
            }
        }
    }
}
