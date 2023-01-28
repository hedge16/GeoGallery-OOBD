package ImplementazionePostgresDAO;

import DAO.SoggettoFotoDAO;
import Database.ConnessioneDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SoggettoFotoImplementazionePostgresDAO implements SoggettoFotoDAO {
    private Connection connection;
    public SoggettoFotoImplementazionePostgresDAO (){
        try{
            connection = ConnessioneDatabase.getInstance().connection;
        }catch (SQLException s){
            s.printStackTrace();
        }
    }


    @Override
    public void aggiungiSoggettoFotoDB(String categoria, String nomeSogg) throws SQLException {
        try{
            PreparedStatement ps = connection.prepareStatement("INSERT INTO galleria_schema.soggettofoto VALUES (DEFAULT,?,'"+categoria+"');");
            ps.setString(1, nomeSogg);
            ps.executeUpdate();
            connection.close();
        } catch (SQLException s){
            s.printStackTrace();
            throw new SQLException();
        }
    }
}
