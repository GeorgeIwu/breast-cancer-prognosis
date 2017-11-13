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

import neuralnet.ml.CalculateScore;
import neuralnet.ml.MLMethod;
import neuralnet.ml.data.MLDataSet;
import neuralnet.ml.ea.score.adjust.ComplexityAdjustedScore;
import neuralnet.ml.ea.train.basic.TrainEA;
import neuralnet.ml.prg.PrgCODEC;
import neuralnet.ml.prg.opp.ConstMutation;
import neuralnet.ml.prg.opp.SubtreeCrossover;
import neuralnet.ml.prg.opp.SubtreeMutation;
import neuralnet.ml.prg.species.PrgSpeciation;
import neuralnet.ml.prg.train.PrgPopulation;
import neuralnet.ml.prg.train.rewrite.RewriteAlgebraic;
import neuralnet.ml.prg.train.rewrite.RewriteConstants;
import neuralnet.ml.train.MLTrain;
import neuralnet.neural.networks.training.TrainingSetScore;

public class EPLGAFactory {
	/**
	 * Create an EPL GA trainer.
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
			final MLDataSet training, final String argsStr) {
		
		PrgPopulation pop = (PrgPopulation)method;
		
		final CalculateScore score = new TrainingSetScore(training);		
		TrainEA train = new TrainEA(pop, score);
		pop.getRules().addRewriteRule(new RewriteConstants());
		pop.getRules().addRewriteRule(new RewriteAlgebraic());
		train.setCODEC(new PrgCODEC());
		train.addOperation(0.8, new SubtreeCrossover());
		train.addOperation(0.1, new SubtreeMutation(pop.getContext(),4));
		train.addOperation(0.1, new ConstMutation(pop.getContext(),0.5,1.0));
		train.addScoreAdjuster(new ComplexityAdjustedScore());
		train.setSpeciation(new PrgSpeciation());
		return train;
	}
}
