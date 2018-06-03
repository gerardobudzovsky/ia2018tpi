package tpi;

import java.awt.Font;
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

import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;


public class ClusteringGenetico {
	
	static private final String newline = "\n";
	static ArrayList<double[]> itemset = new ArrayList<double[]>();
	static int dim = 0;
	static int numTransactions = 0;
	static double MAX = 1.7E300;
	static double tiempoDeEjecucion = 0;

	//Imprime los puntos con sus clusters asociados
	static void ArmarTabla(Individuo mejor){
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
		}
	}
	
	public static void PrepararGrafico(Individuo ind, int clusters, int dimension1, int dimension2) {
		
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
		      ScatterPlotExample example = new ScatterPlotExample("Genético", grafico, clusters, dimension1, dimension2);
		      example.setBounds(710, 10 , 640, 480);
//		      example.setSize(800, 600);
//		      example.setLocationRelativeTo(null);
		      example.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		      example.setVisible(false);
		      VentanaPrincipal.setGrafico1(example);		      
		    });
	}
	
	
	public static double calculateDistance(double[] array1, double[] array2)
	{
        double Sum = 0.0;
        for(int i=0;i<array1.length;i++) {
           Sum = Sum + Math.pow((array1[i]-array2[i]),2.0);
        }
        return Math.pow(Sum, 0.5);
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
		static void Datos() throws Exception {
			
			dim = 0;
			itemset.clear();
			numTransactions =0;
			int bandera = 0;
			String transaFile;
			TreeSet<String> itemsetst = new TreeSet<String>();
			
			int i=0;
			
			//transaFile = "dataset01.txt";
			transaFile= VentanaPrincipal.getFile().getAbsolutePath();
			
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
	
	//public static void main (String[] args) throws Exception{
	 public static void ejecutar() throws Exception{	
		
	     //hora de inicio de ejecucion del programa (en milisegundos)
	     long horaInicioEjecucion = System.currentTimeMillis();
	     
		 int menu = (int)VentanaPrincipal.getMenu();//1 - común, 2-rango, 3-comparación
		 ClusteringGenetico.Datos();		 
		
		switch (menu) {
		
			case 1://común
			{	
				int clusterInicial = (int)VentanaPrincipal.obtenerSpinnerCantidadClusters().getValue();
				int cantidadIndividuos = (int)VentanaPrincipal.getSpinnerCantidadIndividuos().getValue();
				int seleccionar = (int)VentanaPrincipal.getSpinnerPorcentajeSeleccion().getValue();//porcentaje de selección
				int cruzar = (int)VentanaPrincipal.getSpinnerPorcentajeCruza().getValue();//Porcentaje de cruza
				int mutar = (int)VentanaPrincipal.getSpinnerPorcentajeMutacion().getValue();//Porcentaje de mutación
				int generaciones = (int)VentanaPrincipal.getSpinnerCantidadGeneraciones().getValue();
				int clusterFinal = clusterInicial;
				int diferencia = clusterFinal - clusterInicial;
				Boolean BanderaCancelar = false; // bandera para cancelar
				int z = 0; //indice del rango
				int m = 0; //indice de la poblacion de mejores
				double mejorCalinski = 0; //Para determinar la mejor clasificación de clusters
				int mejorIndex = 0;
				int dimensionX = (int)VentanaPrincipal.getSpinnerDimensionX().getValue();//El usuario indica la dimensión 1 que desea graficar
				int dimensionY = (int)VentanaPrincipal.getSpinnerDimensionY().getValue();//El usuario indica la dimensión 2 que desea graficar
				
				//Crear población donde se gurdan los mejores de cada número de clusters
				Poblacion mejores = new Poblacion(itemset, 0, diferencia+1, 
					  		seleccionar, cruzar, mutar, 0, dim);
				
				while ((z < (diferencia+1)) && (BanderaCancelar == false)) {
					
					int clusters = clusterInicial + z;
				
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
					
					
					System.out.println("Cantidad de Clusters:" + clusterInicial);
					System.out.println("Porcentaje de Selección:" + seleccionar);
					System.out.println("Porcentaje de Cruza:" + cruzar);
					System.out.println("Porcentaje de Mutación:" + mutar);
					System.out.println("Cantidad de Individuos:" + cantidadIndividuos);
					System.out.println("Cantidad de Generaciones:" + generaciones);
					
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
//					VentanaPrincipal.getTextArea1().append("Calinski y Harabasz" + newline);
					if (Double.isNaN(calinski)) {
						System.out.println("No se puede calcular el índice, hay clústers vacíos");
						VentanaPrincipal.getTextArea1().append("No se puede calcular el índice, hay clústers vacíos." + newline);
					}
					else {
						System.out.println(calinski);
						VentanaPrincipal.getTextArea1().append("Índice Calinski y Harabasz: " + calinski + newline);

					}
					
					mejor.calinski = calinski;//setear el valor del ínidice en el individuo
					
					//Agrega individuo a la población de mejores
					mejores.poblacion[m] = mejor;
					//Determina el mejor de todos
					if (mejor.calinski > mejorCalinski) {
						mejorIndex = m;
						mejorCalinski = mejor.calinski;
					}
					//Aumenta el ínidice de la población de mejores
					m = m + 1;
										
					System.out.println(" ");
					VentanaPrincipal.getTextArea1().append(newline);
					
					System.out.println("Fitness:");
//					VentanaPrincipal.getTextArea1().append("Fitness." + newline);
					
					if (Double.isNaN(mejor.fitness)) {
						System.out.println("No se puede calcular el fitness, hay clusters vacíos.");
						VentanaPrincipal.getTextArea1().append("No se puede calcular el fitness, hay clusters vacíos." + newline);
					} else {
					System.out.println(mejor.fitness);
					VentanaPrincipal.getTextArea1().append("Fitness: " + mejor.fitness + "." + newline);
					}
					
					System.out.println("");
					VentanaPrincipal.getTextArea1().append(newline);

					
					System.out.println("Centroides:");
					VentanaPrincipal.getTextArea1().append("Centroides" + newline);
					for (int i = 0; i < clusters; i++) {
						double[] cent = new double [dim];
						cent = mejor.centroides.get(i);
						if (Double.isNaN(cent[0])) {
							System.out.println(i + "- Cluster vacío");
							VentanaPrincipal.getTextArea1().append(i + "- Cluster vacío." + newline);							
							} else {
								System.out.println(i + "- "+ Arrays.toString(cent));
								VentanaPrincipal.getTextArea1().append(i + "- "+ Arrays.toString(cent) + newline);
							}						
						
					}
					System.out.println(" ");					
					VentanaPrincipal.getTextArea1().append(newline);

					
					/*System.out.println(" ");
					System.out.println("Xu");
					double denominador = dim*Math.pow(numTransactions, 2.0);
					double division = intraClust/denominador;
					double Xu = dim * Math.log(Math.pow(division, 0.5)) + Math.log(clusters);
					System.out.println(Xu);
					
					System.out.println(" ");
					System.out.println("Mejor Individuo:");
					System.out.println(Arrays.toString(mejor.genes));*/
					
					//PrepararGrafico (mejor, mejor.numClusters);
					
					z = z +1;
					
					//Tabla de puntos con sus cluster asociado
					ArmarTabla(mejor);
					
					BanderaCancelar = (boolean)VentanaPrincipal.getBanderaCancelar(); // Condición para cancelar ejecución
					
				}
				
				PrepararGrafico(mejores.poblacion[mejorIndex], 
							mejores.poblacion[mejorIndex].numClusters, (dimensionX-1), (dimensionY-1));
				}
				break;
			
			case 2://rango
			{	
				int clusterInicial = (int)VentanaPrincipal.obtenerSpinnerCantidadClusters().getValue();
				int cantidadIndividuos = (int)VentanaPrincipal.getSpinnerCantidadIndividuos().getValue();
				int seleccionar = (int)VentanaPrincipal.getSpinnerPorcentajeSeleccion().getValue();//porcentaje de selección
				int cruzar = (int)VentanaPrincipal.getSpinnerPorcentajeCruza().getValue();//Porcentaje de cruza
				int mutar = (int)VentanaPrincipal.getSpinnerPorcentajeMutacion().getValue();//Porcentaje de mutación
				int generaciones = (int)VentanaPrincipal.getSpinnerCantidadGeneraciones().getValue();
				int clusterFinal = (int)VentanaPrincipal.getSpinnerClusterFinal().getValue();
				int diferencia = clusterFinal - clusterInicial;
				Boolean BanderaCancelar = false; // bandera para cancelar
				int z = 0; //indice del rango
				int m = 0; //indice de la poblacion de mejores
				double mejorCalinski = 0; //Para determinar la mejor clasificación de clusters
				int mejorIndex = 0;
				int dimensionX = (int)VentanaPrincipal.getSpinnerDimensionX().getValue();//El usuario indica la dimensión 1 que desea graficar
				int dimensionY = (int)VentanaPrincipal.getSpinnerDimensionY().getValue();//El usuario indica la dimensión 2 que desea graficar
				
				//Crear población donde se gurdan los mejores de cada número de clusters
				Poblacion mejores = new Poblacion(itemset, 0, diferencia+1, 
					  		seleccionar, cruzar, mutar, 0, dim);
				
				while ((z < (diferencia+1)) && (BanderaCancelar == false)) {
					
					int clusters = clusterInicial + z;
				
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
					
					
					System.out.println("Cluster Inicial:" + clusterInicial);
					System.out.println("Cluster Final:" + clusterFinal);
					System.out.println("Porcentaje de Selección:" + seleccionar);
					System.out.println("Porcentaje de Cruza:" + cruzar);
					System.out.println("Porcentaje de Mutación:" + mutar);
					System.out.println("Cantidad de Individuos:" + cantidadIndividuos);
					System.out.println("Cantidad de Generaciones:" + generaciones);
					
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
					if (Double.isNaN(calinski)) {
						System.out.println("No se puede calcular el índice, hay clústers vacíos");
					}
					else {
						System.out.println(calinski);
					}
					
					mejor.calinski = calinski;//setear el valor del ínidice en el individuo
					
					//Agrega individuo a la población de mejores
					mejores.poblacion[m] = mejor;
					//Determina el mejor de todos
					if (mejor.calinski > mejorCalinski) {
						mejorIndex = m;
						mejorCalinski = mejor.calinski;
					}
					//Aumenta el ínidice de la población de mejores
					m = m + 1;
											
					System.out.println(" ");
					
					
					System.out.println("Fitness:");
					if (Double.isNaN(mejor.fitness)) {
						System.out.println("No se puede calcular el fitness, hay clusters vacíos");
					} else {
					System.out.println(mejor.fitness);
					System.out.println(" ");}
					
					System.out.println("Centroides:");
					for (int i = 0; i < clusters; i++) {
						double[] cent = new double [dim];
						cent = mejor.centroides.get(i);
						if (Double.isNaN(cent[0])) {
							System.out.println(i + "- Cluster vacío");
							
							} else {
								System.out.println(i + "- "+ Arrays.toString(cent));	
							}						
						
					}
					System.out.println(" ");					
					
					/*System.out.println(" ");
					System.out.println("Xu");
					double denominador = dim*Math.pow(numTransactions, 2.0);
					double division = intraClust/denominador;
					double Xu = dim * Math.log(Math.pow(division, 0.5)) + Math.log(clusters);
					System.out.println(Xu);
					
					System.out.println(" ");
					System.out.println("Mejor Individuo:");
					System.out.println(Arrays.toString(mejor.genes));*/
					
					//PrepararGrafico (mejor, mejor.numClusters);
					
					z = z +1;
					
					//Tabla de puntos con sus cluster asociado
					ArmarTabla(mejor);
					
					BanderaCancelar = (boolean)VentanaPrincipal.getBanderaCancelar(); // Condición para cancelar ejecución
					
				}
				
				
				VentanaPrincipal.getTextArea1().append("MEJOR CONFIGURACIÓN: " + mejores.poblacion[mejorIndex].numClusters + " clusters." + newline);

				if (Double.isNaN(mejores.poblacion[mejorIndex].calinski)) {
					System.out.println("No se puede calcular el índice, hay clústers vacíos");
					VentanaPrincipal.getTextArea1().append("No se puede calcular el índice, hay clústers vacíos." + newline);
				}
				else {
					System.out.println(mejores.poblacion[mejorIndex].calinski);
					VentanaPrincipal.getTextArea1().append("Índice Calinski y Harabasz: " + mejores.poblacion[mejorIndex].calinski + newline);

				}
				
				System.out.println("Centroides:");
				VentanaPrincipal.getTextArea1().append("Centroides" + newline);
				for (int i = 0; i < mejores.poblacion[mejorIndex].numClusters; i++) {
					double[] cent = new double [dim];
					cent = mejores.poblacion[mejorIndex].centroides.get(i);
					if (Double.isNaN(cent[0])) {
						System.out.println(i + "- Cluster vacío");
						VentanaPrincipal.getTextArea1().append(i + "- Cluster vacío." + newline);							
						} else {
							System.out.println(i + "- "+ Arrays.toString(cent));
							VentanaPrincipal.getTextArea1().append(i + "- "+ Arrays.toString(cent) + newline);
						}						
					
				}
				System.out.println(" ");					
				VentanaPrincipal.getTextArea1().append(newline);
				
				
				//Grafica todos los mejores
				for (int h = 0; h < mejores.poblacion.length; h++) {
					if (h != mejorIndex ) {						
						
						VentanaPrincipal.getTextArea1().append("CONFIGURACIÓN: " + mejores.poblacion[h].numClusters + " clusters." + newline);

						if (Double.isNaN(mejores.poblacion[h].calinski)) {
							System.out.println("No se puede calcular el índice, hay clústers vacíos");
							VentanaPrincipal.getTextArea1().append("No se puede calcular el índice, hay clústers vacíos." + newline);
						}
						else {
							System.out.println(mejores.poblacion[h].calinski);
							VentanaPrincipal.getTextArea1().append("Índice Calinski y Harabasz: " + mejores.poblacion[h].calinski + newline);

						}
						
						System.out.println("Centroides:");
						VentanaPrincipal.getTextArea1().append("Centroides" + newline);
						for (int i = 0; i < mejores.poblacion[h].numClusters; i++) {
							double[] cent = new double [dim];
							cent = mejores.poblacion[h].centroides.get(i);
							if (Double.isNaN(cent[0])) {
								System.out.println(i + "- Cluster vacío");
								VentanaPrincipal.getTextArea1().append(i + "- Cluster vacío." + newline);							
								} else {
									System.out.println(i + "- "+ Arrays.toString(cent));
									VentanaPrincipal.getTextArea1().append(i + "- "+ Arrays.toString(cent) + newline);
								}						
							
						}
						System.out.println(" ");					
						VentanaPrincipal.getTextArea1().append(newline);						
						
					}
				}
				
				
				//Grafica solo el mejor
				PrepararGrafico(mejores.poblacion[mejorIndex], 
								mejores.poblacion[mejorIndex].numClusters, (dimensionX-1), (dimensionY-1));
			}
			break;
			
			case 3:				
				
			{	
				int clusterInicial = (int)VentanaPrincipal.obtenerSpinnerCantidadClusters().getValue();
				int cantidadIndividuos = (int)VentanaPrincipal.getSpinnerCantidadIndividuos().getValue();
				int seleccionar = (int)VentanaPrincipal.getSpinnerPorcentajeSeleccion().getValue();//porcentaje de selección
				int cruzar = (int)VentanaPrincipal.getSpinnerPorcentajeCruza().getValue();//Porcentaje de cruza
				int mutar = (int)VentanaPrincipal.getSpinnerPorcentajeMutacion().getValue();//Porcentaje de mutación
				int generaciones = (int)VentanaPrincipal.getSpinnerCantidadGeneraciones().getValue();
				int clusterFinal = clusterInicial;
				int diferencia = clusterFinal - clusterInicial;
				Boolean BanderaCancelar = false; // bandera para cancelar
				int z = 0; //indice del rango
				int m = 0; //indice de la poblacion de mejores
				double mejorCalinski = 0; //Para determinar la mejor clasificación de clusters
				int mejorIndex = 0;
				int dimensionX = (int)VentanaPrincipal.getSpinnerDimensionX().getValue();//El usuario indica la dimensión 1 que desea graficar
				int dimensionY = (int)VentanaPrincipal.getSpinnerDimensionY().getValue();//El usuario indica la dimensión 2 que desea graficar
				
				//Crear población donde se gurdan los mejores de cada número de clusters
				Poblacion mejores = new Poblacion(itemset, 0, diferencia+1, 
					  		seleccionar, cruzar, mutar, 0, dim);
				
				while ((z < (diferencia+1)) && (BanderaCancelar == false)) {
					
					int clusters = clusterInicial + z;
				
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
					
					System.out.println("Cantidad de Clusters:" + clusterInicial);
					System.out.println("Porcentaje de Selección:" + seleccionar);
					System.out.println("Porcentaje de Cruza:" + cruzar);
					System.out.println("Porcentaje de Mutación:" + mutar);
					System.out.println("Cantidad de Individuos:" + cantidadIndividuos);
					System.out.println("Cantidad de Generaciones:" + generaciones);
					
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
					
					VentanaPrincipal.getTextArea1().append("VALORES DEL ALGORITMO DE CLUSTERING GENÉTICO" + newline);
//					VentanaPrincipal.getTextArea1().append(newline);
					System.out.println("Calinski y Harabasz");
//					VentanaPrincipal.getTextArea1().append("Calinski y Harabasz" + newline);
					if (Double.isNaN(calinski)) {
						System.out.println("No se puede calcular el índice, hay clústers vacíos");
//						VentanaPrincipal.getTextArea1().append("No se puede calcular el índice, hay clústers vacíos." + newline);
					}
					else {
						System.out.println(calinski);
//						VentanaPrincipal.getTextArea1().append("Índice Calinski y Harabasz: " + calinski + newline);
					}
					
					mejor.calinski = calinski;//setear el valor del ínidice en el individuo
					
					//Agrega individuo a la población de mejores
					mejores.poblacion[m] = mejor;
					//Determina el mejor de todos
					if (mejor.calinski > mejorCalinski) {
						mejorIndex = m;
						mejorCalinski = mejor.calinski;
					}
					//Aumenta el ínidice de la población de mejores
					m = m + 1;
										
					System.out.println(" ");
//					VentanaPrincipal.getTextArea1().append(newline);
					

					System.out.println("Fitness:");
//					VentanaPrincipal.getTextArea1().append("Fitness." + newline);
					
					if (Double.isNaN(mejor.fitness)) {
						System.out.println("No se puede calcular el fitness, hay clusters vacíos.");
						VentanaPrincipal.getTextArea1().append("No se puede calcular el fitness, hay clusters vacíos." + newline);
					} else {
					System.out.println(mejor.fitness);
					VentanaPrincipal.getTextArea1().append("Fitness: " + mejor.fitness + "." + newline);
					}
					
					System.out.println("");
					VentanaPrincipal.getTextArea1().append(newline);

					
					System.out.println("Centroides:");
					VentanaPrincipal.getTextArea1().append("Centroides" + newline);
					for (int i = 0; i < clusters; i++) {
						double[] cent = new double [dim];
						cent = mejor.centroides.get(i);
						if (Double.isNaN(cent[0])) {
							System.out.println(i + "- Cluster vacío");
							VentanaPrincipal.getTextArea1().append(i + "- Cluster vacío." + newline);							
							} else {
								System.out.println(i + "- "+ Arrays.toString(cent));
								VentanaPrincipal.getTextArea1().append(i + "- "+ Arrays.toString(cent) + newline);
							}						
						
					}
					System.out.println(" ");					
					VentanaPrincipal.getTextArea1().append(newline);

					
					/*System.out.println(" ");
					System.out.println("Xu");
					double denominador = dim*Math.pow(numTransactions, 2.0);
					double division = intraClust/denominador;
					double Xu = dim * Math.log(Math.pow(division, 0.5)) + Math.log(clusters);
					System.out.println(Xu);
					
					System.out.println(" ");
					System.out.println("Mejor Individuo:");
					System.out.println(Arrays.toString(mejor.genes));*/
					
					//PrepararGrafico (mejor, mejor.numClusters);
					
					z = z +1;
					
					//Tabla de puntos con sus cluster asociado
					ArmarTabla(mejor);
					
					BanderaCancelar = (boolean)VentanaPrincipal.getBanderaCancelar(); // Condición para cancelar ejecución
					
				}
				
				PrepararGrafico(mejores.poblacion[mejorIndex], 
							mejores.poblacion[mejorIndex].numClusters, (dimensionX-1), (dimensionY-1));
				}	
				
				
				Kmeans.ejecutar();
				
				break;		
			
		    }
	
		//hora de inicio de ejecucion del programa (en milisegundos)
		long horaFinEjecucion = System.currentTimeMillis();
		
		//calculo el tiempo de ejecucion del programa (en segundos)
		tiempoDeEjecucion= ((double)(horaFinEjecucion - horaInicioEjecucion)/1000);		
	}
	 
	 static double trunc (double num) {
		 double truncado;
		 truncado=Math.floor(num*1000)/1000; 
		 return truncado;
	 }
	 
	public static double getTiempoDeEjecucion() {
		return tiempoDeEjecucion;
	}

	public static void setTiempoDeEjecucion(double tiempoDeEjecucion) {
		ClusteringGenetico.tiempoDeEjecucion = tiempoDeEjecucion;
	}
		
}