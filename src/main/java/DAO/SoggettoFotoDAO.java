package DAO;

import java.sql.SQLException;

public interface SoggettoFotoDAO {
    void aggiungiSoggettoFotoDB (String categoria, String nomeSogg) throws SQLException;
}
