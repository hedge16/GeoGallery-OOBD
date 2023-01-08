package DAO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DispositivoDAO {

    ArrayList<String> getNomeDispDB (String username) throws SQLException;
    void addDispDB (String username, String nomeDisp) throws SQLException;
    int getCodDispDB (String nomeDisp, String username) throws SQLException;
}
