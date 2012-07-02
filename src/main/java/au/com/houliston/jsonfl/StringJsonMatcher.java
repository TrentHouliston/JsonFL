package au.com.houliston.jsonfl;

/**
 * This class is used to check a String passed in matches this string
 *
 * @author Trent Houliston
 */
class StringJsonMatcher extends ItemJsonMatcher
{

	/**
	 * The string to test against
	 */
	private final String value;

	/**
	 * Creates a new String matcher which will check if the value of this string
	 * works
	 *
	 * @param value The string to store as the test string
	 */
	public StringJsonMatcher(String value)
	{
		this.value = value;
	}

	/**
	 * Checks if the passed String is the same as the stored string
	 *
	 * @param target The String to test
	 *
	 * @return True if the strings are the same
	 */
	@Override
	public boolean match(Object target)
	{
		return value.equals(target);
	}
}
