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
 * This class is used to check a String passed in matches this string
 *
 * Example: {'foo':'bar'}
 *
 * Matches: {'foo':'bar'}
 *
 * @author Trent Houliston
 * @version 1.0
 */
class StringJsonMatcher extends ItemJsonMatcher
{

	/**
	 * The string to test against
	 */
	private final String value;

	/**
	 * Creates a new String matcher which will check if the value of this string
	 * works
	 *
	 * @param value The string to store as the test string
	 */
	public StringJsonMatcher(String value)
	{
		this.value = value;
	}

	/**
	 * Checks if the passed String is the same as the stored string
	 *
	 * @param target The String to test
	 *
	 * @return True if the strings are the same
	 */
	@Override
	public boolean match(Object target)
	{
		return value.equals(target);
	}
}
