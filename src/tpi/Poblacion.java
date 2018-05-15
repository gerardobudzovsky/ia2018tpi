package tpi;
import tpi.Individuo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Poblacion {
	
	static int dim = 0;
	static int numTransactions = 0;
	static ArrayList<double[]> itemset = new ArrayList<double[]>();
	
	
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
	    		    	
	    	double[] arreglo;
			for (i=0; i<itemset.size(); i++) {
				
				arreglo = itemset.get(i);
				System.out.println(Arrays.toString(arreglo));
			}								
		}
	
		public static void main(String[] args) throws Exception {
		
			Datos();
			int clusters = 3;
			int dimension = dim;
			int tamEntrada = numTransactions;
			double Low = 0.0; //para random
		    double High = 100.0; //para random
			int i;
			int j;
			double[] linea;
			
			Poblacion population = new Poblacion (itemset, 5, 50, 
				  	10, 85, 5, clusters, dimension);
			Individuo mejor = new Individuo(numTransactions);
			
			for (j = 0; j < population.poblacion.length; j++) {
				setearcentroides (dimension, population.poblacion[j], itemset, clusters);
			}
			population.calcFitness();
			
			if ((population.getBest().fitness > mejor.fitness)){
				mejor = population.getBest();
				//System.out.println(mejor.fitness);
			}
			
			for (i = 0; i < population.poblacion.length; i++) {
	    	
				System.out.println((population.poblacion[i].fitness));
				
			}
			System.out.println(" ");
			System.out.println(population.getBest().fitness);
			
			population.naturalSelection();
			
			population = population.generate();
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
	
	  ArrayList<double[]> puntos;
	  double tasaMutacion;           // Mutation rate
	  Individuo[] poblacion;             // Array to hold the current population
	  ArrayList<Individuo> matingPool;    // ArrayList which we will use for our "mating pool"
	  //String target;                // Target phrase
	  boolean finished;             // Are we finished evolving?
	  int perfectScore;
	  double seleccion; //Porcentaje de la poblacion que se elige con selección
      double cruzar; // Porcentaje de la poblacion que se elige con cruce
      double mutar; // Porcentaje de la poblacion que se elige con mutación
      int clusters;
      int dimension;
	  
	  //constructor con tasa de mutacion y tamaño de la poblacion
	  Poblacion(ArrayList<double[]> dataset, double mutacion, int tamaño, 
			  	double selecc, double cruz, double mut, int k, int d) {
	    
		seleccion = selecc; //Porcentaje de la poblacion que se elige con selección
	    cruzar = cruz; // Porcentaje de la poblacion que se elige con cruce
	    mutar = mut;
		puntos = dataset;
		tasaMutacion = mutacion;
	    poblacion = new Individuo[tamaño];
	    clusters = k;
	    dimension = d;
	    
	    for (int i = 0; i < poblacion.length; i++) {
	      poblacion[i] = new Individuo(dataset.size(), clusters, dimension, puntos);
	    }
	    //calcFitness();
	    matingPool = new ArrayList<Individuo>();
	    /*finished = false;
	    generations = 0;
	    
	    perfectScore = 1;*/
	  }
	  
	  //constructor con tamaño
	  Poblacion (int tamaño){
		  poblacion = new Individuo[tamaño];
		  
	  }
	  // Fill our fitness array with a value for every member of the population
	  void calcFitness() {
	    for (int i = 0; i < poblacion.length; i++) {
	      poblacion[i].fitness(puntos);
	    }
	  }

	  // Generate a mating pool
	  void naturalSelection() {
	    // Clear the ArrayList
	    //matingPool.clear();

	    Double maxFitness = 0.0;
	    for (int i = 0; i < poblacion.length; i++) {
	      if (poblacion[i].fitness > maxFitness) {
	        maxFitness = poblacion[i].fitness;
	      }
	    }

	    // Based on fitness, each member will get added to the mating pool a certain number of times
	    // a higher fitness = more entries to mating pool = more likely to be picked as a parent
	    // a lower fitness = fewer entries to mating pool = less likely to be picked as a parent
	    for (int i = 0; i < poblacion.length; i++) {
	      
	      double fitness = poblacion[i].fitness/maxFitness;
	      int n = (int) (fitness * 100);  // Arbitrary multiplier, we can also use monte carlo method
	      for (int j = 0; j < n; j++) {              // and pick two random numbers
	        matingPool.add(poblacion[i]);
	      }
	    }
	  }

	  // Create a new generation
	  Poblacion generate() {
	    // Refill the population with children from the mating pool
	   	      
	      //porcentajes de los individuos de seleccion, mutacion y cruza
	      
	      
	      double calselec = poblacion.length*(seleccion/100);
	      double calcru = poblacion.length*(cruzar/100);
	      double calmut = poblacion.length*(mutar/100);
	      int cantsel = (int)calselec;
	      int cantcru = (int)calcru;
	      int cantmut = (int)calmut;
	      
	      Poblacion poblacionsig = new Poblacion(puntos, tasaMutacion, poblacion.length, 
				  	seleccion, cruzar, mutar, clusters, dimension);
	      //Ordeno la poblacion actual para despues hacer "elitista"
	      Quicksort sorter = new Quicksort();
          sorter.sort(poblacion);
          poblacion = sorter.array;
	      
	      //Seleccion elitista
	      for (int i = 0; i < cantsel; i++) {
	    	
	    	  //int a = AleatorioInt(0, matingPool.size());
	    	  poblacionsig.poblacion[i] = sorter.array[poblacion.length-i-1];
	    	  //poblacionsig.poblacion[i].centroides = sorter.array[i].centroides;
	    	  
	      }
	      
	      //Cruza
	      for (int i = cantsel; i < (cantcru + cantsel); i++) {
	    	  
	    	  int a = AleatorioInt(0, matingPool.size());
	    	  int b = AleatorioInt(0, matingPool.size());
	    	  Individuo padreA = matingPool.get(a);
			  Individuo padreB = matingPool.get(b);
			  Individuo hijo = padreA.Cruzar(padreB);
			  poblacionsig.poblacion[i] = hijo;
	      }
	      
	      //mutacion
	      for (int i = (cantcru + cantsel); i < poblacion.length; i++) {
	    	  
	    	  int a = AleatorioInt(0, matingPool.size());
	    	  Individuo xmen = matingPool.get(a).mutar(tasaMutacion, 50);
	    	  poblacionsig.poblacion[i] = xmen;
	      }
	      
	    return poblacionsig;
	  }


	  // Compute the current "most fit" member of the population
	  Individuo getBest() {
	    double worldrecord = 0.0;
	    int index = 0;
	    for (int i = 0; i < poblacion.length; i++) {
	      if (poblacion[i].fitness > worldrecord) {
	        index = i;
	        worldrecord = poblacion[i].fitness;
	      }
	    }
	    
	    return poblacion[index];
	  }

	  

	  // Compute average fitness for the population
	  double getAverageFitness() {
	    double total = 0;
	    for (int i = 0; i < poblacion.length; i++) {
	      total += poblacion[i].fitness;
	    }
	    return total / (poblacion.length);
	  }

	  /*
	  String allPhrases() {
	    String everything = "";
	    
	    int displayLimit = min(population.length,50);
	    
	    
	    for (int i = 0; i < displayLimit; i++) {
	      everything += population[i].getPhrase() + "\n";
	    }
	    return everything;
	  }
	  
	  boolean finished() {
		    return finished;
		  }

		  int getGenerations() {
		    return generations;
		  }*/
	
}
