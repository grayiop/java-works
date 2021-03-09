package hw1;
import java.io.File;

/**
 * 
 * @author 
 *
 */

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;


/**
 * 
 * This class sorts all the points in an array of 2D points to determine a reference point whose x and y 
 * coordinates are respectively the medians of the x and y coordinates of the original points. 
 * 
 * It records the employed sorting algorithm as well as the sorting time for comparison. 
 *
 */
public class PointScanner  
{
	private Point[] points; 
	
	private Point medianCoordinatePoint;  // point whose x and y coordinates are respectively the medians of 
	                                      // the x coordinates and y coordinates of those points in the array points[].
	private Algorithm sortingAlgorithm;    
	
		
	protected long scanTime; 	       // execution time in nanoseconds. 
	
	/**
	 * This constructor accepts an array of points and one of the four sorting algorithms as input. Copy 
	 * the points into the array points[].
	 * 
	 * @param  pts  input array of points 
	 * @throws IllegalArgumentException if pts == null or pts.length == 0.
	 */
	public PointScanner(Point[] pts, Algorithm algo) throws IllegalArgumentException
	{
		if (pts == null || pts.length == 0) throw new IllegalArgumentException();
		points = new Point[pts.length];
		int i;
		for (i = 0; i < pts.length; i++) {
			points[i] = new Point(pts[i]);
		}
		sortingAlgorithm = algo;
	}

	
	/**
	 * This constructor reads points from a file. 
	 * 
	 * @param  inputFileName
	 * @throws FileNotFoundException 
	 * @throws InputMismatchException   if the input file contains an odd number of integers
	 */
	protected PointScanner(String inputFileName, Algorithm algo) throws FileNotFoundException, InputMismatchException
	{
		Scanner scnr = new Scanner(new File(inputFileName));
		ArrayList<Integer> apts = new ArrayList<Integer>();//pts in arraylist
		while(scnr.hasNextInt()) {
			apts.add(scnr.nextInt());
		}
		scnr.close();
		if (apts.size() % 2 != 0) throw new InputMismatchException();
		points = new Point[apts.size() / 2];
		int i;
		int x = 0;
		int y = 1;
		for(i = 0; i < points.length; i++) {
			points[i] = new Point(apts.get(x), apts.get(y));
			x = x + 2;
			y = x + 1;
		}
		sortingAlgorithm = algo;
	}

	
	/**
	 * Carry out two rounds of sorting using the algorithm designated by sortingAlgorithm as follows:  
	 *    
	 *     a) Sort points[] by the x-coordinate to get the median x-coordinate. 
	 *     b) Sort points[] again by the y-coordinate to get the median y-coordinate.
	 *     c) Construct medianCoordinatePoint using the obtained median x- and y-coordinates.     
	 *  
	 * Based on the value of sortingAlgorithm, create an object of SelectionSorter, InsertionSorter, MergeSorter,
	 * or QuickSorter to carry out sorting.       
	 * @param algo
	 * @return
	 */
	public void scan()
	{
		// TODO  
		AbstractSorter aSorter = null; 
		scanTime = 0;
		switch (sortingAlgorithm) {
		case SelectionSort:
			aSorter = new SelectionSorter(points);
			break;
		case InsertionSort:
			aSorter = new InsertionSorter(points);
			break;
		case MergeSort:
			aSorter = new MergeSorter(points);
			break;
		case QuickSort:
			aSorter = new QuickSorter(points);
			break;
		}
		// set up x
		aSorter.setComparator(0);
		long st = System.nanoTime();
		aSorter.sort();
		int mx = aSorter.getMedian().getX();//median x
		scanTime = System.nanoTime() - st;
		//set up y
		aSorter.setComparator(1);
		st = System.nanoTime();
		aSorter.sort();
		scanTime = scanTime + (System.nanoTime() - st);
		int my = aSorter.getMedian().getY();//median y
		medianCoordinatePoint = new Point(mx, my);
		st = System.nanoTime();
		aSorter.sort();
		scanTime = scanTime + (System.nanoTime() - st);
		aSorter.getPoints(points);
		// create an object to be referenced by aSorter according to sortingAlgorithm. for each of the two 
		// rounds of sorting, have aSorter do the following: 
		// 
		//     a) call setComparator() with an argument 0 or 1. 
		//
		//     b) call sort(). 		
		// 
		//     c) use a new Point object to store the coordinates of the Median Coordinate Point
		//
		//     d) set the medianCoordinatePoint reference to the object with the correct coordinates.
		//
		//     e) sum up the times spent on the two sorting rounds and set the instance variable scanTime. 
		
	}
	
	
	/**
	 * Outputs performance statistics in the format: 
	 * 
	 * <sorting algorithm> <size>  <time>
	 * 
	 * For instance, 
	 * 
	 * selection sort   1000	  9200867
	 * 
	 * Use the spacing in the sample run in Section 2 of the project description. 
	 */
	public String stats()
	{
		String algo = null;//set up algo return null if error
		switch (sortingAlgorithm) {
		case SelectionSort:
			algo = "SelectionSort";
			break;
		case InsertionSort:
			algo = "InsertionSort";
			break;
		case MergeSort:
			algo = "MergeSort";
			break;
		case QuickSort:
			algo = "QuickSort";
			break;
		}
		return algo + "\t" + points.length + "\t" + scanTime; 
	}
	
	
	/**
	 * Write MCP after a call to scan(),  in the format "MCP: (x, y)"   The x and y coordinates of the point are displayed on the same line with exactly one blank space 
	 * in between. 
	 */
	@Override
	public String toString()
	{
		int i;
		String out = "";
		for (i = 0; i < points.length; i++) {
			out = out + points[i].getX() + " " + points[i].getY();
		}
			return out + "\n";
	}

	
	/**
	 *  
	 * This method, called after scanning, writes point data into a file by outputFileName. The format 
	 * of data in the file is the same as printed out from toString().  The file can help you verify 
	 * the full correctness of a sorting result and debug the underlying algorithm. 
	 * 
	 * @throws FileNotFoundException
	 */
	public void writeMCPToFile() throws FileNotFoundException
	{
		 try {
			 String a = null;
			 switch (sortingAlgorithm) {
				case SelectionSort:
					a = "SelectionSort.txt";
					break;
				case InsertionSort:
					a = "InsertionSort.txt";
					break;
				case MergeSort:
					a = "MergeSort.txt";
					break;
				case QuickSort:
					a = "QuickSort.txt";
					break;
				}
			 FileWriter myWriter = new FileWriter(a);
		      myWriter.write(this.toString());
		      myWriter.close();
		      System.out.println("File generated: SUCCESS");
		    } catch (IOException e) {
		      System.out.println("File generated: ERROR");
		      e.printStackTrace();
		    }
	}	

	

		
}
