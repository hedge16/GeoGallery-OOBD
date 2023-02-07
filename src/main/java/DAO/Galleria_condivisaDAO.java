package DAO;

import java.sql.SQLException;

public interface Galleria_condivisaDAO {
    int creaGalleriaCondivisaDB (String fondatore, String[] cofondatori, String nomeGalleria) throws SQLException;

}
