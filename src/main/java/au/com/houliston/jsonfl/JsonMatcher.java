/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.houliston.jsonfl;

import java.util.*;

/**
 *
 * @author trent
 */
public abstract class JsonMatcher
{
	//TODO put the method which builds the objects in here

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
	protected Map<String, JsonMatcher> buildGroup(Map<String, Object> query) throws InvalidJsonQueryException
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
			if (key.equals("?or"))
			{
				//Check to make sure the value is a map
				if (!(value instanceof Map))
				{
					throw new InvalidJsonQueryException("An ?or expression must contain an object as its value");
				}
				else
				{
					//Add the ?or matcher
					result.put(key, new OrJsonMatcher((Map) value));
				}
			}
			//If it is a not expression
			else if (key.equals("?not"))
			{
				//Make sure that it is an instance of a map
				if (!(value instanceof Map))
				{
					throw new InvalidJsonQueryException("A ?not expression must contain an object as its value");
				}
				else
				{
					result.put(key, new NotJsonMatcher((LinkedHashMap) value));
				}
			}
			//If the value is a null then we are matching a explicit null
			else
			{
				result.put(key, getMatcher(value));
			}
		}

		//Return this completed map of sub objects for this array
		return result;
	}

	@SuppressWarnings("unchecked")
	protected JsonMatcher getMatcher(Object o) throws InvalidJsonQueryException
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
			throw new InvalidJsonQueryException("Bad definition, contains invalid json objects");
		}
	}
}
