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
 * This class is responsible for matching boolean. It will return true when the
 * boolean which is checked against it is equal to itself
 *
 * Example: {'foo':true}
 *
 * Matches: {'foo':true}
 *
 * @author Trent Houliston
 * @version 1.0
 */
class BooleanJsonMatcher extends ItemJsonMatcher
{

	/**
	 * The value to check against
	 */
	private final boolean value;

	/**
	 * Creates a new matcher for this number
	 *
	 * @param number The number to match against
	 */
	public BooleanJsonMatcher(Boolean bool)
	{
		value = bool;
	}

	/**
	 * Checks if the passed object is equal to the target
	 *
	 * @param target The target boolean to check
	 *
	 * @return if the passed object is the same as this boolean
	 */
	@Override
	public boolean match(Object target)
	{
		//Check the target is a number
		if (target instanceof Boolean)
		{
			//Check if the numbers match
			return value == target;
		}
		else
		{
			return false;
		}
	}
}
