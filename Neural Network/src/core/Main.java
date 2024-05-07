package core;

import data.Puzzle;
import data.PuzzleLoader;
import network.NeuralNetwork;

//The last time I used this I was trying lots of different things to train it on
//Because of that this file is kind of a mess
//Might clean it up in the future so that it works more as a demo


public class Main {

	public static void main(String[] args) {

		NeuralNetwork net = new NeuralNetwork(new int[] { 1, 10, 10, 1 }, 0.00001);
		net.randomizeValues(-1, 1);

		PuzzleLoader pl;
		Puzzle[] data = null;

		int[][][] testBatch = { { { 0, 0 }, { 0 } }, { { 0, 1 }, { 1 } }, { { 1, 0 }, { 1 } }, { { 1, 1 }, { 1 } } };

		/*
		 * int i = 0; while (i < 50000) {
		 * 
		 * net.updateEpoch(testBatch); i++; } net.testEpoch(testBatch);
		 */
		
		double cost = net.testEpoch(randSquareGen(100));
		System.out.println("Average cost of test batch was: " + cost + "\n");

		
		int i = 0;

		while (i < 50000) {

			cost = net.updateEpoch(randSquareGen(256));

			System.out.println("Training on batch " + i);
			System.out.println("Average cost was: " + cost + "\n");

			if (Double.isNaN(cost)) {
				System.exit(0);
			}

			i++;
		}

		cost = net.testEpoch(randSquareGen(100));
		System.out.println("Average cost of test batch was: " + cost + "\n");

		/*
		 * try {
		 * 
		 * pl = new
		 * PuzzleLoader("C:\\Users\\oscar\\Desktop\\NNfiles\\lichess_db_puzzle.csv.bz2")
		 * ;
		 * 
		 * int i = 0; while (i < 1000) { System.out.println(i);
		 * 
		 * int n = (int) Math.floor((Math.random() * (4000 - 100)) + 100); pl = new
		 * PuzzleLoader("C:\\Users\\oscar\\Desktop\\NNfiles\\lichess_db_puzzle.csv.bz2")
		 * ;
		 * 
		 * data = pl.loadSet(n, n+300);
		 * 
		 * net.updateEpoch(PuzzleLoader.dataToBatch(data)); i++; }
		 * 
		 * pl = new
		 * PuzzleLoader("C:\\Users\\oscar\\Desktop\\NNfiles\\lichess_db_puzzle.csv.bz2")
		 * ; data = pl.loadSet(0, 100); net.testEpoch(PuzzleLoader.dataToBatch(data));
		 * 
		 * } catch (FileNotFoundException e) { e.printStackTrace(); } catch
		 * (CompressorException e) { e.printStackTrace(); } catch (IOException e) {
		 * e.printStackTrace(); }
		 */

	}
	
	public static double[][][] randSquareGen(int size) {
		
		double[][][] out = new double[size][][];
		
		for (int i = 0; i < size; i++) {
			
			double n = (Math.random() * (101)) - 50;
			
			out[i] = new double[][] { { n }, { Math.pow(n, 2) } };
			
		}
		
		return out;
		
	}

	// Always from 0 to 100
	public static double[][][] randSineGen(int size) {

		double[][][] out = new double[size][][];

		for (int i = 0; i < size; i++) {

			double n = (Math.random() * (181 - 0)) + 0;

			out[i] = new double[][] { { n }, { Math.sin(Math.toRadians(n)) } };

		}

		return out;
	}

	public static double[][][] contSineGen() {
		double[][][] out = new double[100][][];

		for (int i = 0; i < 180; i++) {

			out[i] = new double[][] { { i }, { Math.sin(Math.toRadians(i)) } };

		}

		return out;
	}

}
