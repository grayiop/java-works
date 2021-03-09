package hw1;

import java.io.FileNotFoundException;
import java.lang.NumberFormatException; 
import java.lang.IllegalArgumentException; 
import java.util.InputMismatchException;

/**
 *  
 * @author Long Zeng
 *
 */

/**
 * 
 * This class implements the mergesort algorithm.   
 *
 */

public class MergeSorter extends AbstractSorter
{
	// Other private instance variables if you need ... 
	
	/** 
	 * Constructor takes an array of points.  It invokes the superclass constructor, and also 
	 * set the instance variables algorithm in the superclass.
	 *  
	 * @param pts   input array of integers
	 */
	public MergeSorter(Point[] pts) 
	{
		super(pts);
		algorithm = "mergesort";
	}


	/**
	 * Perform mergesort on the array points[] of the parent class AbstractSorter. 
	 * 
	 */
	@Override 
	public void sort()
	{
		mergeSortRec(points); 
	}

	
	/**
	 * This is a recursive method that carries out mergesort on an array pts[] of points. One 
	 * way is to make copies of the two halves of pts[], recursively call mergeSort on them, 
	 * and merge the two sorted subarrays into pts[].   
	 * 
	 * @param pts	point array 
	 */
	private void mergeSortRec(Point[] pts)
	{
		if(pts.length > 1) {
			int m = pts.length / 2;
			int i;
			int k;
			Point[] L = new Point[m];
			Point[] R = new Point[pts.length - m];
			for(i = 0; i < L.length; i++) {
				L[i] = pts[i];
			}
			for(k = 0; k < R.length; k++) {
				R[k] = pts[i];
			}
			mergeSortRec(L);
			mergeSortRec(R);
			mergeSort(pts, L, R);
		}
		else {
			return;
		}
	}

	
	// Other private methods in case you need ...
	private void mergeSort(Point[] pts, Point[] Left, Point[] Right) {
		int ll = 0;//left limiter
		int rl = 0;//right limiter
		int pl = 0;//pts limiter
		while(ll < Left.length || rl < Right.length) {
			if(ll < Left.length && rl < Right.length) {
				if(pointComparator.compare(Left[ll], Right[rl]) < 0) {
					pts[pl++] = Left[ll++];
				}
				else {
					pts[pl++] = Right[rl++];
				}
			}
			else if(ll < Left.length) {
				pts[pl++] = Left[ll++];
			}
			else if(rl < Right.length) {
				pts[pl++] = Right[rl++];
			}
		}
		
	}
}
