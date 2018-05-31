package tpi;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.TreeSet;

import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;


public class Kmeans {
	
	static double MAX1 = 1.7E300;//Máximo double
	static ArrayList<double[]> itemset = new ArrayList<double[]>();
	static int dim = 0;
	static int numTransactions = 0;
	
public static void PrepararGrafico (IndividuoK ind, int clusters, int dimension1, int dimension2) {
		
		int[] ctidadPuntos = new int[clusters];
		
		for (int i = 0; i < clusters; i++) {
			for (int j = 0; j < numTransactions; j++) {
				if (ind.genes[j] == i) {
					ctidadPuntos[i] = ctidadPuntos[i] + 1;
				}
			}
		}
		System.out.println("Cantidad de puntos por clúster");
		System.out.println(Arrays.toString(ctidadPuntos));
		
		Cluster[] grafico = new Cluster[clusters];
		
		for (int i = 0; i < clusters; i++) {//Cluster n°
			//Lista de componentes de puntos del cluster i
			ArrayList<double[]> componentes = new ArrayList <double[]>();
			for (int k = 0; k < dim; k++) {//nimension n°
			
				double[] comp = new double[ctidadPuntos[i]];
				int index = 0;
				for (int j = 0; j < numTransactions; j++) {
					
					if (ind.genes[j] == i) {
						comp[index] = itemset.get(j)[k];
						index++;
					}
				}
			System.out.println(Arrays.toString(comp));
			componentes.add(comp);//arreglo de componentes de puntos del cluster i
			}
			Cluster clusteraux = new Cluster(dim, i, componentes);
			grafico[i] = clusteraux;//Arreglo de componentes de cada cluster
			
		}
		SwingUtilities.invokeLater(() -> {
		      ScatterPlotExample example = new ScatterPlotExample("Gráfico", grafico, clusters, dimension1, dimension2);
		      example.setSize(800, 600);
		      example.setLocationRelativeTo(null);
		      example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		      example.setVisible(true);
		    });
	}
	
	static Double AleatorioReal (Double Low, Double High) {
		
		Double resultado = (Math.random() * ((High - Low) + 1)) + Low;
		return resultado;
	}
	
	
	public static double calculateDistance(double[] array1, double[] array2)
	{
        double Sum = 0.0;
        for(int i=0;i<array1.length;i++) {
           Sum = Sum + Math.pow((array1[i]-array2[i]),2.0);
        }
        return Math.sqrt(Sum);
	}
	
	//setear centroides del IndividuoK
	static void setearcentroides (int dimension, IndividuoK ind, ArrayList<double[]> dataset, int clusters) {
		int i;
		
		ArrayList<double[]> listacentroides = new ArrayList<double[]>();
			for (i=0; i<clusters; i++) {
				double[] centroide = new double[dimension];
				ClusterK cluster = new ClusterK (dimension, i);//cluster de (dimension, id)
				centroide = cluster.calCentroide(ind, dataset);
				//System.out.println(" ");
				//System.out.println(Arrays.toString(centroide));
				listacentroides.add(centroide);
			}
		ind.setCentroides(listacentroides);
		/*System.out.println(" ");
		for (i=0; i<clusters; i++) {
			linea = ind.getCentroides().get(i);
			System.out.println(Arrays.toString(linea));
		}*/
	}
	
	static void Datos () throws Exception {
		
		itemset.clear();
		numTransactions = 0;
		int bandera = 0;
		String transaFile;
		TreeSet<String> itemsetst = new TreeSet<String>();
		
		int i=0;
		
		transaFile = "D:/laWazada2.txt";
		
		BufferedReader data = new BufferedReader(new FileReader(transaFile));
	    	    		
	    		String linea=data.readLine();
	    		//System.out.println(linea);
	            StringTokenizer to = new StringTokenizer(linea, "\t");
	    		while (to.hasMoreTokens()) {
	                
	    			String aux = to.nextToken();
	    			dim++;//acumula cantidad de dimensiones
	                
	    		} 
	    	bandera = 1;
	    	//System.out.println(dim);
					
		BufferedReader data_in = new BufferedReader(new FileReader(transaFile));
    	while (data_in.ready()) {    		
    		String line=data_in.readLine();
    		//System.out.println(line);
                
    		if (line.matches("\\s*")) continue; // saltar lineas vacias
    		numTransactions++;
    		StringTokenizer t = new StringTokenizer(line,"\t");
    		double[] elto = new double[dim];
    		i = 0;
    		while (t.hasMoreTokens()) {
    			elto[i] = Double.parseDouble(t.nextToken());
    			i++;
                        
    		}    		
    		itemset.add(elto);
    	}  
    		    	
    	/*double[] arreglo;
		for (i=0; i<itemset.size(); i++) {
			
			arreglo = itemset.get(i);
			System.out.println(Arrays.toString(arreglo));
		}*/							
	}
	
	public static void main (String[] args) throws Exception{
			
			Datos();
			int clusters = 5;
			int dimension = dim;
			int tamEntrada = numTransactions;
			double Low = 0.0; //para random
		    double High = 100.0; //para random
			int i;
			int j;
			int h = 0;
			double[] linea;
			int band = 1;
			IndividuoK IndividuoK = new IndividuoK(tamEntrada, clusters, dimension, itemset);
			
			while ((band == 1) && (h < 1000)) {
				
				h++;
				
				IndividuoK.fitness(itemset);
				
				ArrayList<double[]> centroide1 = IndividuoK.centroides;
				
				setearcentroides (dimension, IndividuoK, itemset, clusters);
				ArrayList<double[]> centroide2 = IndividuoK.centroides;
				double distanciaCent = 0;
				for (i = 0; i<clusters; i++) {
					
					distanciaCent = distanciaCent + calculateDistance(centroide1.get(i), centroide2.get(i));
				}
				
				if (distanciaCent < 0.00000001) {band = 0;}
				
				double distancia;
				double min;
				for (j = 0; j < tamEntrada; j++) {
			  		  min = MAX1;
			  		  for (i = 0; i < clusters; i++) {
					    	  
					    	  distancia = calculateDistance(IndividuoK.centroides.get(i), itemset.get(j));
				    		  if (distancia < min) {
				    			  IndividuoK.genes[j] = i;
				    			  min = distancia;
				    		  }
			  		  }
				}	
				
				System.out.println(IndividuoK.fitness);	
		    
			}
			
			System.out.println(Arrays.toString(IndividuoK.genes));
			System.out.println(IndividuoK.fitness);
			System.out.println(h);
			
			System.out.println("Centroides:");
			for (int a = 0; a < clusters; a++) {
				double[] cent = new double [dim];
				cent = IndividuoK.centroides.get(a);
				if (Double.isNaN(cent[0])) {
					System.out.println(a + "- Cluster vacío");
					
					} else {
						System.out.println(a + "- "+ Arrays.toString(cent));	
					}						
				
			}
			System.out.println(" ");
				
			
			PrepararGrafico (IndividuoK, IndividuoK.numClusters, 0, 1);
			
			
	}

}
