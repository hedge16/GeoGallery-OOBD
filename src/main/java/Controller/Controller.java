package Controller;

import DAO.*;
import ImplementazionePostgresDAO.*;
import Model.*;
import org.postgresql.util.PSQLException;
import org.postgresql.util.ServerErrorMessage;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class Controller {

    Utente utente;
    String[] categorieSoggettoFoto = {"Paesaggi", "Eventi sportivi", "Gruppi di persone", "Ritratti", "Selfie", "Animali", "Cibo", "Matrimoni", "Viaggi", "Natura", "Altro"};

    public Controller(){

    }

    public void setUtente (Utente utente) {
        this.utente = utente;
    }

    public Utente getUtente () {
        return utente;
    }

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

    public ArrayList<String> getUsernameFromUtente (ArrayList<Utente> utenti){
        ArrayList<String> usernames = new ArrayList<>();
        int i;
        for(i=0; i < utenti.size(); i++){
            usernames.add(utenti.get(i).getUsername());
        }
        return usernames;
    }

    public void aggiungiTagFotoDB (String username, int codFoto) throws SQLException{
        try {
            TagDAO t = new TagImplementazionePostgresDAO();
            t.aggiungiTagDB(username, codFoto);
        } catch (SQLException s){
            s.printStackTrace();
            throw new SQLException();
        }

    }

    public String [] getCategorie () {
        return categorieSoggettoFoto;
    }

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

    public void aggiungiPresenzaFotoDB (int codfoto, int codg) throws SQLException {
        try {
            PresenzaFotoDAO pf = new PresenzaFotoImplementazionePostgresDAO();
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

    public void aggiungiPresenzaSoggetto (int codFoto, int codSogg) throws SQLException{
        try {
            PresenzaSoggettoDAO ps = new PresenzaSoggettoImplementazionePostgresDAO();
            ps.aggiungiPresenzaSoggetto(codFoto, codSogg);
        } catch (SQLException s) {
            s.printStackTrace();
            throw new SQLException();
        }
    }

    public void aggiungiPresenzaLuogo (int codFoto, int codLuogo) {
        try {
            PresenzaLuogoDAO pl = new PresenzaLuogoImplementazionePostgresDAO();
            pl.aggiungiPresenzaLuogo(codFoto, codLuogo);
        } catch (SQLException s) {
            s.printStackTrace();
        }
    }

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

    public ArrayList<GalleriaCondivisa> recuperaGallerieCondiviseDB (String username) throws SQLException {
        ArrayList<GalleriaCondivisa> gallerie = null;
        try {
            Galleria_condivisaDAO gc = new GalleriaCondivisaImplementazionePostgresDAO();
            gallerie = gc.recuperaGallerieCondivise(username);
            return gallerie;
        } catch (SQLException s){
            s.printStackTrace();
            throw new SQLException();
        }
    }

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


}
