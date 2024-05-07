package data;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.commons.compress.compressors.CompressorException;
import org.apache.commons.compress.compressors.CompressorInputStream;
import org.apache.commons.compress.compressors.CompressorStreamFactory;


public class PuzzleLoader {

	BufferedReader br;

	public PuzzleLoader(String fileIn) throws CompressorException, IOException {
		FileInputStream fin = new FileInputStream(fileIn);
		BufferedInputStream bis = new BufferedInputStream(fin);
		CompressorInputStream input = new CompressorStreamFactory().createCompressorInputStream(bis);
		BufferedReader br2 = new BufferedReader(new InputStreamReader(input));
		
		br = br2;
	}

	public Puzzle[] loadSet(int firstInd, int secondInd) throws IOException {

		// Includes first and excludes last, max 1,550,522 samples. I.e. 0 <--> 1,550,521
		Puzzle[] trainingSet = new Puzzle[secondInd - firstInd];

		String row;
		try {

			for (int i = 0; i < firstInd; i++) {
				br.readLine();
			}

			for (int i = 0; i < trainingSet.length; i++) {
				
				row = br.readLine();
				String[] data = row.split(",");

				trainingSet[i] = new Puzzle(data[0], data[1], data[2]);
			}

			br.close();

		} catch (

		IOException e) {
			System.out.println("Couldn't get games returning empty set.");
			e.printStackTrace();

		}

		return trainingSet;

	}

	public static double[][][] dataToBatch(Puzzle[] data) {
		ArrayList<double[][]> batch = new ArrayList<double[][]>();
		
		for (Puzzle p : data) {
			do {
				batch.add( new double[][]{p.boardToIntArray(),p.getNextMove()});
			} while (p.proceed());
		}
		
		double[][][] out = new double[batch.size()][2][];
		
		for (int i = 0; i < batch.size(); i++) {
			out[i] = batch.get(i);
		}
		
		return out;
	}
	
}
