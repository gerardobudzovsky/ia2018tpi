package tpi;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.TreeSet;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import javax.imageio.ImageIO;
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
	private JLabel lblLogo;
	private JButton botonEjecutar;
//	private JButton botonCancelar;
	private JLabel lblArchivoSubido;
	private JButton btnMostrarGraficoGenetico;
    private JButton btnMostrarGraficoKMeans;
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
    private static ScatterPlotExample grafico1;
    private static ScatterPlotExample grafico2;
	private static VentanaCentroidesCase1 ventanaCentroidesCase1;
    private static VentanaCentroidesCase2 ventanaCentroidesCase2;   
    private static VentanaCentroidesCase3 ventanaCentroidesCase3;
    private static VentanaClustersAsociadosCase1y2 ventanaClustersAsociadosCase1y2;
    private static VentanaClustersAsociadosCase3 ventanaClustersAsociadosCase3;

	private JButton btnMostrarCentroides;
    private JButton btnMostrarClusters;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaPrincipal ventana1 = new VentanaPrincipal();
					ventana1.setVisible(true);
			        ventana1.setResizable(false);
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
		this.setBounds(5, 5, 802, 648);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		VentanaPrincipal.banderaCancelar= false;
		
		botonEjecutar = new JButton("Ejecutar");
		botonEjecutar.setBounds(15, 553, 89, 23);
		botonEjecutar.addActionListener(this);
		contentPane.add(botonEjecutar);
		
		btnMostrarGraficoGenetico = new JButton("Gráfico");
		btnMostrarGraficoGenetico.setBounds(227, 553, 141, 23);
		btnMostrarGraficoGenetico.addActionListener(this);
		contentPane.add(btnMostrarGraficoGenetico);
		
		btnMostrarGraficoKMeans = new JButton("Gráfico K-Means");
		btnMostrarGraficoKMeans.setBounds(388, 553, 141, 23);
		btnMostrarGraficoKMeans.addActionListener(this);
		contentPane.add(btnMostrarGraficoKMeans);
		
		btnMostrarCentroides = new JButton("Centroides");
		btnMostrarCentroides.setBounds(539, 553, 102, 23);
		btnMostrarCentroides.addActionListener(this);
		contentPane.add(btnMostrarCentroides);
		
		btnMostrarClusters = new JButton("Clusters");
		btnMostrarClusters.setBounds(651, 553, 89, 23);
		btnMostrarClusters.addActionListener(this);
		contentPane.add(btnMostrarClusters);
		
//		botonCancelar = new JButton("Cancelar");
//		botonCancelar.setBounds(123, 553, 89, 23);
//		botonCancelar.addActionListener(this);
//		contentPane.add(botonCancelar);
		
 		textArea1=new JTextArea();
 		textArea1.setEditable(false);
 		
 		scrollpane1=new JScrollPane(textArea1);
 		scrollpane1.setBounds(300, 150, 475, 375);
 		contentPane.add(scrollpane1);
		
		labelCantidadClusters = new JLabel("labelCantidadClusters");
		labelCantidadClusters.setBounds(15, 278, 141, 14);
		contentPane.add(labelCantidadClusters);
		
		spinnerCantidadClusters = new JSpinner();
		spinnerCantidadClusters.setBounds(220, 273, 39, 23);
		spinnerCantidadClusters.setModel(new SpinnerNumberModel(2,2,100,1));
		contentPane.add(spinnerCantidadClusters);
		
		lblClusterFinal = new JLabel("Cluster Final:");
		lblClusterFinal.setBounds(15, 308, 102, 14);
		contentPane.add(lblClusterFinal);
		
		spinnerClusterFinal = new JSpinner();
		spinnerClusterFinal.setBounds(220, 305, 39, 20);
		spinnerClusterFinal.setModel(new SpinnerNumberModel(3,3,100,1));
		contentPane.add(spinnerClusterFinal);
		
		fc = new JFileChooser("C:\\");
		
		botonSeleccionarArchivo = new JButton("Seleccionar Archivo");
		botonSeleccionarArchivo.setBounds(15, 239, 153, 23);
		botonSeleccionarArchivo.addActionListener(this);
		contentPane.add(botonSeleccionarArchivo);
		
        lblArchivoSubido = new JLabel("");
		lblArchivoSubido.setBounds(180, 242, 108, 16);
		contentPane.add(lblArchivoSubido);
        contentPane.add(botonSeleccionarArchivo);
		
		bg=new ButtonGroup();
		
		rdbtnClusteringGenetico = new JRadioButton("Clustering Genético");
		rdbtnClusteringGenetico.setBounds(15, 115, 173, 23);
		rdbtnClusteringGenetico.addChangeListener(this);
		rdbtnClusteringGenetico.setToolTipText("Realiza el clustering utilizando un algoritmo genético.");
		contentPane.add(rdbtnClusteringGenetico);
		bg.add(rdbtnClusteringGenetico);
		
		rdbtnClusteringGeneticoPor = new JRadioButton("Clustering Genético Por Rangos");
		rdbtnClusteringGeneticoPor.setBounds(15, 150, 261, 23);
		rdbtnClusteringGeneticoPor.addChangeListener(this);
		rdbtnClusteringGeneticoPor.setToolTipText("Permite ingresar un rango de clústers y devuelve la mejor configuración.");
		contentPane.add(rdbtnClusteringGeneticoPor);
		bg.add(rdbtnClusteringGeneticoPor);
		
		rdbtnComparacinConKmeans = new JRadioButton("Comparaci\u00F3n con K-Means");
		rdbtnComparacinConKmeans.setBounds(15, 183, 228, 23);
		rdbtnComparacinConKmeans.addChangeListener(this);
		rdbtnComparacinConKmeans.setToolTipText("Compara el algoritmo genético con el algoritmo K-means.");
		contentPane.add(rdbtnComparacinConKmeans);
		bg.add(rdbtnComparacinConKmeans);
		
		lblPorcentajeSeleccion = new JLabel("Porcentaje de Selección");
		lblPorcentajeSeleccion.setBounds(15, 338, 173, 14);
		lblPorcentajeSeleccion.setToolTipText("Define el porcentaje que se va a seleccionar del dataset por selección elitista.");
		contentPane.add(lblPorcentajeSeleccion);
		
		lblPorcentajeCruza = new JLabel("Porcentaje de Cruza");
		lblPorcentajeCruza.setBounds(15, 362, 153, 14);
		lblPorcentajeCruza.setToolTipText("Define el porcentaje que se va a cruzar del dataset por cruza simple.");
		contentPane.add(lblPorcentajeSeleccion);
		contentPane.add(lblPorcentajeCruza);
		
		lblPorcentajeMutacion = new JLabel("Porcentaje de Mutación");
		lblPorcentajeMutacion.setBounds(15, 392, 173, 14);
		lblPorcentajeMutacion.setToolTipText("Define el porcentaje que se va a mutar del dataset por mutación simple.");
		contentPane.add(lblPorcentajeMutacion);
		
		spinnerPorcentajeSeleccion = new JSpinner();
		spinnerPorcentajeSeleccion.setBounds(220, 333, 39, 23);
		spinnerPorcentajeSeleccion.setModel(new SpinnerNumberModel(10,1,100,1));
		spinnerPorcentajeSeleccion.setToolTipText("Define el porcentaje que se va a seleccionar del dataset por selección elitista.");
		contentPane.add(spinnerPorcentajeSeleccion);
		
		spinnerPorcentajeCruza = new JSpinner();
		spinnerPorcentajeCruza.setBounds(220, 357, 39, 23);
		spinnerPorcentajeCruza.setModel(new SpinnerNumberModel(85,1,100,1));
		spinnerPorcentajeCruza.setToolTipText("Define el porcentaje que se va a cruzar del dataset por cruza simple.");
		contentPane.add(spinnerPorcentajeCruza);
		
		spinnerPorcentajeMutacion = new JSpinner();
		spinnerPorcentajeMutacion.setBounds(220, 387, 39, 23);
		spinnerPorcentajeMutacion.setModel(new SpinnerNumberModel(5,1,100,1));
		spinnerPorcentajeMutacion.setToolTipText("Define el porcentaje que se va a mutar del dataset por mutación simple.");
		contentPane.add(spinnerPorcentajeMutacion);
		
		lblCantidadIndividuos = new JLabel("Cantidad de Individuos:");
		lblCantidadIndividuos.setBounds(15, 422, 173, 14);
		contentPane.add(lblCantidadIndividuos);
		
		lblCantidadGeneraciones = new JLabel("Cantidad de Generaciones:");
		lblCantidadGeneraciones.setBounds(15, 449, 173, 14);
		contentPane.add(lblCantidadGeneraciones);
		
		spinnerCantidadIndividuos = new JSpinner();
		spinnerCantidadIndividuos.setBounds(220, 417, 47, 23);
		spinnerCantidadIndividuos.setModel(new SpinnerNumberModel(100,2,10000,1));
		contentPane.add(spinnerCantidadIndividuos);
		
		spinnerCantidadGeneraciones = new JSpinner();
		spinnerCantidadGeneraciones.setBounds(219, 444, 47, 23);
		spinnerCantidadGeneraciones.setModel(new SpinnerNumberModel(100,2,10000,1));
		contentPane.add(spinnerCantidadGeneraciones);
		
		lblDimensionX = new JLabel("Dimensi\u00F3n X: ");
		lblDimensionX.setBounds(15, 479, 102, 14);
		contentPane.add(lblDimensionX);
		
		lblDimensionY = new JLabel("Dimensi\u00F3n Y: ");
		lblDimensionY.setBounds(15, 509, 102, 14);
		contentPane.add(lblDimensionY);
		
		spinnerDimensionX = new JSpinner();
		spinnerDimensionX.setBounds(220, 476, 39, 20);
		contentPane.add(spinnerDimensionX);
		
		spinnerDimensionY = new JSpinner();
		spinnerDimensionY.setBounds(220, 506, 40, 20);
		contentPane.add(spinnerDimensionY);
		
		//Read the picture as a BufferedImage
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File("logo.png"));
		} 
		catch (IOException e) {
		    e.printStackTrace();
		}
		
		lblLogo = new JLabel("TITULO TITULO TITULO TITULO TITULO TITULO TITULO TITULO TITULO");
		lblLogo.setBounds(99, 16, 550, 55);
		contentPane.add(lblLogo);
		
		//Resize the BufferedImage
		Image dimg = img.getScaledInstance(lblLogo.getWidth(), lblLogo.getHeight(),
		        Image.SCALE_SMOOTH);
		
		//Create an ImageIcon
		ImageIcon aImageIcon = new ImageIcon(dimg);
		
		lblLogo.setIcon(aImageIcon);

	}
	
	@Override
	public void stateChanged(ChangeEvent arg0) {
		
        if (rdbtnClusteringGenetico.isSelected()) {    	
        	VentanaPrincipal.menu= 1;
    		btnMostrarGraficoGenetico.setText("Gráfico");

        	labelCantidadClusters.setText("Cantidad de Clusters:");
        	labelCantidadClusters.setToolTipText("Define la cantidad de clústers en las que se quiere dividir el dataset.");
    		spinnerCantidadClusters.setToolTipText("Define la cantidad de clústers en las que se quiere dividir el dataset.");
        }
        if (rdbtnClusteringGeneticoPor.isSelected()) {
        	VentanaPrincipal.menu= 2;

    		btnMostrarGraficoGenetico.setText("Gráfico");
        	labelCantidadClusters.setText("Cluster Inicial:");
        	labelCantidadClusters.setToolTipText("Define la mínima cantidad de clusters a evaluar");
        	spinnerCantidadClusters.setToolTipText("Define la mínima cantidad de clusters a evaluar");
        	lblClusterFinal.setToolTipText("Define la máxima cantidad de clusters a evaluar");
        	spinnerClusterFinal.setToolTipText("Define la máxima cantidad de clusters a evaluar");
        }
        if (rdbtnComparacinConKmeans.isSelected()) {
        	VentanaPrincipal.menu= 3;

        	btnMostrarGraficoGenetico.setText("Gráfico Genético");

    		btnMostrarGraficoKMeans.setText("Gráfico K-Means");
        	labelCantidadClusters.setText("Cantidad de Clusters:");
        	labelCantidadClusters.setToolTipText("Define la cantidad de clústers en las que se quiere dividir el dataset.");
    		spinnerCantidadClusters.setToolTipText("Define la cantidad de clústers en las que se quiere dividir el dataset.");

        } 
		
	}
	
	@Override
	public void actionPerformed(ActionEvent evento){
		
        if (evento.getSource() == botonSeleccionarArchivo) {
        	
            int returnVal = fc.showOpenDialog(VentanaPrincipal.this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
               file = fc.getSelectedFile();               
               //This is where a real application would open the file.
               lblArchivoSubido.setText(file.getName());
               
               try {
            	   
            	int cantidadTransaccionesMenosUno= this.obtenerNumeroDeTransacciones() - 1;            	
            	spinnerCantidadClusters.setModel(new SpinnerNumberModel(2,2,cantidadTransaccionesMenosUno,1));
            	spinnerClusterFinal.setModel(new SpinnerNumberModel(3,3,cantidadTransaccionesMenosUno,1));

				int nroDimensiones= this.obtenerNumeroDeDimensiones();
				lblDimensionX.setToolTipText("Define una dimensión a graficar del dataset entre 1 y "+nroDimensiones);
				spinnerDimensionX.setModel(new SpinnerNumberModel(1,1,nroDimensiones,1));
				spinnerDimensionX.setToolTipText("Define una dimensión a graficar del dataset entre 1 y "+nroDimensiones);
				lblDimensionY.setToolTipText("Define una dimensión a graficar del dataset entre 1 y "+nroDimensiones);
				spinnerDimensionY.setModel(new SpinnerNumberModel(2,1,nroDimensiones,1));
				spinnerDimensionY.setToolTipText("Define una dimensión a graficar del dataset entre 1 y "+nroDimensiones);
	            textArea1.append("El dataset tiene " + nroDimensiones + " dimensiones." + newline);
	            textArea1.append(newline);
				
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
					VentanaPrincipal.textArea1.setText("");
		            textArea1.append("El dataset tiene " + this.obtenerNumeroDeDimensiones() + " dimensiones." + newline);
		            textArea1.append(newline);
					ClusteringGenetico.ejecutar();
					VentanaPrincipal.textArea1.append("El tiempo de ejecución del programa fue de " + ClusteringGenetico.tiempoDeEjecucion + " segundos.");
					JOptionPane.showMessageDialog(null, "El programa se ejecutó con éxito " + ClusteringGenetico.tiempoDeEjecucion + " segundos.", "Información", JOptionPane.INFORMATION_MESSAGE);
				}				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (evento.getSource() == btnMostrarGraficoGenetico) {			
			if (VentanaPrincipal.menu == 3) {				
				if (evento.getSource() == btnMostrarGraficoGenetico) {					
					if (VentanaPrincipal.grafico1.isVisible() == false) {
						this.btnMostrarGraficoGenetico.setText("Gráfico genético");
						VentanaPrincipal.grafico1.setVisible(true);
					}
					else {
						this.btnMostrarGraficoGenetico.setText("Gráfico genético");
						VentanaPrincipal.grafico1.setVisible(false);
					}		
				}
				
			}
			else {				
				if (VentanaPrincipal.grafico1.isVisible() == false) {
					this.btnMostrarGraficoGenetico.setText("Gráfico");
					VentanaPrincipal.grafico1.setVisible(true);
				}
				else {
					this.btnMostrarGraficoGenetico.setText("Gráfico");
					VentanaPrincipal.grafico1.setVisible(false);
				}				
			}			
		}		
			
		if (evento.getSource() == btnMostrarGraficoKMeans) {
			if (VentanaPrincipal.grafico2.isVisible() == false) {
				this.btnMostrarGraficoKMeans.setText("Gráfico K-Means");
				VentanaPrincipal.grafico2.setVisible(true);
			}
			else {
				this.btnMostrarGraficoKMeans.setText("Gráfico K-Means");
				VentanaPrincipal.grafico2.setVisible(false);

			}		
		}
		
//		if (evento.getSource() == botonCancelar) {			
//			VentanaPrincipal.banderaCancelar= true;
//			JOptionPane.showMessageDialog(null, "Ejecución Cancelada", "Aviso", JOptionPane.INFORMATION_MESSAGE);
//		}
		
        if (evento.getSource() == btnMostrarCentroides) {
            try{
            	if (VentanaPrincipal.menu == 1) {
        			if (VentanaPrincipal.ventanaCentroidesCase1.isVisible() == false) {
        				VentanaPrincipal.ventanaCentroidesCase1.setVisible(true);
        			}
        			else {
        				VentanaPrincipal.ventanaCentroidesCase1.setVisible(false);
        			}
            	}
            	else { 
            		if (VentanaPrincipal.menu == 2) {
            			if (VentanaPrincipal.ventanaCentroidesCase2.isVisible() == false) {
            				VentanaPrincipal.ventanaCentroidesCase2.setVisible(true);
            			}
            			else {
            				VentanaPrincipal.ventanaCentroidesCase2.setVisible(false);
            			}
            		}
            		else {
            			if (VentanaPrincipal.ventanaCentroidesCase3.isVisible() == false) {
            				VentanaPrincipal.ventanaCentroidesCase3.setVisible(true);
            			}
            			else {
            				VentanaPrincipal.ventanaCentroidesCase3.setVisible(false);
            			}
            		}
            	}
            
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        
        if(evento.getSource() == btnMostrarClusters) {
            try{
                if (menu == 3) {

        			if (VentanaPrincipal.ventanaClustersAsociadosCase3.isVisible() == false) {
        				VentanaPrincipal.ventanaClustersAsociadosCase3.setVisible(true);
        			}
        			else {
        				VentanaPrincipal.ventanaClustersAsociadosCase3.setVisible(false);
        			}
                	
				} else {
					
        			if (VentanaPrincipal.ventanaClustersAsociadosCase1y2.isVisible() == false) {
        				VentanaPrincipal.ventanaClustersAsociadosCase1y2.setVisible(true);
        			}
        			else {
        				VentanaPrincipal.ventanaClustersAsociadosCase1y2.setVisible(false);
        			}
					
				}
            	            
            }catch(Exception ex){
                ex.printStackTrace();
            }
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
	
	public static ScatterPlotExample getGrafico1() {
		return grafico1;
	}

	public static void setGrafico1(ScatterPlotExample grafico1) {
		VentanaPrincipal.grafico1 = grafico1;
	}
	
	public static ScatterPlotExample getGrafico2() {
		return grafico2;
	}

	public static void setGrafico2(ScatterPlotExample grafico2) {
		VentanaPrincipal.grafico2 = grafico2;
	}
	
    public static VentanaCentroidesCase1 getVentanaCentroidesCase1() {
		return ventanaCentroidesCase1;
	}

	public static void setVentanaCentroidesCase1(VentanaCentroidesCase1 ventanaCentroidesCase1) {
		VentanaPrincipal.ventanaCentroidesCase1 = ventanaCentroidesCase1;
	}

	public static VentanaCentroidesCase2 getVentanaCentroidesCase2() {
		return ventanaCentroidesCase2;
	}

	public static void setVentanaCentroidesCase2(VentanaCentroidesCase2 ventanaCentroidesCase2) {
		VentanaPrincipal.ventanaCentroidesCase2 = ventanaCentroidesCase2;
	}

	public static VentanaCentroidesCase3 getVentanaCentroidesCase3() {
		return ventanaCentroidesCase3;
	}

	public static void setVentanaCentroidesCase3(VentanaCentroidesCase3 ventanaCentroidesCase3) {
		VentanaPrincipal.ventanaCentroidesCase3 = ventanaCentroidesCase3;
	}

	public static VentanaClustersAsociadosCase1y2 getVentanaClustersAsociadosCase1y2() {
		return ventanaClustersAsociadosCase1y2;
	}

	public static void setVentanaClustersAsociadosCase1y2(VentanaClustersAsociadosCase1y2 ventanaClustersAsociadosCase1y2) {
		VentanaPrincipal.ventanaClustersAsociadosCase1y2 = ventanaClustersAsociadosCase1y2;
	}
	
    public static VentanaClustersAsociadosCase3 getVentanaClustersAsociadosCase3() {
		return ventanaClustersAsociadosCase3;
	}

	public static void setVentanaClustersAsociadosCase3(VentanaClustersAsociadosCase3 ventanaClustersAsociadosCase3) {
		VentanaPrincipal.ventanaClustersAsociadosCase3 = ventanaClustersAsociadosCase3;
	}
}
