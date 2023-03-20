package Model;
import java.util.ArrayList;
import java.util.Date;

/**
 * The type Utente.
 */
public class Utente {

    private String nome;
    private String cognome;
    private String email;
    private String username;
    private String password;
    /**
     * The Data reg.
     */
    Date dataReg;
    private int codGalleria;
    private GalleriaPersonale galleriaPersonale;
    private ArrayList<Foto> photos;
    private ArrayList<GalleriaCondivisa> gallerieCondivise;
    private ArrayList<Dispositivo> dispositivi;

    /**
     * Instantiates a new Utente.
     *
     * @param name    the name
     * @param surname the surname
     * @param user    the user
     * @param pass    the pass
     * @param email1  the email 1
     * @param d       the d
     */
    public Utente (String name, String surname, String user, String pass, String email1, Date d){
        this.username=user;
        this.password=pass;
        this.dataReg=d;
        this.nome=name;
        this.cognome=surname;
        this.email=email1;
    }


    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets username.
     *
     * @param username the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password.
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets data reg.
     *
     * @return the data reg
     */
    public Date getDataReg() {
        return dataReg;
    }

    /**
     * Gets nome.
     *
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Sets nome.
     *
     * @param nome the nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Gets cognome.
     *
     * @return the cognome
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * Sets cognome.
     *
     * @param cognome the cognome
     */
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets cod galleria.
     *
     * @return the cod galleria
     */
    public int getCodGalleria() {
        return codGalleria;
    }

    /**
     * Sets cod galleria.
     *
     * @param codGalleria the cod galleria
     */
    public void setCodGalleria(int codGalleria) {
        this.codGalleria = codGalleria;
    }

    /**
     * Gets photos.
     *
     * @return the photos
     */
    public ArrayList<Foto> getPhotos() {
        return photos;
    }

    /**
     * Sets photos.
     *
     * @param photos the photos
     */
    public void setPhotos(ArrayList<Foto> photos) {
        this.photos = photos;
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
     * Sets gallerie condivise.
     *
     * @param gallerieCondivise the gallerie condivise
     */
    public void setGallerieCondivise(ArrayList<GalleriaCondivisa> gallerieCondivise) {
        this.gallerieCondivise = gallerieCondivise;
    }

    /**
     * Gets dispositivi.
     *
     * @return the dispositivi
     */
    public ArrayList<Dispositivo> getDispositivi() {
        return dispositivi;
    }

    /**
     * Sets dispositivi.
     *
     * @param dispositivi the dispositivi
     */
    public void setDispositivi(ArrayList<Dispositivo> dispositivi) {
        this.dispositivi = dispositivi;
    }

    /**
     * Add dispositivo.
     *
     * @param d the d
     */
    public void addDispositivo(Dispositivo d){
        this.dispositivi.add(d);
    }

    /**
     * Remove dispositivo.
     *
     * @param d the d
     */
    public void removeDispositivo(Dispositivo d){
        this.dispositivi.remove(d);
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
     * Sets galleria personale.
     *
     * @param galleriaPersonale the galleria personale
     */
    public void setGalleriaPersonale(GalleriaPersonale galleriaPersonale) {
        this.galleriaPersonale = galleriaPersonale;
    }

    /**
     * Add galleria condivisa.
     *
     * @param g the g
     */
    public void addGalleriaCondivisa(GalleriaCondivisa g){
        this.gallerieCondivise.add(g);
    }


}