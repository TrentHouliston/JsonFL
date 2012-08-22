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

import java.util.List;
import java.util.Map;

/**
 * This matcher checks if a value is between two values, in the case of numbers,
 * that it is numerically between the two, and in the case of Strings they are
 * compared alphabetically
 *
 * Example {'foo':{'?between':[10,20]}}
 *
 * Matches {'foo':7}
 *
 * @author Trent Houliston
 * @version 1.0
 */
class BetweenJsonMatcher extends ItemJsonMatcher
{

	/**
	 * This holds the minimum and maximum objects to compare to
	 */
	private final Comparable<Object> min, max;

	/**
	 * This constructor creates a between matcher which will check if a given
	 * object lies between the two vales
	 *
	 * @param between An object with a ?between expression holding an array with
	 *                   two objects
	 *
	 * @throws InvalidJsonQueryException If the passed object is an invalid
	 */
	@SuppressWarnings("unchecked")
	public BetweenJsonMatcher(Map<String, Object> between) throws InvalidJsonFLException
	{
		//Get the between expression
		Object obj = between.get("?between");

		//Check to make sure that this object is a list
		if (obj instanceof List)
		{
			List<Object> list = (List<Object>) obj;

			//Make sure we only have a minimum and maxiumum object
			if (list.size() == 2)
			{
				try
				{
					//Get our minimum and maximum objects
					min = (Comparable<Object>) list.get(0);
					max = (Comparable<Object>) list.get(1);

					if (min.compareTo(max) > 0)
					{
						throw new InvalidJsonFLException("The minimum element was higher then the maximum");
					}
				}
				catch (ClassCastException ex)
				{
					throw new InvalidJsonFLException("Only Strings and Numbers may be used in a between statement");
				}

				//Check they are both the same class
				if (min == null || max == null || min.getClass() != max.getClass())
				{
					throw new InvalidJsonFLException("Both members of a ?between expression must be the same kind");
				}
			}
			else
			{
				//They put in more or less then 2 elements in the array
				throw new InvalidJsonFLException("A ?between statement must only contain 2 elements");
			}
		}
		else
		{
			//They didnt give an array as the parameter
			throw new InvalidJsonFLException("The ?between statement was incorrectly defined, it must be defined in an array");
		}
	}

	/**
	 * This method tests if the passed object is between the two objects that
	 * were done up
	 *
	 * @param target The object to check if it is between these two objects
	 *
	 * @return true if it matches false otherwise
	 */
	@Override
	@SuppressWarnings("unchecked")
	public boolean match(Object target)
	{
		//Ensure that the object to match is of the same class as the matcher
		if (min.getClass().isAssignableFrom(target.getClass()))
		{
			//Work out the position relative to the max and min

			int down = min.compareTo(target);
			int up = max.compareTo(target);

			//Return if this is between the two
			return (up >= 0 && down <= 0);
		}
		else
		{
			//If its not the same class then return false
			return false;
		}

	}
}
