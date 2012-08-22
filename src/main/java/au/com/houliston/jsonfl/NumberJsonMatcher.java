/**
 * This file is part of JsonFL.
 *
 * JsonFL is free software: you can redistribute it and/or modify it under the
 * terms of the Lesser GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * JsonFL is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the Lesser GNU General Public License for more
 * details.
 *
 * You should have received a copy of the Lesser GNU General Public License
 * along with JsonFL. If not, see <http://www.gnu.org/licenses/>.
 */
package au.com.houliston.jsonfl;

/**
 * This class is responsible for matching numbers. It will return true when the
 * number which is checked against it is equal
 *
 * Example: {'foo':10}
 *
 * Matches: {'foo':10}
 *
 * Matches: {'foo':10.0}
 *
 * @author Trent Houliston
 * @version 1.0
 */
class NumberJsonMatcher extends ItemJsonMatcher
{

	/**
	 * The value to check against
	 */
	private final double value;

	/**
	 * Creates a new matcher for this number
	 *
	 * @param number The number to match against
	 */
	public NumberJsonMatcher(Number number)
	{
		value = number.doubleValue();
	}

	/**
	 * Checks if the passed object is the target number
	 *
	 * @param target The target number to check
	 *
	 * @return if the passed object is this number
	 */
	@Override
	public boolean match(Object target)
	{
		//Check the target is a number
		if (target instanceof Number)
		{
			//Check if the numbers match
			return value == ((Number) target).doubleValue();
		}
		else
		{
			return false;
		}
	}
}
