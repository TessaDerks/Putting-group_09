package AI;

import physics.*;

public class Merge {


    public static void splitList(Individual[] list, int first, int second, int end) {


        int sizeFirst = second - first + 1;
        int sizeSecond = end - second;
        Individual[] firstHalf = new Individual[sizeFirst];
        Individual[] secondHalf = new Individual[sizeSecond];

        for(int i = 0; i < sizeFirst; i++){
            firstHalf[i] = list[first + i];
            //System.out.print(firstHalf[i] + " ");
        }
        //System.out.println();
        for (int j = 0; j < sizeSecond; j++){
            secondHalf[j] = list[second+1+j];
            //System.out.print(secondHalf[j] + " ");
        }
        //System.out.println();

        int m1 = 0, m2 = 0;
        int indexList = first;
        while ( m1 < sizeFirst && m2 < sizeSecond){
            if(firstHalf[m1].getFitness() <= secondHalf[m2].getFitness()){
                list[indexList] = firstHalf[m1];
                m1++;
            }
            else {
                list[indexList] = secondHalf[m2];
                m2++;
            }
            indexList++;
        }
        //for (int k = 0; k < first; k++) System.out.print(merged[k] + " ");

        while(m1 < sizeFirst){
            list[indexList] = firstHalf[m1];
            indexList++;
            m1++;
        }
        while( m2 < sizeSecond){
            list[indexList] = secondHalf[m2];
            indexList++;
            m2++;
        }
    }

    public void sortList(Individual[] list, int start, int end){

        if(start < end){

            int middle = (start + end)/2;
                sortList(list, start, middle);
                sortList(list, middle+1, end);

                splitList(list, start, middle, end);
        }
    }
}
