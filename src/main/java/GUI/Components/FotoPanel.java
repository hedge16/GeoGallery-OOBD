package GUI.Components;

import GUI.Home;
import GUI.Preview;
import Model.Dispositivo;
import Model.Foto;
import Model.Luogo;
import org.imgscalr.Scalr;
import Controller.Controller;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.nio.Buffer;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.Border;

public class FotoPanel extends JPanel {
    private ArrayList<Foto> photos;
    private ArrayList<JLabel> labelFoto;
    private int numFoto;
    private JFrame frame;
    boolean[] isSelected;
    Controller controller;
    private boolean isHome;
    String username;
    Home home;

    Border border = BorderFactory.createLineBorder(Color.CYAN, 3);



    public FotoPanel(ArrayList<Foto> foto, boolean isHome, Controller controller, String username, Home home) {

        this.username = username;
        this.isHome = isHome;
        this.home = home;
        this.controller = controller;
        this.photos = foto;
        numFoto = foto.size();
        labelFoto = new ArrayList<>();

        if (isHome) {
            setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        } else {
            setLayout(new GridLayout(0, 3, 5, 5));
        }


        isSelected = new boolean[foto.size()];

        setBackground(new Color(Color.DARK_GRAY.hashCode()));

        for (int i = 0; i < numFoto; i++) {

            BufferedImage newImage = cropAndResize(foto.get(i));
            ImageIcon icon = new ImageIcon(newImage);
            labelFoto.add(new JLabel(icon));

            // Imposta le dimensioni preferenziali per la label
            labelFoto.get(i).setPreferredSize(new Dimension(newImage.getWidth(), newImage.getHeight()));
            labelFoto.get(i).setToolTipText("Fai doppio click per aprire la foto");
            add(labelFoto.get(i));

            if (!isHome){
                JLabel label = labelFoto.get(i);
                int j = i;
                labelFoto.get(i).addMouseListener(new MouseAdapter() {
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

        for (int i = 0; i < numFoto; i++) {
            Foto foto1 = foto.get(i);
            labelFoto.get(i).addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    if (e.getClickCount() == 2) {
                        try {
                            Luogo luogo = controller.getLuogoFromFotoDB(foto1.getCodFoto());
                            foto1.setLuogo(luogo);
                            Dispositivo dispositivo = controller.getDispositivoDB(foto1.getCodDispositivo());
                            foto1.setDispositivo(dispositivo);
                            Preview preview = new Preview(controller, foto1, home, isHome);
                            preview.frame.setVisible(true);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            });
        }

        if (isHome) {
            if (numFoto % 3 == 0) {
                setPreferredSize(new Dimension(320, ((numFoto / 3) * 100) + 5 * (numFoto / 3)));
            } else {
                setPreferredSize(new Dimension(320, ((numFoto / 3 + 1) * 100) + 5 * ((numFoto / 3) + 1)));
            }
        }


    }

    public boolean[] getSelectedPhotos () {
        return isSelected;
    }

    private BufferedImage cropAndResize (Foto foto){

        Image image = foto.getFoto().getImage();
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

        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
        BufferedImage newImage = bufferedImage.getSubimage(x, y, width, height);
        // Ridimensionamento
        newImage = Scalr.resize(newImage, Scalr.Method.ULTRA_QUALITY, newWidth, newHeight);

        return newImage;
    }

    public void addFoto (Foto foto) {

        BufferedImage newImage = cropAndResize(foto);
        ImageIcon icon = new ImageIcon(newImage);
        JLabel label = new JLabel(icon);
        labelFoto.add(label);

        // Imposta le dimensioni preferenziali per la label
        label.setPreferredSize(new Dimension(newImage.getWidth(), newImage.getHeight()));
        label.setToolTipText("Fai doppio click per aprire la foto");

        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() == 2) {
                    try {
                        Luogo luogo = controller.getLuogoFromFotoDB(foto.getCodFoto());
                        foto.setLuogo(luogo);
                        Dispositivo dispositivo = controller.getDispositivoDB(foto.getCodDispositivo());
                        foto.setDispositivo(dispositivo);
                        Preview preview = new Preview(controller, foto, home, true);
                        preview.frame.setVisible(true);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        add(label);
        labelFoto.add(label);
        numFoto++;
        if (numFoto % 3 == 0) {
            setPreferredSize(new Dimension(320, ((numFoto / 3) * 100) + 10 * (numFoto / 3)));
        } else {
            setPreferredSize(new Dimension(320, ((numFoto / 3 + 1) * 100) + 10 * ((numFoto / 3) + 1)));
        }
        isSelected = new boolean[numFoto];
        revalidate();
        home.scrollPanel.revalidate();
    }

    public void removeFoto (Foto foto) {
        int index = photos.indexOf(foto);
        photos.remove(foto);
        numFoto--;
        remove(labelFoto.get(index));
        labelFoto.remove(index);
        isSelected = new boolean[numFoto];
        revalidate();
        home.scrollPanel.revalidate();
    }


}