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
 * This matcher will check if any of the objects in its group match. If a single
 * one of them returns true then the entire set will be true
 *
 * Example: {'!or':{'foo':'bar','key':'value'}}
 *
 * Matches: {'foo':'bar'}
 *
 * Matches: {'key':'value'}
 *
 * @author Trent Houliston
 * @version 1.0
 */
class OrJsonMatcher extends GroupJsonMatcher
{

	/**
	 * This map holds the children of the matcher
	 */
	private final Map<String, JsonMatcher> children;

	/**
	 * This constructs a group of matchers from the passed definition
	 *
	 * @param query The query defintion to base the matcher on
	 *
	 * @throws InvalidJsonQueryException If this or any of the SubObjects has an
	 *                                      invalid definition
	 */
	public OrJsonMatcher(Map<String, Object> query) throws InvalidJsonFLException
	{
		//Build the childrens matchers
		children = buildGroup(query);
	}

	/**
	 * This method checks if any of its children conditions match, if they do
	 * then this entire section will match
	 *
	 * @param json
	 *
	 * @return
	 */
	@Override
	public boolean match(Map<String, Object> json)
	{
		//Loop through all of our conditions
		for (Map.Entry<String, JsonMatcher> child : children.entrySet())
		{
			//See if any of our conditions matched
			if (findMatch(child.getKey(), child.getValue(), json))
			{
				return true;
			}
		}

		//Nothing matched, return false
		return false;
	}
}
