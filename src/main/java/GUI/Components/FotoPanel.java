package GUI.Components;

import GUI.Preview;
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
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class FotoPanel extends JPanel {
    private ArrayList<Foto> photos;
    private JLabel[] labelFoto;
    private int numFoto;
    private JFrame frame;
    boolean[] isSelected;

    Border border = BorderFactory.createLineBorder(Color.CYAN, 1);



    public FotoPanel(ArrayList<Foto> foto, boolean isHome) {
        this.photos = foto;
        numFoto = foto.size();
        labelFoto = new JLabel[numFoto];
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setPreferredSize(new Dimension(339, 800));

        isSelected = new boolean[foto.size()];



        setBackground(new Color(0x333333));

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

            if (!isHome){
                JLabel label = labelFoto[i];
                int j = i;
                labelFoto[i].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        if (label.getBorder() == null) {
                            label.setBorder(border);
                            isSelected[j] = true;
                        } else {
                            label.setBorder(null);
                            isSelected[j] = false;
                        }
                    }
                });
            }

        }


        for (int i = 0; i < numFoto; i++){
            ImageIcon foto1 = foto.get(i).getFoto();
            labelFoto[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    if (e.getClickCount() == 2) {
                        Preview preview = new Preview(foto1);
                        preview.frame.setVisible(true);
                    }
                }
            });
        }
    }

    public boolean[] getSelectedPhotos () {
        return isSelected;
    }
}