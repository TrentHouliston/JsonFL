package au.com.houliston.jsonfl;

/**
 * Is thrown when the creation string for the JsonFL is invalid
 *
 * @author Trent Houliston
 */
public class InvalidJsonQueryException extends Exception
{

	/**
	 * Creates a new InvalidJsonQueryException with the passed message
	 * 
	 * @param message The message to set
	 */
	public InvalidJsonQueryException(String message)
	{
		super(message);
	}
	
	public InvalidJsonQueryException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
