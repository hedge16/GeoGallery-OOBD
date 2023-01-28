package ImplementazionePostgresDAO;

import DAO.FotoDAO;
import Database.ConnessioneDatabase;
import org.postgresql.util.PSQLException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class FotoImplementazionePostgresDAO implements FotoDAO {

    // Variabile che rappresenta la connessione al database
    private Connection connection;

    /**
     * Questo metodo recupera le foto dal database in base all'autore specificato.
     *
     * @param username l'username dell'autore delle foto da recuperare
     * @return una lista di oggetti ImageIcon rappresentanti le foto recuperate dal database
     * @throws SQLException se si verifica un errore durante l'esecuzione della query o durante la lettura delle foto
     */
    @Override
    public ArrayList<ImageIcon> recuperaFotoDB(String username) throws SQLException {

        // Crea una lista vuota di oggetti ImageIcon
        ArrayList<ImageIcon> miniature = new ArrayList<>();

        try {
            // Prepara la query per selezionare le foto con l'autore specificato
            PreparedStatement ps = connection.prepareStatement("SELECT img FROM galleria_schema.foto WHERE autorescatto = ?;");
            ps.setString(1, username);
            // Esegue la query
            ResultSet rs = ps.executeQuery();
            // Itera sui risultati della query
            while (rs.next()) {

                // Recupera la foto dal risultato della query come array di byte
                byte[] barr = rs.getBytes(1);
                // Crea un oggetto InputStream a partire dall'array di byte
                ByteArrayInputStream bis = new ByteArrayInputStream(barr);
                // Crea un oggetto ImageIcon a partire dallo stream di byte
                ImageIcon immagine = new ImageIcon(ImageIO.read(bis));
                bis.close();
                // Aggiunge l'immagine alla lista
                miniature.add(immagine);
            }
            rs.close();
            connection.close();
        } catch (Exception e) {
            // In caso di errore, solleva un'eccezione SQLException
            throw new SQLException();
        }

        // Restituisce la lista di oggetti ImageIcon
        return miniature;
    }

    /**
     * Questo metodo inserisce una nuova foto nel database.
     *
     * @param privata indica se la foto è privata o pubblica
     * @param rimossa indica se la foto è stata rimossa
     * @param dataScatto la data di scatto della foto
     * @param codgalleriap il codice della galleria a cui appartiene la foto
     * @param autore l'username dell'autore della foto
     * @param codDispositivo il codice del dispositivo utilizzato per scattare la foto
     * @param percorsoFoto il percorso del file che contiene la foto
     * @throws SQLException se si verifica un errore durante l'esecuzione della query
     * @throws FileNotFoundException se il file specificato dal percorso non esiste o non può essere aperto
     */
    @Override
    public int inserisciFotoDB(boolean privata, boolean rimossa, Date dataScatto, int codgalleriap, String autore, int codDispositivo, String percorsoFoto, int codLuogo) throws SQLException, FileNotFoundException {
        int codFoto = -1;
        try {

            // Apre il file specificato dal percorso
            File file = new File(percorsoFoto);
            // Crea un oggetto InputStream a partire dal file
            FileInputStream fis = new FileInputStream(file);
            // Prepara la query di inserimento
            PreparedStatement ps = connection.prepareStatement("INSERT INTO galleria_schema.foto VALUES (DEFAULT,?, ?, ?, ?, ?, ?, ?, ?);");
            // Imposta i valori dei parametri della query
            ps.setBoolean(1, privata);
            ps.setBoolean(2, rimossa);
            java.sql.Date sqlDate = new java.sql.Date(dataScatto.getTime());
            ps.setDate(3, sqlDate);
            ps.setInt(4, codgalleriap);
            ps.setString(5, autore);
            ps.setInt(6, codDispositivo);
            ps.setBinaryStream(7, fis, (int) file.length());
            ps.setInt(8, codLuogo);
            // Esegue la query di inserimento
            ps.executeUpdate();


            // Esegue la query per recuperare il valore attuale della sequenza
            ps = connection.prepareStatement("SELECT codFoto FROM galleria_schema.foto ORDER BY codFoto DESC LIMIT 1;");
            ResultSet rs = ps.executeQuery();

            // Recupera il valore della sequenza
            if (rs.next()) {
                codFoto = rs.getInt(1);
            }



            // Chiude lo stream e la connessione al database
            fis.close();
            connection.close();
            return codFoto;

        } catch(SQLException s) {
            // In caso di errore durante l'esecuzione della query, solleva un'eccezione SQLException
            s.printStackTrace();
            throw new SQLException();
        } catch (IOException f) {
            // In caso di errore durante l'apertura del file, solleva un'eccezione FileNotFoundException
            f.printStackTrace();
            throw new FileNotFoundException();
        }

    }

    /**
     * Costruttore che inizializza la connessione al database.
     */
    public FotoImplementazionePostgresDAO() {
        try {
            // Crea una nuova connessione utilizzando il metodo getInstance della classe ConnessioneDatabase
            connection = ConnessioneDatabase.getInstance().connection;
        } catch (SQLException e) {
            // In caso di errore durante la creazione della connessione, stampa l'errore
            e.printStackTrace();
        }
    }
}

