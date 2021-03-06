/*
 * Encog(tm) Core v3.2 - Java Version
 * http://www.heatonresearch.com/encog/
 * https://github.com/encog/encog-java-core
 
 * Copyright 2008-2013 Heaton Research, Inc.
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
package neuralnet.ensemble.data.factories;

import neuralnet.ensemble.data.EnsembleDataSet;
import neuralnet.ml.data.MLDataPair;

import java.util.ArrayList;
import java.util.Random;

public class NonResamplingDataSetFactory extends EnsembleDataSetFactory {

	private ArrayList<MLDataPair> elementsLeft = new ArrayList<MLDataPair>();
	
	public NonResamplingDataSetFactory(int dataSetSize) {
		super(dataSetSize);
	}
	
	@Override
	public void reload() {
		elementsLeft.clear();
		for (MLDataPair dp:dataSource) {
			elementsLeft.add(dp);
		}
	}
	
	@Override
	public EnsembleDataSet getNewDataSet() {
		Random generator = new Random();
		EnsembleDataSet ds = new EnsembleDataSet(dataSource.getInputSize(), dataSource.getIdealSize());
		for (int i = 0; i < (int) Math.min(dataSetSize,elementsLeft.size()); i++)
		{
			int candidate = generator.nextInt(elementsLeft.size());
			ds.add(elementsLeft.remove(candidate));
		}
		return ds;
	}
}
