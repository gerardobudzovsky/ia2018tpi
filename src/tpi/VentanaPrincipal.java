package tpi;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;


public class VentanaPrincipal extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JButton botonEjecutar;
	private JLabel labelCantidadClusters;
	private static JSpinner spinnerCantidadClusters;
	private JButton botonSeleccionarArchivo;
    private JFileChooser fc;
    private static File file;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaPrincipal ventana1 = new VentanaPrincipal();
					ventana1.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Constructor de la ventana. Aca se crea una nueva ventana.
	 */
	public VentanaPrincipal() {
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Algoritmos genéticos para el análisis de clusters");
		this.setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		botonEjecutar = new JButton("Ejecutar");
		botonEjecutar.setBounds(56, 180, 89, 23);
		botonEjecutar.addActionListener(this);
		contentPane.add(botonEjecutar);
		
		labelCantidadClusters = new JLabel("Cantidad de Clusters:");
		labelCantidadClusters.setBounds(28, 28, 126, 14);
		contentPane.add(labelCantidadClusters);
		
		spinnerCantidadClusters = new JSpinner();
		spinnerCantidadClusters.setBounds(164, 25, 39, 20);
		spinnerCantidadClusters.setModel(new SpinnerNumberModel(2,1,100,1));
		contentPane.add(spinnerCantidadClusters);
		
		fc = new JFileChooser("C:\\");
		
		botonSeleccionarArchivo = new JButton("Seleccionar Archivo");
		botonSeleccionarArchivo.setBounds(28, 75, 126, 23);
		botonSeleccionarArchivo.addActionListener(this);
		contentPane.add(botonSeleccionarArchivo);
	}

	@Override
	public void actionPerformed(ActionEvent evento) {
		
        if (evento.getSource() == botonSeleccionarArchivo) {
        	
            int returnVal = fc.showOpenDialog(VentanaPrincipal.this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
               file = fc.getSelectedFile();               
               //This is where a real application would open the file.
               //textArea1.append("Seleccionado el archivo " + file.getName() + "." + newline);
               //lblArchivoSubido.setText(file.getName());
            } else {
            	//textArea1.append("Cancelada la seleccion de archivo." + newline);
            }
            //textArea1.setCaretPosition(textArea1.getDocument().getLength());
        }
		
		if (evento.getSource() == botonEjecutar) {
			
			try {
				
                if(file==null){
                    JOptionPane.showMessageDialog(null, "Especificar archivo dataset", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else {
    				ClusteringGenetico.ejecutar();
                }				

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public static JSpinner obtenerSpinnerCantidadClusters() {
		return spinnerCantidadClusters;
	}

	public static void setearSpinnerCantidadClusters(JSpinner spinnerCantidadClusters) {
		VentanaPrincipal.spinnerCantidadClusters = spinnerCantidadClusters;
	}

	public static File getFile() {
		return file;
	}

	public static void setFile(File file) {
		VentanaPrincipal.file = file;
	}
	
}
