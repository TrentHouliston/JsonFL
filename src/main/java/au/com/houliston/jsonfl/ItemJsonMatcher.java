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
 * This Object is used to define an Item Matcher, these are matchers which match
 * on the object themselves rather then processing a group of objects
 *
 * @author Trent Houliston
 * @version 1.0
 */
abstract class ItemJsonMatcher extends JsonMatcher
{

	/**
	 * This method is used to match the passed object with the definition found
	 * in this ItemJsonMatcher
	 *
	 * @param target The target object to match to
	 *
	 * @return If the object matched
	 */
	public abstract boolean match(Object target);
}
