package binarysearch;
import java.util.Arrays;  
import java.util.Random;
import java.util.Scanner; 
public class binarysearch {
	public static void main(String args[]){
		Random rand = new Random();
		int[] arr = new int[30];
		System.out.println("The unsorted Random numbers are: ");
		for (int i = 0; i < arr.length; i++) {
	         arr[i] = rand.nextInt(1001); 
	         System.out.println(arr[i]);
	      }
		Arrays.sort(arr);   
		System.out.println("The sorted Random numbers are: ");
		for (int i = 0; i < arr.length; i++) {
	        System.out.println(arr[i]);
	      }
		int number;
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter one number to search: ");
		number = sc.nextInt();
		int result = bin_search(arr, 0, arr.length - 1, number);
	      if (result == -1)
	         System.out.println("The value is not in the array");
	      else
	         System.out.println("The value is at index " + result);
	   }

	    private static int bin_search(int arr[], int left, int right, int x){
	      if (right >= left){
	        int mid = left + (right - left) / 2;
	        if (arr[mid] == x){
				return mid;
			 }
	        if (arr[mid] > x){
				return bin_search(arr, left, mid - 1, x);
			 }
	        return bin_search(arr, mid + 1, right, x);
	      }
	      return -1;
	   }
}
