package ImplementazionePostgresDAO;

import DAO.Galleria_condivisaDAO;
import Database.ConnessioneDatabase;
import Model.GalleriaCondivisa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * The type Galleria condivisa implementazione postgres dao.
 */
public class GalleriaCondivisaImplementazionePostgresDAO implements Galleria_condivisaDAO {

    private Connection connection;

    /**
     * Instantiates a new Galleria condivisa implementazione postgres dao.
     */
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

    @Override
    public ArrayList<GalleriaCondivisa> recuperaGallerieCondivise(String username) throws SQLException {
        ArrayList<GalleriaCondivisa> gallerie = new ArrayList<>();
        PreparedStatement ps = connection.prepareStatement("SELECT codgalleriac FROM galleria_schema.partecipazione WHERE codutente = ?;");
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        ArrayList<Integer> codiciGallerie = new ArrayList<Integer>();
        while (rs.next()) {
            codiciGallerie.add(rs.getInt(1));
        }
        rs.close();
        for (Integer codice : codiciGallerie){
            ps = connection.prepareStatement("SELECT * FROM galleria_schema.galleria_condivisa WHERE codgalleria = ?;");
            ps.setInt(1, codice);
            rs = ps.executeQuery();
            int codGalleria;
            String nomeGalleria;
            GalleriaCondivisa gc = null;
            while (rs.next()){
                codGalleria = rs.getInt(1);
                nomeGalleria = rs.getString(2);
                gc = new GalleriaCondivisa(codGalleria, nomeGalleria);
            }
            rs.close();
            gallerie.add(gc);
        }
        connection.close();
        return gallerie;
    }


}
