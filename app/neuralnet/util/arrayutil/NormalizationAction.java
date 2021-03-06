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
package neuralnet.util.arrayutil;

/**
 * Normalization actions desired.
 */
public enum NormalizationAction {
	/**
	 * Do not normalize the column, just allow it to pass through. This allows
	 * string fields to pass through as well.
	 */
	PassThrough,

	/**
	 * Normalize this column.
	 */
	Normalize,

	/**
	 * Ignore this column, do not include in the output.
	 */
	Ignore,

	/**
	 * Use the "one-of" classification method.
	 */
	OneOf,

	/**
	 * Use the equilateral classification method.
	 */
	Equilateral,

	/**
	 * Use a single-field classification method.
	 */
	SingleField;

	/**
	 * @return True, if this is a classify.
	 */
	public boolean isClassify() {
		return (this == OneOf) || (this == SingleField)
				|| (this == Equilateral);
	}

}
