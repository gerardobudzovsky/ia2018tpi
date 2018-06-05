package tpi;
import java.util.ArrayList;
import java.util.Random;

public class IndividuoK {
	
		static double MAX1 = 1.7E300;//Máximo double
	
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
	
		
		
			double constanteFitness = 100000;//Multiplicador para evitar que el fitness sea muy chico
			ArrayList<double[]> centroides = new ArrayList <double[]>();
			int numClusters;//número de clusters
			int[] genes;//Arreglo de puntos con sus correspondientes clusters
			double fitness;//valor fitness
			int dimension;//canatidad de dimensiones
			ArrayList<double[]> dataset;//dataset
			double MAX;
		  
		  // Constructor: recibe "n" (cantidad de puntos que nos pasan) y
		  // "k" (cantidad de clusters)
			// "d" dimension	
		IndividuoK(int n, int k, int d, ArrayList<double[]> itemset) {
			  
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
			  
			  //Asigna puntos random a los centroides iniciales
			  for (i = 0; i < numClusters; i++) {
				  
				  centroide = dataset.get(r.nextInt(High-Low) + Low);
				  centroides.add(centroide);
				  
			  }
		      
		    	  for (int j = 0; j < n; j++) {
		    		  min = MAX1;
		    		  for (i = 0; i < numClusters; i++) {
				    	  
				    	  distancia = calculateDistance(centroides.get(i), dataset.get(j));
			    		  if (distancia < min) {
			    			  genes[j] = i;
			    			  min = distancia;
			    		  }
		    		  }
		    		  //genes[i] = r.nextInt(High-Low) + Low;
		      
		    	  }
			  
			  /*dataset = itemset;
			  dimension = d;
			  numClusters = k;
			  
			  double maximo = 0;
			  for (int i = 0; i < n; i++) {
				  for (int j = 0; j < dimension; j++) {
					  if (itemset.get(i)[j]>maximo) {
						  maximo = itemset.get(i)[j];
					  }
				  }
			  }
			  
			  Random r = new Random(); //Generador random de 1 a punto ni
			  double Low = 0;
		      double High = maximo;
			  genes = new int[n];
			  
			  double min;
			  double distancia;
			  int i;
			  
			  for (i = 0; i < numClusters; i++) {
				  double[] centroide = new double[d];
				  for (int j = 0; j < dimension; j++) {
					  centroide [j] = AleatorioReal (0.0, maximo);//dataset.get(r.nextInt(High-Low) + Low);
				  }
				  //centroide = dataset.get(r.nextInt(High-Low) + Low);
				  centroides.add(centroide);
			  }
		      
		    	  for (int j = 0; j < n; j++) {
		    		  min = MAX1;
		    		  for (i = 0; i < numClusters; i++) {
				    	  
				    	  distancia = calculateDistance(centroides.get(i), dataset.get(j));
			    		  if (distancia < min) {
			    			  genes[j] = i;
			    			  min = distancia;
			    		  }
		    		  }
		    		  //genes[i] = r.nextInt(High-Low) + Low;
		      
		    	  }*/
		  }
		
		//Constructor IndividuoK sin genes random
		IndividuoK(int n, int k, int d){
			
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
		  
		  ArrayList<double[]> getCentroides(){
			  return centroides;
		  }
		//Sum of Squared Within
		  double SSW (IndividuoK mejor, int clusters, ArrayList<double[]> punto) {
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
		  
		  //Sum of Square Between
		  static double SSB (IndividuoK mejor, int clusters, double[] media) {
				double distance;
				double interClust = 0;
				for (int i = 0; i < clusters; i++) {
					int cont = 0;
					for (int j = 0; j < mejor.genes.length; j++) {
						if (mejor.genes[j] == i) {
							cont++;
						}
						//calcula distancia del centroide y la media del dataset al cuadrado
						if (cont != 0) {
							distance = Math.pow(calculateDistance(mejor.centroides.get(i), media),2.0);
							interClust = interClust + (cont*distance);
						}
					}
				}
				return interClust;
			}
		  
		  //Calcula el promedio del dataset
		  double[] calcMedia() {
				
				double[] media = new double[dimension];
				for (int i = 0; i < dimension; i++) {
					double acum = 0;
					for (int j = 0; j < genes.length; j++) {
						acum = acum + dataset.get(j)[i];
					}
					media[i] = acum;
				}
				/*for (int i = 0; i < dim; i++) {
					media[i] = acum/(dim*numTransactions);
				}*/
				return media;
			}
		 
		  //Fitness function
		  double fitness (ArrayList<double[]> dataset) {
			  double [] media = calcMedia();
			  double inter = SSB (this, this.numClusters, media);
			  double intra = SSW (this, this.numClusters, dataset);
			  double resultado = inter/intra;
			  fitness = resultado;
			  return resultado;
			 
			 /*int j;
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
		  
		     fitness = (1/sumatoria)*constanteFitness;
		     return (1/sumatoria)*constanteFitness;*/
		  }    
		  	
}


