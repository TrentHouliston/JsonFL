package au.com.houliston.jsonfl;

import java.util.Map;

/**
 * This matcher implements a not group, it will match if and only if none of the
 * objects within it match. The contents of the not are an implicit and
 *
 * @author Trent Houliston
 */
class NotJsonMatcher extends GroupJsonMatcher
{

	/**
	 * This is the implicit inner and
	 */
	private final AndJsonMatcher inner;

	/**
	 * This creates a not json matcher, It contains an implicit and of the object matched to its key
	 * @param json The root definition for this not
	 * @throws InvalidJsonQueryException If this or any of the SubObjects are invalid JsonFL
	 */
	public NotJsonMatcher(Map<String, Object> json) throws InvalidJsonQueryException
	{
		inner = new AndJsonMatcher(json);
	}

	/**
	 * Checks if the inner and does not match
	 * 
	 * @param json The definition for this not
	 * @return True if the inner and did not match false otherwise
	 */
	@Override
	public boolean match(Map<String, Object> json)
	{
		return !inner.match(json);
	}
}
