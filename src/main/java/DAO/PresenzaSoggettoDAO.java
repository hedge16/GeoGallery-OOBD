package DAO;

import java.sql.SQLException;

public interface PresenzaSoggettoDAO {
    void aggiungiPresenzaSoggetto (int codFoto, int codSogg) throws SQLException;
}
