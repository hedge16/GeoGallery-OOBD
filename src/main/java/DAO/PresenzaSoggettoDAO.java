package DAO;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * The interface Presenza soggetto dao.
 */
public interface PresenzaSoggettoDAO {
    /**
     * Aggiungi presenza soggetto.
     *
     * @param codFoto the cod foto
     * @param codSogg the cod sogg
     * @throws SQLException the sql exception
     */
    void aggiungiPresenzaSoggetto (int codFoto, int codSogg) throws SQLException;

    /**
     * Gets cod sogg from cod foto.
     *
     * @param codFoto the cod foto
     * @return the cod sogg from cod foto
     * @throws SQLException the sql exception
     */
    ArrayList<Integer> getCodSoggFromCodFoto (int codFoto) throws SQLException;
}
