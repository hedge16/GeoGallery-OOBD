package GUI.Components;

import Model.Foto;
import org.imgscalr.Scalr;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class FotoPanel extends JPanel {
    private ArrayList<Foto> photos;
    private JLabel[] labelFoto;
    private int numFoto;
    private JFrame frame;



    public FotoPanel(ArrayList<Foto> foto) {
        this.photos = foto;
        numFoto = foto.size();
        labelFoto = new JLabel[numFoto];

        if (numFoto % 3 == 0 ) {
            setLayout(new GridLayout(numFoto/ 3, 3, 5, 5));

        } else {
            setLayout(new GridLayout((numFoto / 3) + 1, 3, 5, 5));

        }

        setBackground(new Color(0x2a3c4c));

        for (int i = 0; i < numFoto; i++) {

            Image image = foto.get(i).getFoto().getImage();
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
            add(labelFoto[i]);
        }


        for (JLabel label : labelFoto){
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    if (e.getClickCount() == 2) {


                        JPanel preview = new JPanel();
                        JLabel previewLabel = new JLabel(label.getIcon());
                        preview.setLayout(new BorderLayout());
                        preview.add(previewLabel, BorderLayout.CENTER);
                        frame = new JFrame();
                        frame.setContentPane(preview);
                        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        frame.setSize(label.getWidth(), label.getHeight());
                        frame.setTitle("Preview");
                        frame.setLocationRelativeTo(null); //per far si che il frame si apra al centro dello schermo
                        frame.setVisible(true);


                    }
                }
            });
        }
    }
}