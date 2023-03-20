package Controller;

import DAO.*;
import GUI.Components.FotoPanel;
import ImplementazionePostgresDAO.*;
import Model.*;
import org.postgresql.util.PSQLException;
import org.postgresql.util.ServerErrorMessage;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 * The type Controller.
 */
public class Controller {

    /**
     * The Utente.
     */
    Utente utente;
    /**
     * The Categorie soggetto foto.
     */
    String[] categorieSoggettoFoto = {"Paesaggi", "Eventi sportivi", "Gruppi di persone", "Ritratti", "Selfie", "Animali", "Cibo", "Matrimoni", "Viaggi", "Natura", "Altro"};
    /**
     * The Tutti luoghi.
     */
    ArrayList<Luogo> tuttiLuoghi = null;
    /**
     * The Tutti soggetti.
     */
    ArrayList<SoggettoFoto> tuttiSoggetti = null;
    /**
     * The Foto panel selectable.
     */
    FotoPanel fotoPanelSelectable = null;
    /**
     * The Cod galleria condivisa.
     */
    int codGalleriaCondivisa;

    /**
     * Instantiates a new Controller.
     */
    public Controller(){

    }

    /**
     * Sets utente.
     *
     * @param utente the utente
     */
    public void setUtente (Utente utente) {
        this.utente = utente;
    }

    /**
     * Gets utente.
     *
     * @return the utente
     */
    public Utente getUtente () {
        return utente;
    }

    /**
     * Gets tutti luoghi.
     *
     * @return the tutti luoghi
     */
    public ArrayList<Luogo> getTuttiLuoghi () {
        return tuttiLuoghi;
    }

    /**
     * Sets tutti luoghi.
     *
     * @param tuttiLuoghi the tutti luoghi
     */
    public void setTuttiLuoghi (ArrayList<Luogo> tuttiLuoghi) {
        this.tuttiLuoghi = tuttiLuoghi;
    }

    /**
     * Gets tutti soggetti.
     *
     * @return the tutti soggetti
     */
    public ArrayList<SoggettoFoto> getTuttiSoggetti () {
        return tuttiSoggetti;
    }

    /**
     * Sets tutti soggetti.
     *
     * @param tuttiSoggetti the tutti soggetti
     */
    public void setTuttiSoggetti (ArrayList<SoggettoFoto> tuttiSoggetti) {
        this.tuttiSoggetti = tuttiSoggetti;
    }

    /**
     * Gets foto panel selectable.
     *
     * @return the foto panel selectable
     */
    public FotoPanel getFotoPanelSelectable () {
        return fotoPanelSelectable;
    }

    /**
     * Sets foto panel selectable.
     *
     * @param fotoPanelSelectable the foto panel selectable
     */
    public void setFotoPanelSelectable (FotoPanel fotoPanelSelectable) {
        this.fotoPanelSelectable = fotoPanelSelectable;
    }

    /**
     * Gets cod galleria condivisa.
     *
     * @return the cod galleria condivisa
     */
    public int getCodGalleriaCondivisa () {
        return codGalleriaCondivisa;
    }

    /**
     * Sets cod galleria condivisa.
     *
     * @param codGalleriaCondivisa the cod galleria condivisa
     */
    public void setCodGalleriaCondivisa (int codGalleriaCondivisa) {
        this.codGalleriaCondivisa = codGalleriaCondivisa;
    }


    /**
     * Aggiungi utente db.
     *
     * @param nome     the nome
     * @param cognome  the cognome
     * @param username the username
     * @param password the password
     * @param email    the email
     * @param data     the data
     * @throws PSQLException the psql exception
     * @throws SQLException  the sql exception
     */
    public void aggiungiUtenteDB (String nome, String cognome, String username, String password, String email , Date data) throws PSQLException, SQLException {
        try{
            UtenteDAO u = new UtenteImplementazionePostgresDAO();
            u.aggiungiUtente(nome, cognome, username, password, email, data);
        }catch(PSQLException p){
            ServerErrorMessage errorMessage = new ServerErrorMessage("Errore nell'inserimento dei dati");
            throw new PSQLException(errorMessage);
        }catch(SQLException s){
            throw new SQLException();
        }
    }

    /**
     * Leggi utenti db array list.
     *
     * @return the array list
     * @throws SQLException the sql exception
     */
    public ArrayList<Utente> leggiUtentiDB () throws SQLException {
        ArrayList<Utente> listaUtenti;
        try{
            UtenteDAO u = new UtenteImplementazionePostgresDAO();
            listaUtenti=u.leggiUtenti();
            return listaUtenti;
        }catch(SQLException s ){
            throw new SQLException("Errore");

        }

    }

    /**
     * Recupera gall utente db array list.
     *
     * @param username the username
     * @return the array list
     * @throws SQLException the sql exception
     */
    public ArrayList<Foto> recuperaGallUtenteDB (String username) throws SQLException{
        ArrayList<Foto> photos;
        try{
            FotoDAO f = new FotoImplementazionePostgresDAO();
            photos = f.recuperaFotoDB(username);
            return photos;
        }catch(SQLException p){
            p.printStackTrace();
            throw new SQLException();
        }
    }

    /**
     * Gets cod galleria db.
     *
     * @param username the username
     * @return the cod galleria db
     * @throws SQLException the sql exception
     */
    public int getCodGalleriaDB (String username) throws SQLException {
       int codG=-1;
       try{
           Galleria_personaleDAO g= new GalleriaPImplementazionePostgresDAO();
           codG = g.getCodGDB(username);
       }catch(SQLException s){
           throw new SQLException();

       }
       return codG;
    }

    /**
     * Gets all disp db.
     *
     * @param username the username
     * @return the all disp db
     * @throws SQLException the sql exception
     */
    public ArrayList<Dispositivo> getAllDispDB (String username) throws SQLException{
        ArrayList<Dispositivo> dispositivi;
        try{
            DispositivoDAO d = new DispositivoImplementazionePostgresDAO();
            dispositivi = d.getAllDispDB(username);
        }catch(SQLException s){
            s.printStackTrace();
            throw new SQLException();
        }
        return dispositivi;

    }

    /**
     * Add disp db dispositivo.
     *
     * @param username the username
     * @param nomeDisp the nome disp
     * @return the dispositivo
     * @throws SQLException the sql exception
     */
    public Dispositivo addDispDB (String username, String nomeDisp) throws SQLException{
        try{
            Dispositivo dispositivo;
            DispositivoDAO d = new DispositivoImplementazionePostgresDAO();
            dispositivo = d.addDispDB(username, nomeDisp);
            return dispositivo;
        }catch (SQLException s){
            s.printStackTrace();
            throw new SQLException();
        }

    }

    /**
     * Gets cod disp db.
     *
     * @param nomeDisp the nome disp
     * @param username the username
     * @return the cod disp db
     * @throws SQLException the sql exception
     */
    public int getCodDispDB (String nomeDisp, String username) throws SQLException{
        int codiceDisp=-1;
        try{
            DispositivoDAO d = new DispositivoImplementazionePostgresDAO();
            codiceDisp = d.getCodDispDB(nomeDisp, username);
        }catch( SQLException s ){
            s.printStackTrace();
            throw new SQLException();

        }
        return codiceDisp;
    }

    /**
     * Get username from utente array list.
     *
     * @param utenti the utenti
     * @return the array list
     */
    public ArrayList<String> getUsernameFromUtente (ArrayList<Utente> utenti){
        ArrayList<String> usernames = new ArrayList<>();
        int i;
        for(i=0; i < utenti.size(); i++){
            usernames.add(utenti.get(i).getUsername());
        }
        return usernames;
    }

    /**
     * Aggiungi tag foto db.
     *
     * @param username the username
     * @param codFoto  the cod foto
     * @throws SQLException the sql exception
     */
    public void aggiungiTagFotoDB (String username, int codFoto) throws SQLException{
        try {
            TagDAO t = new TagImplementazionePostgresDAO();
            t.aggiungiTagDB(username, codFoto);
        } catch (SQLException s){
            s.printStackTrace();
            throw new SQLException();
        }

    }

    /**
     * Get categorie string [ ].
     *
     * @return the string [ ]
     */
    public String [] getCategorie () {
        return categorieSoggettoFoto;
    }

    /**
     * Aggiungi soggetto foto db int.
     *
     * @param categoria the categoria
     * @param nomeSogg  the nome sogg
     * @return the int
     * @throws SQLException the sql exception
     */
    public int aggiungiSoggettoFotoDB (String categoria, String nomeSogg) throws SQLException{
        int codSogg;
        try{
            SoggettoFotoDAO s = new SoggettoFotoImplementazionePostgresDAO();
            codSogg = s.aggiungiSoggettoFotoDB(categoria, nomeSogg);
            return codSogg;
        }catch (SQLException s1){
            s1.printStackTrace();
            throw new SQLException();
        }

    }

    /**
     * Aggiungi luogo db int.
     *
     * @param latitudine  the latitudine
     * @param longitudine the longitudine
     * @param nomeLuogo   the nome luogo
     * @return the int
     * @throws SQLException the sql exception
     */
    public int aggiungiLuogoDB (double latitudine, double longitudine, String nomeLuogo) throws SQLException {
        int codLuogo = -1;
        LuogoDAO l = new LuogoImplementazionePostgresDAO();
        try{
            codLuogo = l.aggiungiLuogoDB(latitudine, longitudine, nomeLuogo);
        } catch (SQLException s) {
            s.printStackTrace();
            throw new SQLException();
        }
        return codLuogo;
    }

    /**
     * Aggiungi galleria condivisa db int.
     *
     * @param fondatore    the fondatore
     * @param cofondatori  the cofondatori
     * @param nomeGalleria the nome galleria
     * @return the int
     * @throws SQLException the sql exception
     */
    public int aggiungiGalleriaCondivisaDB (String fondatore, String cofondatori, String nomeGalleria) throws SQLException {
        String[] cofondatoriArray = cofondatori.split(",");
        int codg;
        try {
            Galleria_condivisaDAO gc = new GalleriaCondivisaImplementazionePostgresDAO();
            codg = gc.creaGalleriaCondivisaDB(fondatore, cofondatoriArray, nomeGalleria);
            return codg;
        } catch(SQLException s) {
            s.printStackTrace();
            throw new SQLException();
        }
    }

    /**
     * Aggiungi presenza foto db.
     *
     * @param codfoto the codfoto
     * @param codg    the codg
     * @throws SQLException the sql exception
     */
    public void aggiungiPresenzaFotoDB (int codfoto, int codg) throws SQLException {
        try {
            PresenzaFotoDAO pf = new PresenzaFotoImplementazionePostgresDAO();
            pf.aggiungiPresenzaFotoDB(codfoto, codg);
        } catch (SQLException s){
            s.printStackTrace();
            throw new SQLException();
        }
    }

    /**
     * Elimina foto db.
     *
     * @param foto the foto
     * @throws SQLException the sql exception
     */
    public void eliminaFotoDB (Foto foto) throws SQLException {
        int codf = foto.getCodFoto();
        try {
            FotoDAO f = new FotoImplementazionePostgresDAO();
            f.eliminaFotoDB(codf);
        } catch (SQLException s){
            s.printStackTrace();
            throw new SQLException();
        }
    }

    /**
     * Gets last foto db.
     *
     * @param username the username
     * @return the last foto db
     */
    public Foto getLastFotoDB (String username) {
        Foto f;
        try {
            FotoDAO fd = new FotoImplementazionePostgresDAO();
            f = fd.getLastFoto(username);
            return f;
        } catch (SQLException s) {
            s.printStackTrace();
        }
        return null;

    }

    /**
     * Sets privacy db.
     *
     * @param codfoto the codfoto
     * @param state   the state
     * @throws SQLException the sql exception
     */
    public void setPrivacyDB (int codfoto, boolean state) throws SQLException {
        try{
            FotoDAO f = new FotoImplementazionePostgresDAO();
            f.setPrivacyDB(codfoto, state);
        } catch (SQLException s){
            s.printStackTrace();
            throw new SQLException();
        }

    }

    /**
     * Ricerca foto per luogo db array list.
     *
     * @param input the input
     * @return the array list
     * @throws SQLException the sql exception
     */
    public ArrayList<Foto> ricercaFotoPerLuogoDB (String input) throws SQLException{
        ArrayList<Foto> photos;
        try {
            FotoDAO f = new FotoImplementazionePostgresDAO();
            photos = f.ricercaFotoPerLuogo(input);
            return photos;
        } catch (SQLException s) {
            s.printStackTrace();
            throw new SQLException();
        }

    }

    /**
     * Ricerca foto per luogo db array list.
     *
     * @param input       the input
     * @param latitudine  the latitudine
     * @param longitudine the longitudine
     * @return the array list
     * @throws SQLException the sql exception
     */
    public ArrayList<Foto> ricercaFotoPerLuogoDB (String input, double latitudine, double longitudine) throws SQLException{
        ArrayList<Foto> photos;
        try {
            FotoDAO f = new FotoImplementazionePostgresDAO();
            photos = f.ricercaFotoPerLuogo(input, latitudine, longitudine);
            return photos;
        } catch (SQLException s) {
            s.printStackTrace();
            throw new SQLException();
        }

    }

    /**
     * Ricerca foto per soggetto db array list.
     *
     * @param categoria the categoria
     * @param nome      the nome
     * @return the array list
     * @throws SQLException the sql exception
     */
    public ArrayList<Foto> ricercaFotoPerSoggettoDB (String categoria, String nome) throws SQLException{
        ArrayList<Foto> photos;
        try {
            FotoDAO f = new FotoImplementazionePostgresDAO();
            photos = f.ricercaFotoPerSoggetto(categoria, nome);
            return photos;
        } catch (SQLException s) {
            s.printStackTrace();
            throw new SQLException();
        }

    }

    /**
     * Aggiungi presenza soggetto.
     *
     * @param codFoto the cod foto
     * @param codSogg the cod sogg
     * @throws SQLException the sql exception
     */
    public void aggiungiPresenzaSoggetto (int codFoto, int codSogg) throws SQLException{
        try {
            PresenzaSoggettoDAO ps = new PresenzaSoggettoImplementazionePostgresDAO();
            ps.aggiungiPresenzaSoggetto(codFoto, codSogg);
        } catch (SQLException s) {
            s.printStackTrace();
            throw new SQLException();
        }
    }

    /**
     * Aggiungi presenza luogo.
     *
     * @param codFoto  the cod foto
     * @param codLuogo the cod luogo
     */
    public void aggiungiPresenzaLuogo (int codFoto, int codLuogo) {
        try {
            PresenzaLuogoDAO pl = new PresenzaLuogoImplementazionePostgresDAO();
            pl.aggiungiPresenzaLuogo(codFoto, codLuogo);
        } catch (SQLException s) {
            s.printStackTrace();
        }
    }

    /**
     * Recupera tutti luoghi db array list.
     *
     * @return the array list
     * @throws SQLException the sql exception
     */
    public ArrayList<Luogo> recuperaTuttiLuoghiDB () throws SQLException{
        ArrayList<Luogo> luoghi;
        try{
            LuogoDAO l = new LuogoImplementazionePostgresDAO();
            luoghi=l.recuperaTuttiLuoghiDB();
            return luoghi;
        } catch (SQLException s) {
            s.printStackTrace();
            throw new SQLException();
        }
    }

    /**
     * Recupera tutti soggetti db array list.
     *
     * @return the array list
     * @throws SQLException the sql exception
     */
    public ArrayList<SoggettoFoto> recuperaTuttiSoggettiDB () throws SQLException {
        ArrayList<SoggettoFoto> soggetti;
        try{
            SoggettoFotoDAO sf = new SoggettoFotoImplementazionePostgresDAO();
            soggetti = sf.recuperaTuttiSoggettiDB();
            return soggetti;
        } catch (SQLException s){
            s.printStackTrace();
            throw new SQLException();
        }

    }

    /**
     * Gets luogo from foto db.
     *
     * @param codfoto the codfoto
     * @return the luogo from foto db
     * @throws SQLException the sql exception
     */
    public Luogo getLuogoFromFotoDB (int codfoto) throws SQLException {
        Luogo luogo = null;
        try {
            LuogoDAO l = new LuogoImplementazionePostgresDAO();
            luogo = l.getLuogoFromFoto(codfoto);
            return luogo;
        } catch (SQLException s){
            s.printStackTrace();
            throw new SQLException();
        }
    }

    /**
     * Recupera gallerie condivise db array list.
     *
     * @param username the username
     * @return the array list
     * @throws SQLException the sql exception
     */
    public ArrayList<GalleriaCondivisa> recuperaGallerieCondiviseDB (String username) throws SQLException {
        ArrayList<GalleriaCondivisa> gallerie;
        try {
            Galleria_condivisaDAO gc = new GalleriaCondivisaImplementazionePostgresDAO();
            gallerie = gc.recuperaGallerieCondivise(username);
            return gallerie;
        } catch (SQLException s){
            s.printStackTrace();
            throw new SQLException();
        }
    }

    /**
     * Gets dispositivo db.
     *
     * @param codDisp the cod disp
     * @return the dispositivo db
     * @throws SQLException the sql exception
     */
    public Dispositivo getDispositivoDB(int codDisp) throws SQLException {
        Dispositivo disp;
        try {
            DispositivoDAO d = new DispositivoImplementazionePostgresDAO();
            disp = d.getDispositivo(codDisp);
            return disp;
        } catch (SQLException s){
            s.printStackTrace();
            throw new SQLException();
        }
    }

    /**
     * Carica foto db foto.
     *
     * @param privata           the privata
     * @param nuovo             the nuovo
     * @param data              the data
     * @param username          the username
     * @param idDispositivo     the id dispositivo
     * @param filePath          the file path
     * @param luogo             the luogo
     * @param soggettiNuovi     the soggetti nuovi
     * @param soggettiEsistenti the soggetti esistenti
     * @param tags              the tags
     * @return the foto
     * @throws SQLException the sql exception
     */
    public Foto caricaFotoDB(boolean privata, boolean nuovo, Date data, String username, int idDispositivo, String filePath, Luogo luogo, ArrayList<SoggettoFoto> soggettiNuovi, ArrayList<SoggettoFoto> soggettiEsistenti, String[] tags) throws SQLException {
        try {
            Foto foto;
            FotoDAO f = new FotoImplementazionePostgresDAO();
            foto = f.caricaFoto(privata, nuovo, data, username, idDispositivo, filePath, luogo, soggettiNuovi, soggettiEsistenti, tags);
            return foto;
        } catch (SQLException s) {
            s.printStackTrace();
            throw new SQLException();
        }
    }

    /**
     * Ricerca luoghi top 3 db array list.
     *
     * @return the array list
     * @throws SQLException the sql exception
     */
    public ArrayList<Luogo> ricercaLuoghiTop3DB () throws SQLException {
        ArrayList<Luogo> luoghi;
        try {
            LuogoDAO l = new LuogoImplementazionePostgresDAO();
            luoghi = l.ricercaLuoghiTop3();
            return luoghi;
        } catch (SQLException s) {
            s.printStackTrace();
            throw new SQLException();
        }

    }

    /**
     * Gets foto galleria c.
     *
     * @param codGalleria the cod galleria
     * @return the foto galleria c
     * @throws SQLException the sql exception
     */
    public ArrayList<Foto> getFotoGalleriaC (int codGalleria) throws SQLException {
        ArrayList<Foto> foto;
        try {
            PresenzaFotoDAO pf = new PresenzaFotoImplementazionePostgresDAO();
            ArrayList<Integer> codPhotos = pf.getFotoGalleriaC(codGalleria);
            FotoDAO f = new FotoImplementazionePostgresDAO();
            foto = f.getFotoGalleriaC(codPhotos);
            return foto;
        } catch (SQLException s) {
            s.printStackTrace();
            throw new SQLException();
        }
    }


    /**
     * Gets soggetti from foto.
     *
     * @param codfoto the codfoto
     * @return the soggetti from foto
     */
    public ArrayList<SoggettoFoto> getSoggettiFromFoto (int codfoto) {
        ArrayList<SoggettoFoto> soggetti;
        try {
            PresenzaSoggettoDAO ps = new PresenzaSoggettoImplementazionePostgresDAO();
            ArrayList<Integer> codSoggetti = ps.getCodSoggFromCodFoto(codfoto);
            SoggettoFotoDAO sf = new SoggettoFotoImplementazionePostgresDAO();
            soggetti = sf.recuperaSoggettiDB(codSoggetti);
            return soggetti;
        } catch (SQLException s) {
            s.printStackTrace();
        }
        return null;
    }



}
