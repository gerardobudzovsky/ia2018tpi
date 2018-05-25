package tpi;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * @author imssbora
 */
public class ScatterPlotExample extends JFrame {
  private static final long serialVersionUID = 6294689542092367723L;
  
  String titulo;
  int clusters;
  Cluster[] componentes = new Cluster[clusters];
  int dim1;
  int dim2;
  String superior;
  
  public ScatterPlotExample(String title, Cluster[] puntosxcluster, int numcluster, int dimension1,
		  					int dimension2) {
	
	super(title);
    clusters = numcluster;
    dim1 = dimension1;
    dim2 = dimension2;
    componentes = puntosxcluster;
    
    
    // Create dataset
    XYDataset dataset = createDataset();

    // Create chart
    superior = "Gráfico para: " + String.valueOf(clusters) + " clusters";
    JFreeChart chart = ChartFactory.createScatterPlot(
        superior,
        "X-Axis", "Y-Axis", dataset);

    
    //Changes background color
    XYPlot plot = (XYPlot)chart.getPlot();
    plot.setBackgroundPaint(new Color(255,228,196));
    
   
    // Create Panel
    ChartPanel panel = new ChartPanel(chart);
    setContentPane(panel);
  }

  private XYDataset createDataset() {
    //XYSeriesCollection dataset = new XYSeriesCollection();
    
        	
    	XYSeriesCollection coleccion = new XYSeriesCollection();      
    	for (int i = 0; i < clusters; i++) {             	
    		XYSeries series = new XYSeries(i);         
    		for (int j = 0; j < componentes[i].puntosAsociados.get(dim1).length; j++) {         	
    			series.add(componentes[i].puntosAsociados.get(dim1)[j], 
    						componentes[i].puntosAsociados.get(dim2)[j]);	
    		}   
    		coleccion.addSeries(series);
    	}     
    	return coleccion;
    
    //Boys (Age,weight) series
    /*XYSeries series1 = new XYSeries("Boys");
    series1.add(1, 72.9);
    series1.add(2, 81.6);
    series1.add(3, 88.9);
    series1.add(4, 96);
    series1.add(5, 102.1);
    series1.add(6, 108.5);
    series1.add(7, 113.9);
    series1.add(8, 119.3);
    series1.add(9, 123.8);
    series1.add(10, 124.4);

    dataset.addSeries(series1);
    
   //Girls (Age,weight) series
    XYSeries series2 = new XYSeries("Girls");
    series2.add(1, 72.5);
    series2.add(2, 80.1);
    series2.add(3, 87.2);
    series2.add(4, 94.5);
    series2.add(5, 101.4);
    series2.add(6, 107.4);
    series2.add(7, 112.8);
    series2.add(8, 118.2);
    series2.add(9, 122.9);
    series2.add(10, 123.4);

    dataset.addSeries(series2);

    return dataset;*/
  }

  /*public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      ScatterPlotExample example = new ScatterPlotExample("Scatter Chart Example | BORAJI.COM");
      example.setSize(800, 400);
      example.setLocationRelativeTo(null);
      example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
      example.setVisible(true);
    });
  }*/
}