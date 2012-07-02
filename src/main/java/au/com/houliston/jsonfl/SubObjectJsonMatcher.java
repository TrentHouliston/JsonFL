package au.com.houliston.jsonfl;

import java.util.Map;

/**
 * This object is used to test a sub object (an object within the root object)
 * it behaves as a new implicit root and for this new object. This also contains
 * the special case for checking if a key exists as the empty object will match
 * anything so long as the key exists
 *
 * @author Trent Houliston
 */
class SubObjectJsonMatcher extends ItemJsonMatcher
{

	/**
	 * The root inner matcher to store
	 */
	private final AndJsonMatcher inner;

	/**
	 * Creates a new SubObjectMatcher from the passed definition map
	 * @param target
	 * @throws InvalidJsonQueryException 
	 */
	public SubObjectJsonMatcher(Map<String, Object> target) throws InvalidJsonQueryException
	{
		//Create a new And based on this object
		inner = new AndJsonMatcher(target);
	}

	/**
	 * Checks if the contents of this sub object match
	 * @param target The target to test
	 * @return If the object matched
	 */
	@Override
	@SuppressWarnings("unchecked")
	public boolean match(Object target)
	{
		//Test if this is the special case of an empty object (return true)
		if (inner.isEmpty())
		{
			return true;
		}
		else
		{
			//Otherwise confirm that the subobject passes
			return inner.match((Map<String, Object>) target);
		}
	}
}
