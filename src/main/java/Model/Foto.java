package Model;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Date;

public class Foto {
    private int codFoto;
    private boolean privata;
    private boolean rimossa;
    private Date dataScatto;
    private int codGalleriap;
    private String autore;
    private Utente utente;
    private int codDispositivo;
    private Dispositivo dispositivo;
    private ArrayList<SoggettoFoto> soggetti;
    private ImageIcon foto;
    private ArrayList<Utente> tags;
    private String[] tagsUsernames;
    private Luogo luogo;
    ArrayList<GalleriaCondivisa> gallerieCondivise;
    GalleriaPersonale galleriaPersonale;


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
        this.soggetti = new ArrayList<>();
        this.tags = new ArrayList<>();
    }

    public boolean isPrivata() {
        return privata;
    }

    public boolean isRimossa() {
        return rimossa;
    }

    public void setRimossa(boolean rimossa) {
        this.rimossa = rimossa;
    }

    public int getCodGalleria() {
        return codGalleriap;
    }

    public void setCodGalleria(int codGalleria) {
        this.codGalleriap = codGalleria;
    }

    public int getCodDispositivo() {
        return codDispositivo;
    }

    public void setCodDispositivo(int dispositivo) {
        this.codDispositivo = dispositivo;
    }
    public void aggiungiSoggetto (int idSogg, String nome, String categoria) {
        soggetti.add(new SoggettoFoto(idSogg, nome, categoria));
    };

    public ImageIcon getFoto (){
        return foto;
    }

    public void setFoto (ImageIcon foto) {
        this.foto = foto;
    }
    public int getCodFoto() {
        return codFoto;
    }

    public void setCodFoto(int codFoto) {
        this.codFoto = codFoto;
    }

    public void setPrivata(boolean privata) {
        this.privata = privata;
    }

    public Date getDataScatto() {
        return dataScatto;
    }

    public void setDataScatto(Date dataScatto) {
        this.dataScatto = dataScatto;
    }

    public String getAutore() {
        return autore;
    }

    public void setAutore(String autore) {
        this.autore = autore;
    }

    public ArrayList<SoggettoFoto> getSoggetti() {
        return soggetti;
    }

    public void setSoggetti(ArrayList<SoggettoFoto> soggetti) {
        this.soggetti = soggetti;
    }
    public void setTags (ArrayList<Utente> tags) {
        this.tags = tags;
    }
    public ArrayList<Utente> getTags () {
        return tags;
    }
    public void setLuogo(Luogo luogo) {
        this.luogo = luogo;
    }
    public Luogo getLuogo () {
        return luogo;
    }
    public void setGalleriaPersonale(GalleriaPersonale galleriaPersonale) {
        this.galleriaPersonale = galleriaPersonale;
    }
    public GalleriaPersonale getGalleriaPersonale() {
        return galleriaPersonale;
    }
    public void setGallerieCondivise(ArrayList<GalleriaCondivisa> gallerieCondivise) {
        this.gallerieCondivise = gallerieCondivise;
    }
    public ArrayList<GalleriaCondivisa> getGallerieCondivise() {
        return gallerieCondivise;
    }
    public void setUtente(Utente utente) {
        this.utente = utente;
    }
    public Utente getUtente() {
        return utente;
    }
    public void aggiungiTag (Utente tag) {
        tags.add(tag);
    }
    public void aggiungiGalleriaCondivisa (GalleriaCondivisa galleriaCondivisa) {
        gallerieCondivise.add(galleriaCondivisa);
    }
    public void rimuoviGalleriaCondivisa (GalleriaCondivisa galleriaCondivisa) {
        gallerieCondivise.remove(galleriaCondivisa);
    }
    public void rimuoviTag (Utente tag) {
        tags.remove(tag);
    }
    public void rimuoviSoggetto (SoggettoFoto soggetto) {
        soggetti.remove(soggetto);
    }
    public void aggiungiSoggetto (SoggettoFoto soggetto) {
        soggetti.add(soggetto);
    }
    public void aggiungiLuogo (Luogo luogo) {
        this.luogo = luogo;
    }
    public void rimuoviLuogo (Luogo luogo) {
        this.luogo = null;
    }
    public void aggiungiGalleriaPersonale (GalleriaPersonale galleriaPersonale) {
        this.galleriaPersonale = galleriaPersonale;
    }
    public void rimuoviGalleriaPersonale (GalleriaPersonale galleriaPersonale) {
        this.galleriaPersonale = null;
    }

    public void aggiungiDispositivo (Dispositivo dispositivo) {
        this.dispositivo = dispositivo;
    }

    public void rimuoviDispositivo (Dispositivo dispositivo) {
        this.dispositivo = null;
    }

    public Dispositivo getDispositivo() {
        return dispositivo;
    }

    public void setDispositivo(Dispositivo dispositivo) {
        this.dispositivo = dispositivo;
    }
    public String[] getTagsUsernames() {
        return tagsUsernames;
    }
    public void setTagsUsernames(String[] tagsUsernames) {
        this.tagsUsernames = tagsUsernames;
    }



}
