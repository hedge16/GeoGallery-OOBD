package DAO;

import Model.Utente;
import org.postgresql.util.PSQLException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 * The interface Utente dao.
 */
public interface UtenteDAO {

    /**
     * Aggiungi utente.
     *
     * @param nome     the nome
     * @param cognome  the cognome
     * @param username the username
     * @param password the password
     * @param email    the email
     * @param d        the d
     * @throws PSQLException the psql exception
     */
    void aggiungiUtente(String nome, String cognome, String username, String password, String email, Date d) throws PSQLException;

    /**
     * Leggi utenti array list.
     *
     * @return the array list
     * @throws SQLException the sql exception
     */
    ArrayList<Utente> leggiUtenti () throws SQLException;

    /**
     * Crea gall ut.
     *
     * @param username the username
     * @throws SQLException the sql exception
     */
    void creaGallUt (String username) throws SQLException;
}
