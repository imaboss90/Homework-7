import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Selection <E extends Comparable<E>>{

    int k;
    ArrayList<E> input;
    int size;

    public Selection(){
        int k = 0;
        int size = 0;
        input = new ArrayList<>();
    }

    public Selection(ArrayList<E> list, int val){
        input = list;
        k = val;
        //size = list.size();
    }

    // k = 4, so get the 4th largest number, then sort the array so that it is increasing or decreasing and the k index
    // is that number

    public E method1B(){
        quickSort(input, 0, input.size()-1);
        return input.get(k-1);
    }

    public int pickValue(ArrayList<E> temp, int start, int end){

        E val = temp.get(end);

        int i = (start-1);

        for (int m = start; m <= end; m++){
            if(temp.get(m).compareTo(val) > 0){
                i++;
                E tempVal = temp.get(i);
                temp.set(i, temp.get(m));
                temp.set(m, tempVal);
            }
        }
        E tempVal = temp.get(i + 1);
        temp.set(i + 1, temp.get(end));
        temp.set(end, tempVal);
        return (i+1);
    }

    public void quickSort(ArrayList<E> temp, int start, int end){
        if (start < end){
            int middle = pickValue(temp, start, end);

            quickSort(temp, start, middle-1);
            quickSort(temp, middle + 1, end);
        }
    }

    public E method6A(){
        for (int i = input.size()/2-1; i >= 0; i--){
            upwardsHeap(input, i);
        }
        for (int i = size - 1; i >= 0; i--){
            E tempVal = input.get(0);
            input.set(0, input.get(i));
            input.set(i, tempVal);

            upwardsHeap(input, i);
        }


        return input.get(k-1);
    }


    public void addHeap(E val) {

        // Will add element at the end of ArrayList and size will increase
        if (size == 0){
            input.add(val);
            size++;
        }
        else{
            size++;
            input.add(val);
            upwardsHeap(input, size - 1);

        }
    }

    public void upwardsHeap(ArrayList<E> list, int i){
        int parentVal = (i - 1) / 2;
        if(list.get(i).compareTo(list.get(parentVal)) > 0){
            E temp = list.get(i);
            list.set(i, list.get(parentVal));
            list.set(parentVal, temp);
            upwardsHeap(list, parentVal);
        }
    }

    public E removeHeap() {
        if (size == 0){
            throw new NoSuchElementException("The heap is empty");
        }
        else{
            E lastValue = input.get(size-1);
            input.set(0, lastValue);
            size--;

            downwardsHeap(input,0);

        }
        return input.get(0);
    }

    public void downwardsHeap(ArrayList<E> list, int i){
        int lastVal = i;
        int leftVal = 2 * i + 1;
        int rightVal = 2 * i + 2;

        if (leftVal < size && list.get(leftVal).compareTo(list.get(lastVal)) < 0){
            lastVal = leftVal;
        }
        if (rightVal < size && list.get(rightVal).compareTo(list.get(lastVal)) < 0){
            lastVal = rightVal;
        }
        if (lastVal != i){
            E temp = list.get(i);
            list.set(i, list.get(lastVal));
            list.set(lastVal, temp);

            downwardsHeap(list, lastVal);
        }
    }

    public E method6B(){
        PriorityQueue<E> start = new PriorityQueue<>();
        List<E> list = new ArrayList<>();

        for (E val: input){
            if(start.size() < k){
                start.add(val);
            }
            else{
                if (val.compareTo(start.peek()) > 0){
                    start.poll();
                    start.add(val);
                }
            }
            if (start.size() >= k){
                list.add(start.peek());
            }
            else
                list.add(null);
        }
        return list.get(k-1);
    }


    public static void main(String[] args) {
        ArrayList<Integer> newList = new ArrayList<>();
        newList.add(1);
        newList.add(5);
        newList.add(10);
        newList.add(7);
        newList.add(3);
        newList.add(2);
        newList.add(17);
        newList.add(18);

        ArrayList<Integer> newList2 = new ArrayList<>();
        int randomNum = ThreadLocalRandom.current().nextInt(0, 10000001);
        for (int i = 0; i < 10000001; i++){
            newList2.add(randomNum);
        }

        int k1 = 4;
        int k2 = 100000;

        Selection s1 = new Selection(newList, k1);
        //System.out.println(s1.method1B());
        //System.out.println(s1.method6A());
        //System.out.println(s1.method6B());

        Selection s2 = new Selection(newList2, k2);
        //System.out.println(s2.method1B());
        //System.out.println(s2.method6B());
        System.out.println(s2.method6A());
    }
}
