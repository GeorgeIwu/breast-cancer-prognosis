/*
 * Encog(tm) Core v3.3 - Java Version
 * http://www.heatonresearch.com/encog/
 * https://github.com/encog/encog-java-core
 
 * Copyright 2008-2014 Heaton Research, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *   
 * For more information on Heaton Research copyrights, licenses 
 * and trademarks visit:
 * http://www.heatonresearch.com/copyright
 */
package neuralnet.util.simple;

import neuralnet.Encog;
import neuralnet.EncogError;
import neuralnet.app.analyst.csv.basic.BasicFile;
import neuralnet.engine.network.activation.ActivationSigmoid;
import neuralnet.engine.network.activation.ActivationTANH;
import neuralnet.mathutil.error.ErrorCalculation;
import neuralnet.mathutil.randomize.generate.GenerateRandom;
import neuralnet.ml.MLClassification;
import neuralnet.ml.MLContext;
import neuralnet.ml.MLMethod;
import neuralnet.ml.MLRegression;
import neuralnet.ml.data.MLData;
import neuralnet.ml.data.MLDataPair;
import neuralnet.ml.data.MLDataSet;
import neuralnet.ml.data.basic.BasicMLData;
import neuralnet.ml.data.basic.BasicMLDataSet;
import neuralnet.ml.data.buffer.BufferedMLDataSet;
import neuralnet.ml.data.buffer.MemoryDataLoader;
import neuralnet.ml.data.buffer.codec.CSVDataCODEC;
import neuralnet.ml.data.buffer.codec.DataSetCODEC;
import neuralnet.ml.data.specific.CSVNeuralDataSet;
import neuralnet.ml.data.versatile.MatrixMLDataSet;
import neuralnet.ml.svm.SVM;
import neuralnet.ml.svm.training.SVMTrain;
import neuralnet.ml.train.MLTrain;
import neuralnet.neural.freeform.FreeformNetwork;
import neuralnet.neural.freeform.training.FreeformResilientPropagation;
import neuralnet.neural.networks.BasicNetwork;
import neuralnet.neural.networks.ContainsFlat;
import neuralnet.neural.networks.training.propagation.Propagation;
import neuralnet.neural.networks.training.propagation.resilient.ResilientPropagation;
import neuralnet.neural.pattern.FeedForwardPattern;
import neuralnet.util.Format;
import neuralnet.util.csv.CSVFormat;
import neuralnet.util.csv.ReadCSV;
import neuralnet.util.logging.EncogLogging;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * General utility class for Encog. Provides for some common Encog procedures.
 */
public final class EncogUtility {
	
	static public class FalsePositiveReport {
		private final int truePositive;
		private final int trueNegative;
		private final int negativeCount;
		private final int positiveCount;
		
		public int getCount() {
			return this.negativeCount + this.positiveCount;
		}

		public int getTruePositive() {
			return truePositive;
		}



		public int getTrueNegative() {
			return trueNegative;
		}



		public int getNegativeCount() {
			return negativeCount;
		}



		public int getPositiveCount() {
			return positiveCount;
		}



		public FalsePositiveReport(int truePositive,
				int trueNegative, int positiveCount, int negativeCount) {
			super();
			this.truePositive = truePositive;
			this.trueNegative = trueNegative;
			this.positiveCount = positiveCount;
			this.negativeCount = negativeCount;
			
		}
		
		public String toString() {
			StringBuilder result = new StringBuilder();
			result.append("(True Positive Correct=");
			result.append(this.truePositive);
			result.append("/");
			result.append(this.positiveCount);
			result.append("(");
			result.append(Format.formatPercent(((double)this.truePositive)/this.positiveCount));
			result.append(")");
			result.append(",True Negative Correct=");
			result.append(this.trueNegative);
			result.append("/");
			result.append(this.trueNegative);
			result.append("(");
			result.append(Format.formatPercent(((double)this.trueNegative)/this.negativeCount));
			result.append(")");
			result.append(",Total Correct=");
			result.append(this.truePositive+this.trueNegative);
			result.append("/");
			result.append(this.positiveCount+this.negativeCount);
			result.append("(");
			result.append(Format.formatPercent(((double)this.truePositive+this.trueNegative)/getCount()));
			result.append(")");
			result.append(")");
			
			
			return result.toString();
		}
		
	}

	/**
	 * Convert a CSV file to a binary training file.
	 * 
	 * @param csvFile
	 *            The CSV file.
	 * @param binFile
	 *            The binary file.
	 * @param inputCount
	 *            The number of input values.
	 * @param outputCount
	 *            The number of output values.
	 * @param headers
	 *            True, if there are headers on the3 CSV.
	 */
	public static void convertCSV2Binary(final File csvFile,
			final File binFile, final int inputCount, final int outputCount,
			final boolean headers) {
		binFile.delete();
		final CSVNeuralDataSet csv = new CSVNeuralDataSet(csvFile.toString(),
				inputCount, outputCount, false);
		final BufferedMLDataSet buffer = new BufferedMLDataSet(binFile);
		buffer.beginLoad(inputCount, outputCount);
		for (final MLDataPair pair : csv) {
			buffer.add(pair);
		}
		buffer.endLoad();
	}
		
    /**
     * Load CSV to memory.
     * @param filename The CSV file to load.
     * @param input The input count.
     * @param ideal The ideal count.
     * @param headers True, if headers are present.
     * @param format The loaded dataset.
     * @param significance True, if there is a significance column.
     * @return The loaded dataset.
     */
    public static MLDataSet loadCSV2Memory(String filename, int input, int ideal, boolean headers, CSVFormat format, boolean significance)
    {
        DataSetCODEC codec = new CSVDataCODEC(new File(filename), format, headers, input, ideal, significance);
        MemoryDataLoader load = new MemoryDataLoader(codec);
        MLDataSet dataset = load.external2Memory();
        return dataset;
    }

	/**
	 * Evaluate the network and display (to the console) the output for every
	 * value in the training set. Displays ideal and actual.
	 * 
	 * @param network
	 *            The network to evaluate.
	 * @param training
	 *            The training set to evaluate.
	 */
	public static void evaluate(final MLRegression network,
			final MLDataSet training) {
		for (final MLDataPair pair : training) {
			final MLData output = network.compute(pair.getInput());
			System.out.println("Input="
					+ EncogUtility.formatNeuralData(pair.getInput())
					+ ", Actual=" + EncogUtility.formatNeuralData(output)
					+ ", Ideal="
					+ EncogUtility.formatNeuralData(pair.getIdeal()));

		}
	}

	/**
	 * Format neural data as a list of numbers.
	 * 
	 * @param data
	 *            The neural data to format.
	 * @return The formatted neural data.
	 */
	public static String formatNeuralData(final MLData data) {
		final StringBuilder result = new StringBuilder();
		for (int i = 0; i < data.size(); i++) {
			if (i != 0) {
				result.append(',');
			}
			result.append(Format.formatDouble(data.getData(i), 4));
		}
		return result.toString();
	}

	/**
	 * Create a simple feedforward neural network.
	 * 
	 * @param input
	 *            The number of input neurons.
	 * @param hidden1
	 *            The number of hidden layer 1 neurons.
	 * @param hidden2
	 *            The number of hidden layer 2 neurons.
	 * @param output
	 *            The number of output neurons.
	 * @param tanh
	 *            True to use hyperbolic tangent activation function, false to
	 *            use the sigmoid activation function.
	 * @return The neural network.
	 */
	public static BasicNetwork simpleFeedForward(final int input,
			final int hidden1, final int hidden2, final int output,
			final boolean tanh) {
		final FeedForwardPattern pattern = new FeedForwardPattern();
		pattern.setInputNeurons(input);
		pattern.setOutputNeurons(output);
		if (tanh) {
			pattern.setActivationFunction(new ActivationTANH());
		} else {
			pattern.setActivationFunction(new ActivationSigmoid());
		}

		if (hidden1 > 0) {
			pattern.addHiddenLayer(hidden1);
		}
		if (hidden2 > 0) {
			pattern.addHiddenLayer(hidden2);
		}

		final BasicNetwork network = (BasicNetwork)pattern.generate();
		network.reset();
		return network;
	}

	/**
	 * Train the neural network, using SCG training, and output status to the
	 * console.
	 * 
	 * @param network
	 *            The network to train.
	 * @param trainingSet
	 *            The training set.
	 * @param minutes
	 *            The number of minutes to train for.
	 */
	public static void trainConsole(final BasicNetwork network,
			final MLDataSet trainingSet, final int minutes) {
		final Propagation train = new ResilientPropagation(network, trainingSet);
		train.setThreadCount(0);
		EncogUtility.trainConsole(train, network, trainingSet, minutes);
	}

	/**
	 * Train the network, using the specified training algorithm, and send the
	 * output to the console.
	 * 
	 * @param train
	 *            The training method to use.
	 * @param network
	 *            The network to train.
	 * @param trainingSet
	 *            The training set.
	 * @param minutes
	 *            The number of minutes to train for.
	 */
	public static void trainConsole(final MLTrain train,
			final BasicNetwork network, final MLDataSet trainingSet,
			final int minutes) {

		long current, elapsed, remaining;

		System.out.println("Beginning training...");
		final long start = System.currentTimeMillis();
		do {
			train.iteration();

			current = System.currentTimeMillis();
			elapsed = (current - start) / 1000;// seconds
			remaining = (minutes * 60) - elapsed;

			int iteration = train.getIteration();
			
			System.out.println("Iteration #" + Format.formatInteger(iteration)
					+ " Error:" + Format.formatPercent(train.getError())
					+ " elapsed time = " + Format.formatTimeSpan((int) elapsed)
					+ " time left = "
					+ Format.formatTimeSpan((int) remaining));

		} while (remaining > 0);
		train.finishTraining();
	}

	/**
	 * Train the method, to a specific error, send the output to the console.
	 * 
	 * @param method
	 *            The method to train.
	 * @param dataSet
	 *            The training set to use.
	 * @param error
	 *            The error level to train to.
	 */
	public static void trainToError(final MLMethod method,
			final MLDataSet dataSet, final double error) {

		MLTrain train;

		if (method instanceof SVM) {
			train = new SVMTrain((SVM)method, dataSet);
		} if(method instanceof FreeformNetwork ) {
			train = new FreeformResilientPropagation((FreeformNetwork) method, dataSet);
		} else {
			train = new ResilientPropagation((ContainsFlat)method, dataSet);
		}
		EncogUtility.trainToError(train, error);
	}

	/**
	 * Train to a specific error, using the specified training method, send the
	 * output to the console.
	 * 
	 * @param train
	 *            The training method.
	 * @param error
	 *            The desired error level.
	 */
	public static void trainToError(final MLTrain train,
			final double error) {

		int epoch = 1;

		System.out.println("Beginning training...");

		do {
			train.iteration();

			System.out.println("Iteration #" + Format.formatInteger(epoch)
					+ " Error:" + Format.formatPercent(train.getError())
					+ " Target Error: " + Format.formatPercent(error));
			epoch++;
		} while ((train.getError() > error) && !train.isTrainingDone());
		train.finishTraining();
	}

	/**
	 * Private constructor.
	 */
	private EncogUtility() {

	}

	public static MLDataSet loadEGB2Memory(File filename) {
		BufferedMLDataSet buffer = new BufferedMLDataSet(filename);
		MLDataSet result = buffer.loadToMemory();
		buffer.close();
		return result;
	}

    /**
     * Convert a CSV file to a binary training file.
     * @param csvFile The binary file.
     * @param binFile The binary file.
     * @param inputCount The number of input values. 
     * @param outputCount The number of output values.
     * @param headers True, if there are headers on the CSV.
     */
    public static void convertCSV2Binary(String csvFile,
             String binFile, int inputCount, int outputCount,
             boolean headers)
    {

        (new File(binFile)).delete();
        CSVNeuralDataSet csv = new CSVNeuralDataSet(csvFile.toString(),
               inputCount, outputCount, headers);
        BufferedMLDataSet buffer = new BufferedMLDataSet(new File(binFile));
        buffer.beginLoad(inputCount, outputCount);
        for(MLDataPair pair : csv)
        {
            buffer.add(pair);
        }
        buffer.endLoad();
    }
    
    public static void convertCSV2Binary(File csvFile, CSVFormat format,
            File binFile, int[] input, int[] ideal,
            boolean headers)
   {

       binFile.delete();
       ReadCSV csv = new ReadCSV(csvFile.toString(), headers, format);
       
       BufferedMLDataSet buffer = new BufferedMLDataSet(binFile);
       buffer.beginLoad(input.length, ideal.length);
       while(csv.next())
       {
    	   BasicMLData inputData = new BasicMLData(input.length);
    	   BasicMLData idealData = new BasicMLData(ideal.length);
    	   
    	   // handle input data
    	   for(int i=0;i<input.length;i++) {
    		   inputData.setData(i, csv.getDouble(input[i]));
    	   }
    	   
    	   // handle input data
    	   for(int i=0;i<ideal.length;i++) {
    		   idealData.setData(i, csv.getDouble(ideal[i]));
    	   }
    	   
    	   // add to dataset
    	   
           buffer.add(inputData,idealData);
       }
       buffer.endLoad();
   }

	public static double calculateRegressionError(MLRegression method,
			MLDataSet data) {
		
		final ErrorCalculation errorCalculation = new ErrorCalculation();
		if( method instanceof MLContext )
			((MLContext)method).clearContext();

        try {
            for (final MLDataPair pair : data) {
                final MLData actual = method.compute(pair.getInput());
                errorCalculation.updateError(actual.getData(), pair.getIdeal()
                        .getData(), pair.getSignificance());
            }
        } catch(EncogError e) {
            return Double.NaN;
        }
		return errorCalculation.calculate();
	}

	public static void saveCSV(File targetFile, CSVFormat format, MLDataSet set) {
		
		FileWriter outFile = null;
		PrintWriter out = null;
		
		try {
			outFile = new FileWriter(targetFile);
			out = new PrintWriter(outFile);
			
			for(MLDataPair data: set) {
				StringBuilder line = new StringBuilder();
				
				for(int i=0;i<data.getInput().size();i++) {
					double d = data.getInput().getData(i);
					BasicFile.appendSeparator(line, format);
					line.append( format.format(d, Encog.DEFAULT_PRECISION));
				}
				
				for(int i=0;i<data.getIdeal().size();i++) {
					double d = data.getIdeal().getData(i);
					BasicFile.appendSeparator(line, format);
					line.append( format.format(d, Encog.DEFAULT_PRECISION));
				}
								
				out.println(line);
			}					
		} catch(IOException ex) {
			throw new EncogError(ex);
		} finally {
			if( outFile!=null ) {
				try {
					outFile.close();
				} catch (IOException e) {
					EncogLogging.log(e);
				}
			}
			if( out!=null ) {
				out.close();
			}
		}		
	}

	/**
	 * Calculate the classification error.
	 * @param method The method to check.
	 * @param data The data to check.
	 * @return The error.
	 */
	public static double calculateClassificationError(MLClassification method,
			MLDataSet data) {
		int total = 0;
		int correct = 0;
		
		for(MLDataPair pair : data ) {
			int ideal = (int)pair.getIdeal().getData(0);
			int actual = method.classify(pair.getInput());
			if( actual==ideal )
				correct++;
			total++;
		}
		return (double)(total-correct) / (double)total;
	}

	/**
	 * Save a training set to an EGB file.
	 * @param f The file.
	 * @param data The data.
	 */
	public static void saveEGB(File f, MLDataSet data) {
		BufferedMLDataSet binary = new BufferedMLDataSet(f);
		binary.load(data);
		data.close();		
	}

	public static void explainErrorMSE(MLRegression method,
			MatrixMLDataSet training) {
		StringBuilder line = new StringBuilder();
		double sum = 0;
		int count = 0;
		int itemNum = 0;
		
		for (final MLDataPair pair : training) {
			final MLData output = method.compute(pair.getInput());
			double dsum = 0;
			for(int i=0;i<output.size();i++) { 
				double diff = output.getData()[i] - pair.getIdeal().getData(i);
				dsum+=diff*diff;
				count++;
			}
			sum+=dsum;
			
			line.setLength(0);
			line.append("Item #");
			line.append(itemNum++);
			line.append(", Actual=");
			line.append(EncogUtility.formatNeuralData(output));
			line.append(", Ideal=");
			line.append(EncogUtility.formatNeuralData(pair.getIdeal()));
			line.append(", Delta=");
			line.append(Format.formatDouble(dsum, 4));
			line.append(", Count=");
			line.append(count);
			line.append(", MSE=");
			line.append(Format.formatDouble(sum/count, 4));
			System.out.println(line.toString());
		}
	}
	
	public static void explainErrorRMS(MLRegression method,
			MatrixMLDataSet training) {
		StringBuilder line = new StringBuilder();
		double sum = 0;
		int count = 0;
		int itemNum = 0;
		
		for (final MLDataPair pair : training) {
			final MLData output = method.compute(pair.getInput());
			double dsum = 0;
			for(int i=0;i<output.size();i++) { 
				double diff = output.getData()[i] - pair.getIdeal().getData(i);
				dsum+=diff*diff;
				count++;
			}
			sum+=dsum;
			
			line.setLength(0);
			line.append("Item #");
			line.append(itemNum++);
			line.append(", Actual=");
			line.append(EncogUtility.formatNeuralData(output));
			line.append(", Ideal=");
			line.append(EncogUtility.formatNeuralData(pair.getIdeal()));
			line.append(", Delta=");
			line.append(Format.formatDouble(dsum, 4));
			line.append(", Count=");
			line.append(count);
			line.append(", RMS=");
			line.append(Format.formatDouble(Math.sqrt(sum/count), 4));
			System.out.println(line.toString());
		}
	}

	public static FalsePositiveReport calculatePositiveNegative(MLRegression method,
			MatrixMLDataSet data) {
		int truePositive = 0;
		int trueNegative = 0; 
		int negativeCount = 0; 
		int positiveCount = 0;
		
		for(MLDataPair pair : data ) {
			MLData actual = method.compute(pair.getInput());
			boolean actualPositive = actual.getData(0)>actual.getData(1);
			boolean idealPositive = pair.getIdeal().getData(0)>pair.getIdeal().getData(1);
			
			if( idealPositive ) {
				if( actualPositive ) {
					truePositive++;
				}
				positiveCount++;
			} else {
				if( !actualPositive ) { 
					trueNegative++;
				}
				negativeCount++;
			}
			
		}
		return new FalsePositiveReport(truePositive, trueNegative, positiveCount, negativeCount);
	}

	public static MLDataSet[] splitTrainValidate(MLDataSet trainingSet, GenerateRandom rnd, double trainingPercent) {
		if( trainingPercent<0 || trainingPercent>1) {
			throw new EncogError("Training percent must be between 0 and 1.");
		}

		MLDataSet[] result = new MLDataSet[2];
		result[0] = new BasicMLDataSet();
		result[1] = new BasicMLDataSet();

		// initial split
		for(MLDataPair pair: trainingSet ) {
			if( rnd.nextDouble()<trainingPercent ) {
				result[0].add(pair);
			} else {
				result[1].add(pair);
			}
		}

		return result;
	}
}