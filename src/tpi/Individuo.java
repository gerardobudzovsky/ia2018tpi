package tpi;
import tpi.ClusteringGenetico;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;

public class Individuo {
	
		/*public static void main(String[] args) {
			Individuo ind = new Individuo(20, 5);
			
			System.out.println(Arrays.toString(ind.getPhrase()));
		}*/
		// The genetic sequence
		  int[] genes;
		  float fitness;
		  
		  // Constructor: recibe "n" (cantidad de puntos que pasa karanik) y
		  // "k" (cantidad de clusters)
		  Individuo(int n, int k) {
			  
			  Random r = new Random(); //Generador random de 1 a clusters k
			  int Low = 1;
		      int High = k+1;
			  genes = new int[n];
			  
		      for (int i = 0; i < genes.length; i++) {
		      genes[i] = r.nextInt(High-Low) + Low;
		      
		    }
		  }
		  int[] getPhrase() {
			  return genes;
		  }
		  // Converts character array to a String
		  /*void getPhrase() {
			int i;
		    for (i=0; i<genes.length; i++) {
		    	System.out.print(genes[i]);
		    }
		  }
		  
		  // Fitness function (returns floating point % of "correct" characters)
		  /*void fitness (String target) {
		     int score = 0;
		     for (int i = 0; i < genes.length; i++) {
		        if (genes[i] == target.charAt(i)) {
		          score++;
		        }
		     }
		     
		     
		     fitness = (float)score / (float)target.length();
		  }
		  
		  Crossover
		  DNA crossover(DNA partner) {
		    // A new child
		    DNA child = new DNA(genes.length);
		    
		    int midpoint = int(random(genes.length)); // Pick a midpoint
		    
		    // Half from one, half from the other
		    for (int i = 0; i < genes.length; i++) {
		      if (i > midpoint) child.genes[i] = genes[i];
		      else              child.genes[i] = partner.genes[i];
		    }
		    return child;
		  }
		  
		  // Based on a mutation probability, picks a new random character
		  void mutate(float mutationRate) {
		    for (int i = 0; i < genes.length; i++) {
		      if (random(1) < mutationRate) {
		        genes[i] = (char) random(32,128);
		      }
		    }
		  }

	
	
	
			/*public static void main(String[] args) {
			System.out.println("Chau mundo");*/
		}


