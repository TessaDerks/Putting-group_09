package AI;

public class Merge {

    /**
     *
     * @param list Array of Individuals,
     * @param first int, 0 < first < second
     * @param second int, first < second < end
     * @param end second < end < size of the list
     */
    public static void splitList(Individual[] list, int first, int second, int end) {


        int sizeFirst = second - first + 1;
        int sizeSecond = end - second;
        Individual[] firstHalf = new Individual[sizeFirst];
        Individual[] secondHalf = new Individual[sizeSecond];

        //System.out.print(firstHalf[i] + " ");
        System.arraycopy(list, first, firstHalf, 0, sizeFirst);
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

    /**
     *
     * @param list arrayList of individuals
     * @param start int, 0 < start < end
     * @param end int, start < end < list.size
     */
    public void sortList(Individual[] list, int start, int end){

        if(start < end){

            int middle = (start + end)/2;
                sortList(list, start, middle);
                sortList(list, middle+1, end);

                splitList(list, start, middle, end);
        }
    }
}
