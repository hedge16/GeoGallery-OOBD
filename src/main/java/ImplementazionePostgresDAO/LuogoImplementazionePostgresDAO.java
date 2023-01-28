package ImplementazionePostgresDAO;

import DAO.LuogoDAO;
import Database.ConnessioneDatabase;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LuogoImplementazionePostgresDAO implements LuogoDAO {
    private Connection connection;
    public LuogoImplementazionePostgresDAO () {
        try{
            connection = ConnessioneDatabase.getInstance().connection;
        }catch( SQLException s){
            s.printStackTrace();
        }
    }


    @Override
    public int aggiungiLuogoDB(double latitudine, double longitudine, String nome) throws SQLException {
        try{
            PreparedStatement ps = connection.prepareStatement("INSERT INTO galleria_schema.luogo VALUES (DEFAULT,?,?,?);");
            ps.setDouble(1, latitudine);
            ps.setDouble(2, longitudine);
            ps.setString(3, nome);
            ps.executeUpdate();

            ps = connection.prepareStatement("SELECT codLuogo FROM galleria_schema.luogo ORDER BY codLuogo DESC LIMIT 1;");
            int codLuogo = -1;
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                codLuogo = rs.getInt(1);
            }
            connection.close();
            return codLuogo;
        } catch (SQLException s) {
            s.printStackTrace();
            throw new SQLException();
        }
    }
}
