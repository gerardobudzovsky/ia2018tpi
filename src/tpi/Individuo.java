package tpi;
import java.util.ArrayList;
import java.util.Random;

public class Individuo {
	
		public static double calculateDistance(double[] array1, double[] array2)
		{
	        double Sum = 0.0;
	        for(int i=0;i<array1.length;i++) {
	           Sum = Sum + Math.pow((array1[i]-array2[i]),2.0);
	        }
	        return Math.sqrt(Sum);
		}
		
		Double AleatorioReal (Double Low, Double High) {
			
			Double resultado = (Math.random() * ((High - Low) + 1)) + Low;
			return resultado;
		}

	
		int AleatorioInt (int Low, int High) {
			
			Random r = new Random();
			int resultado = r.nextInt(High-Low) + Low;
			return resultado;
		}
	
		public static double ErrorCuadratico(double val1, double val2) {
			
			double error;
			
			error = Math.pow(val1 - val2, 2.0);
			
			return error;
			
		}
	
		/*public static void main(String[] args) {
			Individuo ind = new Individuo(20, 5);
			
			System.out.println(Arrays.toString(ind.getPhrase()));
		}*/
	
		// The genetic sequence
			ArrayList<double[]> centroides = new ArrayList <double[]>();
			int numClusters;
			int[] genes;
			double fitness;
			int dimension;
			ArrayList<double[]> dataset;
		  
		  // Constructor: recibe "n" (cantidad de puntos que nos pasan) y
		  // "k" (cantidad de clusters)
			// "d" dimension	
		Individuo(int n, int k, int d, ArrayList<double[]> itemset) {
			  
			  dataset = itemset;
			  dimension = d;
			  numClusters = k;
			  
			  Random r = new Random(); //Generador random de 1 a punto ni
			  int Low = 0;
		      int High = n;
			  genes = new int[n];
			  double[] centroide = new double[d];
			  double min;
			  double distancia;
			  int i;
			  
			  for (i = 0; i < numClusters; i++) {
				  
				  centroide = dataset.get(r.nextInt(High-Low) + Low);
				  centroides.add(centroide);
				  
			  }
		      
		    	  for (int j = 0; j < n; j++) {
		    		  min = 1000000.00;
		    		  for (i = 0; i < numClusters; i++) {
				    	  
				    	  distancia = calculateDistance(centroides.get(i), dataset.get(j));
			    		  if (distancia < min) {
			    			  genes[j] = i;
			    			  min = distancia;
			    		  }
		    		  }
		    		  //genes[i] = r.nextInt(High-Low) + Low;
		      
		    	  }
		  }
		
		//Constructor individuo vacío de tamaño n
		Individuo(int n){
			
			genes = new int[n];		
					
		}
		  
		  int[] getPhrase() {
			  return genes;
		  }
		  
		  int getClusters() {
			  return numClusters;
		  }
		  double getFitness() {
			  return fitness;
		  }
		 
		  void setCentroides (ArrayList<double[]> lista){
			centroides = lista;
		  }
		  
		  ArrayList<double[]> getCentroides(){
			  return centroides;
		  }
		 
		  //Fitness function
		  double fitness (ArrayList<double[]> dataset) {
			 
			 int j;
			 int k;
		     double[] punto;
		     double[] centroide;
		     double sumatoria = 0;
		     double error;
		     
		     for (int i = 0; i < numClusters; i++) {
		    	for (j = 0; j < genes.length; j++) {
		    		for (k = 0; k < dimension; k++) {
		    			if (genes[j] == i) {
		    				punto = dataset.get(j);
		    				centroide = centroides.get(i);
		    				//error = Math.abs(punto[k]+centroide[k]);
		    				error = ErrorCuadratico (punto[k], centroide[k]);
		    				sumatoria = sumatoria + error;
		    			}
		    			
		    		}
		        }
		     }
		  
		     fitness = (1/sumatoria)*100000;
		     return (1/sumatoria)*100000;
		  }    
		  
		  //Cruzar
		  Individuo Cruzar(Individuo pareja) {
		    
			 // Nuevo hijo
		    Individuo hijito = new Individuo(genes.length, numClusters, dimension, dataset);
		    
		    int midpoint = AleatorioInt(0, numClusters); //int(random(genes.length)); // Pick a midpoint
		    
		    // Half from one, half from the other
		    for (int i = 0; i < genes.length; i++) {
		      if (i > midpoint) hijito.genes[i] = genes[i];
		      else              hijito.genes[i] = pareja.genes[i];
		    }
		    return hijito;
		  }
		  
		  
		  // tasaGenes es el porcentaje de genes a mutar
		  Individuo mutar(double tasaMutacion, double tasaGenes) {
		    
			Double porcentual = tasaGenes/100;
			Individuo xmen = new Individuo (genes.length, numClusters, dimension, dataset);
			xmen.genes = genes;
			double calcGen = genes.length * porcentual;
			int cantGen = (int)calcGen;
			
			for (int i = 0; i < cantGen; i++) {
				
				int posicion = AleatorioInt(0, genes.length);
				xmen.genes[posicion] = AleatorioInt(0, numClusters);
		    }
			return xmen;
		  }

	
}


