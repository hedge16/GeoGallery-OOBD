package DAO;

import Model.Utente;
import org.postgresql.util.PSQLException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public interface UtenteDAO {

    void aggiungiUtente(String nome, String cognome, String username, String password, String email, Date d) throws PSQLException;
    ArrayList<Utente> leggiUtenti () throws SQLException;
    void creaGallUt (String username) throws SQLException;
}
