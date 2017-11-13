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
package neuralnet.ensemble.aggregator;

import neuralnet.ensemble.EnsembleAggregator;
import neuralnet.ensemble.data.EnsembleDataSet;
import neuralnet.ml.data.MLData;
import neuralnet.ml.data.basic.BasicMLData;

import java.util.ArrayList;

public class Averaging implements EnsembleAggregator {

	@Override
	public MLData evaluate(ArrayList<MLData> outputs) {
		int outputSize = outputs.get(0).size();
		BasicMLData acc = new BasicMLData(outputSize);
		for (MLData out: outputs)
		{
			acc = (BasicMLData) acc.plus(out);
		}

		acc = (BasicMLData) acc.times(1.0 / outputs.size());
		return 	acc;

	}

	@Override
	public String getLabel() {
		return "averaging";
	}

	@Override
	public void train() {
		//This is a no-op in this aggregator
	}

	@Override
	public void setTrainingSet(EnsembleDataSet trainingSet) {
		// This is a no-op in this aggregator.
	}

	@Override
	public boolean needsTraining() {
		return false;
	}

	@Override
	public void setNumberOfMembers(int members) {
		//does nothing
	}
}
