package Model;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

// Mejorar logica
public class ConexionFirebase {
    
    public Firestore Conexion() {
        try {
            InputStream serviceAccount = getClass().getResourceAsStream("/auth\\mimicAuth.json");
            System.out.println(serviceAccount);
            GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(credentials)
                    .build();
            FirebaseApp.initializeApp(options);

            System.out.println("Conexion exitosa");
        } catch (IOException ex) {
            System.out.println("Error en la conexion");
        }
        return FirestoreClient.getFirestore();
    }
}
