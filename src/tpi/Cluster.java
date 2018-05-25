package tpi;
import tpi.Individuo;
import java.util.ArrayList;
import java.util.Arrays;

public class Cluster {
	
	int id;
	int dimension;
	double[] centroide;
	ArrayList<double[]> puntosAsociados;
	
	//constructor con dimension "n" como el item set y número de cluster;
	Cluster(int d, int clusterid){
		dimension = d;
		id = clusterid;
	}
	
	Cluster(int d, int clusterid, ArrayList<double[]> puntos){
		dimension = d;
		id = clusterid;
		puntosAsociados = puntos;
	}
	
	double[] getCentroide() {
		return centroide;
	}
	
	double[] calCentroide(Individuo ind, ArrayList<double[]> itemset){
		
		double[] salida = new double[dimension];
		double[] centroideaux = new double[dimension];
		double dime;
		double[] linea;
		int i;
		int j;
		int miembros;
		miembros = 0;
		int h;
				
		//obtiene total de cada dimensión
		for (i = 0; i < ind.getPhrase().length; i++) { //recorre individuos
		
			
			if (ind.getPhrase()[i] == id) { //pregunta si el ind pertenece al cluster
			
				miembros = miembros + 1;
				
				for (j=0; j<dimension; j++) {//Acumula cada dimension 
				
					linea = itemset.get(i);//Accede a la transaccion
					dime = linea[j];//Accede a la dimension
					centroideaux[j] = centroideaux[j] + dime;
				
				}
								
			}
			
				
		}
		
		//System.out.println(Arrays.toString(centroideaux));
		//System.out.println(miembros);
		double[] promedioxDimension = new double[dimension];
		for (h=0; h<dimension; h++) {
			promedioxDimension[h] = centroideaux[h]/miembros;
		}
		
		double potencia;
		double[] sumatoria = new double[dimension];
		for (i = 0; i < ind.getPhrase().length; i++) { //recorre individuos
				
					double resta = 0;
					if (ind.getPhrase()[i] == id) { //pregunta si el ind pertenece al cluster
					
						for (j=0; j<dimension; j++) {//Acumula cada dimension 
						
							linea = itemset.get(i);//Accede a la transaccion
							dime = linea[j];//Accede a la dimension
							resta = linea[j] - promedioxDimension[j];
							potencia = Math.pow(resta, 2.0);
							sumatoria[j] = sumatoria[j] + potencia;
							
						
						}
										
					}
					
						
			}
		double multiplicacion;
			for (i = 0; i < dimension; i++) {
				
				multiplicacion = sumatoria[i]/(miembros - 1);
				salida[i] = Math.pow(multiplicacion, 0.5);
				
			}
		
			centroide = promedioxDimension;
		
		return promedioxDimension;
	}
}
