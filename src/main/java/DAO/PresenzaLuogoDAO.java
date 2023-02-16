package DAO;

import java.sql.SQLException;

public interface PresenzaLuogoDAO {
    void aggiungiPresenzaLuogo (int codFoto, int codLuogo) throws SQLException;

}
