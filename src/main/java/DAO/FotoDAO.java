package DAO;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public interface FotoDAO {

    ArrayList<ImageIcon> recuperaFotoDB (String username) throws SQLException;
    int inserisciFotoDB (boolean privata, boolean rimossa, Date dataScatto, int codgalleriap, String autore, int codDispositivo, String percorsoFoto) throws SQLException, FileNotFoundException;
}
