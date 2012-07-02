package au.com.houliston.jsonfl;

/**
 * This Object is used to define an Item Matcher, these are matchers which match
 * on the object themselves rather then processing a group of objects
 *
 * @author Trent Houliston
 */
abstract class ItemJsonMatcher extends JsonMatcher
{

	/**
	 * This method is used to match the passed object with the definition found
	 * in this ItemJsonMatcher
	 *
	 * @param target The target object to match to
	 *
	 * @return If the object matched
	 */
	public abstract boolean match(Object target);
}
