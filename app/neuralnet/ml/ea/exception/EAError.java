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
package neuralnet.ml.ea.exception;

import neuralnet.EncogError;

/**
 * A general evolutionary algorithm error.
 */
public class EAError extends EncogError {

	/**
	 * The serial ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Construct the exception.
	 * @param msg The message.
	 */
	public EAError(final String msg) {
		super(msg);
	}

	/**
	 * Construct the exception.
	 * @param msg The message.
	 * @param t A throwable error.
	 */
	public EAError(final String msg, final Throwable t) {
		super(msg, t);
	}

	/**
	 * Construct the exception.
	 * @param t A throwable error.
	 */
	public EAError(final Throwable t) {
		super(t);
	}

}
