package script.leetCode.T0Sort;


public class QuickSort {

    public static void quickSort(int[] arr, int low, int high) {

        int pivot = arr[low];

        while(low < high){

            while(low < high && arr[high] > pivot){
                high--;
            }
            swap(arr, low, high);
            while(low < high && arr[low] <= pivot){
                low++;
            }
            
        }

    }

    public static void swap(int[] arr, int i, int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {

        int[] arr = {49, 38, 65, 97, 23};

        quickSort(arr, 0, arr.length - 1);

        printArr(arr);
    }

    public static void printArr(int[] arr){

        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
        }
        System.out.println();
    }
}
