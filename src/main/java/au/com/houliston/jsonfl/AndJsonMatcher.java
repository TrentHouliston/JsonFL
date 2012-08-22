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
import java.util.Map.Entry;

/**
 * This is a group matcher for ands. Every passed json object is an Implicit
 * and. However you can also declare an explicit and using !and
 *
 * Example {'foo':'bar','key':'value'}
 *
 * Matches {'foo':'bar','key':'value'}
 *
 * @author Trent Houliston
 * @version 1.0
 */
class AndJsonMatcher extends GroupJsonMatcher
{

	/**
	 * This holds all of the children of this And
	 */
	private final Map<String, JsonMatcher> children;

	/**
	 * Create a new And matcher based on the passed query object populates its
	 * children with the matchers based on the definition in the objects
	 *
	 * @param query the root object which defines the and matcher, its children
	 *                 must all return true for this object to return true
	 *
	 * @throws InvalidJsonQueryException if the passed object or one of its
	 *                                      children is not a valid definition of
	 *                                      JsonFL
	 */
	public AndJsonMatcher(Map<String, Object> query) throws InvalidJsonFLException
	{
		//Call the parents buildQuery method which will build all the children
		children = buildGroup(query);
	}

	@Override
	public boolean match(Map<String, Object> json)
	{
		//Loop through all of the key/values in this level of the object
		for (Entry<String, JsonMatcher> child : children.entrySet())
		{
			//Check if any of the items failed
			if (!findMatch(child.getKey(), child.getValue(), json))
			{
				return false;
			}
		}

		//If nothing invalidate this stage then return true;
		return true;
	}
}
