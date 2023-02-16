package ImplementazionePostgresDAO;

import DAO.PresenzaSoggettoDAO;
import Database.ConnessioneDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PresenzaSoggettoImplementazionePostgresDAO implements PresenzaSoggettoDAO {
    private Connection connection;

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
}
