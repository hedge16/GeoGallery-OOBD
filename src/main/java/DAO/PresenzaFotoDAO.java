package DAO;

import java.sql.SQLException;

public interface PresenzaFotoDAO {
    void aggiungiPresenzaFotoDB (int codFoto, int codGalleriaC) throws SQLException;
}
