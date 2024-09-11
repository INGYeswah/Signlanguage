package Model;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

// Mejorar logica
public class Player {

    private String Nickname, Name, Email, Password;
    private int Foto, Level;

    public Player(String Nickname, String Nombre, String Email, String Password, int Foto, int Level) {
        this.Nickname = Nickname;
        this.Name = Nombre;
        this.Email = Email;
        this.Password = Password;
        this.Foto = Foto;
        this.Level = Level;
    }

    public Player() {

    }

    public void addPlayer(Firestore db) {
        try {
            DocumentReference docRef = db.collection("users").document(this.Email);
            Map<String, Object> data = new HashMap<>();
            data.put("name", this.Name);
            data.put("nickname", this.Nickname);
            data.put("email", this.Email);
            data.put("password", this.Password);
            data.put("foto", this.Foto);
            data.put("level", this.Level);
            data.put("puntuacion", 0);
            ApiFuture<WriteResult> result = docRef.set(data);
            System.out.println("Update time : " + result.get().getUpdateTime());
        } catch (InterruptedException | ExecutionException ex) {
            System.out.println("Error en la creacion");
        }
    }

    public String getNickname() {
        return Nickname;
    }

    public String getName() {
        return Name;
    }

    public String getEmail() {
        return Email;
    }

    public String getPassword() {
        return Password;
    }

    public void setNickname(String Nickname) {
        this.Nickname = Nickname;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public int getFoto() {
        return Foto;
    }

    public void setFoto(int Foto) {
        this.Foto = Foto;
    }

    public void setLevel(int level) {
        this.Level = level;
    }

    public int getLevel() {
        return Level;
    }

}
