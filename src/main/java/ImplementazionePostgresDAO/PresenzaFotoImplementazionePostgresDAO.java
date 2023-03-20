package ImplementazionePostgresDAO;

import DAO.PresenzaFotoDAO;
import Database.ConnessioneDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * The type Presenza foto implementazione postgres dao.
 */
public class PresenzaFotoImplementazionePostgresDAO implements PresenzaFotoDAO {

    private Connection connection;

    /**
     * Instantiates a new Presenza foto implementazione postgres dao.
     */
    public PresenzaFotoImplementazionePostgresDAO () {
        try{
            connection = ConnessioneDatabase.getInstance().connection;
        } catch (SQLException s) {
            s.printStackTrace();
        }
    }
    @Override
    public void aggiungiPresenzaFotoDB(int codFoto, int codGalleriaC) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("INSERT INTO galleria_schema.presenzafoto VALUES (?,?);");
        ps.setInt(1, codGalleriaC);
        ps.setInt(2, codFoto);
        ps.executeUpdate();
        connection.close();
    }

    @Override
    public ArrayList<Integer> getFotoGalleriaC(int codGalleriaC) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT codfoto FROM galleria_schema.presenzafoto WHERE codgalleriac = ?;");
        ps.setInt(1, codGalleriaC);
        ResultSet rs = ps.executeQuery();
        ArrayList<Integer> codPhotos = new ArrayList<>();
        while (rs.next()) {
               codPhotos.add(rs.getInt(1));
        }
        rs.close();
        connection.close();
        return codPhotos;
    }

}
