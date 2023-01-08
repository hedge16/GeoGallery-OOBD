package Controller;

import DAO.*;
import ImplementazionePostgresDAO.*;
import Model.Utente;
import org.postgresql.util.PSQLException;
import org.postgresql.util.ServerErrorMessage;


import javax.swing.*;
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

    public ArrayList<ImageIcon> recuperaGallUtente (String username){
        ArrayList<ImageIcon> miniature = null;
        FotoDAO f = new FotoImplementazionePostgresDAO();
        try{
            miniature = f.recuperaFotoDB(username);
        }catch(SQLException p){

        }
        return miniature;
    }

    public int aggiungiFoto (boolean privata, boolean rimossa, Date dataScatto, int codgalleriap, String autore, int codDispositivo, String percorsoFoto) throws SQLException, FileNotFoundException {
        int codFoto = -1;
        FotoDAO u = new FotoImplementazionePostgresDAO();
        try{
            codFoto = u.inserisciFotoDB(privata, rimossa, dataScatto, codgalleriap, autore, codDispositivo, percorsoFoto);
            if (codFoto == 1){
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

    public ArrayList<String> getDisp (String username) throws SQLException{
        ArrayList<String> dispositivi = null;
        DispositivoDAO d = new DispositivoImplementazionePostgresDAO();
        try{
            dispositivi = d.getNomeDispDB(username);
        }catch(SQLException s){
            s.printStackTrace();
            throw new SQLException();
        }
        dispositivi.add("<Aggiungi Dispositivo>");
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

}
