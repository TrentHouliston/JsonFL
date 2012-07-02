package au.com.houliston.jsonfl;

/**
 * This class is responsible for matching numbers. It will return true
 *
 * @author Trent Houliston
 */
class NumberJsonMatcher extends ItemJsonMatcher
{

	/**
	 * The value to check against
	 */
	private final double value;

	/**
	 * Creates a new matcher for this number
	 *
	 * @param number The number to match against
	 */
	public NumberJsonMatcher(Number number)
	{
		value = number.doubleValue();
	}

	/**
	 * Checks if the passed object is the target number
	 *
	 * @param target The target number to check
	 *
	 * @return if the passed object is this number
	 */
	@Override
	public boolean match(Object target)
	{
		//Check the target is a number
		if (target instanceof Number)
		{
			//Check if the numbers match
			return value == ((Number) target).doubleValue();
		}
		else
		{
			return false;
		}
	}
}
