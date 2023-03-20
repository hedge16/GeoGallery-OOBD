package DAO;

import Model.Dispositivo;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * The interface Dispositivo dao.
 */
public interface DispositivoDAO {

    /**
     * Gets all disp db.
     *
     * @param username the username
     * @return the all disp db
     * @throws SQLException the sql exception
     */
    ArrayList<Dispositivo> getAllDispDB (String username) throws SQLException;

    /**
     * Add disp db dispositivo.
     *
     * @param username the username
     * @param nomeDisp the nome disp
     * @return the dispositivo
     * @throws SQLException the sql exception
     */
    Dispositivo addDispDB (String username, String nomeDisp) throws SQLException;

    /**
     * Gets cod disp db.
     *
     * @param nomeDisp the nome disp
     * @param username the username
     * @return the cod disp db
     * @throws SQLException the sql exception
     */
    int getCodDispDB (String nomeDisp, String username) throws SQLException;

    /**
     * Gets dispositivo.
     *
     * @param codDisp the cod disp
     * @return the dispositivo
     * @throws SQLException the sql exception
     */
    Dispositivo getDispositivo (int codDisp) throws SQLException;
}
