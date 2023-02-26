package DAO;

import Model.Dispositivo;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DispositivoDAO {

    ArrayList<Dispositivo> getAllDispDB (String username) throws SQLException;
    void addDispDB (String username, String nomeDisp) throws SQLException;
    int getCodDispDB (String nomeDisp, String username) throws SQLException;
    Dispositivo getDispositivo (int codDisp) throws SQLException;
}
