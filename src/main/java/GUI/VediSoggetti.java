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


/**
 * The type Vedi soggetti.
 */
public class VediSoggetti extends JFrame {
    /**
     * The Main frame.
     */
    protected JFrame mainFrame;

    private JScrollPane scrollPane1;
    private JTable tabellaSoggetti;

    /**
     * Instantiates a new Vedi soggetti.
     *
     * @param soggetti       the soggetti
     * @param frameChiamante the frame chiamante
     */
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
    }

}
