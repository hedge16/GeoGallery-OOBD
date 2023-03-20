package DAO;

import Model.SoggettoFoto;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * The interface Soggetto foto dao.
 */
public interface SoggettoFotoDAO {
    /**
     * Aggiungi soggetto foto db int.
     *
     * @param categoria the categoria
     * @param nomeSogg  the nome sogg
     * @return the int
     * @throws SQLException the sql exception
     */
    int aggiungiSoggettoFotoDB (String categoria, String nomeSogg) throws SQLException;

    /**
     * Recupera tutti soggetti db array list.
     *
     * @return the array list
     * @throws SQLException the sql exception
     */
    ArrayList<SoggettoFoto> recuperaTuttiSoggettiDB () throws SQLException;

    /**
     * Recupera soggetti db array list.
     *
     * @param codSoggetti the cod soggetti
     * @return the array list
     * @throws SQLException the sql exception
     */
    ArrayList<SoggettoFoto> recuperaSoggettiDB (ArrayList<Integer> codSoggetti) throws SQLException;
}
