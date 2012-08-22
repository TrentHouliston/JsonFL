/**
 * This file is part of JsonFL.
 *
 * JsonFL is free software: you can redistribute it and/or modify it under the
 * terms of the Lesser GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * JsonFL is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the Lesser GNU General Public License for more
 * details.
 *
 * You should have received a copy of the Lesser GNU General Public License
 * along with JsonFL. If not, see <http://www.gnu.org/licenses/>.
 */
package au.com.houliston.jsonfl;

/**
 * Is thrown when the creation string for the JsonFL is invalid
 *
 * @author Trent Houliston
 * @version 1.0
 */
public class InvalidJsonFLException extends Exception
{

	/**
	 * Creates a new InvalidJsonFLException with the passed message
	 *
	 * @param message The message to set
	 */
	public InvalidJsonFLException(String message)
	{
		super(message);
	}

	/**
	 * Creates a new InvalidJsonFLException along with a message and a cause
	 *
	 * @param message The message to set
	 * @param cause   The root cause which made this exception
	 */
	public InvalidJsonFLException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
