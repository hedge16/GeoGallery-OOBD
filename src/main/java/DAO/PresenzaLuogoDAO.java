package DAO;

import java.sql.SQLException;

/**
 * The interface Presenza luogo dao.
 */
public interface PresenzaLuogoDAO {
    /**
     * Aggiungi presenza luogo.
     *
     * @param codFoto  the cod foto
     * @param codLuogo the cod luogo
     * @throws SQLException the sql exception
     */
    void aggiungiPresenzaLuogo (int codFoto, int codLuogo) throws SQLException;

}
