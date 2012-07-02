package au.com.houliston.jsonfl;

import java.util.Map;

/**
 * This abstract class is use as the base for all matchers which are a container
 * on a group of sub matchers
 *
 * @author Trent Houliston
 */
abstract class GroupJsonMatcher extends JsonMatcher
{

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