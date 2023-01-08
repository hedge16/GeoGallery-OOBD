package ImplementazionePostgresDAO;

import DAO.DispositivoDAO;
import Database.ConnessioneDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DispositivoImplementazionePostgresDAO implements DispositivoDAO {

    private Connection connection;

    public DispositivoImplementazionePostgresDAO(){

        try{
            connection = ConnessioneDatabase.getInstance().connection;

        }catch(SQLException s){
            s.printStackTrace();
        }

    }


    @Override
    public ArrayList<String> getNomeDispDB(String username) throws SQLException {
        ArrayList<String> dispositivi = new ArrayList<>();
        PreparedStatement ps = connection.prepareStatement("SELECT nomeDisp FROM galleria_schema.dispositivo where proprietario = ?;");
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            dispositivi.add(rs.getString(1));
        }
        rs.close();
        connection.close();
        return dispositivi;
    }

    @Override
    public void addDispDB(String username, String nomeDisp) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("INSERT INTO galleria_schema.dispositivo VALUES (DEFAULT, ?, ?);");
        ps.setString(1, nomeDisp);
        ps.setString(2, username);
        ps.executeUpdate();
        connection.close();
    }

    @Override
    public int getCodDispDB(String nomeDisp, String username) throws SQLException {
        int codDisp=-1;
        try{
            PreparedStatement ps = connection.prepareStatement("SELECT codDisp FROM galleria_schema.dispositivo WHERE nomeDisp = ? AND proprietario = ?;");
            ps.setString(1, nomeDisp);
            ps.setString(2, username);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                codDisp = rs.getInt(1);
            }
            rs.close();
            connection.close();
        } catch (SQLException s) {
            s.printStackTrace();
        }
        return codDisp;
    }


}
