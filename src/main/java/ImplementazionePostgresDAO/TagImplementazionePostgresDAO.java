package ImplementazionePostgresDAO;

import DAO.TagDAO;
import Database.ConnessioneDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TagImplementazionePostgresDAO implements TagDAO {

    private Connection connection;
    public TagImplementazionePostgresDAO (){
        try{
            connection = ConnessioneDatabase.getInstance().connection;
        }catch (SQLException s){
            s.printStackTrace();
        }
    }


    @Override
    public void aggiungiTagDB(String username, int codFoto) throws SQLException {
        try{
            PreparedStatement ps = connection.prepareStatement("INSERT INTO galleria_schema.tag VALUES (?,?);");
            ps.setInt(1, codFoto);
            ps.setString(2, username);
            ps.executeUpdate();
            connection.close();
        } catch (SQLException s){
            s.printStackTrace();
            throw new SQLException();
        }
    }
}
