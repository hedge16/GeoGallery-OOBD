package ImplementazionePostgresDAO;

import DAO.PresenzaLuogoDAO;
import Database.ConnessioneDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PresenzaLuogoImplementazionePostgresDAO implements PresenzaLuogoDAO {

    private Connection connection;

    public PresenzaLuogoImplementazionePostgresDAO() {
        try{
            connection = ConnessioneDatabase.getInstance().connection;
        } catch (SQLException s) {
            s.printStackTrace();
        }
    }

    @Override
    public void aggiungiPresenzaLuogo(int codFoto, int codLuogo) throws SQLException {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO galleria_schema.presenzaLuogo VALUES (?,?);");
            ps.setInt(1, codFoto);
            ps.setInt(2, codLuogo);
            ps.executeUpdate();
            connection.close();
        } catch (SQLException s) {
            s.printStackTrace();
        }

    }
}
