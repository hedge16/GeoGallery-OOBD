package DAO;

import Model.GalleriaCondivisa;

import java.sql.SQLException;
import java.util.ArrayList;

public interface Galleria_condivisaDAO {
    int creaGalleriaCondivisaDB (String fondatore, String[] cofondatori, String nomeGalleria) throws SQLException;
    ArrayList<GalleriaCondivisa> recuperaGallerieCondivise (String username) throws SQLException;

}
