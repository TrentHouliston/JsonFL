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
 * This Class implements the Javascript Object Notation Filtering Language It
 * will receive a Map describing the query and from this it will build a series
 * of tree based matcher objects. These will be used to match any other Map
 * which is put into the system
 *
 * @author Trent Houliston
 * @version 1.0
 */
public class JsonFL
{
	//TODO re read all comments
	//TODO write the standard in the readme file

	/**
	 * The root matcher which is an implicit and
	 */
	private final AndJsonMatcher root;

	/**
	 * This creates a new JsonFL object which will implement the passed query.
	 * This object can then be tested against any map passed in to test if it
	 * matches
	 *
	 * @param query The query to build
	 *
	 * @throws InvalidJsonQueryException if the passed JsonFL expression is not
	 *                                      valid
	 */
	public JsonFL(Map<String, Object> query) throws InvalidJsonFLException
	{
		root = new AndJsonMatcher(query);
	}

	/**
	 * Tests if the passed map matches the query that built this matcher
	 *
	 * @param json The data to test
	 *
	 * @return if the passed data matched this matcher
	 */
	public boolean matches(Map<String, Object> json)
	{
		try
		{
			//Try to match
			return root.match(json);
		}
		catch (Exception ex)
		{
			//If there was an exception assume that it didnt match
			return false;
		}
	}
}
