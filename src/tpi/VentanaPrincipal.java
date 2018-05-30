package tpi;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.StringTokenizer;
import java.util.TreeSet;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JRadioButton;
import java.io.BufferedReader;


public class VentanaPrincipal extends JFrame implements ActionListener, ChangeListener {

	private JPanel contentPane;
	private JLabel lblAlgoritmoAPriori;
	private JButton botonEjecutar;
	private JButton botonCancelar;
	private JLabel labelCantidadClusters;
	private static JSpinner spinnerCantidadClusters;
	private JButton botonSeleccionarArchivo;
    private JFileChooser fc;
    private static File file;
    private JRadioButton rdbtnClusteringGenetico;
    private JRadioButton rdbtnClusteringGeneticoPor;
    private JRadioButton rdbtnComparacinConKmeans;
    private ButtonGroup bg;
    private static int menu;
	private JLabel lblPorcentajeSeleccion;
    private JLabel lblPorcentajeCruza;
    private JLabel lblPorcentajeMutacion;
    private static JSpinner spinnerPorcentajeSeleccion;
    private static JSpinner spinnerPorcentajeCruza;
    private static JSpinner spinnerPorcentajeMutacion;
    private int numeroTransacciones;
    private JLabel lblCantidadIndividuos;
    private JLabel lblCantidadGeneraciones;
    private static JSpinner spinnerCantidadIndividuos;
    private static JSpinner spinnerCantidadGeneraciones;
    private static Boolean banderaCancelar;
    private JLabel lblClusterFinal;
    private JSpinner spinnerClusterFinal;

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
		this.setBounds(100, 100, 542, 473);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		botonEjecutar = new JButton("Ejecutar");
		botonEjecutar.setBounds(39, 384, 89, 23);
		botonEjecutar.addActionListener(this);
		contentPane.add(botonEjecutar);
		botonEjecutar.setVisible(false);
		
		labelCantidadClusters = new JLabel("labelCantidadClusters");
		labelCantidadClusters.setBounds(39, 187, 141, 14);
		contentPane.add(labelCantidadClusters);
		labelCantidadClusters.setVisible(false);
		
		spinnerCantidadClusters = new JSpinner();
		spinnerCantidadClusters.setBounds(207, 184, 39, 20);
		spinnerCantidadClusters.setModel(new SpinnerNumberModel(2,2,100,1));
		contentPane.add(spinnerCantidadClusters);
		spinnerCantidadClusters.setVisible(false);
		
		fc = new JFileChooser("C:\\");
		
		botonSeleccionarArchivo = new JButton("Seleccionar Archivo");
		botonSeleccionarArchivo.setBounds(39, 143, 153, 23);
		botonSeleccionarArchivo.addActionListener(this);
		contentPane.add(botonSeleccionarArchivo);
		botonSeleccionarArchivo.setVisible(false);		
		
		bg=new ButtonGroup();
		
		rdbtnClusteringGenetico = new JRadioButton("Clustering Genetico");
		rdbtnClusteringGenetico.setBounds(39, 17, 173, 23);
		rdbtnClusteringGenetico.addChangeListener(this);
		contentPane.add(rdbtnClusteringGenetico);
		bg.add(rdbtnClusteringGenetico);
		
		rdbtnClusteringGeneticoPor = new JRadioButton("Clustering Genetico Por Rangos");
		rdbtnClusteringGeneticoPor.setBounds(39, 51, 228, 23);
		rdbtnClusteringGeneticoPor.addChangeListener(this);
		contentPane.add(rdbtnClusteringGeneticoPor);
		bg.add(rdbtnClusteringGeneticoPor);
		
		rdbtnComparacinConKmeans = new JRadioButton("Comparaci\u00F3n con K-Means");
		rdbtnComparacinConKmeans.setBounds(39, 88, 207, 23);
		rdbtnComparacinConKmeans.addChangeListener(this);
		contentPane.add(rdbtnComparacinConKmeans);
		bg.add(rdbtnComparacinConKmeans);
		
		lblAlgoritmoAPriori = new JLabel("");
		lblAlgoritmoAPriori.setIcon(new ImageIcon("logo.png"));
		lblAlgoritmoAPriori.setBounds(125, 0, 573, 44);
		contentPane.add(lblAlgoritmoAPriori);
		
		lblPorcentajeSeleccion = new JLabel("Porcentaje Seleccion");
		lblPorcentajeSeleccion.setBounds(39, 217, 141, 14);
		contentPane.add(lblPorcentajeSeleccion);
		lblPorcentajeSeleccion.setVisible(false);
		
		lblPorcentajeCruza = new JLabel("Porcentaje Cruza");
		lblPorcentajeCruza.setBounds(39, 242, 123, 14);
		contentPane.add(lblPorcentajeCruza);
		lblPorcentajeCruza.setVisible(false);
		
		lblPorcentajeMutacion = new JLabel("Porcentaje Mutacion");
		lblPorcentajeMutacion.setBounds(39, 267, 123, 14);
		contentPane.add(lblPorcentajeMutacion);
		lblPorcentajeMutacion.setVisible(false);
		
		spinnerPorcentajeSeleccion = new JSpinner();
		spinnerPorcentajeSeleccion.setBounds(207, 215, 39, 20);
		spinnerPorcentajeSeleccion.setModel(new SpinnerNumberModel(10,1,100,1));
		contentPane.add(spinnerPorcentajeSeleccion);
		spinnerPorcentajeSeleccion.setVisible(false);
		
		spinnerPorcentajeCruza = new JSpinner();
		spinnerPorcentajeCruza.setBounds(207, 239, 39, 20);
		spinnerPorcentajeCruza.setModel(new SpinnerNumberModel(85,1,100,1));
		contentPane.add(spinnerPorcentajeCruza);
		spinnerPorcentajeCruza.setVisible(false);
		
		spinnerPorcentajeMutacion = new JSpinner();
		spinnerPorcentajeMutacion.setBounds(207, 264, 39, 20);
		spinnerPorcentajeMutacion.setModel(new SpinnerNumberModel(5,1,100,1));
		contentPane.add(spinnerPorcentajeMutacion);
		spinnerPorcentajeMutacion.setVisible(false);
		
		lblCantidadIndividuos = new JLabel("Cantidad de Individuos:");
		lblCantidadIndividuos.setBounds(39, 292, 153, 14);
		contentPane.add(lblCantidadIndividuos);
		lblCantidadIndividuos.setVisible(false);		
		
		lblCantidadGeneraciones = new JLabel("Cantidad de Generaciones:");
		lblCantidadGeneraciones.setBounds(39, 317, 153, 14);
		contentPane.add(lblCantidadGeneraciones);
		lblCantidadGeneraciones.setVisible(false);
		
		spinnerCantidadIndividuos = new JSpinner();
		spinnerCantidadIndividuos.setBounds(207, 289, 47, 20);
		spinnerCantidadIndividuos.setModel(new SpinnerNumberModel(100,2,10000,1));
		contentPane.add(spinnerCantidadIndividuos);
		spinnerCantidadIndividuos.setVisible(false);
		
		spinnerCantidadGeneraciones = new JSpinner();
		spinnerCantidadGeneraciones.setBounds(207, 314, 47, 20);
		spinnerCantidadGeneraciones.setModel(new SpinnerNumberModel(100,2,10000,1));
		contentPane.add(spinnerCantidadGeneraciones);
		spinnerCantidadGeneraciones.setVisible(false);
		
		botonCancelar = new JButton("Cancelar");
		botonCancelar.setBounds(157, 384, 89, 23);
		contentPane.add(botonCancelar);
		
		lblClusterFinal = new JLabel("Cluster Final:");
		lblClusterFinal.setBounds(293, 187, 73, 14);
		contentPane.add(lblClusterFinal);
		
		spinnerClusterFinal = new JSpinner();
		spinnerClusterFinal.setBounds(376, 184, 39, 20);
		spinnerClusterFinal.setModel(new SpinnerNumberModel(5,1,100,1));
		contentPane.add(spinnerClusterFinal);
		lblClusterFinal.setVisible(false);

	}
	
	@Override
	public void stateChanged(ChangeEvent arg0) {
		
        if (rdbtnClusteringGenetico.isSelected()) {    	
        	this.setMenu(1);
        	botonEjecutar.setVisible(true);
        	botonSeleccionarArchivo.setVisible(true);
        	spinnerCantidadClusters.setVisible(true);
        	labelCantidadClusters.setText("Cantidad de Clusters:");
        	labelCantidadClusters.setVisible(true);
        	lblPorcentajeSeleccion.setVisible(true);
        	lblPorcentajeCruza.setVisible(true);
        	lblPorcentajeMutacion.setVisible(true);
        	spinnerPorcentajeSeleccion.setVisible(true);
        	spinnerPorcentajeCruza.setVisible(true);
        	spinnerPorcentajeMutacion.setVisible(true);
    		lblCantidadIndividuos.setVisible(true);
    		lblCantidadGeneraciones.setVisible(true);
    		spinnerCantidadIndividuos.setVisible(true);
    		spinnerCantidadGeneraciones.setVisible(true);
        }
        if (rdbtnClusteringGeneticoPor.isSelected()) {
        	this.setMenu(2);
        	botonEjecutar.setVisible(true);
        	botonSeleccionarArchivo.setVisible(true);
        	spinnerCantidadClusters.setVisible(true);
        	labelCantidadClusters.setText("Cluster Inicial:");
        	labelCantidadClusters.setVisible(true);
        	lblPorcentajeSeleccion.setVisible(true);
        	lblPorcentajeCruza.setVisible(true);
        	lblPorcentajeMutacion.setVisible(true);
        	spinnerPorcentajeCruza.setVisible(true);
        	spinnerPorcentajeMutacion.setVisible(true);
    		lblCantidadIndividuos.setVisible(true);
    		lblCantidadGeneraciones.setVisible(true);
    		spinnerCantidadIndividuos.setVisible(true);
    		spinnerCantidadGeneraciones.setVisible(true);
    		lblClusterFinal.setVisible(true);
        }
        if (rdbtnComparacinConKmeans.isSelected()) {
        	this.setMenu(3);
        	botonEjecutar.setVisible(true);
        	botonSeleccionarArchivo.setVisible(true);
        	spinnerCantidadClusters.setVisible(true);
        	labelCantidadClusters.setVisible(true);
        	lblPorcentajeSeleccion.setVisible(true);
        	lblPorcentajeCruza.setVisible(true);
        	lblPorcentajeMutacion.setVisible(true);
        	spinnerPorcentajeCruza.setVisible(true);
        	spinnerPorcentajeMutacion.setVisible(true);
    		lblCantidadIndividuos.setVisible(true);
    		lblCantidadGeneraciones.setVisible(true);
    		spinnerCantidadIndividuos.setVisible(true);
    		spinnerCantidadGeneraciones.setVisible(true);
        } 
		
	}

	
	public int obtenerNumeroDeTransacciones() throws Exception {
		
		String archivoTransacciones;   			
		archivoTransacciones= VentanaPrincipal.getFile().getAbsolutePath();
        BufferedReader data_in = new BufferedReader(new FileReader(archivoTransacciones));
    	while (data_in.ready()) {    		
    		String line=data_in.readLine();
                
    		if (line.matches("\\s*")) {
    			continue; // saltar lineas vacias
    		}
    		
    		numeroTransacciones++;
    	}
    	return numeroTransacciones++;
	}
	
	@Override
	public void actionPerformed(ActionEvent evento){
		
        if (evento.getSource() == botonSeleccionarArchivo) {
        	
            int returnVal = fc.showOpenDialog(VentanaPrincipal.this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
               file = fc.getSelectedFile();               
               //This is where a real application would open the file.
               //textArea1.append("Seleccionado el archivo " + file.getName() + "." + newline);
               //lblArchivoSubido.setText(file.getName());
               
               try {
            	int cantidadClustersMenosUno= this.obtenerNumeroDeTransacciones() - 1;
				spinnerCantidadClusters.setModel(new SpinnerNumberModel(2,2,cantidadClustersMenosUno,1));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}               
            } else {
            	//textArea1.append("Cancelada la seleccion de archivo." + newline);
            }
            //textArea1.setCaretPosition(textArea1.getDocument().getLength());
        }
		
		if (evento.getSource() == botonEjecutar) {
			
			int sumaPorcentajes= (int)this.getSpinnerPorcentajeSeleccion().getValue() + (int)this.getSpinnerPorcentajeCruza().getValue() + (int)this.getSpinnerPorcentajeMutacion().getValue();
			if (sumaPorcentajes != 100) {
				JOptionPane.showMessageDialog(null, "La sumatoria de los porcentajes de selección, cruza y mutación debe sumar 100", "Error", JOptionPane.ERROR_MESSAGE);
			}			
			
			try {
				
                if(file == null){
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
		
		if (evento.getSource() == botonCancelar) {			
			this.setBanderaCancelar(true);
			JOptionPane.showMessageDialog(null, "Ejecución Cancelar", "Aviso", JOptionPane.INFORMATION_MESSAGE);
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
	
    public static int getMenu() {
		return menu;
	}

	public static void setMenu(int menu) {
		VentanaPrincipal.menu = menu;
	}

	public static JSpinner getSpinnerCantidadClusters() {
		return spinnerCantidadClusters;
	}

	public static void setSpinnerCantidadClusters(JSpinner spinnerCantidadClusters) {
		VentanaPrincipal.spinnerCantidadClusters = spinnerCantidadClusters;
	}

	public static JSpinner getSpinnerCantidadIndividuos() {
		return spinnerCantidadIndividuos;
	}

	public static void setSpinnerCantidadIndividuos(JSpinner spinnerCantidadIndividuos) {
		VentanaPrincipal.spinnerCantidadIndividuos = spinnerCantidadIndividuos;
	}

	public static JSpinner getSpinnerCantidadGeneraciones() {
		return spinnerCantidadGeneraciones;
	}

	public static void setSpinnerCantidadGeneraciones(JSpinner spinnerCantidadGeneraciones) {
		VentanaPrincipal.spinnerCantidadGeneraciones = spinnerCantidadGeneraciones;
	}

	public static JSpinner getSpinnerPorcentajeSeleccion() {
		return spinnerPorcentajeSeleccion;
	}

	public static void setSpinnerPorcentajeSeleccion(JSpinner spinnerPorcentajeSeleccion) {
		VentanaPrincipal.spinnerPorcentajeSeleccion = spinnerPorcentajeSeleccion;
	}

	public static JSpinner getSpinnerPorcentajeCruza() {
		return spinnerPorcentajeCruza;
	}

	public static void setSpinnerPorcentajeCruza(JSpinner spinnerPorcentajeCruza) {
		VentanaPrincipal.spinnerPorcentajeCruza = spinnerPorcentajeCruza;
	}

	public static JSpinner getSpinnerPorcentajeMutacion() {
		return spinnerPorcentajeMutacion;
	}

	public static void setSpinnerPorcentajeMutacion(JSpinner spinnerPorcentajeMutacion) {
		VentanaPrincipal.spinnerPorcentajeMutacion = spinnerPorcentajeMutacion;
	}
	
	public static Boolean getBanderaCancelar() {
		return banderaCancelar;
	}

	public static void setBanderaCancelar(Boolean banderaCancelar) {
		VentanaPrincipal.banderaCancelar = banderaCancelar;
	}
}
