package DAO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PartecipazioneDAO {
    void aggiungiPartecipazioneDB (String username, int codGalleriaC) throws SQLException;
}
