package Model;

public class Dispositivo {
    private int id;
    private String nome;
    private String proprietario;
    public Dispositivo(int id, String nome, String proprietario) {
        this.id = id;
        this.nome = nome;
        this.proprietario = proprietario;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getProprietario () {
        return proprietario;
    }

    public void setProprietario (String proprietario) {
       this.proprietario = proprietario;
    }

}
