/*
 * Created by JFormDesigner on Wed Feb 15 19:05:26 CET 2023
 */

package GUI;

import Model.SoggettoFoto;

import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.ArrayList;


public class VediSoggetti extends JFrame {
    protected JFrame mainFrame;
    public VediSoggetti(ArrayList<SoggettoFoto> soggetti, JFrame frameChiamante) {

        initComponents();

        DefaultTableModel model = new DefaultTableModel(new String[] {"Categoria", "Nome"}, 0);
        for (SoggettoFoto soggetto : soggetti) {
            model.addRow(new Object[] {soggetto.getCategoria(), soggetto.getNome()});
        }
        tabellaSoggetti.setModel(model);

        mainFrame = new JFrame();
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainFrame.setContentPane(scrollPane1);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(frameChiamante);
        mainFrame.setVisible(true);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        scrollPane1 = new JScrollPane();
        tabellaSoggetti = new JTable();

        //======== this ========
        var contentPane = getContentPane();

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(tabellaSoggetti);
        }

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE)
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JScrollPane scrollPane1;
    private JTable tabellaSoggetti;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
