/**
 *
 * Copyright (c) 2014, the Railo Company Ltd. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either 
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public 
 * License along with this library.  If not, see <http://www.gnu.org/licenses/>.
 * 
 **/
/**
 * Implements the CFML Function bitxor
 */
package lucee.runtime.functions.math;

import lucee.runtime.PageContext;
import lucee.runtime.exp.PageException;
import lucee.runtime.ext.function.Function;
import lucee.runtime.op.Caster;
import lucee.runtime.op.Decision;

public final class BitXor implements Function {
	private static final long serialVersionUID = -8542953468910888215L;

	public static double call(PageContext pc, double number, double number2) throws PageException {
		if (!Decision.isInteger(number) || !Decision.isInteger(number2)) {
			return Caster.toLongValueLossless(number) ^ Caster.toLongValueLossless(number2);
		}
		return Caster.toIntValueLossless(number) ^ Caster.toIntValueLossless(number2);
	}

}