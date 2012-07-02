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
 * @author Trent Houliston
 */
class IndexedArrayJsonMatcher extends ItemJsonMatcher
{

	/**
	 * This object holds the children indexes and objects that they should match
	 * to.
	 */
	private final Map<Integer, JsonMatcher> children;

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
	public IndexedArrayJsonMatcher(Map<String, Object> map) throws InvalidJsonQueryException
	{
		//Get the ?indexed field
		Object obj = map.get("?indexed");

		if (obj instanceof Map)
		{
			//Parse the object into a map of filtering objects
			children = buildIndexedMatcher((Map<String, Object>) obj);
		}
		else
		{
			//The indexed expression was not an object
			throw new InvalidJsonQueryException("?indexed expressions must be an object");
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
		if (!(target instanceof List))
		{
			List<Object> t = (List<Object>) target;

			//Loop through all our requirements (our matching set)
			for (Entry<Integer, JsonMatcher> match : children.entrySet())
			{
				try
				{
					//Get the two objects to compare
					Object them = t.get(match.getKey());
					JsonMatcher us = match.getValue();

					//Check if we are a group matcher
					if (us instanceof GroupJsonMatcher)
					{
						//Pass the root of this through to be matched
						if (!((GroupJsonMatcher) us).match((LinkedHashMap<String, Object>) them))
						{
							return false;
						}
					}
					//Otherwise it is an item matcher
					else
					{
						//Try to match the object
						if (!((ItemJsonMatcher) us).match(them))
						{
							return false;
						}
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
	private Map<Integer, JsonMatcher> buildIndexedMatcher(Map<String, Object> map) throws InvalidJsonQueryException
	{
		LinkedHashMap<Integer, JsonMatcher> result = new LinkedHashMap<Integer, JsonMatcher>();

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
