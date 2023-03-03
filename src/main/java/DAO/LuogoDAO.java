package DAO;

import Model.Luogo;

import java.sql.SQLException;
import java.util.ArrayList;

public interface LuogoDAO {
    int aggiungiLuogoDB (double latitudine, double longitudine, String nome) throws SQLException;
    ArrayList<Luogo> recuperaTuttiLuoghiDB () throws SQLException;
    Luogo getLuogoFromFoto (int codfoto) throws SQLException;
    ArrayList<Luogo> ricercaLuoghiTop3 () throws SQLException;
}
