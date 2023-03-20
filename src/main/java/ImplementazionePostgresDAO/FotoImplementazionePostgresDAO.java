package ImplementazionePostgresDAO;

import DAO.FotoDAO;
import Database.ConnessioneDatabase;
import Model.Dispositivo;
import Model.Foto;
import Model.Luogo;
import Model.SoggettoFoto;
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

/**
 * The type Foto implementazione postgres dao.
 */
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
    public ArrayList<Foto> recuperaFotoDB(String username) throws SQLException {

        // Crea una lista vuota di oggetti ImageIcon
        ArrayList<Foto> photos = new ArrayList<>();

        try {
            // Prepara la query per selezionare le foto con l'autore specificato
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM galleria_schema.foto WHERE autorescatto = ? AND rimossa = false;");
            ps.setString(1, username);
            // Esegue la query
            ResultSet rs = ps.executeQuery();
            // Itera sui risultati della query
            while (rs.next()) {
                // Recupera i dati dal risultato della query
                int codFoto = rs.getInt(1);
                boolean privata = rs.getBoolean(2);
                boolean rimossa = rs.getBoolean(3);
                Date dataScatto = rs.getDate(4);
                int codGalleria = rs.getInt(5);
                String autore = rs.getString(6);
                int codDispositivo = rs.getInt(7);
                byte[] barr = rs.getBytes(8);
                // Crea un oggetto InputStream a partire dall'array di byte
                ByteArrayInputStream bis = new ByteArrayInputStream(barr);
                // Crea un oggetto ImageIcon a partire dallo stream di byte
                ImageIcon immagine = new ImageIcon(ImageIO.read(bis));
                bis.close();
                // Crea un oggetto Foto con i dati recuperati
                Foto foto = new Foto(codFoto, privata, rimossa, dataScatto, codGalleria, autore, codDispositivo, immagine);
                // Aggiunge l'oggetto Foto alla lista
                photos.add(foto);
            }
            rs.close();
            connection.close();
        } catch (Exception e) {
            // In caso di errore, solleva un'eccezione SQLException
            throw new SQLException();
        }

        // Restituisce la lista di oggetti ImageIcon
        return photos;
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
    public Foto inserisciFotoDB(boolean privata, boolean rimossa, Date dataScatto, int codgalleriap, String autore, int codDispositivo, String percorsoFoto) throws SQLException, FileNotFoundException {
        int codFoto = -1;
        Foto foto;
        try {

            // Apre il file specificato dal percorso
            File file = new File(percorsoFoto);
            // Crea un oggetto InputStream a partire dal file
            FileInputStream fis = new FileInputStream(file);
            // Prepara la query di inserimento
            PreparedStatement ps = connection.prepareStatement("INSERT INTO galleria_schema.foto VALUES (DEFAULT,?, ?, ?, ?, ?, ?, ?);");
            // Imposta i valori dei parametri della query
            ps.setBoolean(1, privata);
            ps.setBoolean(2, rimossa);
            java.sql.Date sqlDate = new java.sql.Date(dataScatto.getTime());
            ps.setDate(3, sqlDate);
            ps.setInt(4, codgalleriap);
            ps.setString(5, autore);
            ps.setInt(6, codDispositivo);
            ps.setBinaryStream(7, fis, (int) file.length());
            // Esegue la query di inserimento
            ps.executeUpdate();

            // Esegue la query per recuperare il valore attuale della sequenza
            ps = connection.prepareStatement("SELECT codFoto FROM galleria_schema.foto ORDER BY codFoto DESC LIMIT 1;");
            ResultSet rs = ps.executeQuery();

            // Recupera il codice della foto appena inserita
            if (rs.next()) {
                codFoto = rs.getInt(1);
            }

            foto = new Foto(codFoto, privata, rimossa, dataScatto, codgalleriap, autore, codDispositivo, new ImageIcon(percorsoFoto));
            // Chiude lo stream e la connessione al database
            fis.close();
            rs.close();
            return foto;

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

    @Override
    public void eliminaFotoDB(int codFoto) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("UPDATE galleria_schema.foto SET rimossa = true WHERE codFoto = ?;");
        ps.setInt(1, codFoto);
        ps.executeUpdate();
        connection.close();
    }

    @Override
    public Foto getLastFoto(String username) throws SQLException {
        Foto foto = null;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM galleria_schema.foto ORDER BY codFoto DESC LIMIT 1;");
            ResultSet rs = ps.executeQuery();
            int codFoto;
            boolean privata;
            boolean rimossa;
            Date dataScatto;
            int codGalleria;
            String autore;
            int codDispositivo;
            byte[] barr;
            while (rs.next()) {
                // Recupera i dati dal risultato della query
                codFoto = rs.getInt(1);
                privata = rs.getBoolean(2);
                rimossa = rs.getBoolean(3);
                dataScatto = rs.getDate(4);
                codGalleria = rs.getInt(5);
                autore = rs.getString(6);
                codDispositivo = rs.getInt(7);
                barr = rs.getBytes(8);
                // Crea un oggetto InputStream a partire dall'array di byte
                ByteArrayInputStream bis = new ByteArrayInputStream(barr);
                // Crea un oggetto ImageIcon a partire dallo stream di byte
                ImageIcon immagine = new ImageIcon(ImageIO.read(bis));
                bis.close();

                // Crea un oggetto Foto con i dati recuperati
                foto = new Foto(codFoto, privata, rimossa, dataScatto, codGalleria, autore, codDispositivo, immagine);
            }
            rs.close();
            connection.close();
            return foto;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void setPrivacyDB(int codfoto, boolean state) throws SQLException {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE galleria_schema.foto SET privata = ? WHERE codFoto = ?;");
            ps.setBoolean(1, state);
            ps.setInt(2, codfoto);
            ps.executeUpdate();
            connection.close();
        } catch (SQLException s) {
            s.printStackTrace();
        }
    }

    @Override
    public ArrayList<Foto> ricercaFotoPerLuogo (String nomeLuogo) throws SQLException {
        ArrayList<Foto> photos = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM galleria_schema.foto WHERE codFoto IN (SELECT codFoto FROM galleria_schema.presenzaLuogo WHERE codLuogo = (SELECT codLuogo FROM galleria_schema.luogo WHERE nomeLuogo = ?)) AND privata = false AND rimossa = false;");
            ps.setString(1, nomeLuogo);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int codFoto = rs.getInt(1);
                boolean privata = rs.getBoolean(2);
                boolean rimossa = rs.getBoolean(3);
                Date dataScatto = rs.getDate(4);
                int codGalleria = rs.getInt(5);
                String autore = rs.getString(6);
                int codDispositivo = rs.getInt(7);
                byte[] barr = rs.getBytes(8);

                // Crea un oggetto InputStream a partire dall'array di byte
                ByteArrayInputStream bis = new ByteArrayInputStream(barr);
                // Crea un oggetto ImageIcon a partire dallo stream di byte
                ImageIcon immagine = new ImageIcon(ImageIO.read(bis));
                bis.close();
                Foto f = new Foto(codFoto, privata, rimossa, dataScatto, codGalleria, autore, codDispositivo, immagine);
                photos.add(f);

            }
            rs.close();
            connection.close();
            return photos;
        } catch (Exception s) {
            s.printStackTrace();
            throw new SQLException();
        }

    }

    @Override
    public ArrayList<Foto> ricercaFotoPerLuogo(String nomeLuogo, double latitudine, double longitudine) throws SQLException {
        try {
            PreparedStatement ps;
            if (nomeLuogo.equals("") || nomeLuogo.equals("nome luogo")) {
                ps = connection.prepareStatement("SELECT * FROM galleria_schema.foto WHERE codFoto IN (SELECT codFoto FROM galleria_schema.presenzaLuogo WHERE codLuogo = (SELECT codLuogo FROM galleria_schema.luogo WHERE latitudine = ? AND longitudine = ?)) AND privata = false AND rimossa = false;");
                ps.setDouble(1, latitudine);
                ps.setDouble(2, longitudine);
            } else {
                ps = connection.prepareStatement("SELECT * FROM galleria_schema.foto WHERE codFoto IN (SELECT codFoto FROM galleria_schema.presenzaLuogo WHERE codLuogo = (SELECT codLuogo FROM galleria_schema.luogo WHERE nomeLuogo = ? AND latitudine = ? AND longitudine = ?)) AND privata = false AND rimossa = false;");
                ps.setString(1, nomeLuogo);
                ps.setDouble(2, latitudine);
                ps.setDouble(3, longitudine);
            }
            ResultSet rs = ps.executeQuery();
            ArrayList<Foto> photos = new ArrayList<>();
            while (rs.next()) {
                int codFoto = rs.getInt(1);
                boolean privata = rs.getBoolean(2);
                boolean rimossa = rs.getBoolean(3);
                Date dataScatto = rs.getDate(4);
                int codGalleria = rs.getInt(5);
                String autore = rs.getString(6);
                int codDispositivo = rs.getInt(7);
                byte[] barr = rs.getBytes(8);

                // Crea un oggetto InputStream a partire dall'array di byte
                ByteArrayInputStream bis = new ByteArrayInputStream(barr);
                // Crea un oggetto ImageIcon a partire dallo stream di byte
                ImageIcon immagine = new ImageIcon(ImageIO.read(bis));
                bis.close();
                Foto f = new Foto(codFoto, privata, rimossa, dataScatto, codGalleria, autore, codDispositivo, immagine);
                photos.add(f);
            }
            rs.close();
            connection.close();
            return photos;

        } catch (Exception s) {
            s.printStackTrace();
            throw new SQLException();
        }
    }

    @Override
    public ArrayList<Foto> ricercaFotoPerSoggetto(String categoria, String nome) throws SQLException {
        ArrayList<Foto> photos = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM galleria_schema.foto WHERE codFoto IN (SELECT codFoto FROM galleria_schema.presenzaSoggetto WHERE codSogg = (SELECT codSogg FROM galleria_schema.soggettofoto WHERE categoria = ? AND nome = ?)) AND privata = false AND rimossa = false;");
            ps.setString(1, categoria);
            ps.setString(2, nome);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int codFoto = rs.getInt(1);
                boolean privata = rs.getBoolean(2);
                boolean rimossa = rs.getBoolean(3);
                Date dataScatto = rs.getDate(4);
                int codGalleria = rs.getInt(5);
                String autore = rs.getString(6);
                int codDispositivo = rs.getInt(7);
                byte[] barr = rs.getBytes(8);

                // Crea un oggetto InputStream a partire dall'array di byte
                ByteArrayInputStream bis = new ByteArrayInputStream(barr);
                // Crea un oggetto ImageIcon a partire dallo stream di byte
                ImageIcon immagine = new ImageIcon(ImageIO.read(bis));
                bis.close();
                Foto f = new Foto(codFoto, privata, rimossa, dataScatto, codGalleria, autore, codDispositivo, immagine);
                photos.add(f);

            }
            rs.close();
            connection.close();
            return photos;

        } catch (Exception s) {
            s.printStackTrace();
            throw new SQLException();
        }
    }

    @Override
    public Foto caricaFoto(boolean privata, boolean nuovo, Date data, String username, int idDispositivo, String filePath, Luogo luogo, ArrayList<SoggettoFoto> soggettiNuovi, ArrayList<SoggettoFoto> soggettiEsistenti, String[] tags) throws SQLException {
        try {
            Foto foto;
            int codGallP = -1;
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement("SELECT codGalleria FROM galleria_schema.galleria_personale WHERE proprietario = ?;");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                codGallP = rs.getInt(1);
            }

            foto = inserisciFotoDB(privata, false, data, codGallP, username, idDispositivo, filePath);
            foto.setLuogo(luogo);

            if (nuovo){
                ps = connection.prepareStatement("INSERT INTO galleria_schema.luogo VALUES (DEFAULT,?,?,?);");
                ps.setDouble(1, luogo.getLatitudine());
                ps.setDouble(2, luogo.getLongitudine());
                ps.setString(3, luogo.getNomeMnemonico());
                ps.executeUpdate();

                ps = connection.prepareStatement("SELECT codLuogo FROM galleria_schema.luogo ORDER BY codLuogo DESC LIMIT 1;");
                int codLuogo = -1;
                rs = ps.executeQuery();
                while (rs.next()){
                    codLuogo = rs.getInt(1);
                }
                rs.close();
                ps = connection.prepareStatement("INSERT INTO galleria_schema.presenzaLuogo VALUES (?,?);");
                ps.setInt(1, foto.getCodFoto());
                ps.setInt(2, codLuogo);
                ps.executeUpdate();
            } else if (luogo != null){
                ps = connection.prepareStatement("INSERT INTO galleria_schema.presenzaLuogo VALUES (?,?);");
                ps.setInt(1, foto.getCodFoto());
                ps.setInt(2, luogo.getCodLuogo());
                ps.executeUpdate();
            }

            foto.setTagsUsernames(tags);

            if (tags != null) {
                int i = 0;
                int numTag = tags.length;
                while (i < numTag) {
                    ps = connection.prepareStatement("INSERT INTO galleria_schema.tag VALUES (?,?);");
                    ps.setInt(1, foto.getCodFoto());
                    ps.setString(2, username);
                    ps.executeUpdate();
                    i++;
                }
            }
            int codSogg;
            if (!soggettiNuovi.isEmpty()){
                for (SoggettoFoto sf : soggettiNuovi){
                    ps = connection.prepareStatement("INSERT INTO galleria_schema.soggettofoto VALUES (DEFAULT,?,?);");
                    ps.setString(1, sf.getNome());
                    ps.setString(2, sf.getCategoria());
                    ps.executeUpdate();

                    ps = connection.prepareStatement("SELECT codSogg FROM galleria_schema.soggettofoto ORDER BY codSogg DESC LIMIT 1;");
                    rs = ps.executeQuery();
                    codSogg = -1;
                    while (rs.next()){
                        codSogg = rs.getInt(1);
                    }
                    rs.close();
                    ps = connection.prepareStatement("INSERT INTO galleria_schema.presenzaSoggetto VALUES (?,?);");
                    ps.setInt(1, foto.getCodFoto());
                    ps.setInt(2, codSogg);
                    ps.executeUpdate();
                }
            }
            if (!soggettiEsistenti.isEmpty()) {
                for (SoggettoFoto sf : soggettiEsistenti) {
                    ps = connection.prepareStatement("INSERT INTO galleria_schema.presenzaSoggetto VALUES (?,?);");
                    ps.setInt(1, foto.getCodFoto());
                    ps.setInt(2, sf.getId());
                    ps.executeUpdate();
                }
            }
            soggettiEsistenti.addAll(soggettiNuovi);
            foto.setSoggetti(soggettiEsistenti);
            connection.commit();
            connection.close();
            return foto;
        } catch (Exception s){
            connection.rollback();
            connection.close();
            s.printStackTrace();
            throw new SQLException();
        }
    }

    @Override
    public ArrayList<Foto> getFotoGalleriaC(ArrayList<Integer> codPhotos) throws SQLException {
        ArrayList<Foto> photos;
        try {
            int i = 0;
            int numFoto = codPhotos.size();
            photos = new ArrayList<>();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM galleria_schema.foto WHERE codFoto = ? AND privata = false;");
            ResultSet rs = null;
            int codFoto;
            boolean privata;
            boolean rimossa;
            Date dataScatto;
            int codGalleria;
            String autore;
            int codDispositivo;
            byte[] barr;
            while (i < numFoto){
                ps.setInt(1, codPhotos.get(i));
                rs = ps.executeQuery();
                while (rs.next()){
                    codFoto = rs.getInt(1);
                    privata = rs.getBoolean(2);
                    rimossa = rs.getBoolean(3);
                    dataScatto = rs.getDate(4);
                    codGalleria = rs.getInt(5);
                    autore = rs.getString(6);
                    codDispositivo = rs.getInt(7);
                    barr = rs.getBytes(8);
                    ByteArrayInputStream bis = new ByteArrayInputStream(barr);
                    ImageIcon immagine = new ImageIcon(ImageIO.read(bis));
                    bis.close();
                    Foto f = new Foto(codFoto, privata, rimossa, dataScatto, codGalleria, autore, codDispositivo, immagine);
                    photos.add(f);
                }
                rs.close();
                i++;
            }

            connection.close();
            return photos;
        } catch (Exception s) {
            s.printStackTrace();
            throw new SQLException();
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


