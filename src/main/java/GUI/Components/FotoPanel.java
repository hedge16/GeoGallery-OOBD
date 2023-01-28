package GUI.Components;

import org.imgscalr.Scalr;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class FotoPanel extends JPanel {
    private ArrayList<ImageIcon> foto;
    private JLabel[] labelFoto;
    private int numFoto;



    public FotoPanel(ArrayList<ImageIcon> foto) {
        this.foto = foto;
        numFoto = foto.size();
        labelFoto = new JLabel[numFoto];

        if (numFoto % 3 == 0 ) {
            setLayout(new GridLayout(numFoto / 3, 3));
        } else {
            setLayout(new GridLayout((numFoto / 3) + 1, 3));

        }

        for (int i = 0; i < numFoto; i++) {

            Image image = foto.get(i).getImage();
            ImageIcon icona = new ImageIcon(image);
            // Dimensioni dell'immagine originale
            int originalWidth = icona.getIconWidth();
            int originalHeight = icona.getIconHeight();
            // Dimensioni dell'immagine ridimensionata
            int newWidth = 100;
            int newHeight = 100;

            // Calcola le proporzioni originali
            float originalProportion = (float) originalWidth / originalHeight;

            // Calcola le proporzioni della nuova immagine
            float newProportion = (float) newWidth / newHeight;

            int x, y, width, height;

            if (originalProportion >= newProportion) {
                // L'immagine originale è più larga delle nuove proporzioni
                width = (int) (originalHeight * newProportion);
                height = originalHeight;
                x = (originalWidth - width) / 2;
                y = 0;
            } else {
                // L'immagine originale è più alta delle nuove proporzioni
                width = originalWidth;
                height = (int) (originalWidth / newProportion);
                x = 0;
                y = (originalHeight - height) / 2;
            }

            BufferedImage newImage = ((BufferedImage) image).getSubimage(x, y, width, height);
            // Ridimensionamento
            newImage = Scalr.resize(newImage, Scalr.Method.ULTRA_QUALITY, newWidth, newHeight);
            ImageIcon icon = new ImageIcon(newImage);
            labelFoto[i] = new JLabel(icon);

            // Imposta le dimensioni preferenziali per la label
            labelFoto[i].setPreferredSize(new Dimension(newWidth, newHeight));
            labelFoto[i].setBorder(new EmptyBorder(5, 5, 5, 5));
            add(labelFoto[i]);
        }
    }
}