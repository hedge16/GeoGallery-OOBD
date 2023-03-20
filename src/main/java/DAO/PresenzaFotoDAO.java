package DAO;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * The interface Presenza foto dao.
 */
public interface PresenzaFotoDAO {
    /**
     * Aggiungi presenza foto db.
     *
     * @param codFoto      the cod foto
     * @param codGalleriaC the cod galleria c
     * @throws SQLException the sql exception
     */
    void aggiungiPresenzaFotoDB (int codFoto, int codGalleriaC) throws SQLException;

    /**
     * Gets foto galleria c.
     *
     * @param codGalleriaC the cod galleria c
     * @return the foto galleria c
     * @throws SQLException the sql exception
     */
    ArrayList<Integer> getFotoGalleriaC (int codGalleriaC) throws SQLException;
}
