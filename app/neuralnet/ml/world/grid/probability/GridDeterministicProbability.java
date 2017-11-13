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
package neuralnet.ml.world.grid.probability;

import neuralnet.ml.world.Action;
import neuralnet.ml.world.State;
import neuralnet.ml.world.SuccessorState;
import neuralnet.ml.world.WorldError;
import neuralnet.ml.world.grid.GridState;
import neuralnet.ml.world.grid.GridWorld;

import java.util.Set;
import java.util.TreeSet;

public class GridDeterministicProbability extends GridAbstractProbability {
	
	public GridDeterministicProbability(GridWorld theWorld) {
		super(theWorld);
	}

	@Override
	public double calculate(State resultState, State previousState, Action desiredAction) {
		
		if( !(resultState instanceof GridState) || !(previousState instanceof GridState) ) {
			throw new WorldError("Must be instance of GridState");
		}
		
		GridState gridResultState = (GridState)resultState;
		GridState gridPreviousState = (GridState)previousState;
				
		Action resultingAction =  determineResultingAction(gridPreviousState, gridResultState);
		GridState desiredState = determineActionState(gridPreviousState,desiredAction);
		
		// are we trying to move nowhere
		if( gridResultState==gridPreviousState ) {
			if( GridWorld.isStateBlocked(desiredState) )
				return 1.0;
			else
				return 0.0;
		}
		
		if( resultingAction!=null )
			return 1.0;
		else
			return 0.0;
	}

	@Override
	public Set<SuccessorState> determineSuccessorStates(State state,
			Action action) {
		
		Set<SuccessorState> result = new TreeSet<SuccessorState>();		
		if(action!=null) {
			State newState = determineActionState((GridState)state, action);
			result.add(new SuccessorState(newState,1.0));
		}
		return result;
	}

}
