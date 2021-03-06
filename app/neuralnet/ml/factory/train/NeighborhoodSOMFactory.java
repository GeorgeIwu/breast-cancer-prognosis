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
package neuralnet.ml.factory.train;

import neuralnet.EncogError;
import neuralnet.mathutil.rbf.RBFEnum;
import neuralnet.ml.MLMethod;
import neuralnet.ml.data.MLDataSet;
import neuralnet.ml.factory.MLTrainFactory;
import neuralnet.ml.factory.parse.ArchitectureParse;
import neuralnet.ml.train.MLTrain;
import neuralnet.neural.som.SOM;
import neuralnet.neural.som.training.basic.BasicTrainSOM;
import neuralnet.neural.som.training.basic.neighborhood.*;
import neuralnet.util.ParamsHolder;
import neuralnet.util.csv.CSVFormat;
import neuralnet.util.csv.NumberList;

import java.util.Map;

/**
 * Train an SOM network with a neighborhood method.
 */
public class NeighborhoodSOMFactory {

	/**
	 * Create a LMA trainer.
	 * 
	 * @param method
	 *            The method to use.
	 * @param training
	 *            The training data to use.
	 * @param argsStr
	 *            The arguments to use.
	 * @return The newly created trainer.
	 */
	public MLTrain create(final MLMethod method, 
			final MLDataSet training,
			final String argsStr) {

		if (!(method instanceof SOM)) {
			throw new EncogError(
					"Neighborhood training cannot be used on a method of type: "
							+ method.getClass().getName());
		}

		final Map<String, String> args = ArchitectureParse.parseParams(argsStr);
		final ParamsHolder holder = new ParamsHolder(args);

		final double learningRate = holder.getDouble(
				MLTrainFactory.PROPERTY_LEARNING_RATE, false, 0.7);
		final String neighborhoodStr = holder.getString(
				MLTrainFactory.PROPERTY_NEIGHBORHOOD, false, "rbf");
		final String rbfTypeStr = holder.getString(
				MLTrainFactory.PROPERTY_RBF_TYPE, false, "gaussian");

		RBFEnum t;

		if (rbfTypeStr.equalsIgnoreCase("Gaussian")) {
			t = RBFEnum.Gaussian;
		} else if (rbfTypeStr.equalsIgnoreCase("Multiquadric")) {
			t = RBFEnum.Multiquadric;
		} else if (rbfTypeStr.equalsIgnoreCase("InverseMultiquadric")) {
			t = RBFEnum.InverseMultiquadric;
		} else if (rbfTypeStr.equalsIgnoreCase("MexicanHat")) {
			t = RBFEnum.MexicanHat;
		} else {
			t = RBFEnum.Gaussian;
		}

		NeighborhoodFunction nf = null;

		if (neighborhoodStr.equalsIgnoreCase("bubble")) {
			nf = new NeighborhoodBubble(1);
		} else if (neighborhoodStr.equalsIgnoreCase("rbf")) {
			final String str = holder.getString(
					MLTrainFactory.PROPERTY_DIMENSIONS, true, null);
			final int[] size = NumberList.fromListInt(CSVFormat.EG_FORMAT, str);
			nf = new NeighborhoodRBF(size, t);
		} else if (neighborhoodStr.equalsIgnoreCase("rbf1d")) {
			nf = new NeighborhoodRBF1D(t);
		}
		if (neighborhoodStr.equalsIgnoreCase("single")) {
			nf = new NeighborhoodSingle();
		}

		final BasicTrainSOM result = new BasicTrainSOM((SOM) method,
				learningRate, training, nf);

		if (args.containsKey(MLTrainFactory.PROPERTY_ITERATIONS)) {
			final int plannedIterations = holder.getInt(
					MLTrainFactory.PROPERTY_ITERATIONS, false, 1000);
			final double startRate = holder.getDouble(
					MLTrainFactory.PROPERTY_START_LEARNING_RATE, false, 0.05);
			final double endRate = holder.getDouble(
					MLTrainFactory.PROPERTY_END_LEARNING_RATE, false, 0.05);
			final double startRadius = holder.getDouble(
					MLTrainFactory.PROPERTY_START_RADIUS, false, 10);
			final double endRadius = holder.getDouble(
					MLTrainFactory.PROPERTY_END_RADIUS, false, 1);
			result.setAutoDecay(plannedIterations, startRate, endRate,
					startRadius, endRadius);
		}

		return result;
	}
}
