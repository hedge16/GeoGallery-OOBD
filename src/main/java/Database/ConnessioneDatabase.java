package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The type Connessione database.
 */
public class ConnessioneDatabase {

    // ATTRIBUTI
    private static ConnessioneDatabase instance;
    /**
     * la connessione al database
     */
    public Connection connection = null;
    private String nome = "postgres";
    private String password = "password";
    private String url = "jdbc:postgresql://localhost:5432/Galleria";
    private String driver = "org.postgresql.Driver";

    /**
     * Costruttore della classe ConnessioneDatabase
     *
     * @throws SQLException l'eccezione sql che viene lanciata nel caso in cui la connessione al database non vada a buon fine
     */
    private ConnessioneDatabase() throws SQLException {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, nome, password);
        } catch (ClassNotFoundException ex) {
            System.out.println("Database Connection Creation Failed : " + ex.getMessage());
            ex.printStackTrace();
        }

    }


    /**
     * Questo metodo restituisce l'istanza della classe ConnessioneDatabase
     *
     * @return l 'istanza della classe ConnessioneDatabase
     * @throws SQLException l'eccezione sql che viene lanciata nel caso in cui la connessione al database non vada a buon fine
     */
    public static ConnessioneDatabase getInstance() throws SQLException {
        if (instance == null) {
            instance = new ConnessioneDatabase();
        } else if (instance.connection.isClosed()) {
            instance = new ConnessioneDatabase();
        }
        return instance;
    }
}





