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

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * This is used for checking an array to a list of indexes and values. It will
 * check a set of indexes match the predefined matchers
 *
 * Example: {'foo':{'?indexed':{'0':'bar','2':'value'}}}
 *
 * Matches: {'foo':['bar','with','value']}
 *
 * @author Trent Houliston
 * @version 1.0
 */
class IndexedArrayJsonMatcher extends ItemJsonMatcher
{

	/**
	 * This object holds the children indexes and objects that they should match
	 * to.
	 */
	private final Map<Integer, ItemJsonMatcher> children;

	/**
	 * Builds an IndexedArrayMatcher, this matches against an array against a
	 * set of indexed matchers
	 *
	 * @param map A map containing an ?indexed field which contains an object
	 *               mapping of integers to matchers
	 *
	 * @throws InvalidJsonQueryException if the passed object is not a valid
	 *                                      JsonFL expression
	 */
	@SuppressWarnings("unchecked")
	public IndexedArrayJsonMatcher(Map<String, Object> map) throws InvalidJsonFLException
	{
		//Get the ?indexed field
		Object obj = map.get("?indexed");

		if (obj instanceof Map)
		{
			//Parse the object into a map of filtering objects
			children = buildIndexedMatcher((Map<String, Object>) obj);

			if (children.isEmpty())
			{
				throw new InvalidJsonFLException("Having an empty IndexedArray makes no sense");
			}
		}
		else
		{
			//The indexed expression was not an object
			throw new InvalidJsonFLException("?indexed expressions must be an object");
		}
	}

	/**
	 * Checks if the passed object matches this matcher object
	 *
	 * @param target An array to test against this matcher
	 *
	 * @return True if it matches, false otherwise
	 */
	@Override
	@SuppressWarnings("unchecked")
	public boolean match(Object target)
	{
		//Check if this is a list
		if (target instanceof List)
		{
			List<Object> t = (List<Object>) target;

			//Loop through all our requirements (our matching set)
			for (Entry<Integer, ItemJsonMatcher> match : children.entrySet())
			{
				try
				{
					//Get the two objects to compare
					Object them = t.get(match.getKey());
					ItemJsonMatcher us = match.getValue();

					//Try to match the object
					if (!us.match(them))
					{
						return false;
					}
				}
				catch (IndexOutOfBoundsException ex)
				{
					return false;
				}
			}

		}
		else
		{
			//This was not a list
			return false;
		}

		return true;
	}

	/**
	 * This creates a matcher to use for this, it parses all the objects into
	 * maps of integers to matchers
	 *
	 * @param map The definition for this indexed matcher
	 *
	 * @return A map of integer indexes to matchers which can be used to match
	 *            particular indexes
	 *
	 * @throws InvalidJsonQueryException If the JsonFL statement was invalid
	 */
	@SuppressWarnings("unchecked")
	private Map<Integer, ItemJsonMatcher> buildIndexedMatcher(Map<String, Object> map) throws InvalidJsonFLException
	{
		LinkedHashMap<Integer, ItemJsonMatcher> result = new LinkedHashMap<Integer, ItemJsonMatcher>();

		//Get all of the index/matchers
		Set<Entry<String, Object>> set = map.entrySet();

		//Loop though all of the index/matchers
		for (Entry<String, Object> ob : set)
		{
			//Create matchers from the objects
			result.put(Integer.parseInt(ob.getKey()), getMatcher(ob.getValue()));
		}

		//Return the completed array
		return result;
	}
}
