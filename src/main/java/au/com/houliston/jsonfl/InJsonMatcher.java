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

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * This checks if the passed object is in the list that was used to to create
 * this matcher.
 *
 * Example: {'foo':{'?in':['a','b',{'?regex':'c'}}}
 *
 * Matches: {'foo':'a'}
 *
 * Matches: {'foo':'cement'}
 *
 * @author Trent Houliston
 * @version 1.0
 */
class InJsonMatcher extends ItemJsonMatcher
{

	/**
	 * The object which holds all of the possibilities that the passed object
	 * can be
	 */
	private final List<ItemJsonMatcher> list;

	/**
	 * This creates an in expression. It will check if the passed object is in
	 * the set of objects
	 *
	 * @param in The definition containing the options that this can be in
	 *
	 * @throws InvalidJsonQueryException if the passed object was not a valid
	 *                                      query
	 */
	@SuppressWarnings("unchecked")
	public InJsonMatcher(Map<String, Object> in) throws InvalidJsonFLException
	{
		//Get the in object
		Object o = in.get("?in");

		//Create the set
		list = new LinkedList<ItemJsonMatcher>();

		//Check it is a list
		if (o instanceof List)
		{
			//Add all the elements to the list
			for (Object ob : (List) o)
			{
				list.add(this.getMatcher(ob));
			}

			if (list.isEmpty())
			{
				throw new InvalidJsonFLException("An empty ?in will never match anything");
			}
		}
		else
		{
			//It was not an array
			throw new InvalidJsonFLException("The Paramter for an ?in must be an array");
		}
	}

	/**
	 * Checks if the passed object is in the HashSet that was setup during
	 * instantiation
	 *
	 * @param target The object to check if it is in the set
	 *
	 * @return True if it is in the set, false otherwise
	 */
	@Override
	public boolean match(Object target)
	{
		for (ItemJsonMatcher m : list)
		{
			if (m.match(target))
			{
				return true;
			}
		}
		return false;
	}
}
