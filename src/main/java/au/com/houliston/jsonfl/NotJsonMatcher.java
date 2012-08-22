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

/**
 * This matcher implements a not group, it will match if and only if none of the
 * objects within it match. The contents of the not are an implicit and
 *
 * Example: {'!not':{'foo':'bar'}}
 *
 * Matches: {'key':'value'}
 *
 * @author Trent Houliston
 * @version 1.0
 */
class NotJsonMatcher extends GroupJsonMatcher
{

	/**
	 * This is the implicit inner and
	 */
	private final AndJsonMatcher inner;

	/**
	 * This creates a not Json matcher, It contains an implicit and of the
	 * object matched to its key
	 *
	 * @param json The root definition for this not
	 *
	 * @throws InvalidJsonQueryException If this or any of the SubObjects are
	 *                                      invalid JsonFL
	 */
	public NotJsonMatcher(Map<String, Object> json) throws InvalidJsonFLException
	{
		inner = new AndJsonMatcher(json);
	}

	/**
	 * Checks if the inner and does not match
	 *
	 * @param json The definition for this not
	 *
	 * @return True if the inner and did not match false otherwise
	 */
	@Override
	public boolean match(Map<String, Object> json)
	{
		return !inner.match(json);
	}
}
