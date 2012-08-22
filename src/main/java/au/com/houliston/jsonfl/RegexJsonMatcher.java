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

import java.util.Map;
import java.util.regex.Pattern;

/**
 * This matcher allows you to perform a regular expression on the object passed
 * in and will return true if it matches. This is used to perform a regular
 * expression on any String or Number (which will be converted to its decimal
 * representation before being matched)
 *
 * Example: {'foo':{'?regex':'^b?r$'}}
 *
 * Matches: {'foo':'bar'}
 *
 * @author Trent Houliston
 * @version 1.0
 */
class RegexJsonMatcher extends ItemJsonMatcher
{

	/**
	 * This is the regular expression pattern to match
	 */
	private final Pattern regex;

	/**
	 * Construct a new regular expression matcher for the passed Regular
	 * expression
	 *
	 * @param pattern The pattern to use as the regular expression
	 *
	 * @throws InvalidJsonQueryException if the expression is invalid
	 */
	public RegexJsonMatcher(Map<String, Object> pattern) throws InvalidJsonFLException
	{
		try
		{
			//Create a new pattern from this definition
			regex = Pattern.compile((String) pattern.get("?regex"));
		}
		catch (Exception ex)
		{
			throw new InvalidJsonFLException("The passed regular expression was invalid", ex);
		}
	}

	/**
	 * This method checks to see if the passed object matches the regular
	 * expression it will run a toString on the object so numbers will be
	 * formatted as well and tested
	 *
	 * @param target The target object to be tested against this expression
	 *
	 * @return If the regular expression was found in this object
	 */
	@Override
	public boolean match(Object target)
	{
		//Test if the target is not a string or number
		if (target instanceof String || target instanceof Number)
		{
			return regex.matcher(target.toString()).find();
		}
		else
		{
			return false;
		}
	}
}
