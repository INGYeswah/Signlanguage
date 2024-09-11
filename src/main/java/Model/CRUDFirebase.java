package Model;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.mimic.mimic.App;
import com.mimic.mimic.Result;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CRUDFirebase {

    public static boolean addPlayerFirebase(Player player, Firestore db) {
        player.addPlayer(db);
        return false;
    }

    public static void obtenerDatos(Firestore db) {
        try {
            CollectionReference personas = db.collection("users");
            ApiFuture<QuerySnapshot> querySnaps = personas.get();
            for (DocumentSnapshot document : querySnaps.get().getDocuments()) {
                System.out.println(document.getString("name"));
                System.out.println(document.getString("nickname"));
                System.out.println(document.getString("email"));
                System.out.println(document.getString("password"));
            }

        } catch (InterruptedException | ExecutionException ex) {
            System.out.println("Error al obtener datos");
        }
    }

    public static Result obtenerDatosPuntuaciones(Firestore db) {
        ArrayList<Integer> puntuaciones = new ArrayList<>();
        ArrayList<String> nombres = new ArrayList<>();
        try {
            CollectionReference personas = db.collection("users");
            ApiFuture<QuerySnapshot> querySnaps = personas.get();
            for (DocumentSnapshot document : querySnaps.get().getDocuments()) {
                nombres.add(document.getString("email"));
                puntuaciones.add(((Long) document.get("puntuacion")).intValue());
            }

        } catch (InterruptedException | ExecutionException ex) {
            System.out.println("Error al obtener datos");
        }
        return new Result<>(nombres, puntuaciones);
    }

    public static void obtenerDatos(Firestore db, String dato) {
        try {
            DocumentReference user = db.collection("users").document(dato);
            ApiFuture<DocumentSnapshot> querySnaps = user.get();
            DocumentSnapshot document = querySnaps.get();
            App.player.setName(document.getString("name"));
            App.player.setNickname(document.getString("nickname"));
            App.player.setEmail(document.getString("email"));
            App.player.setFoto(((Long) document.get("foto")).intValue());
            App.player.setLevel(((Long) document.get("level")).intValue());

        } catch (InterruptedException | ExecutionException ex) {
            System.out.println("Error al buscar dato");
        }
    }

    public static void modificarDato(Firestore db, String document, String field, String modDato) {
        try {
            DocumentReference docRef = db.collection("users").document(document);
            ApiFuture<WriteResult> future = docRef.update(field, modDato);
            WriteResult result = future.get();
            System.out.println("Write result: " + result);
        } catch (InterruptedException | ExecutionException ex) {
            System.out.println("Error en la creacion");
        }
    }

    public static void modificarDato(Firestore db, String document, String field, int modDato) {
        try {
            DocumentReference docRef = db.collection("users").document(document);
            ApiFuture<WriteResult> future = docRef.update(field, modDato);
            WriteResult result = future.get();
            System.out.println("Write result: " + result);
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(CRUDFirebase.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static boolean obtenerDato(Firestore db, String dato) {
        try {
            DocumentReference user = db.collection("users").document(dato);
            ApiFuture<DocumentSnapshot> querySnaps = user.get();
            DocumentSnapshot document = querySnaps.get();
            return (document.getData() == null);

        } catch (InterruptedException | ExecutionException ex) {
            System.out.println("Error al buscar dato");
        }
        return false;
    }

    public static String obtenerDato(Firestore db, String dato, String field) {
        try {
            DocumentReference user = db.collection("users").document(dato);
            ApiFuture<DocumentSnapshot> querySnaps = user.get();
            DocumentSnapshot document = querySnaps.get();
            return document.get(field).toString();

        } catch (InterruptedException | ExecutionException ex) {
            System.out.println("Error al buscar dato");
        }
        return "";
    }

}
