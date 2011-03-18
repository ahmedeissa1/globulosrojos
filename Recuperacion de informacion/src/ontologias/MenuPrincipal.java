/*
 * MenuPrincipal.java
 *
 * Created on Feb 26, 2011, 7:05:01 PM
 */
package ontologias;

import es.ucm.fdi.gaia.ontobridge.test.gui.PnlInstancesTree;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import ontologias.interfaz.panel.PanelArbolClasesInstancias;
import ontologias.interfaz.panel.PanelArbolInstancias;
import ontologias.interfaz.panel.PanelArbolPropiedades;
import ontologias.interfaz.panel.PanelArbolSubclases;
import ontologias.interfaz.panel.PanelListaNoticias;
import ontologias.interfaz.panel.PanelPropiedades;
import ontologias.utils.CargadorImagenes;
import ontologias.utils.Ontologia;

/**
 *
 * @author markel
 */
public class MenuPrincipal extends javax.swing.JFrame {

    /** Creates new form MenuPrincipal */
    public MenuPrincipal() {
        //Para iniciar la interfaz en estado maximizado
        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        CargadorImagenes loader = new CargadorImagenes();
        images = loader.loadImages();
        imageIndex = 0;
        nombre = images.get(imageIndex).getDescription();
        initComponents();
        labelFoto.setIcon(images.get(imageIndex));

        crearPanelPropiedades();

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelListaNoticias = new PanelArbolClasesInstancias(this);
        panelFoto = new javax.swing.JPanel();
        labelFoto = new javax.swing.JLabel();
        panelMenu = new javax.swing.JPanel();
        botonAnterior = new javax.swing.JButton();
        botonSiguiente = new javax.swing.JButton();
        botonM = new javax.swing.JButton();
        botonT = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Marcador Semantico de Imagenes");
        setMinimumSize(new java.awt.Dimension(944, 715));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        this.getContentPane().add(panelListaNoticias);
        this.pack();
        panelListaNoticias.setMinimumSize(new java.awt.Dimension(253, 600));
        panelListaNoticias.setPreferredSize(new java.awt.Dimension(253, 600));

        javax.swing.GroupLayout panelListaNoticiasLayout = new javax.swing.GroupLayout(panelListaNoticias);
        panelListaNoticias.setLayout(panelListaNoticiasLayout);
        panelListaNoticiasLayout.setHorizontalGroup(
            panelListaNoticiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 253, Short.MAX_VALUE)
        );
        panelListaNoticiasLayout.setVerticalGroup(
            panelListaNoticiasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 688, Short.MAX_VALUE)
        );

        panelFoto.setMaximumSize(new java.awt.Dimension(950, 332));
        panelFoto.setMinimumSize(new java.awt.Dimension(950, 332));

        labelFoto.setText(" ");

        javax.swing.GroupLayout panelFotoLayout = new javax.swing.GroupLayout(panelFoto);
        panelFoto.setLayout(panelFotoLayout);
        panelFotoLayout.setHorizontalGroup(
            panelFotoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFotoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelFoto)
                .addContainerGap(935, Short.MAX_VALUE))
        );
        panelFotoLayout.setVerticalGroup(
            panelFotoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFotoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelFoto)
                .addContainerGap(472, Short.MAX_VALUE))
        );

        panelMenu.setBackground(new java.awt.Color(223, 132, 40));
        panelMenu.setMaximumSize(new java.awt.Dimension(950, 182));
        panelMenu.setMinimumSize(new java.awt.Dimension(950, 182));

        botonAnterior.setText("Anterior");
        botonAnterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAnteriorActionPerformed(evt);
            }
        });

        botonSiguiente.setText("Siguiente");
        botonSiguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonSiguienteActionPerformed(evt);
            }
        });

        botonM.setText("M");
        botonM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonMActionPerformed(evt);
            }
        });

        botonT.setText("Modificar Ontologia");
        botonT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonTActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelMenuLayout = new javax.swing.GroupLayout(panelMenu);
        panelMenu.setLayout(panelMenuLayout);
        panelMenuLayout.setHorizontalGroup(
            panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMenuLayout.createSequentialGroup()
                .addGap(185, 185, 185)
                .addComponent(botonAnterior)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botonM, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botonT, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botonSiguiente)
                .addContainerGap(429, Short.MAX_VALUE))
        );
        panelMenuLayout.setVerticalGroup(
            panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMenuLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonM)
                    .addComponent(botonAnterior)
                    .addComponent(botonT)
                    .addComponent(botonSiguiente))
                .addContainerGap(126, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(panelListaNoticias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelFoto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelMenu, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelListaNoticias, javax.swing.GroupLayout.DEFAULT_SIZE, 688, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelFoto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonSiguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonSiguienteActionPerformed
        if (imageIndex + 1 == images.size()) {
            imageIndex = 0;
            labelFoto.setIcon(images.get(imageIndex));
            return;
        }
        labelFoto.setIcon(images.get(++imageIndex));
        nombre = images.get(imageIndex).getDescription();
    }//GEN-LAST:event_botonSiguienteActionPerformed

    private void botonAnteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAnteriorActionPerformed
        if (imageIndex - 1 == -1) {
            imageIndex = images.size() - 1;
            labelFoto.setIcon(images.get(imageIndex));
            return;
        }
        labelFoto.setIcon(images.get(--imageIndex));
        nombre = images.get(imageIndex).getDescription();
    }//GEN-LAST:event_botonAnteriorActionPerformed

    private void botonMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonMActionPerformed
        javax.swing.JFrame window = new javax.swing.JFrame(Ontologia.getMainOnto().getURL());
        PanelArbolSubclases subclases = new PanelArbolSubclases(Ontologia.getInstance(), "Noticia");
        window.getContentPane().add(subclases);
        window.pack();
        window.setSize(300, 600);
        window.setVisible(true);
    }//GEN-LAST:event_botonMActionPerformed

    private void botonTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonTActionPerformed
        javax.swing.JFrame window = new javax.swing.JFrame(Ontologia.getMainOnto().getURL());
        PanelArbolClasesInstancias subclases = new PanelArbolClasesInstancias(this);
        window.getContentPane().add(subclases);
        window.pack();
        window.setSize(300, 600);
        window.setVisible(true);
    }//GEN-LAST:event_botonTActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        if(JOptionPane.showConfirmDialog(this, "¿Desea guardar la ontologia?","Guardar",JOptionPane.YES_NO_OPTION) == 0)
            Ontologia.getInstance().save("files/Ontologia.owl");
    }//GEN-LAST:event_formWindowClosing

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new MenuPrincipal().setVisible(true);
            }
        });
    }

    public void mostrarImagen(String imagen) {
        for (int i = 0; i < images.size(); i++) {
            ImageIcon img = images.get(i);
            if (img.getDescription().equals(imagen)) {
                labelFoto.setIcon(img);
                this.imageIndex = i;
                return;
            }
        }
    }

    public void crearPanelPropiedades() {
        //genera una ventana con las propidades de la foto
        ventanaPopUp = new javax.swing.JFrame(Ontologia.getMainOnto().getURL() + nombre);
        javax.swing.JButton botonUpdate = new javax.swing.JButton();
        botonUpdate.setText("Actualizar"); // NOI18N
        botonUpdate.setName("botonActualizar");
        //botonUpdate.setLocation(200, 200);
        panelPropiedadesPopUp = new PanelPropiedades(nombre);
        panelPropiedadesPopUp.add(botonUpdate);
        botonUpdate.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                botonActualizar(e);
            }
        });
        //PnlInstancesTree tree = new PnlInstancesTree(Ontologia.getInstance(), "Paul_Gasol");
        ventanaPopUp.getContentPane().add(panelPropiedadesPopUp);
        ventanaPopUp.pack();
        //ventanaPopUp.setSize(600, 600);
        ventanaPopUp.setVisible(true);
    }


    public void botonActualizar(java.awt.event.ActionEvent evt) {
        ventanaPopUp.remove(panelPropiedadesPopUp);
        javax.swing.JButton botonUpdate = new javax.swing.JButton();
        botonUpdate.setText("Actualizar"); // NOI18N
        botonUpdate.setName("botonActualizar");
        //botonUpdate.setLocation(200, 200);
        panelPropiedadesPopUp = new PanelPropiedades(nombre);
        panelPropiedadesPopUp.add(botonUpdate);
        botonUpdate.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                botonActualizar(e);
            }
        });
        //PnlInstancesTree tree = new PnlInstancesTree(Ontologia.getInstance(), "Paul_Gasol");
        ventanaPopUp.getContentPane().add(panelPropiedadesPopUp);
        ventanaPopUp.pack();
        ventanaPopUp.setVisible(true);

    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonAnterior;
    private javax.swing.JButton botonM;
    private javax.swing.JButton botonSiguiente;
    private javax.swing.JButton botonT;
    private javax.swing.JLabel labelFoto;
    private javax.swing.JPanel panelFoto;
    private javax.swing.JPanel panelListaNoticias;
    private javax.swing.JPanel panelMenu;
    // End of variables declaration//GEN-END:variables
    ArrayList<ImageIcon> images;
    private int imageIndex;
    String nombre;
    javax.swing.JFrame ventanaPopUp;
    PanelPropiedades panelPropiedadesPopUp;
}
