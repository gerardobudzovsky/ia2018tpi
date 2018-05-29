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
	  double tasaMutacion;           // Tasa de mutación
	  Individuo[] poblacion;             // Arregla de individuos con la población actual
	  ArrayList<Individuo> matingPool;    // ArrayList donde se armar'a la ruleta
	  boolean finished;             
	  int perfectScore;
	  double seleccion; //Porcentaje de la poblacion que se elige con selección
      double cruzar; // Porcentaje de la poblacion que se elige con cruce
      double mutar; // Porcentaje de la poblacion que se elige con mutación
      int clusters;
      int dimension;
	  
	  //constructor con individuos vacíos (simple y rápido)
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
	      poblacion[i] = new Individuo(dataset.size(), clusters, dimension);
	    }
	    
	    matingPool = new ArrayList<Individuo>();
	    
	  }
	  
	  //constructor con individuos y genes llenos por random
	  Poblacion(ArrayList<double[]> dataset, int tamaño, 
			  	double selecc, double cruz, double mut, int k, int d) {
	    
		seleccion = selecc; //Porcentaje de la poblacion que se elige con selección
	    cruzar = cruz; // Porcentaje de la poblacion que se elige con cruce
	    mutar = mut;
		puntos = dataset;
		//tasaMutacion = mutacion;
	    poblacion = new Individuo[tamaño];
	    clusters = k;
	    dimension = d;
	    
	    for (int i = 0; i < poblacion.length; i++) {
	      poblacion[i] = new Individuo(dataset.size(), clusters, dimension, puntos);
	    }
	    
	    matingPool = new ArrayList<Individuo>();
	    
	  }
	  
	  //constructor con tamaño
	  Poblacion (int tamaño){
		  poblacion = new Individuo[tamaño];
		  
	  }
	  //Asigna firness a cada individuo
	  void calcFitness(int desde) {
	    for (int i = desde; i < poblacion.length; i++) {
	      poblacion[i].fitness(puntos);
	    }
	  }

	  //Genera el arreglo para la ruleta
	  void naturalSelection() {
	    
	    matingPool.clear();

	    Double maxFitness = 0.0;
	    for (int i = 0; i < poblacion.length; i++) {
	      if (poblacion[i].fitness > maxFitness) {
	        maxFitness = poblacion[i].fitness;
	      }
	    }

	    //Basado en su fitness y el total, cada inidividuo sera introducido una cierta cantidad de veces a la rulera
	  for (int i = 0; i < poblacion.length; i++) {
	      
	      double fitness = poblacion[i].fitness/maxFitness;
	      int n = (int) (fitness * 100);  //Multiplicador arbitrario que fija el tamaño de la ruleta
	      for (int j = 0; j < n; j++) {
	        matingPool.add(poblacion[i]);
	      }
	    }
	  }

	  //Crea la nueva generación
	  Poblacion generate() {
	       	      
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
	      
	      //Seleccion elitista
	      for (int i = 0; i < cantsel; i++) {
	    	
	    	  //int a = AleatorioInt(0, matingPool.size());
	    	  Individuo mejores = poblacion[poblacion.length-i-1];
	    	  poblacionsig.poblacion[i] = mejores;
	    	  //poblacionsig.poblacion[i].centroides = sorter.array[i].centroides;
	    	  
	      }
	      
	      //Cruza simple
	      for (int i = cantsel; i < (cantcru + cantsel); i++) {
	    	  
	    	  int a = AleatorioInt(0, matingPool.size());
	    	  int b = AleatorioInt(0, matingPool.size());
	    	  Individuo padreA = matingPool.get(a);
			  Individuo padreB = matingPool.get(b);
			  Individuo hijo = padreA.Cruzar(padreB);
			  poblacionsig.poblacion[i] = hijo;
	      }
	      
	      //Mutación
	      for (int i = (cantcru + cantsel); i < poblacion.length; i++) {
	    	  
	    	  int a = AleatorioInt(0, matingPool.size());
	    	  Individuo xmen = matingPool.get(a).mutar(tasaMutacion, 80);
	    	  poblacionsig.poblacion[i] = xmen;
	      }
	      
	    return poblacionsig;
	  }


	  //Computa el mejor individuo de la generación
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

	  

	  //Calcula el fitness promedio de la población
	  double getAverageFitness() {
	    double total = 0;
	    for (int i = 0; i < poblacion.length; i++) {
	      total += poblacion[i].fitness;
	    }
	    return total / (poblacion.length);
	  }

	  
}
