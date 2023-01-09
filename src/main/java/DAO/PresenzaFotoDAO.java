package DAO;

import java.sql.SQLException;

public interface PresenzaFotoDAO {
    void aggiungiPresenzaDB (int codFoto, int codGalleriaC) throws SQLException;
}
