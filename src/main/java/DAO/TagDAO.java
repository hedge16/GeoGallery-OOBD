package DAO;

import java.sql.SQLException;

public interface TagDAO {
    public void aggiungiTagDB (String username, int codFoto) throws SQLException;
}
