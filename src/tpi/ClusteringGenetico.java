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
	
	
	public static double calculateDistance(double[] array1, double[] array2)
	{
        double Sum = 0.0;
        for(int i=0;i<array1.length;i++) {
           Sum = Sum + Math.pow((array1[i]-array2[i]),2.0);
        }
        return Math.sqrt(Sum);
	}
	
	//data random de prueba
	static ArrayList<double[]> RandomDataSet(int dimension, int tamEntrada, double High, double Low) {
		
		ArrayList<double[]> dataset = new ArrayList<double[]>();
		int j;
		int i;
		
		for (j=0; j<tamEntrada; j++) {
			double[] arreglo = new double [dimension];//debo crear un objeto para cada posici�n de la lista
			for (i=0; i<dimension; i++) {
				
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
		//TreeSet<double[]> tree = new TreeSet <double[]>();//Mejor estrucutura segun Blas(?
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
			
			transaFile = "D:/dataset01.txt";
			
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
	    		double[] elto = new double[2];
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
		int clusters = 3;
		int dimension = dim;
		int tamEntrada = numTransactions;
		double Low = 0.0; //para random
	    double High = 100.0; //para random
		int i;
		int j;
		double[] linea;
		
		/*
		//creo individuos de "tamEntrada" posiciones, clusters y dimension
		Individuo ind = new Individuo(tamEntrada, clusters, dimension);
		
		//Dataset de prueba
		ArrayList<double[]> dataset = new ArrayList<double[]>();
		
		System.out.println(" ");
		System.out.println(Arrays.toString(ind.getPhrase()));//Imprimo el individuo
		System.out.println(" ");
		
		//dataset = RandomDataSet(dimension, tamEntrada, High, Low);//Genero un dataset random de prueba
				
		System.out.println(" ");
		setearcentroides (dimension, ind, itemset, clusters);
		
		System.out.println(" ");
		System.out.println(ind.fitness(itemset));*/
		
		Poblacion population = new Poblacion (itemset, 5, 100, 
			  	10, 85, 5, clusters, dimension);
		Individuo mejor = new Individuo(numTransactions);
		
		
		/* for (i = 0; i < population.poblacion.length; i++) {
        	
        	System.out.println((population.poblacion[i].fitness));	
        }*/
		
		for (i = 0; i<100; i++) {
		
			population.calcFitness();
			
			if ((population.getBest().fitness > mejor.fitness)){
				
				mejor = population.getBest();
				
			}
			
			System.out.println(population.getBest().fitness);
						
			population.naturalSelection();
			
			population = population.generate();
			
			for (j = 10; j < population.poblacion.length; j++) {
				setearcentroides (dimension, population.poblacion[j], itemset, clusters);
			}
			
							
		}
		
		System.out.println("Fitness:");
		System.out.println(mejor.fitness);
		System.out.println(" ");
		
		System.out.println("Centroides:");
		for (i = 0; i < clusters; i++) {
			double[] cent = new double [dimension];
			cent = mejor.centroides.get(i);
			System.out.println(Arrays.toString(cent));
			
		}
		System.out.println(" ");
		
		
		double distancia;
		double min;
		for (j = 0; j < tamEntrada; j++) {
	  		  min = 1000000.00;
	  		  for (i = 0; i < clusters; i++) {
			    	  
			    	  distancia = calculateDistance(mejor.centroides.get(i), itemset.get(j));
		    		  if (distancia < min) {
		    			  mejor.genes[j] = i;
		    			  min = distancia;
		    		  }
	  		  }
	  		  
	    
		}
		
		System.out.println("Mejor Individuo:");
		System.out.println(Arrays.toString(mejor.genes));
		System.out.println(" ");
		ArrayList<double[]> salida = new ArrayList<double[]>();
		
		System.out.println("Puntos asociados:");
		for (i = 0; i < numTransactions; i++) {
			
			double[] aux = new double [dimension+1];
			for (j = 0; j<dimension; j++) {
				aux [j] = itemset.get(i)[j];
			}
			aux[dimension] = mejor.genes[i];
			salida.add(aux);
			
		}
		double[] arreglo;
		for (i=0; i<numTransactions; i++) {
			
			arreglo = salida.get(i);
			System.out.println(Arrays.toString(arreglo));
		}
		
		
		
	}
		
}
		
		
		
			
