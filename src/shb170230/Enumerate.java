
/** Starter code for permutations and combinations of distinct items
 *  @author
 **/

package shb170230;

import java.util.Arrays;

public class Enumerate<T> {
    T[] arr;
    int k;
    int count;
    Approver<T> app;

    //-----------------Constructors-------------------

    public Enumerate(T[] arr, int k, Approver<T> app) {
	this.arr = arr;
	this.k = k;
	this.count = 0;
	this.app = app;
    }

    public Enumerate(T[] arr, Approver<T> app) {
	this(arr, arr.length, app);
    }

    public Enumerate(T[] arr, int k) {
	this(arr, k, new Approver<T>());
    }

    public Enumerate(T[] arr) {
	this(arr, arr.length, new Approver<T>());
    }

    //-------------Methods of Enumerate class: To do-----------------

    // n = arr.length, choose k things, d elements arr[0..d-1] done
    // c more elements are needed from arr[d..n-1].  d = k-c.
    public void permute(int c) {
        if (c == 0) {
            visit(arr);
        } else {
            int d = k - c;
            permute(c - 1);
            for (int i = d + 1; i < arr.length; i++) {
                swap(d, i);
                permute(c - 1);
                swap(d, i);
            }
        }
    }

    public void visit(T[] array) {
	count++;
        //app.visit(array, k);
    }
    
    //----------------------Nested class: Approver-----------------------


    // Class to decide whether to extend a permutation with a selected item
    // Extend this class in algorithms that need to enumerate permutations with precedence constraints
    public static class Approver<T> {
	/* Extend permutation by item? */
	public boolean select(T item) { return true; }

        /* Backtrack selected item */
	public void unselect(T item) { }

	/* Visit a permutation or combination */
	public void visit(T[] array, int k) {
	    for (int i = 0; i < k; i++) {
		System.out.print(array[i] + " ");
	    }
	    System.out.println();
	}
    }

    //-----------------------Utilities-----------------------------

    void swap(int i, int j) {
	T tmp = arr[i];
	arr[i] = arr[j];
	arr[j] = tmp;
    }

    void reverse(int low, int high) {
	while(low < high) {
	    swap(low, high);
	    low++;
	    high--;
	}
    }

    //--------------------Static methods----------------------------

    // Enumerate permutations of k items out of n = arr.length
    public static<T> Enumerate<T> permute(T[] arr, int k) {
	Enumerate<T> e = new Enumerate<>(arr, k);
	e.permute(k);
	return e;
    }

    public static void main(String args[]) {
        int n = 1000;
        int k = 10;
        if (args.length > 0) {
            n = Integer.parseInt(args[0]);
            k = n;
        }
        if (args.length > 1) {
            k = Integer.parseInt(args[1]);
        }
        Integer[] arr = new Integer[n];
        for (int i = 0; i < n; i++) {
            arr[i] = i + 1;
        }
        System.out.println(Arrays.toString(arr));

        System.out.println("Permutations: " + n + " " + k);
        Integer[] test = {1, 2, 2, 3, 3, 4};
        Enumerate<Integer> e = permute(arr, k);

        System.out.println("Count: " + e.count + "\n_________________________");
    }
}

