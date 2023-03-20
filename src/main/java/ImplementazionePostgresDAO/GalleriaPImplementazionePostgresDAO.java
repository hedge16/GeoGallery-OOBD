package ImplementazionePostgresDAO;

import DAO.Galleria_personaleDAO;
import Database.ConnessioneDatabase;

import java.sql.*;

/**
 * The type Galleria p implementazione postgres dao.
 */
public class GalleriaPImplementazionePostgresDAO implements Galleria_personaleDAO {

    // Variabile che rappresenta la connessione al database
    private Connection connection;

    /**
     * Costruttore che inizializza la connessione al database.
     */
    public GalleriaPImplementazionePostgresDAO () {
        try {
            // Crea una nuova connessione utilizzando il metodo getInstance della classe ConnessioneDatabase
            connection = ConnessioneDatabase.getInstance().connection;
        } catch (SQLException e) {
            // In caso di errore durante la creazione della connessione, stampa l'errore
            e.printStackTrace();
        }
    }

    /**
     * Questo metodo restituisce il codice di una galleria personale dato l'username dell'utente proprietario.
     *
     * @param username l'username dell'utente proprietario della galleria personale
     * @return il codice della galleria personale, o -1 se non è stata trovata alcuna galleria personale associata all'utente
     * @throws SQLException se si verifica un errore durante l'esecuzione della query
     */
    @Override
    public int getCodGDB(String username) throws SQLException {
        // Inizializza il codice della galleria personale a -1
        int codG = -1;
        // Prepara la query di selezione
        PreparedStatement ps = connection.prepareStatement("SELECT codgalleria FROM galleria_schema.galleria_personale WHERE proprietario = ?;");
        // Sostituisce il primo placeholder con l'username passato come argomento
        ps.setString(1, username);
        // Esegue la query di selezione
        ResultSet rs = ps.executeQuery();
        // Itera sui risultati della query
        while (rs.next()){
            // Assegna il valore del campo "codg" del risultato corrente al codice della galleria personale
            codG = rs.getInt("codgalleria");
        }
        rs.close();
        connection.close();
        // Restituisce il codice della galleria personale
        return codG;
    }
}

