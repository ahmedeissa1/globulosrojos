package grupo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import jcolibri.cbrcore.CBRCase;

import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import recomendadorPorPerfil.RecomendadorPorPerfil;
import sistema.Perfil;

//VS4E -- DO NOT REMOVE THIS LINE!
public class CrearGrupo extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTable jTable0;
	private JScrollPane jScrollPane0;
	private JButton jBSalir;
	private JButton jBCrear;
	private static final String PREFERRED_LOOK_AND_FEEL = "javax.swing.plaf.metal.MetalLookAndFeel";
	public CrearGrupo() {
		initComponents();
	}

	private void initComponents() {
		setTitle("Crear nuevo grupo");
		setLayout(new GroupLayout());
		add(getJScrollPane0(), new Constraints(new Leading(25, 276, 12, 12), new Leading(20, 180, 10, 10)));
		add(getJBCrear(), new Constraints(new Leading(56, 94, 10, 10), new Leading(217, 12, 12)));
		add(getJBSalir(), new Constraints(new Leading(168, 94, 12, 12), new Leading(217, 12, 12)));
		setSize(320, 255);
	}

	private JButton getJBCrear() {
		if (jBCrear == null) {
			jBCrear = new JButton();
			jBCrear.setText("Recomendar");
			jBCrear.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					botonCrearActionPerformed(event);
				}
			});
		}
		return jBCrear;
	}

	private JButton getJBSalir() {
		if (jBSalir == null) {
			jBSalir = new JButton();
			jBSalir.setText("Salir");
			jBSalir.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					botonSalirActionPerformed(event);
				}
			});
		}
		return jBSalir;
	}

	private JScrollPane getJScrollPane0() {
		if (jScrollPane0 == null) {
			jScrollPane0 = new JScrollPane();
			jScrollPane0.setViewportView(getJTable0());
		}
		return jScrollPane0;
	}

	private JTable getJTable0() {
		if (jTable0 == null) {
			jTable0 = new JTable();
			jTable0.setModel(new ListaUsuariosTableModel());
		}
		return jTable0;
	}

	private static void installLnF() {
		try {
			String lnfClassname = PREFERRED_LOOK_AND_FEEL;
			if (lnfClassname == null)
				lnfClassname = UIManager.getCrossPlatformLookAndFeelClassName();
			UIManager.setLookAndFeel(lnfClassname);
		} catch (Exception e) {
			System.err.println("Cannot install " + PREFERRED_LOOK_AND_FEEL
					+ " on this platform:" + e.getMessage());
		}
	}
	
	private void botonCrearActionPerformed(ActionEvent event) {
		ArrayList<Boolean> seleccionados = ((ListaUsuariosTableModel)(this.jTable0.getModel())).getSeleccionados();
		ArrayList<CBRCase> usuarios = ((ListaUsuariosTableModel)(this.jTable0.getModel())).getUsuarios();
		ArrayList<Perfil> usuariosSeleccionados = new ArrayList<Perfil>();
		for (int i = 0; i < usuarios.size(); i++) {
			if (seleccionados.get(i)) {
				usuariosSeleccionados.add((Perfil)usuarios.get(i).getDescription());
				System.out.println((Perfil)usuarios.get(i).getDescription());
			}
		}
		
		Perfil perfilGrupo = new Perfil(usuariosSeleccionados);
		System.out.println(perfilGrupo);
		RecomendadorPorPerfil rpp = new RecomendadorPorPerfil(perfilGrupo);
		rpp.recomendar();
		this.dispose();
	}

	private void botonSalirActionPerformed(ActionEvent event) {
		this.dispose();
	}

	/**
	 * Main entry of the class.
	 * Note: This class is only created so that you can easily preview the result at runtime.
	 * It is not expected to be managed by the designer.
	 * You can modify it as you like.
	 */
	public static void main(String[] args) {
		installLnF();
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				CrearGrupo frame = new CrearGrupo();
				frame.setDefaultCloseOperation(CrearGrupo.EXIT_ON_CLOSE);
				frame.setTitle("CrearGrupo");
				frame.getContentPane().setPreferredSize(frame.getSize());
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
	}

}
