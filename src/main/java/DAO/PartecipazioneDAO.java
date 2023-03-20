package DAO;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * The interface Partecipazione dao.
 */
public interface PartecipazioneDAO {
    /**
     * Aggiungi partecipazione db.
     *
     * @param username     the username
     * @param codGalleriaC the cod galleria c
     * @throws SQLException the sql exception
     */
    void aggiungiPartecipazioneDB (String username, int codGalleriaC) throws SQLException;
}
