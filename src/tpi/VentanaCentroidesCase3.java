package tpi;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.border.BevelBorder;

/**Esta clase representa la ventana
 * emergente donde se muestran el informe
 * para el usuario de las reglas generadas *
 * @author Grupo9-IA2017
 */

public class VentanaCentroidesCase3 extends JDialog implements ChangeListener{

	private final JPanel contentPanel = new JPanel();
	private JRadioButton rdbtnMuyConfiable;
	private JRadioButton rdbtnConfiable;
	private JRadioButton rdbtnPocoConfiable;
	private JRadioButton rdbtnNoConfiable;
	private ButtonGroup bg;
	private JLabel lblNivelesDeConfiabilidad;
	private JLabel lblNewLabel;
	private JTable table1;
	private JTable table2;
	private JTable table3;
	private JTable table4;
	private JScrollPane scrollPane;

	public VentanaCentroidesCase3() {
		
            setBounds(200, 150, 640, 480);
            getContentPane().setLayout(new BorderLayout());
            contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
            getContentPane().add(contentPanel, BorderLayout.CENTER);

            Vector columnTitles1 = new Vector();
    		
            columnTitles1.add("Centroide Genético");			
    		for (int i = 0; i < ClusteringGenetico.mejorIndividuoCase3.dimension; i++) {		
    	        columnTitles1.add("Dimensión " + (i + 1));			
    		}
    		
            Vector columnTitles2 = new Vector();
            
            columnTitles2.add("Centroide Kmeans");			
    		for (int i = 0; i < Kmeans.mejorIndividuoCase3.dimension; i++) {		
    	        columnTitles2.add("Dimensión " + (i + 1));			
    		}
    		

            Vector row1 = new Vector();
            
            for (int i = 0; i < ClusteringGenetico.mejorIndividuoCase3.numClusters; i++) {
                Vector first = new Vector();
                
                first.add(i);
                
                for (int j = 0; j < ClusteringGenetico.mejorIndividuoCase3.dimension; j++) {
                	first.add(ClusteringGenetico.mejorIndividuoCase3.centroides.get(i)[j]);
    			}
                row1.add(first);
                
    		}
            
            Vector row2 = new Vector();
            
            for (int i = 0; i < Kmeans.mejorIndividuoCase3.numClusters; i++) {
                Vector first = new Vector();
                
                first.add(i);
                
                for (int j = 0; j < Kmeans.mejorIndividuoCase3.dimension; j++) {
                	first.add(Kmeans.mejorIndividuoCase3.centroides.get(i)[j]);
    			}
                row2.add(first);                
    		}
            
            

        TableModel model = new DefaultTableModel(row1, columnTitles1) {
            public Class getColumnClass(int column) {
              Class returnValue;
              if ((column >= 0) && (column < getColumnCount())) {
                returnValue = getValueAt(0, column).getClass();
              } else {
                returnValue = Object.class;
              }
              return returnValue;
            }
        };
        
        TableModel model2 = new DefaultTableModel(row2, columnTitles2) {
            public Class getColumnClass(int column) {
              Class returnValue;
              if ((column >= 0) && (column < getColumnCount())) {
                returnValue = getValueAt(0, column).getClass();
              } else {
                returnValue = Object.class;
              }
              return returnValue;
            }
        };

        table1 = new JTable( model );
        table2 = new JTable( model2 );

        table1.setAutoCreateRowSorter(true);
        table2.setAutoCreateRowSorter(true);

		
        scrollPane = new JScrollPane(table1);
        scrollPane.setBounds(10,11,343,419);
        contentPanel.add(scrollPane); 
		
	bg=new ButtonGroup();
		
	rdbtnMuyConfiable = new JRadioButton("Muy Confiable");
	rdbtnMuyConfiable.setBounds(448, 33, 109, 23);
	rdbtnMuyConfiable.addChangeListener(this);
	contentPanel.setLayout(null);
	contentPanel.add(rdbtnMuyConfiable);
        bg.add(rdbtnMuyConfiable);
		
	rdbtnConfiable = new JRadioButton("Confiable");
	rdbtnConfiable.setBounds(448, 59, 109, 23);
	rdbtnConfiable.addChangeListener(this);
	contentPanel.add(rdbtnConfiable);
        bg.add(rdbtnConfiable);
		
	rdbtnPocoConfiable = new JRadioButton("Poco Confiable");
	rdbtnPocoConfiable.setBounds(447, 85, 121, 23);
	rdbtnPocoConfiable.addChangeListener(this);
	contentPanel.add(rdbtnPocoConfiable);
        bg.add(rdbtnPocoConfiable);
		
	rdbtnNoConfiable = new JRadioButton("No Confiable");
	rdbtnNoConfiable.setBounds(448, 111, 109, 23);
	rdbtnNoConfiable.addChangeListener(this);
	contentPanel.add(rdbtnNoConfiable);
        bg.add(rdbtnNoConfiable);
        
        lblNivelesDeConfiabilidad = new JLabel("Niveles de confiabilidad");
        lblNivelesDeConfiabilidad.setBounds(438, 11, 141, 15);
        lblNivelesDeConfiabilidad.setFont(new Font("Tahoma", Font.BOLD, 12));
        contentPanel.add(lblNivelesDeConfiabilidad);
        
        lblNewLabel = new JLabel("");
        lblNewLabel.setBounds(366, 141, 248, 115);
        contentPanel.add(lblNewLabel);
        
        JPanel panel = new JPanel();
        panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        panel.setBounds(363, 136, 251, 222);
        contentPanel.add(panel);
        
        this.show();
	}

	@Override
	public void stateChanged(ChangeEvent e) {        
        if (rdbtnMuyConfiable.isSelected()) {
        	scrollPane.setViewportView(table1);
    		lblNewLabel.setText("<html>"
//    							+ "<p><i>Índice Calinski y Harabasz: </i>"+ ClusteringGenetico.mejorIndividuoCase3.calinski + "</p>"
								+ "<p>Fitness :" + ClusteringGenetico.mejorIndividuoCase3.fitness + "</p>"
    							+ "</html>");			
        }
            if (rdbtnConfiable.isSelected()) {
            	scrollPane.setViewportView(table2);
            	lblNewLabel.setText("<html>"
//    								+ "<p><i>Índice Calinski y Harabasz: </i>"+ Kmeans.mejorIndividuoCase3.calinski + "</p>"
    								+ "<p>Fitness :" + Kmeans.mejorIndividuoCase3.fitness + "</p>"
    								+ "</html>");            }
            if (rdbtnPocoConfiable.isSelected()) {
        	scrollPane.setViewportView(table3);
		lblNewLabel.setText("<html>Una regla poco confiable tiene una relacion entre los items de la premisa y los del consecuente bastante baja. Solamente entre el 50% y el 70% de las veces que los clientes compren los items de la premisa, tambien van adquirir los del consecuente</html>");
            }        
            if (rdbtnNoConfiable.isSelected()) {
        	scrollPane.setViewportView(table4);
		lblNewLabel.setText("<html>Una regla no confiable presenta una relacion muy debil (menor al 50%) de probabilidad de que un cliente que adquiera los items de la premisa, tambien adquiera los del consecuente</html>");
            }		
        }
}
