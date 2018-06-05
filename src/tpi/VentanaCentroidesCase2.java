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

public class VentanaCentroidesCase2 extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JLabel lblNivelesDeConfiabilidad;
	private JLabel lblNewLabel;
	private JTable table1;
	private JScrollPane scrollPane;

	public VentanaCentroidesCase2() {

		this.setTitle("Centroides");
		this.setBounds(200, 150, 640, 480);
		this.getContentPane().setLayout(new BorderLayout());
		this.contentPanel.setLayout(null);
		this.contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.getContentPane().add(contentPanel, BorderLayout.CENTER);

        Vector columnTitles = new Vector();
    		
        columnTitles.add("Centroide");			
    	for (int i = 0; i < ClusteringGenetico.mejorIndividuoCase2.dimension; i++) {		
    		columnTitles.add("Dimensión " + (i + 1));			
    	}		
            
    	Vector row = new Vector();
            
    	for (int i = 0; i < ClusteringGenetico.mejorIndividuoCase2.numClusters; i++) {
    		Vector first = new Vector();
               
    		first.add(i);
               
    		for (int j = 0; j < ClusteringGenetico.mejorIndividuoCase2.dimension; j++) {
    			first.add(ClusteringGenetico.mejorIndividuoCase2.centroides.get(i)[j]);
    		}
    		row.add(first);
        }

        TableModel model = new DefaultTableModel(row, columnTitles) {
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
        table1.setAutoCreateRowSorter(true);
		
        scrollPane = new JScrollPane(table1);
        scrollPane.setBounds(10,11,343,419);
        contentPanel.add(scrollPane); 
        
        scrollPane.setViewportView(table1);
        
        lblNivelesDeConfiabilidad = new JLabel("Datos");
        lblNivelesDeConfiabilidad.setBounds(363, 11, 237, 15);
        lblNivelesDeConfiabilidad.setFont(new Font("Tahoma", Font.BOLD, 12));
        contentPanel.add(lblNivelesDeConfiabilidad);
        
        lblNewLabel = new JLabel("");
        lblNewLabel.setBounds(373, 48, 241, 47);
        contentPanel.add(lblNewLabel);
        
		lblNewLabel.setText("<html>"
							+ "<p>Índice Calinski y Harabasz:"+ ClusteringGenetico.mejorIndividuoCase2.calinski + "</p>"
							+ "<p>Fitness :" + trunc(ClusteringGenetico.mejorIndividuoCase2.fitness) + "</p>"
							+ "</html>");
        
        JPanel panel = new JPanel();
        panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        panel.setBounds(363, 37, 251, 68);
        contentPanel.add(panel);
        
        this.show();
	}
	
	static double trunc (double num) {
		 double truncado;
		 truncado=Math.floor(num*1000)/1000; 
		 return truncado;
	 }
}