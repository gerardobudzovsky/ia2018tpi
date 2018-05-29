package tpi;
import java.util.ArrayList;
import java.util.Arrays;

public class ClusterK {
	
	int id;
	int dimension;
	double[] centroide;
	ArrayList<double[]> puntosAsociados;
	
	//constructor con dimension "n" como el item set y número de cluster;
	ClusterK(int d, int clusterid){
		dimension = d;
		id = clusterid;
	}
	ClusterK(int d, int clusterid, ArrayList<double[]> puntos){
		dimension = d;
		id = clusterid;
		puntosAsociados = puntos;
	}
	
	double[] getCentroide() {
		return centroide;
	}
	
	double[] calCentroide(IndividuoK ind, ArrayList<double[]> itemset){
		
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
		for (h=0; h<dimension; h++) {
			salida[h] = centroideaux[h]/miembros;
			}
			centroide = salida;
		
		return salida;
	}
}
