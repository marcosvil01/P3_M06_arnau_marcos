package db;

import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
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
        } catch (Exception e) {
            System.err.println("Error loading .env file: " + e.getMessage());
        }
    }

    public static Collection getCollection() throws Exception {
        try {
            // Registre de la base de dades eXistDB
            Class<?> cl = Class.forName("org.exist.xmldb.DatabaseImpl");
            DatabaseManager.registerDatabase((org.xmldb.api.base.Database) cl.newInstance());

            // Connexió a la col·lecció
            Collection col = DatabaseManager.getCollection(URI + COLLECTION_PATH, USERNAME, PASSWORD);
            if (col == null) {
                System.out.println("\"Error: Unable to access collection at path \" + COLLECTION_PATH + \". Please verify that the collection exists and the URI is correct.");
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
}
