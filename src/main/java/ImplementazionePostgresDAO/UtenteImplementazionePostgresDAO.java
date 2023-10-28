package ImplementazionePostgresDAO;

import DAO.UtenteDAO;
import Database.ConnessioneDatabase;
import Model.Utente;
import org.postgresql.util.PSQLException;
import org.postgresql.util.ServerErrorMessage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The type Utente implementazione postgres dao.
 */
public class UtenteImplementazionePostgresDAO implements UtenteDAO {

    // Variabile che rappresenta la connessione al database
    private Connection connection;

    /**
     * Costruttore che inizializza la connessione al database.
     */
    public UtenteImplementazionePostgresDAO() {
        try {
            // Crea una nuova connessione utilizzando il metodo getInstance della classe ConnessioneDatabase
            connection = ConnessioneDatabase.getInstance().connection;
        } catch (SQLException e) {
            // In caso di errore durante la creazione della connessione, stampa l'errore
            e.printStackTrace();
        }
    }

    /**
     * Questo metodo aggiunge un nuovo utente nel database.
     *
     * @param nome il nome dell'utente
     * @param cognome il cognome dell'utente
     * @param username l'username dell'utente
     * @param password la password dell'utente
     * @param email l'email dell'utente
     * @param d la data di registrazione dell'utente
     * @throws PSQLException se si verifica un errore durante l'inserimento dei dati
     *      */
    @Override

    public void aggiungiUtente(String nome, String cognome, String username, String password, String email, Date d) throws PSQLException {
        try {
            // Prepara la query di inserimento
            PreparedStatement aggiungiUser = connection.prepareStatement("INSERT INTO galleria_schema.utente VALUES(?,?,?,?,?,?);");
            // Imposta i parametri della query
            aggiungiUser.setString(1, nome);
            aggiungiUser.setString(2, cognome);
            aggiungiUser.setString(3, username);
            aggiungiUser.setString(4, password);
            aggiungiUser.setString(5, email);
            aggiungiUser.setDate(6, new java.sql.Date(d.getTime()));
            // Esegue la query di inserimento
            aggiungiUser.executeUpdate();
            // Creazione della galleria personale dell'utente sul DB
            creaGallUt(username);
            // Chiude la connnsesione al database
            connection.close();
        } catch (PSQLException e) {
            // In caso di errore durante l'inserimento, crea un oggetto ServerErrorMessage con un messaggio di errore
            ServerErrorMessage errorMessage = new ServerErrorMessage("Errore nell'inserimento dei dati");
            // Solleva un'eccezione PSQLException con il messaggio di errore
            throw new PSQLException(errorMessage);
        } catch (SQLException s) {
            // In caso di altri errori, stampa l'errore
            s.printStackTrace();
        }
    }

    /**
     * Questo metodo restituisce una lista di oggetti Utente, recuperando gli utenti dal database.
     *
     * @return la lista di oggetti Utente
     * @throws SQLException se si verifica un errore durante l'esecuzione della query
     */
    public ArrayList<Utente> leggiUtenti() throws SQLException {
        // Crea una lista di oggetti Utente vuota
        ArrayList<Utente> listaUtenti = new ArrayList<>();
        try {
            // Prepara la query di selezione
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM galleria_schema.utente");
            // Esegue la query di selezione
            ResultSet rs = stmt.executeQuery();
            // Itera sui risultati della query
            while (rs.next()) {
                // Crea un nuovo oggetto Utente con i valori del risultato corrente
                Utente ut = new Utente(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getDate(6));
                // Aggiunge l'oggetto Utente alla lista
                listaUtenti.add(ut);
            }
            // Chiude il ResultSet e la connessione al database
            rs.close();
            connection.close();
            // Restituisce la lista di oggetti Utente
            return listaUtenti;
        } catch (SQLException e) {
            // In caso di errore durante l'esecuzione della query, stampa l'errore
            e.printStackTrace();
            // Solleva un'eccezione con un messaggio di errore
            throw new SQLException("Errore nella query col DB");
        }
    }

    @Override
    public void creaGallUt(String username) throws SQLException {
        try{
            PreparedStatement ps = connection.prepareStatement("INSERT INTO galleria_schema.galleria_personale VALUES (DEFAULT,?);");
            ps.setString(1, username);
            ps.executeUpdate();
            connection.close();
        }catch(SQLException s){
            throw new SQLException();

        }
    }

}
