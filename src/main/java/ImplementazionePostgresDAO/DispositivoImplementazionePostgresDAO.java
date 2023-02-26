package ImplementazionePostgresDAO;

import DAO.DispositivoDAO;
import Database.ConnessioneDatabase;
import Model.Dispositivo;

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
    public ArrayList<Dispositivo> getAllDispDB(String username) throws SQLException {
        ArrayList<Dispositivo> dispositivi = new ArrayList<>();
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM galleria_schema.dispositivo WHERE proprietario = ?;");
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        int codDisp;
        String nomeDisp;
        String proprietario;
        Dispositivo disp;
        while(rs.next()){
            codDisp = rs.getInt("coddisp");
            nomeDisp = rs.getString("nomedisp");
            proprietario = rs.getString("proprietario");
            disp = new Dispositivo(codDisp, nomeDisp, proprietario);
            dispositivi.add(disp);
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

    @Override
    public Dispositivo getDispositivo(int codDisp) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT nomedisp, proprietario FROM galleria_schema.dispositivo WHERE codDisp = ?;");
        ps.setInt(1, codDisp);
        ResultSet rs = ps.executeQuery();
        String nomeDisp;
        String proprietario;
        Dispositivo disp = null;
        while (rs.next()){
            nomeDisp = rs.getString("nomedisp");
            proprietario = rs.getString("proprietario");
            disp = new Dispositivo(codDisp, nomeDisp, proprietario);
        }
        rs.close();
        connection.close();
        return disp;

    }


}
