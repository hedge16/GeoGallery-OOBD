package ImplementazionePostgresDAO;

import DAO.PresenzaSoggettoDAO;
import Database.ConnessioneDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * The type Presenza soggetto implementazione postgres dao.
 */
public class PresenzaSoggettoImplementazionePostgresDAO implements PresenzaSoggettoDAO {
    private Connection connection;

    /**
     * Instantiates a new Presenza soggetto implementazione postgres dao.
     */
    public PresenzaSoggettoImplementazionePostgresDAO() {
        try{
            connection = ConnessioneDatabase.getInstance().connection;
        } catch (SQLException s){
            s.printStackTrace();
        }
    }

    @Override
    public void aggiungiPresenzaSoggetto(int codFoto, int codSogg) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("INSERT INTO galleria_schema.presenzaSoggetto VALUES (?,?);");
        ps.setInt(1, codFoto);
        ps.setInt(2, codSogg);
        ps.executeUpdate();
        connection.close();
    }

    @Override
    public ArrayList<Integer> getCodSoggFromCodFoto(int codFoto) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT codSogg FROM galleria_schema.presenzaSoggetto WHERE codFoto = ?;");
        ps.setInt(1, codFoto);
        ArrayList<Integer> codSogg = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            codSogg.add(rs.getInt(1));
        }
        connection.close();
        return codSogg;
    }
}
