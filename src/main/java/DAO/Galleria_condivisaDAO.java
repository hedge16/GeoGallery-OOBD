package DAO;

import Model.GalleriaCondivisa;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * The interface Galleria condivisa dao.
 */
public interface Galleria_condivisaDAO {
    /**
     * Crea galleria condivisa db int.
     *
     * @param fondatore    the fondatore
     * @param cofondatori  the cofondatori
     * @param nomeGalleria the nome galleria
     * @return the int
     * @throws SQLException the sql exception
     */
    int creaGalleriaCondivisaDB (String fondatore, String[] cofondatori, String nomeGalleria) throws SQLException;

    /**
     * Recupera gallerie condivise array list.
     *
     * @param username the username
     * @return the array list
     * @throws SQLException the sql exception
     */
    ArrayList<GalleriaCondivisa> recuperaGallerieCondivise (String username) throws SQLException;

}
