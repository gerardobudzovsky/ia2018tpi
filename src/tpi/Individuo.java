package tpi;
import java.util.ArrayList;
import java.util.Random;

public class Individuo {
		
		static double MAX = 1.7E300;//Máximo double
		
		//Calcula la distancia entre dos vectores de "n" dimensiones
		public static double calculateDistance(double[] array1, double[] array2)
		{
	        double Sum = 0.0;
	        for(int i=0;i<array1.length;i++) {
	           Sum = Sum + Math.pow((array1[i]-array2[i]),2.0);
	        }
	        return Math.sqrt(Sum);
		}
		
		//Genera un número aleatorio real entre Low y High
		Double AleatorioReal (Double Low, Double High) {
			
			Double resultado = (Math.random() * ((High - Low) + 1)) + Low;
			return resultado;
		}

		//Genera un número entero real entre low y high
		int AleatorioInt (int Low, int High) {
			
			Random r = new Random();
			int resultado = r.nextInt(High-Low) + Low;
			return resultado;
		}
	
		//Calcula el error cuadrático entre 2 valores
		public static double ErrorCuadratico(double val1, double val2) {
			
			double error;
			error = Math.pow(val1 - val2, 2.0);
			return error;
			
		}
	
		double constanteFitness = 100000;//Multiplicador para evitar que el fitness sea muy chico
		ArrayList<double[]> centroides = new ArrayList <double[]>();
		int numClusters;
		int[] genes;//Vector con "n" posiciones (puntos) con su correspondiente cluster
		double fitness;
		int dimension;
		ArrayList<double[]> dataset;
		double calinski;
		  
		// Constructor que llena genes con random
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
		    		  min = MAX;
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
		Individuo(int n, int k, int d){
			
			genes = new int[n];
			dimension = d;
			numClusters = k;
					
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
		  
		  void setCalinski (double valor) {
			  calinski = valor;
		  }
		  
		  ArrayList<double[]> getCentroides(){
			  return centroides;
		  }
		  
		  //Sum of Squared Within
		  double SSW (Individuo mejor, int clusters, ArrayList<double[]> punto) {
				double distance;
				double intraClust = 0;
				for (int i = 0; i < clusters; i++) {
					double acum = 0;
					for (int j = 0; j < genes.length; j++) {
						if (mejor.genes[j] == i) {
							distance = Math.pow(calculateDistance(mejor.centroides.get(i), punto.get(j)),2.0);
							acum = acum + distance;
						}
										
						intraClust = intraClust + acum;
					}
				}
				return intraClust;
			}
		  
		  //Función fitness
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
		     
		     //Se invierte el valor para que el mayor fitness sea el mejor
		     fitness = (1/sumatoria)*constanteFitness;
		     return (1/sumatoria)*constanteFitness;
		  }
		  
		  //Cruza simple
		  Individuo Cruzar(Individuo pareja) {
		    
			 // Nuevo hijo
		    Individuo hijito = new Individuo(genes.length, numClusters, dimension, dataset);
		    
		    int midpoint = AleatorioInt(0, numClusters); //Punto medio al azar
		    
		    //La mitad de uno y la mitad de otro padre
		    for (int i = 0; i < genes.length; i++) {
		      if (i > midpoint) hijito.genes[i] = genes[i];
		      else              hijito.genes[i] = pareja.genes[i];
		    }
		    return hijito;
		  }
		  
		  
		  //TasaGenes es el porcentaje de genes a mutar
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


