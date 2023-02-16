package DAO;

import java.sql.SQLException;

public interface SoggettoFotoDAO {
    int aggiungiSoggettoFotoDB (String categoria, String nomeSogg) throws SQLException;
}
