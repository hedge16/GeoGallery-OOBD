package DAO;

import Model.Luogo;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * The interface Luogo dao.
 */
public interface LuogoDAO {
    /**
     * Aggiungi luogo db int.
     *
     * @param latitudine  the latitudine
     * @param longitudine the longitudine
     * @param nome        the nome
     * @return the int
     * @throws SQLException the sql exception
     */
    int aggiungiLuogoDB (double latitudine, double longitudine, String nome) throws SQLException;

    /**
     * Recupera tutti luoghi db array list.
     *
     * @return the array list
     * @throws SQLException the sql exception
     */
    ArrayList<Luogo> recuperaTuttiLuoghiDB () throws SQLException;

    /**
     * Gets luogo from foto.
     *
     * @param codfoto the codfoto
     * @return the luogo from foto
     * @throws SQLException the sql exception
     */
    Luogo getLuogoFromFoto (int codfoto) throws SQLException;

    /**
     * Ricerca luoghi top 3 array list.
     *
     * @return the array list
     * @throws SQLException the sql exception
     */
    ArrayList<Luogo> ricercaLuoghiTop3 () throws SQLException;
}
