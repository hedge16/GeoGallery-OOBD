package DAO;

import Model.SoggettoFoto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SoggettoFotoDAO {
    int aggiungiSoggettoFotoDB (String categoria, String nomeSogg) throws SQLException;
    ArrayList<SoggettoFoto> recuperaTuttiSoggettiDB () throws SQLException;
}
