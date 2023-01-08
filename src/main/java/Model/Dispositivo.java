package Model;

public class Dispositivo {
    private int id;
    private String nome;
    private String descrizione;
    private String marca;
    private String modello;

    public Dispositivo(int id, String nome, String descrizione, String marca, String modello) {
        this.id = id;
        this.nome = nome;
        this.descrizione = descrizione;
        this.marca = marca;
        this.modello = modello;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public String getMarca() {
        return marca;
    }

    public String getModello() {
        return modello;
    }


}
