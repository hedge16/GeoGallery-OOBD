package DAO;

import java.sql.SQLException;

public interface LuogoDAO {
    void aggiungiLuogoDB (double latitudine, double longitudine, String nome) throws SQLException;
}
