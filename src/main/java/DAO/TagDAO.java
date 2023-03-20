package DAO;

import java.sql.SQLException;

/**
 * The interface Tag dao.
 */
public interface TagDAO {
    /**
     * Aggiungi tag db.
     *
     * @param username the username
     * @param codFoto  the cod foto
     * @throws SQLException the sql exception
     */
    public void aggiungiTagDB (String username, int codFoto) throws SQLException;
}
