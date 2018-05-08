package tpi;
import tpi.ClusteringGenetico;
import tpi.Individuo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Cluster {
	
public static void main(String[] args) {
		
		double[] linea;
		Random random = new Random();
		int i;
		int j;
		Individuo ind = new Individuo(10, 4);
		ArrayList<double[]> dataset = new ArrayList<double[]>();
		double[] arreglo = new double [3];
		Cluster cluster = new Cluster (dataset.size(), 1);
		
		//Genera un dataset al azar para probar
		for (j=0; j<10; j++) {
			for (i=0; i<3; i++) 
			{
				arreglo [i] = random.nextInt(1000) / 100.0;
		
			}
		//System.out.println(Arrays.toString(arreglo));
		dataset.add(arreglo);
		}
		for (i=0; i<dataset.size(); i++) {
			for (j=0; j<3; j++) //Acumula cada dimension
			{
			
			linea = dataset.get(i);//Accede a la transaccion
			//dim = linea[j];//Accede a la dimension
			//System.out.println(Arrays.toString(linea));
			
			} 
		}
		
		System.out.println(" ");
		//System.out.println(Arrays.toString(ind.getPhrase()));
		System.out.println(" ");
		System.out.println(Arrays.toString(cluster.getCentroide(ind, dataset)));
		
		
	}
	
	int id;
	int dimension;
	double[] centroide;
	//constructor con dimension "n" como el item set y número de cluster;
	Cluster(int n, int clusterid){
		dimension = n;
		id = clusterid;
	
	}

	double [] getCentroide(Individuo ind, ArrayList<double[]> itemset){
		
		double[] salida = new double[3];
		double[] centroideaux = new double[3];
		double dim;
		double[] linea;
		int i;
		int j;
		int miembros = 0;
		
		//obtiene total de cada dimensión
		for (i = 0; i < ind.getPhrase().length; i++) //recorre individuos
		{
			
			if (ind.getPhrase()[i] == id) //pregunta si el ind pertenece al cluster
			{
				for (j=0; j<dimension; j++) //Acumula cada dimension
				{
				
				linea = itemset.get(i);//Accede a la transaccion
				dim = linea[j];//Accede a la dimension
				centroideaux[j] = centroideaux[j] + dim;
				miembros = miembros + 1;
				
				}
		
					
			}
			
			for (i=0; i<dimension; i++) 
			{
			centroide[i] = centroideaux[i]/miembros;
			salida [i] = centroide[i];
			}
				
		}
		return salida;
	}
}
