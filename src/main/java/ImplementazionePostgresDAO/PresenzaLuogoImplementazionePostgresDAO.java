package ImplementazionePostgresDAO;

import DAO.PresenzaLuogoDAO;
import Database.ConnessioneDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * The type Presenza luogo implementazione postgres dao.
 */
public class PresenzaLuogoImplementazionePostgresDAO implements PresenzaLuogoDAO {

    private Connection connection;

    /**
     * Costruttore della classe
     */
    public PresenzaLuogoImplementazionePostgresDAO() {
        try{
            connection = ConnessioneDatabase.getInstance().connection;
        } catch (SQLException s) {
            s.printStackTrace();
        }
    }

    /**
     * Metodo che aggiunge una presenza di luogo
     * @param codFoto codice della foto
     * @param codLuogo codice del luogo
     * @throws SQLException
     */
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
