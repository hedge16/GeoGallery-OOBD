package Model;
import java.util.Date;

public class Utente {

    private String nome;
    private String cognome;
    private String email;
    private String username;
    private String password;
    Date dataReg;


    public Utente (String name, String surname, String user, String pass, String email1, Date d){
        this.username=user;
        this.password=pass;
        this.dataReg=d;
        this.nome=name;
        this.cognome=surname;
        this.email=email1;

    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



}