package DAO;

import java.sql.SQLException;

public interface PartecipazioneDAO {
    void aggiungiPartecipazioneDB (String username, int codGalleriaC) throws SQLException;
}
