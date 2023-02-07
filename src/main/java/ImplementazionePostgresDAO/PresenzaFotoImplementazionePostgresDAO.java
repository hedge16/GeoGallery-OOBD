package ImplementazionePostgresDAO;

import DAO.PresenzaFotoDAO;
import Database.ConnessioneDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PresenzaFotoImplementazionePostgresDAO implements PresenzaFotoDAO {

    private Connection connection;

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
}
