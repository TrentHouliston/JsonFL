package au.com.houliston.jsonfl;

import java.util.Map;
import java.util.Map.Entry;

/**
 * This is a group matcher for ands. Every passed json object is an Implicit and
 *
 * @author Trent Houliston
 * @version 1.0
 */
class AndJsonMatcher extends GroupJsonMatcher
{

	/**
	 * This holds all of the children of this And TODO
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
	public AndJsonMatcher(Map<String, Object> query) throws InvalidJsonQueryException
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
			//If this child of the And is another Group matcher, pass it the root
			//node so that it is able to loop through
			if (child.getValue() instanceof GroupJsonMatcher)
			{
				GroupJsonMatcher gMatch = (GroupJsonMatcher) child.getValue();
				//If this group matcher invalidates it return false
				if (!gMatch.match(json))
				{
					return false;
				}
			}
			//If the object passed does not have the key that we are looking for
			//then it does not match
			else if (!json.containsKey(child.getKey()))
			{
				return false;
			}
			//Otherwise pass the value to the Item matcher to see if it matches
			else if (!((ItemJsonMatcher) child.getValue()).match(json.get(child.getKey())))
			{
				return false;
			}
		}

		//If nothing invalidate this stage then return true;
		return true;
	}

	/**
	 * Checks if this object has no children, Is used by the SubObject matcher
	 * so that if an empty object is passed, it always returns true by
	 * definition (but checks for the existence of a Sub object)
	 *
	 * @return true if this and matcher has no children, false otherwise
	 */
	public boolean isEmpty()
	{
		return children.isEmpty();
	}
}
