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

import java.util.*;

/**
 * This is for matching arrays. It will match if and only if the number of
 * elements in the definition and the passed object are the same, and that all
 * the elements in the array match
 *
 * Example {'foo':[1,2,3]}
 *
 * Matches {'foo':[1,2,3]}
 *
 * @author Trent Houliston
 * @version 1.0
 */
class ArrayJsonMatcher extends ItemJsonMatcher
{

	/**
	 * This object holds the array children of this Array matcher
	 */
	private final List<ItemJsonMatcher> children;

	/**
	 * Constructor for the matcher, It processes the children into a set of
	 * JsonMatchers for use
	 *
	 * @param list the definition for this array
	 *
	 * @throws InvalidJsonQueryException if the definition of this object or one
	 *                                      of its sub objects is invalid
	 */
	ArrayJsonMatcher(List<Object> list) throws InvalidJsonFLException
	{
		//Process this array into an array of JsonMatchers
		children = buildArrayMatcher(list);
	}

	/**
	 * This matcher makes sure that for the passed object, every one of the keys
	 * defined in this and match a value in the passed object
	 *
	 * @param target The root of the object level to compare to
	 *
	 * @return If all of the sub objects declared in this and match
	 */
	@Override
	@SuppressWarnings("unchecked")
	public boolean match(Object target)
	{
		//If the object we are passed for this key is not an array then it does not match
		if (!(target instanceof List))
		{
			return false;
		}
		else
		{
			List t = (List) target;

			//Check if we have the same number of children, if not then this does not match
			if (children.size() != t.size())
			{
				return false;
			}
			else
			{
				//Loop through all of the array elements
				for (int i = 0; i < children.size(); i++)
				{
					//Get the objects to compare
					Object them = t.get(i);
					ItemJsonMatcher us = children.get(i);

					//Check if the matcher matches
					if (!us.match(them))
					{
						return false;
					}
				}
			}
		}

		//If none of our checks failed, then return true
		return true;
	}

	/**
	 * This creates a matcher to use for this, it parses all the objects an
	 * array of matchers to use
	 *
	 * @param list The definition for this array
	 *
	 * @return A map of integer indexes to matchers which can be used to match
	 *            particular indexes
	 *
	 * @throws InvalidJsonQueryException If the JsonFL statement was invalid
	 */
	@SuppressWarnings("unchecked")
	private List<ItemJsonMatcher> buildArrayMatcher(List<Object> list) throws InvalidJsonFLException
	{
		ArrayList<ItemJsonMatcher> result = new ArrayList<ItemJsonMatcher>();

		//Loop though all of the matchers
		for (Object ob : list)
		{
			//Create matchers from the objects
			result.add(getMatcher(ob));
		}

		result.trimToSize();

		//Return the completed array
		return result;
	}
}
