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

import java.util.List;
import java.util.Map;

/**
 * This is the root class for all JsonFL Matcher objects.
 *
 * @author Trent Houliston
 * @version 1.0
 */
abstract class JsonMatcher
{

	/**
	 * This is an explicit undefined object to be passed down when a key does
	 * not exist
	 */
	protected static final Object UNDEFINED = new Object();

	/**
	 * This method is used to match objects of an unknown type. It will work out
	 * whether to pass down to a lower group, or use an item matcher and if so
	 * whether to use undefined
	 *
	 * @param key     The key that this object is attached to
	 * @param matcher The matcher which is to be used against this
	 * @param root    The root of the current level of the object being matched
	 *
	 * @return If the object matched
	 */
	public boolean findMatch(String key, JsonMatcher matcher, Map<String, Object> root)
	{
		//Check if it is a group json matcher
		if (matcher instanceof GroupJsonMatcher)
		{
			//Check if it matches with the root context
			return ((GroupJsonMatcher) matcher).match(root);
		}
		//Check if we have the key
		else if (root.containsKey(key))
		{
			//Match as an item
			return ((ItemJsonMatcher) matcher).match(root.get(key));
		}
		//Otherwise try matching with the undefined object
		else
		{
			return ((ItemJsonMatcher) matcher).match(UNDEFINED);
		}
	}

	/**
	 * This method will return a matcher for an object, It will work out what
	 * kind of matcher to create and then return it
	 *
	 * @param o The object which defines this matcher
	 *
	 * @return A JsonMatcher for the passed object
	 *
	 * @throws InvalidJsonFLException If the passed object is not valid JsonFL
	 */
	@SuppressWarnings("unchecked")
	protected ItemJsonMatcher getMatcher(Object o) throws InvalidJsonFLException
	{
		//If we are matching an explicit null then use the Null matcher
		if (o == null)
		{
			return new NullJsonMatcher();
		}
		//If the subobject is a map then it is either a expression object or a subobject
		else if (o instanceof Map)
		{
			//Get the object in question
			Map<String, Object> var = (Map<String, Object>) o;

			//If it is an in expression, make an in matcher
			if (var.containsKey("?in"))
			{
				return new InJsonMatcher(var);
			}
			//If it is a between expression, make a between matcher
			else if (var.containsKey("?between"))
			{
				return new BetweenJsonMatcher(var);
			}
			//If it is an indexed expression, make an indexed matcher
			else if (var.containsKey("?indexed"))
			{
				return new IndexedArrayJsonMatcher(var);
			}
			//If it is a regex expression, make a regex matcher
			else if (var.containsKey("?regex"))
			{
				return new RegexJsonMatcher(var);
			}
			//If it is a wildcard expression make a wildcard matcher
			else if (var.containsKey("?exists"))
			{
				return new ExistsJsonMatcher(var);
			}
			//Otherwise this is a subobject
			else
			{
				return new SubObjectJsonMatcher(var);
			}
		}
		//If it is a list then the subobject is an array matcher
		else if (o instanceof List)
		{
			return new ArrayJsonMatcher((List<Object>) o);
		}
		//If its a number then its a number matcher
		else if (o instanceof Number)
		{
			return new NumberJsonMatcher((Number) o);
		}
		//If its a string then it is a string matcher
		else if (o instanceof String)
		{
			return new StringJsonMatcher((String) o);
		}
		//Otherwise it must be some other random java object
		else
		{
			throw new InvalidJsonFLException("Bad definition, contains invalid json objects");
		}
	}
}
