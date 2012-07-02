package au.com.houliston.jsonfl;

import java.util.Map;

/**
 * This matcher will check if any of the objects in its group match. If a single
 * one of them returns true then the entire set will be true
 *
 * @author Trent Houliston
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
	public OrJsonMatcher(Map<String, Object> query) throws InvalidJsonQueryException
	{
		//Build the childrens matchers
		children = buildGroup(query);
	}

	/**
	 * This method checks if any of its children conditions match, if they
	 * do then this entire section will match
	 * 
	 * @param json
	 * @return 
	 */
	@Override
	public boolean match(Map<String, Object> json)
	{
		//Loop through all of our conditions
		for (Map.Entry<String, JsonMatcher> child : children.entrySet())
		{
			//Check if this is a group matcher
			if (child.getValue() instanceof GroupJsonMatcher)
			{
				//See if this matches our root node
				if (((GroupJsonMatcher) child.getValue()).match(json))
				{
					return true;
				}
			}
			//Check if the other object has this key
			else if (json.containsKey(child.getKey()))
			{
				//Check if it matches
				if (((ItemJsonMatcher) child.getValue()).match(child.getValue()))
				{
					return true;
				}
			}
		}

		//Nothing matched, return false
		return false;
	}
}
