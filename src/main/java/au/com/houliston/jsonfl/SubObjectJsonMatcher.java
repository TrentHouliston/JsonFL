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
 * This object is used to test a sub object (an object within the root object)
 * it behaves as a new implicit root and for this new object. This also contains
 * the special case for checking if a key exists as the empty object will match
 * anything so long as the key exists
 *
 * Example: {'key':{'sub':'object'}}
 *
 * Matches: {'key':{'sub':'object'}}
 *
 * @author Trent Houliston
 * @version 1.0
 */
class SubObjectJsonMatcher extends ItemJsonMatcher
{

	/**
	 * The root inner matcher to store
	 */
	private final AndJsonMatcher inner;

	/**
	 * Creates a new SubObjectMatcher from the passed definition map
	 *
	 * @param target the root of the SubObject
	 *
	 * @throws InvalidJsonQueryException if this or any of the definitions
	 *                                      within it are invalid
	 */
	public SubObjectJsonMatcher(Map<String, Object> target) throws InvalidJsonFLException
	{
		//Create a new And based on this object
		inner = new AndJsonMatcher(target);
	}

	/**
	 * Checks if the contents of this sub object match
	 *
	 * @param target The target to test
	 *
	 * @return If the object matched
	 */
	@Override
	@SuppressWarnings("unchecked")
	public boolean match(Object target)
	{
		//Otherwise confirm that the subobject passes
		return inner.match((Map<String, Object>) target);
	}
}
