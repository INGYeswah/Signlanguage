package Soundtrack;

import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Soundtrack extends Thread {

    private static String BC;
    private boolean running;

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        int i=1;
        while (true) {
            playMusic(i);
            try {
                Thread.sleep(1000); // Pausa entre repeticiones (opcional)
            } catch (InterruptedException e) {
                System.out.println("Problemas en el hilo de soundtrack");
            }
            i = (i<2)?i+=1:0;
        }
    }

    private void playMusic(int i) {
        InputStream audioSrc = getClass().getResourceAsStream("/soundtrack\\musica" + i +".wav");
        System.out.println("/soundtrack\\musica" + i +".wav");
        if (audioSrc == null) {
            System.out.println("No se pudo encontrar el archivo de sonido.");
            return;
        }
        try (AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioSrc)) {
            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            try (Clip audioclip = (Clip) AudioSystem.getLine(info)) {
                audioclip.open(audioStream);
                audioclip.start();

                while (!audioclip.isRunning()) {
                    Thread.sleep(10);
                }

                while (audioclip.isRunning()) {
                    Thread.sleep(10);
                }

                audioclip.close();

            } catch (Exception e) {
            }

        } catch (UnsupportedAudioFileException | IOException ex) {

        }
    }

}
