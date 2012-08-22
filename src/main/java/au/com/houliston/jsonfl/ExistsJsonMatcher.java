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

import java.util.Map;

/**
 * The purpose of this class is simply to check that a particular key exists. It
 * will return true if the requirement for existence is met (whether true of
 * false)
 *
 * Example {'foo':{'?exists':true}}
 *
 * Matches {'foo':'bar'}
 *
 * Matches {'foo':null'}
 *
 * @author Trent Houliston
 * @version 1.0
 */
public class ExistsJsonMatcher extends ItemJsonMatcher
{

	private final boolean exists;

	/**
	 * Used to check for key existence only
	 *
	 * @param ob
	 *
	 * @throws InvalidJsonFLException
	 */
	public ExistsJsonMatcher(Map<String, Object> ob) throws InvalidJsonFLException
	{

		if (ob.get("?exists") instanceof Boolean)
		{
			exists = (Boolean) ob.get("?exists");
		}
		else
		{
			throw new InvalidJsonFLException("An exists expression can only check if an object does exist");
		}
	}

	/**
	 * Will return a boolean stating if the key matched for existence or non
	 * existence
	 *
	 * @param target The target to be matched
	 *
	 * @return True if the key exists
	 */
	@Override
	public boolean match(Object target)
	{
		//Check if we were passed the undefined object
		if (target == UNDEFINED)
		{
			return !exists;
		}
		else
		{
			return exists;
		}
	}
}
