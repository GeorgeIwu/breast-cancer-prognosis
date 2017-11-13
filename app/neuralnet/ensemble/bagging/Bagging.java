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
package neuralnet.ensemble.bagging;

import neuralnet.ensemble.*;
import neuralnet.ensemble.EnsembleTypes.ProblemType;
import neuralnet.ensemble.data.factories.EnsembleDataSetFactory;
import neuralnet.ensemble.data.factories.ResamplingDataSetFactory;

import java.util.ArrayList;

public class Bagging extends Ensemble {

	private int splits;

	public Bagging(int splits, int dataSetSize, EnsembleMLMethodFactory mlFactory,
			EnsembleTrainFactory trainFactory, EnsembleAggregator aggregator)
	{
		this(splits,dataSetSize,mlFactory,trainFactory,aggregator,new ResamplingDataSetFactory(dataSetSize));
	}
	
	public Bagging(int splits, int dataSetSize, EnsembleMLMethodFactory mlFactory, 
			EnsembleTrainFactory trainFactory, EnsembleAggregator aggregator, EnsembleDataSetFactory edf)
	{
		this.dataSetFactory = edf;
		this.splits = splits;
		this.mlFactory = mlFactory;
		this.trainFactory = trainFactory;
		this.members = new ArrayList<EnsembleML>();
		this.aggregator = aggregator;
		initMembers();
	}
	
	@Override
	public void initMembers()
	{
		this.initMembersBySplits(this.splits);
	}

	@Override
	public ProblemType getProblemType() {
		return EnsembleTypes.ProblemType.CLASSIFICATION;
	}

	@Override
	public EnsembleML getMember(int memberNumber) {
		return members.get(memberNumber);
	}

	public void trainStep() {
		for (EnsembleML current : members)
		{
			current.trainStep();
		}
	}


}
