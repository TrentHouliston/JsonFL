package au.com.houliston.jsonfl;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * This checks if the passed object is in the list that was used to to create
 * this this. It uses a HashSet which allows a constant time contains method
 *
 * @author Trent Houliston
 */
class InJsonMatcher extends ItemJsonMatcher
{

	/**
	 * The object which holds all of the possibilities that the passed object
	 * can be
	 */
	private final HashSet<Object> set;

	/**
	 * This creates an in expression. It will check if the passed object is in
	 * the set of objects
	 *
	 * @param in The definition containing the options that this can be in
	 *
	 * @throws InvalidJsonQueryException if the passed object was not a valid
	 *                                      query
	 */
	@SuppressWarnings("unchecked")
	public InJsonMatcher(Map<String, Object> in) throws InvalidJsonQueryException
	{
		//Get the in object
		Object o = in.get("?in");

		//Create the set
		set = new HashSet<Object>();

		//Check it is a list
		if (o instanceof List)
		{
			//Add all the elements to the list
			set.addAll((List) o);
		}
		else
		{
			//It was not an array
			throw new InvalidJsonQueryException("The Paramter for an ?in must be an array");
		}
	}

	/**
	 * Checks if the passed object is in the HashSet that was setup during
	 * instantiation
	 *
	 * @param target The object to check if it is in the set
	 *
	 * @return True if it is in the set, false otherwise
	 */
	@Override
	public boolean match(Object target)
	{
		return set.contains(target);
	}
}
