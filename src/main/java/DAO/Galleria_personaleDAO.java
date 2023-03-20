package DAO;

import java.sql.SQLException;

/**
 * The interface Galleria personale dao.
 */
public interface Galleria_personaleDAO {

    /**
     * Gets cod gdb.
     *
     * @param username the username
     * @return the cod gdb
     * @throws SQLException the sql exception
     */
    int getCodGDB (String username) throws SQLException;


}
