package tpi;

public class Quicksort {
    
    public Individuo[] array;
    private int length;
 
    public void sort(Individuo[] inputArr) {
         
        if (inputArr == null || inputArr.length == 0) {
            return;
        }
        this.array = inputArr;
        length = inputArr.length;
        quickSort(0, length - 1);
    }
 
    private void quickSort(int lowerIndex, int higherIndex) {
         
        int i = lowerIndex;
        int j = higherIndex;
        // Calcular pivot
         double pivot = array[lowerIndex+(higherIndex-lowerIndex)/2].fitness;
        // Dividir en dos arreglos
        while (i <= j) {
            
            while (array[i].fitness < pivot) {
                i++;
            }
            while (array[j].fitness > pivot) {
                j--;
            }
            if (i <= j) {
                exchangeNumbers(i, j);
                //mover el índice a ambos lados
                i++;
                j--;
            }
        }
        // llamar a quicksort() recursivamente
        if (lowerIndex < j)
            quickSort(lowerIndex, j);
        if (i < higherIndex)
            quickSort(i, higherIndex);
    }
 
    private void exchangeNumbers(int i, int j) {
        Individuo temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
    
}