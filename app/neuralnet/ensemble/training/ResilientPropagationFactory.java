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

package neuralnet.ensemble.training;

import neuralnet.ensemble.EnsembleTrainFactory;
import neuralnet.ml.MLMethod;
import neuralnet.ml.data.MLDataSet;
import neuralnet.ml.train.MLTrain;
import neuralnet.neural.networks.BasicNetwork;
import neuralnet.neural.networks.training.propagation.resilient.RPROPType;
import neuralnet.neural.networks.training.propagation.resilient.ResilientPropagation;

public class ResilientPropagationFactory implements EnsembleTrainFactory {

	private double dropoutRate = 0;
	private RPROPType type = RPROPType.RPROPp;
	
	@Override
	public MLTrain getTraining(MLMethod mlMethod, MLDataSet trainingData) {
		ResilientPropagation rp = new ResilientPropagation((BasicNetwork) mlMethod, trainingData);
		rp.setRPROPType(this.type);
		rp.setDroupoutRate(this.dropoutRate);
		return (MLTrain) rp;
	}
	
	@Override
	public MLTrain getTraining(MLMethod mlMethod, MLDataSet trainingData, double dropoutRate) {
		ResilientPropagation rp = new ResilientPropagation((BasicNetwork) mlMethod, trainingData);
		rp.setRPROPType(this.type);
		rp.setDroupoutRate(dropoutRate);
		return (MLTrain) rp;
	}
	@Override
	public String getLabel() {
		String l = "resprop";
		if(dropoutRate > 0)
		{
			l += "-" + dropoutRate;
		}
		return l;
	}

	@Override
	public void setDropoutRate(double rate) {
		dropoutRate = rate;
	}

	public RPROPType getRPROPType() {
		return type;
	}

	public void setRPROPType(RPROPType type) {
		this.type = type;
	}

}
