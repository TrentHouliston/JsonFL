package au.com.houliston.jsonfl;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * This method allows you to perform a regular expression on the object passed
 * in and will return true if it matches
 *
 * @author Trent Houliston
 */
class RegexJsonMatcher extends ItemJsonMatcher
{

	/**
	 * This is the regular expression pattern to match
	 */
	private final Pattern regex;

	/**
	 * Construct a new regular expression matcher for the passed Regular
	 * expression
	 *
	 * @param pattern The pattern to use as the regular expression
	 */
	public RegexJsonMatcher(Map<String, Object> pattern)
	{
		//Create a new pattern from this definition
		regex = Pattern.compile((String) pattern.get("?regex"));
	}

	/**
	 * This method checks to see if the passed object matches the regular
	 * expression it will run a toString on the object so numbers will be
	 * formatted as well and tested
	 *
	 * @param target The target object to be tested against this expression
	 *
	 * @return If the regular expression was found in this object
	 */
	@Override
	public boolean match(Object target)
	{
		//TODO what if target is an object or an array?
		return regex.matcher(target.toString()).find();
	}
}
