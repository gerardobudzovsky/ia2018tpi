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

public class VentanaClustersAsociadosCase1y2 extends JDialog{

	private final JPanel contentPanel = new JPanel();
	private JLabel lblNivelesDeConfiabilidad;
	private JLabel lblNewLabel;
	private JTable table1;
	private JScrollPane scrollPane;

	public VentanaClustersAsociadosCase1y2() {
		
		this.setTitle("Clusters Asociados");
		this.setBounds(200, 150, 640, 480);
		this.getContentPane().setLayout(new BorderLayout());
		this.contentPanel.setLayout(null);
        this.contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.getContentPane().add(contentPanel, BorderLayout.CENTER);

        Vector columnTitles1 = new Vector();            
         		
    	for (int i = 0; i < ClusteringGenetico.puntosAsociados.get(0).length - 1; i++) {		
    		columnTitles1.add("Dimensión " + (i + 1));			
    	}
    	columnTitles1.add("Cluster");
    		

        Vector row1 = new Vector();
        
        for (int i = 0; i < ClusteringGenetico.puntosAsociados.size(); i++) {
        
        	Vector first = new Vector();
            
        	for (int j = 0; j < ClusteringGenetico.puntosAsociados.get(i).length; j++) {
        		first.add(ClusteringGenetico.puntosAsociados.get(i)[j]);
    		}
                row1.add(first);                
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

        table1 = new JTable(model);
        table1.setAutoCreateRowSorter(true);
		
        scrollPane = new JScrollPane(table1);
        scrollPane.setBounds(10,11,343,419);
        contentPanel.add(scrollPane);
        
		scrollPane.setViewportView(table1);
        
        lblNivelesDeConfiabilidad = new JLabel("Datos");
        lblNivelesDeConfiabilidad.setBounds(363, 11, 226, 15);
        lblNivelesDeConfiabilidad.setFont(new Font("Tahoma", Font.BOLD, 12));
        contentPanel.add(lblNivelesDeConfiabilidad);
        
        lblNewLabel = new JLabel("");
        lblNewLabel.setBounds(373, 45, 210, 38);
        contentPanel.add(lblNewLabel);
        double fitnessMejorIndividuo = 0;
        if (VentanaPrincipal.getMenu() == 1) {
        	fitnessMejorIndividuo= ClusteringGenetico.mejorIndividuoCase1.fitness;
		} else {
			if (VentanaPrincipal.getMenu() == 2) {
				fitnessMejorIndividuo= ClusteringGenetico.mejorIndividuoCase2.fitness;
			}
		}
		lblNewLabel.setText("<html>"
							+ "<p>Fitness :" +trunc(fitnessMejorIndividuo) + "</p>"							
							+"</html>");
        
        JPanel panel = new JPanel();
        panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        panel.setBounds(363, 37, 251, 66);
        contentPanel.add(panel);
        
        this.show();
	}
	
	static double trunc (double num) {
		double truncado;
		truncado=Math.floor(num*1000)/1000; 
		return truncado;
	}	 
}