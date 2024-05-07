package network;

import java.util.Arrays;

import util.Matrix;

public class NeuralNetwork {

	// [0] refers to size of input layer, [1] is size of first hidden layer
	int[] layers;

	// [0] refers to first hidden layer. For weights this means to the right, so
	// between input and hl1
	Matrix[] weights;
	Matrix[] biases;
	
	double learningRate;

	public NeuralNetwork(int[] nodes, double learningRate_) {

		layers = nodes;

		// Initialize list of weight matrix and list of biases vectors
		weights = new Matrix[layers.length - 1];
		biases = new Matrix[layers.length - 1];

		// fixes sizes related to hidden layers
		for (int i = 0; i < weights.length; i++) {
			weights[i] = new Matrix(nodes[i + 1], nodes[i]);
			biases[i] = new Matrix(nodes[i + 1]);
		}
		
		learningRate = learningRate_;
		
	}

	public void randomizeValues(double min, double max) {
		for (int i = 0; i < weights.length; i++) {
			weights[i].randomize(min, max);
			biases[i].randomize(min, max);
		}

	}
	
	// Forward propagation
	public Matrix predict(double[] input) {

		if (layers[0] != input.length) {
			System.out.println("Input array does not match network");
			return null;
		}

		Matrix tmp = Matrix.arrayToMatrix(input);
		Matrix out = new Matrix(layers[layers.length - 1]);

		// full forward prop
		for (int i = 0; i < weights.length; i++) {
			tmp = Matrix.multiply(weights[i], tmp);
			tmp = Matrix.add(tmp, biases[i]);
			tmp = tmp.rectify();
		}

		out = new Matrix(tmp, true);

		return out;
	}

	// return cost of specific training example
	public double costOfOne(double[] input, double[] expected) {

		Matrix exp = Matrix.arrayToMatrix(expected);
		Matrix diff = Matrix.subtract(exp, predict(input));

		return diff.getSquareSum() / 2;
	}

	// Perform first forward propagation, then backwards, calculating the gradient
	// for every weight and bias of one 
	public Matrix[][] calcGradient(double[] input, double[] expected) {

		// [i][j] means layer i, where i = 0 means the first layer
		// [j] differentiates between the weights and the biases. j = 0 is the weights
		// matrix and j = 1 is the biases matrix
		Matrix[][] out = new Matrix[weights.length][2];

		Matrix tmp = Matrix.arrayToMatrix(input);

		Matrix[] neuronActives = new Matrix[weights.length];

		// First we do forward prop calculating all the neuron activations
		for (int i = 0; i < weights.length; i++) {
			tmp = Matrix.multiply(weights[i], tmp);
			tmp = Matrix.add(tmp, biases[i]);
			tmp = tmp.rectify();

			neuronActives[i] = new Matrix(tmp, true);
		}

		Matrix dz = Matrix.subtract(neuronActives[weights.length - 1], Matrix.arrayToMatrix(expected))
				.dRect(neuronActives[weights.length - 1]);

		for (int i = weights.length - 1; i > 0; i--) {

			out[i][0] = Matrix.multiply(dz, neuronActives[i - 1].transpose());
			out[i][1] = dz;

			dz = Matrix.multiply(weights[i].transpose(), dz);
		}

		out[0][0] = Matrix.multiply(dz, Matrix.arrayToMatrix(input).transpose());
		out[0][1] = dz;

		return out;

	}

	public double updateEpoch(double[][][] miniBatch) {
				
		Matrix[][] total = new Matrix[weights.length][2];
		
		int m = miniBatch.length;
		double totalCost = 0.0;
		
		for (int i = 0; i < weights.length; i++) {
			total[i][0] = new Matrix(weights[i], false).fill(0);
			total[i][1] = new Matrix(biases[i], false).fill(0);
		}
		
		for (int i = 0; i < miniBatch.length; i++) {
			
			Matrix[][] der = calcGradient(miniBatch[i][0], miniBatch[i][1]);
			
			totalCost += costOfOne(miniBatch[i][0], miniBatch[i][1]);
			
			for (int j = 0; j < weights.length; j++) {
				total[j][0] = Matrix.add(der[j][0], total[j][0]);
				total[j][1] = Matrix.add(der[j][1], total[j][1]);
			}
			
		}
		
		for (int i = 0; i < weights.length; i++) {			
			
			total[i][0] = total[i][0].scale(1.0/m);
			total[i][1] = total[i][1].scale(1.0/m);
			
			weights[i] = Matrix.subtract(weights[i], total[i][0].scale(learningRate));
			biases[i] = Matrix.subtract(biases[i], total[i][1].scale(learningRate));
			
		} 
		
		return totalCost/m;
				
	}
	
	public double testEpoch(double[][][] testBatch) {
		
		double totalCost = 0.0;
		
		for (int i = 0; i < testBatch.length; i++) {
			
			System.out.print("Input: ");
			System.out.println(Arrays.toString(testBatch[i][0]));
			System.out.print("Guessed: ");
			predict(testBatch[i][0]).print();
			System.out.print("Expected: ");
			System.out.println(Arrays.toString(testBatch[i][1]) + "\n");
			
			totalCost += costOfOne(testBatch[i][0], testBatch[i][1]);
		}
		
		return totalCost/testBatch.length;
		
	}
	
}
