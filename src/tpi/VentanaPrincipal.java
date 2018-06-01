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
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

import java.io.BufferedReader;


public class VentanaPrincipal extends JFrame implements ActionListener, ChangeListener {

	private JPanel contentPane;
	static private final String newline = "\n";
	private JButton botonEjecutar;
	private JButton botonCancelar;
	private static JTextArea textArea1;
	private JScrollPane scrollpane1;
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
    private static JSpinner spinnerClusterFinal;
    private JLabel  lblDimensionX;
    private JLabel  lblDimensionY;
    private static JSpinner spinnerDimensionX;
    private static JSpinner spinnerDimensionY;

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
		this.setBounds(100, 100, 885, 501);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		VentanaPrincipal.banderaCancelar= false;
		
		botonEjecutar = new JButton("Ejecutar");
		botonEjecutar.setBounds(39, 427, 89, 23);
		botonEjecutar.addActionListener(this);
		contentPane.add(botonEjecutar);
		botonEjecutar.setVisible(false);
		
		botonCancelar = new JButton("Cancelar");
		botonCancelar.setBounds(157, 427, 89, 23);
		botonCancelar.addActionListener(this);
		contentPane.add(botonCancelar);
		botonCancelar.setVisible(false);
		
 		textArea1=new JTextArea();
 		textArea1.setEditable(false);
 		
 		scrollpane1=new JScrollPane(textArea1);
 		scrollpane1.setBounds(300, 150, 550, 300);
 		contentPane.add(scrollpane1);
		
		labelCantidadClusters = new JLabel("labelCantidadClusters");
		labelCantidadClusters.setBounds(39, 187, 141, 14);
		contentPane.add(labelCantidadClusters);
		labelCantidadClusters.setVisible(false);
		
		spinnerCantidadClusters = new JSpinner();
		spinnerCantidadClusters.setBounds(220, 183, 39, 23);
		spinnerCantidadClusters.setModel(new SpinnerNumberModel(2,2,100,1));
		contentPane.add(spinnerCantidadClusters);
		spinnerCantidadClusters.setVisible(false);
		spinnerCantidadClusters.setEnabled(false);
		
		lblClusterFinal = new JLabel("Cluster Final:");
		lblClusterFinal.setBounds(39, 212, 73, 14);
		contentPane.add(lblClusterFinal);
		lblClusterFinal.setEnabled(false);
		lblClusterFinal.setVisible(false);
		
		spinnerClusterFinal = new JSpinner();
		spinnerClusterFinal.setBounds(220, 209, 39, 20);
		spinnerClusterFinal.setModel(new SpinnerNumberModel(3,3,100,1));
		contentPane.add(spinnerClusterFinal);
		spinnerClusterFinal.setEnabled(false);
		spinnerClusterFinal.setVisible(false);
		
		fc = new JFileChooser("C:\\");
		
		botonSeleccionarArchivo = new JButton("Seleccionar Archivo");
		botonSeleccionarArchivo.setBounds(39, 143, 153, 23);
		botonSeleccionarArchivo.addActionListener(this);
		contentPane.add(botonSeleccionarArchivo);
		botonSeleccionarArchivo.setVisible(false);		
		
		bg=new ButtonGroup();
		
		rdbtnClusteringGenetico = new JRadioButton("Clustering Genético");
		rdbtnClusteringGenetico.setBounds(39, 32, 173, 23);
		rdbtnClusteringGenetico.addChangeListener(this);
		rdbtnClusteringGenetico.setToolTipText("Realiza el clustering utilizando un algoritmo genético.");
		contentPane.add(rdbtnClusteringGenetico);
		bg.add(rdbtnClusteringGenetico);
		
		rdbtnClusteringGeneticoPor = new JRadioButton("Clustering Genético Por Rangos");
		rdbtnClusteringGeneticoPor.setBounds(39, 58, 228, 23);
		rdbtnClusteringGeneticoPor.addChangeListener(this);
		rdbtnClusteringGeneticoPor.setToolTipText("Permite ingresar un rango de clústers y devuelve la mejor configuración.");
		contentPane.add(rdbtnClusteringGeneticoPor);
		bg.add(rdbtnClusteringGeneticoPor);
		
		rdbtnComparacinConKmeans = new JRadioButton("Comparaci\u00F3n con K-Means");
		rdbtnComparacinConKmeans.setBounds(39, 84, 207, 23);
		rdbtnComparacinConKmeans.addChangeListener(this);
		rdbtnComparacinConKmeans.setToolTipText("Compara el algoritmo genético con el algoritmo K-means.");
		contentPane.add(rdbtnComparacinConKmeans);
		bg.add(rdbtnComparacinConKmeans);
		
		lblPorcentajeSeleccion = new JLabel("Porcentaje de Selección");
		lblPorcentajeSeleccion.setBounds(39, 237, 141, 14);
		lblPorcentajeSeleccion.setToolTipText("Define el porcentaje que se va a seleccionar del dataset por selección elitista.");
		contentPane.add(lblPorcentajeSeleccion);
		lblPorcentajeSeleccion.setVisible(false);
		
		lblPorcentajeCruza = new JLabel("Porcentaje de Cruza");
		lblPorcentajeCruza.setBounds(39, 262, 123, 14);
		lblPorcentajeCruza.setToolTipText("Define el porcentaje que se va a cruzar del dataset por cruza simple.");
		contentPane.add(lblPorcentajeSeleccion);
		contentPane.add(lblPorcentajeCruza);
		lblPorcentajeCruza.setVisible(false);
		
		lblPorcentajeMutacion = new JLabel("Porcentaje de Mutación");
		lblPorcentajeMutacion.setBounds(39, 287, 141, 14);
		lblPorcentajeMutacion.setToolTipText("Define el porcentaje que se va a mutar del dataset por mutación simple.");
		contentPane.add(lblPorcentajeMutacion);
		lblPorcentajeMutacion.setVisible(false);
		
		spinnerPorcentajeSeleccion = new JSpinner();
		spinnerPorcentajeSeleccion.setBounds(220, 233, 39, 23);
		spinnerPorcentajeSeleccion.setModel(new SpinnerNumberModel(10,1,100,1));
		spinnerPorcentajeSeleccion.setToolTipText("Define el porcentaje que se va a seleccionar del dataset por selección elitista.");
		contentPane.add(spinnerPorcentajeSeleccion);
		spinnerPorcentajeSeleccion.setVisible(false);
		
		spinnerPorcentajeCruza = new JSpinner();
		spinnerPorcentajeCruza.setBounds(220, 258, 39, 23);
		spinnerPorcentajeCruza.setModel(new SpinnerNumberModel(85,1,100,1));
		spinnerPorcentajeCruza.setToolTipText("Define el porcentaje que se va a cruzar del dataset por cruza simple.");
		contentPane.add(spinnerPorcentajeCruza);
		spinnerPorcentajeCruza.setVisible(false);
		
		spinnerPorcentajeMutacion = new JSpinner();
		spinnerPorcentajeMutacion.setBounds(220, 283, 39, 23);
		spinnerPorcentajeMutacion.setModel(new SpinnerNumberModel(5,1,100,1));
		spinnerPorcentajeMutacion.setToolTipText("Define el porcentaje que se va a mutar del dataset por mutación simple.");
		contentPane.add(spinnerPorcentajeMutacion);
		spinnerPorcentajeMutacion.setVisible(false);
		
		lblCantidadIndividuos = new JLabel("Cantidad de Individuos:");
		lblCantidadIndividuos.setBounds(39, 312, 153, 14);
		contentPane.add(lblCantidadIndividuos);
		lblCantidadIndividuos.setVisible(false);		
		
		lblCantidadGeneraciones = new JLabel("Cantidad de Generaciones:");
		lblCantidadGeneraciones.setBounds(39, 337, 153, 14);
		contentPane.add(lblCantidadGeneraciones);
		lblCantidadGeneraciones.setVisible(false);
		
		spinnerCantidadIndividuos = new JSpinner();
		spinnerCantidadIndividuos.setBounds(212, 308, 47, 23);
		spinnerCantidadIndividuos.setModel(new SpinnerNumberModel(100,2,10000,1));
		contentPane.add(spinnerCantidadIndividuos);
		spinnerCantidadIndividuos.setVisible(false);
		
		spinnerCantidadGeneraciones = new JSpinner();
		spinnerCantidadGeneraciones.setBounds(212, 333, 47, 23);
		spinnerCantidadGeneraciones.setModel(new SpinnerNumberModel(100,2,10000,1));
		contentPane.add(spinnerCantidadGeneraciones);
		spinnerCantidadGeneraciones.setVisible(false);
		
		lblDimensionX = new JLabel("Dimensi\u00F3n X: ");
		lblDimensionX.setBounds(39, 362, 102, 14);
		contentPane.add(lblDimensionX);
		lblDimensionX.setEnabled(false);
		lblDimensionX.setVisible(false);
		
		lblDimensionY = new JLabel("Dimensi\u00F3n Y: ");
		lblDimensionY.setBounds(39, 387, 102, 14);
		contentPane.add(lblDimensionY);
		lblDimensionY.setEnabled(false);
		lblDimensionY.setVisible(false);
		
		spinnerDimensionX = new JSpinner();
		spinnerDimensionX.setBounds(220, 359, 39, 20);
		contentPane.add(spinnerDimensionX);
		spinnerDimensionX.setEnabled(false);
		spinnerDimensionX.setVisible(false);
		
		spinnerDimensionY = new JSpinner();
		spinnerDimensionY.setBounds(219, 384, 40, 20);
		contentPane.add(spinnerDimensionY);
		spinnerDimensionY.setEnabled(false);
		spinnerDimensionY.setVisible(false);


	}
	
	@Override
	public void stateChanged(ChangeEvent arg0) {
		
        if (rdbtnClusteringGenetico.isSelected()) {    	
        	VentanaPrincipal.menu= 1;
        	botonEjecutar.setVisible(true);
        	botonCancelar.setVisible(true);
        	botonSeleccionarArchivo.setVisible(true);
        	labelCantidadClusters.setText("Cantidad de Clusters:");
        	labelCantidadClusters.setToolTipText("Define la cantidad de clústers en las que se quiere dividir el dataset.");
        	labelCantidadClusters.setVisible(true);
    		spinnerCantidadClusters.setToolTipText("Define la cantidad de clústers en las que se quiere dividir el dataset.");
        	spinnerCantidadClusters.setVisible(true);
        	lblClusterFinal.setEnabled(false);
        	lblClusterFinal.setVisible(true);
    		spinnerClusterFinal.setEnabled(false);
    		spinnerClusterFinal.setVisible(true);
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
    		lblDimensionX.setVisible(true);
    		lblDimensionY.setVisible(true);
    		spinnerDimensionX.setVisible(true);
    		spinnerDimensionY.setVisible(true);
        }
        if (rdbtnClusteringGeneticoPor.isSelected()) {
        	VentanaPrincipal.menu= 2;
        	botonEjecutar.setVisible(true);
        	botonCancelar.setVisible(true);
        	botonSeleccionarArchivo.setVisible(true);
        	labelCantidadClusters.setText("Cluster Inicial:");
        	labelCantidadClusters.setToolTipText("Define la mínima cantidad de clusters a evaluar");
        	labelCantidadClusters.setVisible(true);
        	spinnerCantidadClusters.setToolTipText("Define la mínima cantidad de clusters a evaluar");
        	spinnerCantidadClusters.setVisible(true);
        	lblClusterFinal.setToolTipText("Define la máxima cantidad de clusters a evaluar");
        	lblClusterFinal.setEnabled(true);
        	lblClusterFinal.setVisible(true);
        	spinnerClusterFinal.setToolTipText("Define la máxima cantidad de clusters a evaluar");
    		spinnerClusterFinal.setEnabled(true);
    		spinnerClusterFinal.setVisible(true);
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
    		lblDimensionX.setVisible(true);
    		lblDimensionY.setVisible(true);
    		spinnerDimensionX.setVisible(true);
    		spinnerDimensionY.setVisible(true);
        }
        if (rdbtnComparacinConKmeans.isSelected()) {
        	VentanaPrincipal.menu= 3;
        	botonEjecutar.setVisible(true);
        	botonCancelar.setVisible(true);
        	botonSeleccionarArchivo.setVisible(true);
        	labelCantidadClusters.setText("Cantidad de Clusters:");
        	labelCantidadClusters.setToolTipText("Define la cantidad de clústers en las que se quiere dividir el dataset.");
        	labelCantidadClusters.setVisible(true);
    		spinnerCantidadClusters.setToolTipText("Define la cantidad de clústers en las que se quiere dividir el dataset.");
        	spinnerCantidadClusters.setVisible(true);
        	lblClusterFinal.setEnabled(false);
        	lblClusterFinal.setVisible(true);
    		spinnerClusterFinal.setEnabled(false);
    		spinnerClusterFinal.setVisible(true);
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
    		lblDimensionX.setVisible(true);
    		lblDimensionY.setVisible(true);
    		spinnerDimensionX.setVisible(true);
    		spinnerDimensionY.setVisible(true);
        } 
		
	}
	
	@Override
	public void actionPerformed(ActionEvent evento){
		
        if (evento.getSource() == botonSeleccionarArchivo) {
        	
            int returnVal = fc.showOpenDialog(VentanaPrincipal.this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
               file = fc.getSelectedFile();               
               //This is where a real application would open the file.
               textArea1.append("Seleccionado el archivo " + file.getName() + "." + newline);
               //lblArchivoSubido.setText(file.getName());
               
               try {
            	int cantidadTransaccionesMenosUno= this.obtenerNumeroDeTransacciones() - 1;            	
            	spinnerCantidadClusters.setModel(new SpinnerNumberModel(2,2,cantidadTransaccionesMenosUno,1));
            	spinnerCantidadClusters.setEnabled(true);
            	spinnerClusterFinal.setModel(new SpinnerNumberModel(3,3,cantidadTransaccionesMenosUno,1));
				spinnerClusterFinal.setEnabled(true);
				lblDimensionX.setEnabled(true);
				spinnerDimensionX.setModel(new SpinnerNumberModel(1,1,this.obtenerNumeroDeDimensiones(),1));
				spinnerDimensionX.setEnabled(true);
				lblDimensionY.setEnabled(true);
				spinnerDimensionY.setModel(new SpinnerNumberModel(2,1,this.obtenerNumeroDeDimensiones(),1));
				spinnerDimensionY.setEnabled(true);
				
			   } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			   }               
            } else {
            	textArea1.append("Cancelada la seleccion de archivo." + newline);
            }
            textArea1.setCaretPosition(textArea1.getDocument().getLength());
        }
		
		if (evento.getSource() == botonEjecutar) {
			
			boolean existeError;
			existeError= false;
			int sumaPorcentajes= (int)VentanaPrincipal.spinnerPorcentajeSeleccion.getValue() + (int)VentanaPrincipal.spinnerPorcentajeCruza.getValue() + (int)VentanaPrincipal.spinnerPorcentajeMutacion.getValue();
			if (sumaPorcentajes != 100) {
				existeError= true;
				JOptionPane.showMessageDialog(null, "La sumatoria de los porcentajes de selección, cruza y mutación debe sumar 100", "Error", JOptionPane.ERROR_MESSAGE);
			}
			
			if (((int)VentanaPrincipal.spinnerClusterFinal.getValue() <= (int)VentanaPrincipal.spinnerCantidadClusters.getValue()) & (VentanaPrincipal.menu == 2)) {
				existeError= true; 
				JOptionPane.showMessageDialog(null, "El cluster final no puede ser menor o igual al cluster inicial", "Error", JOptionPane.ERROR_MESSAGE);
			}
			
			if(file == null){
				existeError= true;
				JOptionPane.showMessageDialog(null, "Especificar el archivo dataset", "Error", JOptionPane.ERROR_MESSAGE);
            }
			
			if ((int)spinnerDimensionX.getValue() == (int)spinnerDimensionY.getValue()) {
				existeError= true;
				JOptionPane.showMessageDialog(null, "Debe elegir dos dimensiones diferentes", "Error", JOptionPane.ERROR_MESSAGE);

			}
			
			try {
				if (existeError == false) {
					ClusteringGenetico.ejecutar();
					JOptionPane.showMessageDialog(null, "El tiempo de ejecución del programa fue de "+ClusteringGenetico.getTiempoDeEjecucion()+" segundos.", "Información", JOptionPane.INFORMATION_MESSAGE);
				}				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (evento.getSource() == botonCancelar) {			
			VentanaPrincipal.banderaCancelar= true;
			JOptionPane.showMessageDialog(null, "Ejecución Cancelada", "Aviso", JOptionPane.INFORMATION_MESSAGE);
		}
		
	}
	
	public int obtenerNumeroDeTransacciones() throws Exception {
		
		String archivoTransacciones;   			
		archivoTransacciones= file.getAbsolutePath();
        BufferedReader data_in = new BufferedReader(new FileReader(archivoTransacciones));
    	while (data_in.ready()) {    		
    		String line= data_in.readLine();
                
    		if (line.matches("\\s*")) {
    			continue; // saltar lineas vacias
    		}
    		
    		numeroTransacciones++;
    	}
    	return numeroTransacciones++;
	}
	
	public int obtenerNumeroDeDimensiones() throws Exception {
		
		String archivoTransacciones= file.getAbsolutePath();
        BufferedReader data = new BufferedReader(new FileReader(archivoTransacciones));
        String linea=data.readLine();
        StringTokenizer to = new StringTokenizer(linea, "\t");
	    int nroDimensiones = 0;
        while (to.hasMoreTokens()) {
	    	String aux = to.nextToken();
	    	nroDimensiones++;//acumula cantidad de dimensiones
	    } 
    	return nroDimensiones++;
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

	public static JSpinner getSpinnerClusterFinal() {
		return spinnerClusterFinal;
	}

	public static void setSpinnerClusterFinal(JSpinner spinnerClusterFinal) {
		VentanaPrincipal.spinnerClusterFinal = spinnerClusterFinal;
	}

	public static JTextArea getTextArea1() {
		return textArea1;
	}

	public static void setTextArea1(JTextArea textArea1) {
		VentanaPrincipal.textArea1 = textArea1;
	}

	public static JSpinner getSpinnerDimensionX() {
		return spinnerDimensionX;
	}

	public static void setSpinnerDimensionX(JSpinner spinnerDimensionX) {
		VentanaPrincipal.spinnerDimensionX = spinnerDimensionX;
	}

	public static JSpinner getSpinnerDimensionY() {
		return spinnerDimensionY;
	}

	public static void setSpinnerDimensionY(JSpinner spinnerDimensionY) {
		VentanaPrincipal.spinnerDimensionY = spinnerDimensionY;
	}
	
	
}
