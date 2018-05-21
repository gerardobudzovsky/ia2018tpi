package tpi;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.TreeSet;


public class ClusteringGenetico {
	
	static ArrayList<double[]> itemset = new ArrayList<double[]>();
	static int dim = 0;
	static int numTransactions = 0;
	static double MAX = 1.7E300;
	
	
	public static double calculateDistance(double[] array1, double[] array2)
	{
        double Sum = 0.0;
        for(int i=0;i<array1.length;i++) {
           Sum = Sum + Math.pow((array1[i]-array2[i]),2.0);
        }
        return Math.sqrt(Sum);
	}
	
	//data random de prueba
	static ArrayList<double[]> RandomDataSet(int dim, int tamEntrada, double High, double Low) {
		
		ArrayList<double[]> dataset = new ArrayList<double[]>();
		int j;
		int i;
		
		for (j=0; j<tamEntrada; j++) {
			double[] arreglo = new double [dim];//debo crear un objeto para cada posición de la lista
			for (i=0; i<dim; i++) {
				
				arreglo [i] =  (Math.random() * ((High - Low) + 1)) + Low;
						
			}
				System.out.println(Arrays.toString(arreglo));
				dataset.add(arreglo);
		}
		return dataset;
	}
	
	//setear centroides del individuo
	static void setearcentroides (int dimension, Individuo ind, ArrayList<double[]> dataset, int clusters) {
		int i;
		//TreeSet<double[]> tree = new TreeSet <double[]>();
		ArrayList<double[]> listacentroides = new ArrayList<double[]>();
			for (i=0; i<clusters; i++) {
				double[] centroide = new double[dimension];
				Cluster cluster = new Cluster (dimension, i);//cluster de (dimension, id)
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
	
	//Datos de entrada
		static void Datos () throws Exception {
			
			
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
		
		static Individuo pasoFinal (Individuo mejor, int clusters) {
			double distancia;
			double min;
			for (int j = 0; j < numTransactions; j++) {
		  		  min = MAX;
		  		  for (int i = 0; i < clusters; i++) {
				    	  
				    	  distancia = calculateDistance(mejor.centroides.get(i), itemset.get(j));
			    		  if (distancia < min) {
			    			  mejor.genes[j] = i;
			    			  min = distancia;
			    		  }
		  		  }
		  	 
		    }
			return mejor;
		}
		
		static double[] calcMedia() {
			
			double[] media = new double[dim];
			for (int i = 0; i < dim; i++) {
				double acum = 0;
				for (int j = 0; j < numTransactions; j++) {
					acum = acum + itemset.get(j)[i];
				}
				media[i] = acum;
			}
			/*for (int i = 0; i < dim; i++) {
				media[i] = acum/(dim*numTransactions);
			}*/
			return media;
		}
		
		static double SSB (Individuo mejor, int clusters, double[] media) {
			double distance;
			double interClust = 0;
			for (int i = 0; i < clusters; i++) {
				int cont = 0;
				for (int j = 0; j < numTransactions; j++) {
					if (mejor.genes[j] == i) {
						cont++;
					}
					//calcula distancia del centroide y la media del dataset al cuadrado
					distance = Math.pow(calculateDistance(mejor.centroides.get(i), media),2.0);
					interClust = interClust + (cont*distance);
				}
			}
			return interClust;
		}
	
	public static void main (String[] args) throws Exception{
		
		Datos();
		int clusters = 5;//cantidad de clusters
		int cantidadIndividuos = 100;
		int seleccionar = 10;//porcentaje de selección
		int cruzar = 80;//Porcentaje de cruza
		int mutar = 10;//Porcentaje de mutación
		int generaciones = 100;
		
		//Se genera la población inicial con centroides al azar del dataset
		Poblacion population = new Poblacion (itemset, cantidadIndividuos, 
			  	seleccionar, cruzar, mutar, clusters, dim);
		
		//Se calcula el fitness de toda la pobalción
		population.calcFitness(0);
		
		//Se crea el individuo donde se elojará el mejor de la población
		Individuo mejor = new Individuo(numTransactions, clusters, dim);
		
		//Auxiliar para dejar inalterados los seleccionados
		int selec = (int)(cantidadIndividuos*seleccionar/100);
		if (seleccionar == 100) {
			selec = cantidadIndividuos-1;
		}
		
		/* for (i = 0; i < population.poblacion.length; i++) {
        	
        	System.out.println((population.poblacion[i].fitness));	
        }*/
		
		
		//Inicia el AG
		for (int i = 0; i<generaciones; i++) {
						
			Individuo mejorFitness = population.getBest();
			
			//Guarda el individuo con mejor fitness
			if (( mejorFitness.fitness > mejor.fitness)){
				
				System.out.println(i);
				mejor = mejorFitness;
				System.out.println(mejor.fitness);
				
			}
			
			//System.out.println(mejorFitness.fitness);
			//System.out.println(i);			
			
			//Crea la ruleta para la selección
			population.naturalSelection();
			
			//Genera los individuos para la seguiente generación
			//con un porcentaje de selección, cruza y mutación
			population = population.generate();
			
			//Carga los centroides en los individuos nuevos mutados y cruzados
			for (int j = selec; j < population.poblacion.length; j++) {
				setearcentroides (dim, population.poblacion[j], itemset, clusters);
			}
			
			population.calcFitness(selec);				
		}
		
		System.out.println(" ");
		System.out.println("Fitness:");
		System.out.println(mejor.fitness);
		System.out.println(" ");
		
		System.out.println("Centroides:");
		for (int i = 0; i < clusters; i++) {
			double[] cent = new double [dim];
			cent = mejor.centroides.get(i);
			System.out.println(Arrays.toString(cent));
			
		}
		System.out.println(" ");
		
		//Reacomoda puntos lejanos al mejor centroide
		mejor = pasoFinal (mejor, clusters);
					
		//Calcula la media del dataset
		double [] media;
		media = calcMedia();
		
		//Calcula la distancia intercluster: Sum of Squared Between
		double interClust;
		interClust = SSB (mejor, clusters, media);
		
		//Calcula la distancia intracluster: Sum of Squared Within
		double intraClust;
		intraClust = mejor.SSW (mejor, clusters, itemset);
			
		//System.out.println("Distancia intercluster");
		//System.out.println(interClust);
		
		//Calcula el índice Calinski y Harabasz
		double calinski;
		double numerador = interClust/(clusters-1);
		double denominador1 = intraClust/(numTransactions-clusters);
		calinski = numerador/denominador1;
		System.out.println(" ");
		System.out.println("Calinski y Harabasz");
		System.out.println(calinski);
		
		System.out.println(" ");
		System.out.println("Xu");
		double denominador = dim*Math.pow(numTransactions, 2.0);
		double division = intraClust/denominador;
		double Xu = dim * Math.log(Math.pow(division, 0.5)) + Math.log(clusters);
		System.out.println(Xu);
		
		System.out.println(" ");
		System.out.println("Mejor Individuo:");
		System.out.println(Arrays.toString(mejor.genes));
		
		/*
		//Imprime los puntos con sus clusters asociados
		ArrayList<double[]> salida = new ArrayList<double[]>();
		System.out.println(" ");
		System.out.println("Puntos asociados:");
		for (int i = 0; i < numTransactions; i++) {
			
			double[] aux = new double [dim+1];
			for (int j = 0; j<dim; j++) {
				aux [j] = itemset.get(i)[j];
			}
			aux[dim] = mejor.genes[i];
			salida.add(aux);
			
		}
		double[] arreglo;
		for (int i=0; i<numTransactions; i++) {
			
			arreglo = salida.get(i);
			System.out.println(Arrays.toString(arreglo));
		}*/
		 
		
	}
		
}
		
		
		
			
