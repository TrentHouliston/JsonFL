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
 * This matcher checks for an explicit null in the Json, it will return true if
 * the Json was set to null, not to undefined
 *
 * Example: {'foo':null}
 *
 * Matches: {'foo':null}
 *
 * @author Trent Houliston
 * @version 1.0
 */
class NullJsonMatcher extends ItemJsonMatcher
{

	/**
	 * Empty constructor as this can only be a null
	 */
	public NullJsonMatcher()
	{
	}

	/**
	 * Checks if the object is a null
	 *
	 * @param target The object to test against
	 *
	 * @return True if this object is null false otherwise
	 */
	@Override
	public boolean match(Object target)
	{
		return target == null;
	}
}
