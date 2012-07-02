package au.com.houliston.jsonfl;

/**
 * This matcher checks for an explicit null in the Json, it will return true if
 * the Json was set to null, not to undefined
 *
 * @author Trent Houliston
 */
class NullJsonMatcher extends ItemJsonMatcher
{

	/**
	 * Empty constructor as this can only be a null
	 */
	public NullJsonMatcher()
	{
	}

	/**
	 * Checks if the object is a null
	 *
	 * @param target The object to test against
	 *
	 * @return True if this object is null false otherwise
	 */
	@Override
	public boolean match(Object target)
	{
		return target == null;
	}
}
