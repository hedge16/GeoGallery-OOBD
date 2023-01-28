package DAO;

import java.sql.SQLException;

public interface LuogoDAO {
    int aggiungiLuogoDB (double latitudine, double longitudine, String nome) throws SQLException;
}
