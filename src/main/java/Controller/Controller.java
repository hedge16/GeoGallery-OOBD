package Controller;

import DAO.*;
import ImplementazionePostgresDAO.*;
import Model.*;
import org.postgresql.util.PSQLException;
import org.postgresql.util.ServerErrorMessage;


import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class Controller {


    public Controller(){

    }

    public void aggiungiUtente (String nome, String cognome, String username, String password, String email , Date data) throws PSQLException, SQLException {
        UtenteDAO u = new UtenteImplementazionePostgresDAO();
        try{
            u.aggiungiUtente(nome, cognome, username, password, email, data);
            u = new UtenteImplementazionePostgresDAO();
            u.creaGallUt(username);
        }catch(PSQLException p){
            ServerErrorMessage errorMessage = new ServerErrorMessage("Errore nell'inserimento dei dati");
            throw new PSQLException(errorMessage);
        }catch(SQLException s){
            throw new SQLException();
        }


    }

    public ArrayList<Utente> leggiUtentiDB () throws SQLException {
        UtenteDAO u = new UtenteImplementazionePostgresDAO();
        ArrayList<Utente> listaUtenti;
        try{
            listaUtenti=u.leggiUtenti();
            return listaUtenti;
        }catch(SQLException s ){
            throw new SQLException("Errore");

        }

    }

    public ArrayList<Foto> recuperaGallUtente (String username){
        ArrayList<Foto> photos = null;
        FotoDAO f = new FotoImplementazionePostgresDAO();
        try{
            photos = f.recuperaFotoDB(username);
        }catch(SQLException p){

        }
        return photos;
    }

    public int aggiungiFoto (boolean privata, boolean rimossa, Date dataScatto, int codgalleriap, String autore, int codDispositivo, String percorsoFoto) throws SQLException, FileNotFoundException {
        int codFoto = -1;
        FotoDAO u = new FotoImplementazionePostgresDAO();
        try{
            codFoto = u.inserisciFotoDB(privata, rimossa, dataScatto, codgalleriap, autore, codDispositivo, percorsoFoto);
            if (codFoto == -1){
                throw new SQLException();
            }
            return codFoto;

        }catch(SQLException s){
            s.printStackTrace();
            throw new SQLException();

        }catch(FileNotFoundException f){
            f.printStackTrace();
            throw new FileNotFoundException();

        }


    }

    public int getCodG (String username) throws SQLException {
       int codG=-1;
       Galleria_personaleDAO g= new GalleriaPImplementazionePostgresDAO();
       try{
           codG = g.getCodGDB(username);

       }catch(SQLException s){
           throw new SQLException();

       }
       return codG;
    }

    public ArrayList<Dispositivo> getAllDisp (String username) throws SQLException{
        ArrayList<Dispositivo> dispositivi = new ArrayList<>();
        DispositivoDAO d = new DispositivoImplementazionePostgresDAO();
        try{
            dispositivi = d.getAllDispDB(username);
        }catch(SQLException s){
            s.printStackTrace();
            throw new SQLException();
        }
        return dispositivi;

    }

    public void addDisp (String username, String nomeDisp) throws SQLException{
        DispositivoDAO d = new DispositivoImplementazionePostgresDAO();
        try{
            d.addDispDB(username, nomeDisp);
        }catch (SQLException s){
            s.printStackTrace();
            throw new SQLException();
        }

    }

    public int getCodDisp (String nomeDisp, String username) throws SQLException{
        int codiceDisp=-1;
        DispositivoDAO d = new DispositivoImplementazionePostgresDAO();
        try{
            codiceDisp = d.getCodDispDB(nomeDisp, username);

        }catch( SQLException s ){
            s.printStackTrace();
            throw new SQLException();

        }
        return codiceDisp;
    }

    public ArrayList<String> getUsernameFromUtente (ArrayList<Utente> utenti){
        ArrayList<String> usernames = new ArrayList<>();
        int i;
        for(i=0; i < utenti.size(); i++){
            usernames.add(utenti.get(i).getUsername());
        }
        return usernames;
    }

    public void aggiungiTagFoto (String username, int codFoto) throws SQLException{
        TagDAO t = new TagImplementazionePostgresDAO();
        try {
            t.aggiungiTagDB(username, codFoto);

        } catch (SQLException s){
            s.printStackTrace();
            throw new SQLException();
        }

    }

    public String [] getCategorie () {
        String[] categorie = {"Paesaggi", "Eventi sportivi", "Gruppi di persone", "Ritratti", "Selfie", "Animali", "Cibo", "Matrimoni", "Viaggi", "Natura"};
        return categorie;
    }

    public int aggiungiSoggettoFoto (String categoria, String nomeSogg) throws SQLException{
        SoggettoFotoDAO s = new SoggettoFotoImplementazionePostgresDAO();
        int codSogg;
        try{
            codSogg = s.aggiungiSoggettoFotoDB(categoria, nomeSogg);
            return codSogg;
        }catch (SQLException s1){
            s1.printStackTrace();
            throw new SQLException();
        }

    }

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

    public int aggiungiGalleriaCondivisa (String fondatore, String cofondatori, String nomeGalleria) throws SQLException {
        String[] cofondatoriArray = cofondatori.split(",");
        Galleria_condivisaDAO gc = new GalleriaCondivisaImplementazionePostgresDAO();
        int codg;
        try {
            codg = gc.creaGalleriaCondivisaDB(fondatore, cofondatoriArray, nomeGalleria);
            return codg;
        } catch(SQLException s) {
            s.printStackTrace();
            throw new SQLException();
        }
    }

    public void aggiungiPresenzaFoto (int codfoto, int codg) throws SQLException {
        PresenzaFotoDAO pf = new PresenzaFotoImplementazionePostgresDAO();
        try {
            pf.aggiungiPresenzaFotoDB(codfoto, codg);
        } catch (SQLException s){
            s.printStackTrace();
            throw new SQLException();
        }
    }

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

    public void setPrivacyDB (int codfoto, boolean state) throws SQLException {
        try{
            FotoDAO f = new FotoImplementazionePostgresDAO();
            f.setPrivacyDB(codfoto, state);
        } catch (SQLException s){
            s.printStackTrace();
            throw new SQLException();
        }

    }

    public ArrayList<Foto> ricercaFotoPerLuogo (String input) throws SQLException{
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

    public ArrayList<Foto> ricercaFotoPerSoggetto (String categoria, String nome) throws SQLException{
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

    public void aggiungiPresenzaSoggetto (int codFoto, int codSogg) throws SQLException{
        PresenzaSoggettoDAO ps = new PresenzaSoggettoImplementazionePostgresDAO();
        try {
            ps.aggiungiPresenzaSoggetto(codFoto, codSogg);
        } catch (SQLException s) {
            s.printStackTrace();
            throw new SQLException();
        }
    }

    public void aggiungiPresenzaLuogo (int codFoto, int codLuogo) {
        PresenzaLuogoDAO pl = new PresenzaLuogoImplementazionePostgresDAO();
        try {
            pl.aggiungiPresenzaLuogo(codFoto, codLuogo);
        } catch (SQLException s) {
            s.printStackTrace();
        }
    }

    public ArrayList<Luogo> recuperaTuttiLuoghiDB () throws SQLException{
        ArrayList<Luogo> luoghi;
        LuogoDAO l = new LuogoImplementazionePostgresDAO();
        try{
            luoghi=l.recuperaTuttiLuoghiDB();
            return luoghi;
        } catch (SQLException s) {
            s.printStackTrace();
            throw new SQLException();
        }
    }

    public ArrayList<SoggettoFoto> recuperaTuttiSoggettiDB () throws SQLException {
        ArrayList<SoggettoFoto> soggetti;
        SoggettoFotoDAO sf = new SoggettoFotoImplementazionePostgresDAO();
        try{
            soggetti = sf.recuperaTuttiSoggettiDB();
            return soggetti;
        } catch (SQLException s){
            s.printStackTrace();
            throw new SQLException();
        }

    }

    public Luogo getLuogoFromFoto (int codfoto) throws SQLException {
        LuogoDAO l = new LuogoImplementazionePostgresDAO();
        Luogo luogo = null;
        try {
            luogo = l.getLuogoFromFoto(codfoto);
            return luogo;
        } catch (SQLException s){
            s.printStackTrace();
            throw new SQLException();
        }
    }

    public ArrayList<GalleriaCondivisa> recuperaGallerieCondivise (String username) throws SQLException {
        ArrayList<GalleriaCondivisa> gallerie = null;
        Galleria_condivisaDAO gc = new GalleriaCondivisaImplementazionePostgresDAO();
        try {
            gallerie = gc.recuperaGallerieCondivise(username);
            return gallerie;
        } catch (SQLException s){
            s.printStackTrace();
            throw new SQLException();
        }
    }

    public Dispositivo getDispositivo (int codDisp) throws SQLException {
        DispositivoDAO d = new DispositivoImplementazionePostgresDAO();
        Dispositivo disp;
        try {
            disp = d.getDispositivo(codDisp);
            return disp;
        } catch (SQLException s){
            s.printStackTrace();
            throw new SQLException();
        }
    }

    public void caricaFoto(boolean privata, boolean nuovo, Date data, String username, int idDispositivo, String filePath, Luogo luogo, ArrayList<SoggettoFoto> soggettiNuovi, ArrayList<SoggettoFoto> soggettiEsistenti, String[] tags) throws SQLException {
        FotoDAO f = new FotoImplementazionePostgresDAO();
        try {
            f.caricaFoto(privata, nuovo, data, username, idDispositivo, filePath, luogo, soggettiNuovi, soggettiEsistenti, tags);
        } catch (SQLException s) {
            s.printStackTrace();
            throw new SQLException();
        }
    }

    public ArrayList<Luogo> ricercaLuoghiTop3 () throws SQLException {
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


}
