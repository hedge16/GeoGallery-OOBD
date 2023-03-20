package DAO;

import Model.Foto;
import Model.Luogo;
import Model.SoggettoFoto;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 * The interface Foto dao.
 */
public interface FotoDAO {

    /**
     * Recupera foto db array list.
     *
     * @param username the username
     * @return the array list
     * @throws SQLException the sql exception
     */
    ArrayList<Foto> recuperaFotoDB (String username) throws SQLException;

    /**
     * Inserisci foto db foto.
     *
     * @param privata        the privata
     * @param rimossa        the rimossa
     * @param dataScatto     the data scatto
     * @param codgalleriap   the codgalleriap
     * @param autore         the autore
     * @param codDispositivo the cod dispositivo
     * @param percorsoFoto   the percorso foto
     * @return the foto
     * @throws SQLException          the sql exception
     * @throws FileNotFoundException the file not found exception
     */
    Foto inserisciFotoDB (boolean privata, boolean rimossa, Date dataScatto, int codgalleriap, String autore, int codDispositivo, String percorsoFoto) throws SQLException, FileNotFoundException;

    /**
     * Elimina foto db.
     *
     * @param codFoto the cod foto
     * @throws SQLException the sql exception
     */
    void eliminaFotoDB (int codFoto) throws SQLException;

    /**
     * Gets last foto.
     *
     * @param username the username
     * @return the last foto
     * @throws SQLException the sql exception
     */
    Foto getLastFoto (String username) throws SQLException;

    /**
     * Sets privacy db.
     *
     * @param codfoto the codfoto
     * @param state   the state
     * @throws SQLException the sql exception
     */
    void setPrivacyDB (int codfoto, boolean state) throws SQLException;

    /**
     * Ricerca foto per luogo array list.
     *
     * @param nomeLuogo the nome luogo
     * @return the array list
     * @throws SQLException the sql exception
     */
    ArrayList<Foto> ricercaFotoPerLuogo (String nomeLuogo) throws SQLException;

    /**
     * Ricerca foto per luogo array list.
     *
     * @param nomeLuogo   the nome luogo
     * @param latitudine  the latitudine
     * @param longitudine the longitudine
     * @return the array list
     * @throws SQLException the sql exception
     */
    ArrayList<Foto> ricercaFotoPerLuogo (String nomeLuogo, double latitudine, double longitudine) throws SQLException;

    /**
     * Ricerca foto per soggetto array list.
     *
     * @param categoria the categoria
     * @param nome      the nome
     * @return the array list
     * @throws SQLException the sql exception
     */
    ArrayList<Foto> ricercaFotoPerSoggetto (String categoria, String nome) throws SQLException;

    /**
     * Carica foto foto.
     *
     * @param privata           the privata
     * @param nuovo             the nuovo
     * @param data              the data
     * @param username          the username
     * @param idDispositivo     the id dispositivo
     * @param filePath          the file path
     * @param luogo             the luogo
     * @param soggettiNuovi     the soggetti nuovi
     * @param soggettiEsistenti the soggetti esistenti
     * @param tags              the tags
     * @return the foto
     * @throws SQLException the sql exception
     */
    Foto caricaFoto (boolean privata, boolean nuovo, Date data, String username, int idDispositivo, String filePath, Luogo luogo, ArrayList<SoggettoFoto> soggettiNuovi, ArrayList<SoggettoFoto> soggettiEsistenti, String[] tags) throws SQLException ;

    /**
     * Gets foto galleria c.
     *
     * @param codPhotos the cod photos
     * @return the foto galleria c
     * @throws SQLException the sql exception
     */
    ArrayList<Foto> getFotoGalleriaC (ArrayList<Integer> codPhotos) throws SQLException;

}
