package GUI;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

import ij.process.ColorProcessor;
import ij.process.ImageProcessor;

public class FotoPanel extends JPanel {
    private ArrayList<ImageIcon> foto;
    private JLabel[] labelFoto;
    private int numFoto;



    public FotoPanel(ArrayList<ImageIcon> foto) {
        this.foto = foto;
        numFoto = foto.size();
        labelFoto = new JLabel[numFoto];
        setLayout(new GridLayout(numFoto / 3, 3));

        Dimension miniatureSize = new Dimension(200, 200);

        for (int i = 0; i < numFoto; i++) {


            Image image = foto.get(i).getImage();
            Image newImage = image.getScaledInstance(miniatureSize.width, miniatureSize.height, Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(newImage);
            labelFoto[i] = new JLabel(icon);

            // Imposta le dimensioni preferenziali per la label
            labelFoto[i].setPreferredSize(miniatureSize);

            add(labelFoto[i]);
        }
    }
}