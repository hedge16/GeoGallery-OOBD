package GUI;

import java.awt.*;
import GUI.Components.FotoPanel;

import javax.swing.*;

public class RisultatoRicerca extends JFrame {
    protected JFrame mainFrame;

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
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
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
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JScrollPane scrollPanel;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
