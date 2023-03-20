package Model;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Date;

/**
 * The type Foto.
 */
public class Foto {
    private int codFoto;
    private boolean privata;
    private boolean rimossa;
    private Date dataScatto;
    private int codGalleriap;
    private String autore;
    private Utente utente;
    private int codDispositivo;
    private Dispositivo dispositivo = null;
    private ArrayList<SoggettoFoto> soggetti;
    private ImageIcon foto;
    private ArrayList<Utente> tags;
    private String[] tagsUsernames;
    private Luogo luogo = null;
    /**
     * The Gallerie condivise.
     */
    ArrayList<GalleriaCondivisa> gallerieCondivise;
    /**
     * The Galleria personale.
     */
    GalleriaPersonale galleriaPersonale;


    /**
     * Costruttore della classe Foto
     *
     * @param codFoto        the cod foto
     * @param privata        boolean che indica se la foto è privata
     * @param rimossa        boolean che indica se la foto è stata rimossa
     * @param d              data dello scatto
     * @param codGalleria    codice della galleria
     * @param autore         autore della foto
     * @param codDispositivo codice del dispositivo con cui è stata scattata la foto
     * @param foto           immagine della foto
     */
    public Foto(int codFoto, boolean privata, boolean rimossa, Date d, int codGalleria, String autore, int codDispositivo, ImageIcon foto) {
        this.codFoto = codFoto;
        this.privata = privata;
        this.rimossa = rimossa;
        this.dataScatto = d;
        this.codGalleriap = codGalleria;
        this.gallerieCondivise = new ArrayList<>();
        this.autore = autore;
        this.codDispositivo = codDispositivo;
        this.foto = foto;
    }

    /**
     * Is privata boolean.
     *
     * @return the boolean
     */
    public boolean isPrivata() {
        return privata;
    }

    /**
     * Is rimossa boolean.
     *
     * @return the boolean
     */
    public boolean isRimossa() {
        return rimossa;
    }

    /**
     * Sets rimossa.
     *
     * @param rimossa the rimossa
     */
    public void setRimossa(boolean rimossa) {
        this.rimossa = rimossa;
    }

    /**
     * Gets cod galleria.
     *
     * @return the cod galleria
     */
    public int getCodGalleria() {
        return codGalleriap;
    }

    /**
     * Sets cod galleria.
     *
     * @param codGalleria the cod galleria
     */
    public void setCodGalleria(int codGalleria) {
        this.codGalleriap = codGalleria;
    }

    /**
     * Gets cod dispositivo.
     *
     * @return the cod dispositivo
     */
    public int getCodDispositivo() {
        return codDispositivo;
    }

    /**
     * Sets cod dispositivo.
     *
     * @param dispositivo the dispositivo
     */
    public void setCodDispositivo(int dispositivo) {
        this.codDispositivo = dispositivo;
    }

    /**
     * Aggiungi soggetto.
     *
     * @param idSogg    the id sogg
     * @param nome      the nome
     * @param categoria the categoria
     */
    public void aggiungiSoggetto (int idSogg, String nome, String categoria) {
        soggetti.add(new SoggettoFoto(idSogg, nome, categoria));
    };

    /**
     * Get foto image icon.
     *
     * @return the image icon
     */
    public ImageIcon getFoto (){
        return foto;
    }

    /**
     * Sets foto.
     *
     * @param foto the foto
     */
    public void setFoto (ImageIcon foto) {
        this.foto = foto;
    }

    /**
     * Gets cod foto.
     *
     * @return the cod foto
     */
    public int getCodFoto() {
        return codFoto;
    }

    /**
     * Sets cod foto.
     *
     * @param codFoto the cod foto
     */
    public void setCodFoto(int codFoto) {
        this.codFoto = codFoto;
    }

    /**
     * Sets privata.
     *
     * @param privata the privata
     */
    public void setPrivata(boolean privata) {
        this.privata = privata;
    }

    /**
     * Gets data scatto.
     *
     * @return the data scatto
     */
    public Date getDataScatto() {
        return dataScatto;
    }

    /**
     * Sets data scatto.
     *
     * @param dataScatto the data scatto
     */
    public void setDataScatto(Date dataScatto) {
        this.dataScatto = dataScatto;
    }

    /**
     * Gets autore.
     *
     * @return the autore
     */
    public String getAutore() {
        return autore;
    }

    /**
     * Sets autore.
     *
     * @param autore the autore
     */
    public void setAutore(String autore) {
        this.autore = autore;
    }

    /**
     * Gets soggetti.
     *
     * @return the soggetti
     */
    public ArrayList<SoggettoFoto> getSoggetti() {
        return soggetti;
    }

    /**
     * Sets soggetti.
     *
     * @param soggetti the soggetti
     */
    public void setSoggetti(ArrayList<SoggettoFoto> soggetti) {
        this.soggetti = soggetti;
    }

    /**
     * Sets tags.
     *
     * @param tags the tags
     */
    public void setTags (ArrayList<Utente> tags) {
        this.tags = tags;
    }

    /**
     * Gets tags.
     *
     * @return the tags
     */
    public ArrayList<Utente> getTags () {
        return tags;
    }

    /**
     * Sets luogo.
     *
     * @param luogo the luogo
     */
    public void setLuogo(Luogo luogo) {
        this.luogo = luogo;
    }

    /**
     * Gets luogo.
     *
     * @return the luogo
     */
    public Luogo getLuogo () {
        return luogo;
    }

    /**
     * Sets galleria personale.
     *
     * @param galleriaPersonale the galleria personale
     */
    public void setGalleriaPersonale(GalleriaPersonale galleriaPersonale) {
        this.galleriaPersonale = galleriaPersonale;
    }

    /**
     * Gets galleria personale.
     *
     * @return the galleria personale
     */
    public GalleriaPersonale getGalleriaPersonale() {
        return galleriaPersonale;
    }

    /**
     * Sets gallerie condivise.
     *
     * @param gallerieCondivise the gallerie condivise
     */
    public void setGallerieCondivise(ArrayList<GalleriaCondivisa> gallerieCondivise) {
        this.gallerieCondivise = gallerieCondivise;
    }

    /**
     * Gets gallerie condivise.
     *
     * @return the gallerie condivise
     */
    public ArrayList<GalleriaCondivisa> getGallerieCondivise() {
        return gallerieCondivise;
    }

    /**
     * Sets utente.
     *
     * @param utente the utente
     */
    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    /**
     * Gets utente.
     *
     * @return the utente
     */
    public Utente getUtente() {
        return utente;
    }

    /**
     * Aggiungi tag.
     *
     * @param tag the tag
     */
    public void aggiungiTag (Utente tag) {
        tags.add(tag);
    }

    /**
     * Aggiungi galleria condivisa.
     *
     * @param galleriaCondivisa the galleria condivisa
     */
    public void aggiungiGalleriaCondivisa (GalleriaCondivisa galleriaCondivisa) {
        gallerieCondivise.add(galleriaCondivisa);
    }

    /**
     * Rimuovi galleria condivisa.
     *
     * @param galleriaCondivisa the galleria condivisa
     */
    public void rimuoviGalleriaCondivisa (GalleriaCondivisa galleriaCondivisa) {
        gallerieCondivise.remove(galleriaCondivisa);
    }

    /**
     * Rimuovi tag.
     *
     * @param tag the tag
     */
    public void rimuoviTag (Utente tag) {
        tags.remove(tag);
    }

    /**
     * Rimuovi soggetto.
     *
     * @param soggetto the soggetto
     */
    public void rimuoviSoggetto (SoggettoFoto soggetto) {
        soggetti.remove(soggetto);
    }

    /**
     * Aggiungi soggetto.
     *
     * @param soggetto the soggetto
     */
    public void aggiungiSoggetto (SoggettoFoto soggetto) {
        soggetti.add(soggetto);
    }

    /**
     * Aggiungi luogo.
     *
     * @param luogo the luogo
     */
    public void aggiungiLuogo (Luogo luogo) {
        this.luogo = luogo;
    }

    /**
     * Rimuovi luogo.
     *
     * @param luogo the luogo
     */
    public void rimuoviLuogo (Luogo luogo) {
        this.luogo = null;
    }

    /**
     * Aggiungi galleria personale.
     *
     * @param galleriaPersonale the galleria personale
     */
    public void aggiungiGalleriaPersonale (GalleriaPersonale galleriaPersonale) {
        this.galleriaPersonale = galleriaPersonale;
    }

    /**
     * Rimuovi galleria personale.
     *
     * @param galleriaPersonale the galleria personale
     */
    public void rimuoviGalleriaPersonale (GalleriaPersonale galleriaPersonale) {
        this.galleriaPersonale = null;
    }

    /**
     * Aggiungi dispositivo.
     *
     * @param dispositivo the dispositivo
     */
    public void aggiungiDispositivo (Dispositivo dispositivo) {
        this.dispositivo = dispositivo;
    }

    /**
     * Rimuovi dispositivo.
     *
     * @param dispositivo the dispositivo
     */
    public void rimuoviDispositivo (Dispositivo dispositivo) {
        this.dispositivo = null;
    }

    /**
     * Gets dispositivo.
     *
     * @return the dispositivo
     */
    public Dispositivo getDispositivo() {
        return dispositivo;
    }

    /**
     * Sets dispositivo.
     *
     * @param dispositivo the dispositivo
     */
    public void setDispositivo(Dispositivo dispositivo) {
        this.dispositivo = dispositivo;
    }

    /**
     * Get tags usernames string [ ].
     *
     * @return the string [ ]
     */
    public String[] getTagsUsernames() {
        return tagsUsernames;
    }

    /**
     * Sets tags usernames.
     *
     * @param tagsUsernames the tags usernames
     */
    public void setTagsUsernames(String[] tagsUsernames) {
        this.tagsUsernames = tagsUsernames;
    }



}
