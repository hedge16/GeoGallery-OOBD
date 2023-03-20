package GUI;

import java.awt.*;
import GUI.Components.FotoPanel;

import javax.swing.*;

/**
 * The type Risultato ricerca.
 */
public class RisultatoRicerca extends JFrame {
    /**
     * The Main frame.
     */
    protected JFrame mainFrame;

    private JScrollPane scrollPanel;

    /**
     * Instantiates a new Risultato ricerca.
     *
     * @param panel          the panel
     * @param frameChiamante the frame chiamante
     * @param results        the results
     */
    public RisultatoRicerca (FotoPanel panel, JFrame frameChiamante, int results) {
        initComponents(panel);

        mainFrame = new JFrame(results + " risultati");
        mainFrame.setContentPane(scrollPanel);
        mainFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(frameChiamante);
        mainFrame.setVisible(true);

    }

    private void initComponents(FotoPanel panel) {
        scrollPanel = new JScrollPane(panel);

        //======== this ========
        setMaximumSize(new Dimension(800, 800));
        var contentPane = getContentPane();

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addComponent(scrollPanel, GroupLayout.DEFAULT_SIZE, 578, Short.MAX_VALUE)
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addComponent(scrollPanel, GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE)
        );
        pack();
        setLocationRelativeTo(getOwner());
    }

}
