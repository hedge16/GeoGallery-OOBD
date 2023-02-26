package ImplementazionePostgresDAO;

import DAO.SoggettoFotoDAO;
import Database.ConnessioneDatabase;
import Model.SoggettoFoto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
    public int aggiungiSoggettoFotoDB(String categoria, String nomeSogg) throws SQLException {
        try{
            PreparedStatement ps = connection.prepareStatement("INSERT INTO galleria_schema.soggettofoto VALUES (DEFAULT,?,'"+categoria+"');");
            ps.setString(1, nomeSogg);
            ps.executeUpdate();

            ps = connection.prepareStatement("SELECT codSogg FROM galleria_schema.soggettofoto ORDER BY codSogg DESC LIMIT 1;");
            ResultSet rs = ps.executeQuery();
            int codSogg = -1;
            while (rs.next()){
                codSogg = rs.getInt(1);
            }
            rs.close();
            connection.close();
            return codSogg;
        } catch (SQLException s){
            s.printStackTrace();
            throw new SQLException();
        }
    }

    @Override
    public ArrayList<SoggettoFoto> recuperaTuttiSoggettiDB() throws SQLException {
        ArrayList<SoggettoFoto> soggetti = new ArrayList<>();
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM galleria_schema.soggettofoto;");
        ResultSet rs = ps.executeQuery();

        while (rs.next()){
            int codSogg = rs.getInt(1);
            String nomeSogg = rs.getString(2);
            String categoria = rs.getString(3);
            SoggettoFoto sf = new SoggettoFoto(codSogg, nomeSogg, categoria);
            soggetti.add(sf);
        }
        rs.close();
        connection.close();
        return soggetti;
    }
}
