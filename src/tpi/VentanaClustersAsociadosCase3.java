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

public class VentanaClustersAsociadosCase3 extends JDialog implements ChangeListener{

	private final JPanel contentPanel = new JPanel();
	private JRadioButton rdbtnMuyConfiable;
	private JRadioButton rdbtnConfiable;
	private ButtonGroup bg;
	private JLabel lblNivelesDeConfiabilidad;
	private JLabel lblNewLabel;
	private JTable table1;
	private JTable table2;
	private JScrollPane scrollPane;

	public VentanaClustersAsociadosCase3() {
		
		this.setTitle("Clusters Asociados");
		this.setBounds(200, 150, 640, 480);
		this.getContentPane().setLayout(new BorderLayout());
        this.contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.getContentPane().add(contentPanel, BorderLayout.CENTER);

        Vector columnTitles1 = new Vector();            
         		
    	for (int i = 0; i < ClusteringGenetico.puntosAsociados.get(0).length - 1; i++) {		
    		columnTitles1.add("Dimensión " + (i + 1));			
    	}
    	columnTitles1.add("Cluster");
    		
        Vector columnTitles2 = new Vector();            
    	
        for (int i = 0; i < Kmeans.puntosAsociados.get(0).length - 1; i++) {		
        	columnTitles2.add("Dimensión " + (i + 1));			
    	}
    	columnTitles2.add("Cluster");

        Vector row1 = new Vector();
        
        for (int i = 0; i < ClusteringGenetico.puntosAsociados.size(); i++) {
        
        	Vector first = new Vector();
            
        	for (int j = 0; j < ClusteringGenetico.puntosAsociados.get(i).length; j++) {
        		first.add(ClusteringGenetico.puntosAsociados.get(i)[j]);
    		}
                row1.add(first);                
    	}
            
            
        Vector row2 = new Vector();

        for (int i = 0; i < Kmeans.puntosAsociados.size(); i++) {
        	Vector first = new Vector();
                
        	for (int j = 0; j < Kmeans.puntosAsociados.get(i).length; j++) {
        		first.add(Kmeans.puntosAsociados.get(i)[j]);
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
		
		rdbtnMuyConfiable = new JRadioButton("Genético");
		rdbtnMuyConfiable.setBounds(448, 33, 109, 23);
		rdbtnMuyConfiable.addChangeListener(this);
		contentPanel.setLayout(null);
		contentPanel.add(rdbtnMuyConfiable);
        bg.add(rdbtnMuyConfiable);
		
		rdbtnConfiable = new JRadioButton("K-Means");
		rdbtnConfiable.setBounds(448, 59, 109, 23);
		rdbtnConfiable.addChangeListener(this);
		contentPanel.add(rdbtnConfiable);
        bg.add(rdbtnConfiable);
        
        lblNivelesDeConfiabilidad = new JLabel("Clusters Asociados");
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
					            +"<b>Fitness</b>"
					            +"<br>"
					            +trunc(ClusteringGenetico.mejorIndividuoCase3.fitness)
		    		            +"</html>");			
		}
		
		if (rdbtnConfiable.isSelected()) {
			scrollPane.setViewportView(table2);
			lblNewLabel.setText("<html>"
			                    +"<b>Fitness</b>"
			                    +"<br>"
			                    +trunc(Kmeans.mejorIndividuoCase3.fitness)
		                        +"</html>");
		}	
	}
	
	 static double trunc (double num) {
		 double truncado;
		 truncado=Math.floor(num*1000)/1000; 
		 return truncado;
	 }
	 
}

