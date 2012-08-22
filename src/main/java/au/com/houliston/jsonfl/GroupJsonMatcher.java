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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This abstract class is use as the base for all matchers which are a container
 * on a group of sub matchers
 *
 * @author Trent Houliston
 * @version 1.0
 */
abstract class GroupJsonMatcher extends JsonMatcher
{

	/**
	 * This method is used by all group matchers to build its sub objects, the
	 * Group Matchers will handle returning true or false based on which of
	 * these match
	 *
	 * @param query The json object which is to be used as the definition for
	 *                 the submatchers
	 *
	 * @return A map of the matchers
	 *
	 * @throws InvalidJsonQueryException If this object or any of the sub
	 *                                      objects it is created throw an
	 *                                      exception on creation
	 */
	@SuppressWarnings("unchecked")
	protected Map<String, JsonMatcher> buildGroup(Map<String, Object> query) throws InvalidJsonFLException
	{
		//The storage for the results of this
		HashMap<String, JsonMatcher> result = new HashMap<String, JsonMatcher>();

		//Loop through the map we are given
		for (Map.Entry<String, Object> en : query.entrySet())
		{
			//Store the key and values
			String key = en.getKey();
			Object value = en.getValue();
			//If the subobject is an or expression then create an or matcher
			if (key.equals("!and"))
			{
				//Check to make sure the value is a map
				if (value instanceof Map)
				{
					//Add the !and matcher
					result.put(key, new AndJsonMatcher((Map<String, Object>) value));
				}
				//If it is a list then there are several of this kind of group at this level
				else if (value instanceof List)
				{
					//Loop through the list
					List<Object> l = (List<Object>) value;
					for (Object o : l)
					{
						//Ensure that every item is an object
						if (o instanceof Map)
						{
							//Use the hashcode to ensure that the key is unique
							AndJsonMatcher m = new AndJsonMatcher((Map<String, Object>) o);
							result.put(key + m.hashCode(), m);
						}
						else
						{
							throw new InvalidJsonFLException("Every element of an !and expression array must be an object");
						}
					}
				}
				else
				{
					throw new InvalidJsonFLException("An !and expression must contain an object or array as its value");
				}
			}
			//If the subobject is an or expression then create an or matcher
			else if (key.equals("!or"))
			{
				//Check to make sure the value is a map
				if (value instanceof Map)
				{
					//Add the !or matcher
					result.put(key, new OrJsonMatcher((Map<String, Object>) value));
				}
				//If it is a list then there are several of this kind of group at this level
				else if (value instanceof List)
				{
					//Loop through the list
					List<Object> l = (List<Object>) value;
					for (Object o : l)
					{
						//Ensure that every item is an object
						if (o instanceof Map)
						{
							//Use the hashcode to ensure that the key is unique
							OrJsonMatcher m = new OrJsonMatcher((Map<String, Object>) o);
							result.put(key + m.hashCode(), m);
						}
						else
						{
							throw new InvalidJsonFLException("Every element of an !or expression array must be an object");
						}
					}
				}
				else
				{
					throw new InvalidJsonFLException("An !or expression must contain an object or array as its value");
				}
			}
			//If it is a not expression
			else if (key.equals("!not"))
			{
				//Make sure that it is an instance of a map
				if (!(value instanceof Map))
				{
					throw new InvalidJsonFLException("A !not expression must contain an object or array as its value");
				}
				//If it is a list then there are several of this kind of group at this level
				else if (value instanceof List)
				{
					//Loop through the list
					List<Object> l = (List<Object>) value;
					for (Object o : l)
					{
						//Ensure that every item is an object
						if (o instanceof Map)
						{
							//Use the hashcode to ensure that the key is unique
							NotJsonMatcher m = new NotJsonMatcher((Map<String, Object>) o);
							result.put(key + m.hashCode(), m);
						}
						else
						{
							throw new InvalidJsonFLException("Every element of a !not expression array must be an object");
						}
					}
				}
				else
				{
					result.put(key, new NotJsonMatcher((Map<String, Object>) value));
				}
			}
			//If there is a double ? then this is an escaped expression, ignore the first ?
			else if (key.startsWith("??") || key.startsWith("!!"))
			{
				result.put(key.substring(1), getMatcher(value));
			}
			//Otherwise we are just a normal non group matcher
			else
			{
				result.put(key, getMatcher(value));
			}
		}

		//Return this completed map of sub objects for this array
		return result;
	}

	/**
	 * This method is used to test if the object matches, it must return true if
	 * the group matches and false otherwise
	 *
	 * @param json The json root object for the level to be tested
	 *
	 * @return If the group of submatchers match
	 */
	public abstract boolean match(Map<String, Object> json);
}