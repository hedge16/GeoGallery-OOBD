package ImplementazionePostgresDAO;

import DAO.LuogoDAO;
import Database.ConnessioneDatabase;
import Model.Luogo;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * The type Luogo implementazione postgres dao.
 */
public class LuogoImplementazionePostgresDAO implements LuogoDAO {
    private Connection connection;

    /**
     * Instantiates a new Luogo implementazione postgres dao.
     */
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
            rs.close();
            connection.close();
            return codLuogo;
        } catch (SQLException s) {
            s.printStackTrace();
            throw new SQLException();
        }
    }

    @Override
    public ArrayList<Luogo> recuperaTuttiLuoghiDB() throws SQLException {
        ArrayList<Luogo> luoghi = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM galleria_schema.luogo");
            ResultSet rs = ps.executeQuery();
            Luogo luogo;
            while (rs.next()) {
                int codLuogo = rs.getInt(1);
                double latitudine = rs.getDouble(2);
                double longitudine = rs.getDouble(3);
                String nomeLuogo = rs.getString(4);
                luogo = new Luogo(codLuogo, nomeLuogo, latitudine, longitudine);
                luoghi.add(luogo);
            }
            rs.close();
            connection.close();
            return luoghi;
        } catch (SQLException s){
            s.printStackTrace();
        }
        return luoghi;
    }

    @Override
    public Luogo getLuogoFromFoto(int codfoto) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT codluogo FROM galleria_schema.presenzaluogo WHERE codfoto = ?;");
        ps.setInt(1, codfoto);
        ResultSet rs = ps.executeQuery();
        int codLuogo = -1;
        while (rs.next()){
            codLuogo = rs.getInt(1);
        }
        rs.close();

        ps = connection.prepareStatement("SELECT * FROM galleria_schema.luogo WHERE codLuogo = ?;");
        ps.setInt(1, codLuogo);
        rs = ps.executeQuery();
        Luogo luogo = null;
        double latitudine;
        double longitudine;
        String nomeLuogo;
        while (rs.next()){
            codLuogo = rs.getInt(1);
            latitudine = rs.getDouble(2);
            longitudine = rs.getDouble(3);
            nomeLuogo = rs.getString(4);
            luogo = new Luogo(codLuogo, nomeLuogo, latitudine, longitudine);
        }
        rs.close();
        connection.close();
        return luogo;
    }

    @Override
    public ArrayList<Luogo> ricercaLuoghiTop3() throws SQLException {
        ArrayList<Luogo> luoghi = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM galleria_schema.top3;");
            ResultSet rs = ps.executeQuery();
            Luogo luogo;
            while (rs.next()) {
                int codLuogo = rs.getInt(1);
                ps = connection.prepareStatement("SELECT * FROM galleria_schema.luogo WHERE codLuogo = ?;");
                ps.setInt(1, codLuogo);
                ResultSet rs2 = ps.executeQuery();
                double latitudine;
                double longitudine;
                String nomeLuogo;
                while (rs2.next()){
                    codLuogo = rs2.getInt(1);
                    latitudine = rs2.getDouble(2);
                    longitudine = rs2.getDouble(3);
                    nomeLuogo = rs2.getString(4);
                    luogo = new Luogo(codLuogo, nomeLuogo, latitudine, longitudine);
                    luogo.setNumeroFoto(rs.getInt(2));
                    luoghi.add(luogo);
                }
                rs2.close();

            }
            rs.close();
            connection.close();
            return luoghi;
        } catch (SQLException s){
            s.printStackTrace();
        }
        return luoghi;
    }
}
