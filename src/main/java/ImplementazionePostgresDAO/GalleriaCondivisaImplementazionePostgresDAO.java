package ImplementazionePostgresDAO;

import DAO.Galleria_condivisaDAO;
import Database.ConnessioneDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GalleriaCondivisaImplementazionePostgresDAO implements Galleria_condivisaDAO {

    private Connection connection;

    public GalleriaCondivisaImplementazionePostgresDAO () {
        try {
            connection = ConnessioneDatabase.getInstance().connection;
        } catch (SQLException s) {
            s.printStackTrace();
        }

    }

    @Override
    public int creaGalleriaCondivisaDB(String fondatore, String[] cofondatori, String nomeGalleria) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("INSERT INTO galleria_schema.galleria_condivisa VALUES (DEFAULT, ?);");
        ps.setString(1, nomeGalleria);
        ps.executeUpdate();

        ps = connection.prepareStatement("SELECT codGalleria FROM galleria_schema.galleria_condivisa ORDER BY codGalleria DESC LIMIT 1;");
        ResultSet rs = ps.executeQuery();
        int codGalleria = -1;
        while (rs.next()) {
            codGalleria = rs.getInt(1);
            System.out.println(codGalleria);
        }
        rs.close();

        ps = connection.prepareStatement("INSERT INTO galleria_schema.partecipazione VALUES (?,?);");
        ps.setString(1, fondatore);
        ps.setInt(2, codGalleria);
        ps.executeUpdate();

        for (int i = 0; i < cofondatori.length; i++) {
            ps = connection.prepareStatement("INSERT INTO galleria_schema.partecipazione VALUES (?,?);");
            ps.setString(1, cofondatori[i]);
            ps.setInt(2, codGalleria);
            ps.executeUpdate();
        }
        connection.close();
        return codGalleria;
    }


}
