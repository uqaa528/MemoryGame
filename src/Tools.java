import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

class Tools {
    private Clip currentClip;

    static ImageIcon resizeImage(String imagePath, int width, int height)
    {
        BufferedImage imageFile = null;
        try {
            imageFile = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedImage resizedImage = new BufferedImage(width, height, imageFile.getType());
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(imageFile, 0, 0, width, height, null);
        g2d.dispose();

        return new ImageIcon(resizedImage);
    }

    void playSound (String audioPath)
    {
        setClip(audioPath);
        getCurrentClip().start();
    }

    void setClip (String audioPath)
    {
        AudioInputStream audioInputStream = null;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(new File(audioPath).getAbsoluteFile());
        } catch (UnsupportedAudioFileException e) {
            System.out.println("Audio file format not supported!");
        } catch (IOException e) {
            System.out.println("File missing.");
        }
        try {
            setCurrentClip(AudioSystem.getClip());
        } catch (LineUnavailableException e) {
            System.out.println("File missing.");
        }
        try {
            currentClip.open(audioInputStream);
        } catch (LineUnavailableException | IOException e) {
            System.out.println("File missing.");
        }
    }

    private void setCurrentClip(Clip currentClip) {
        this.currentClip = currentClip;
    }

    Clip getCurrentClip() {
        return currentClip;
    }
}
