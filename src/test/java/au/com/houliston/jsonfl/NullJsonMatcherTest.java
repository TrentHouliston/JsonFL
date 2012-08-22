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

import com.google.gson.Gson;
import java.util.LinkedHashMap;
import junit.framework.TestCase;

/**
 * Tests the NullJsonMatcher
 *
 * @author Trent Houliston
 * @version 1.0
 */
public class NullJsonMatcherTest extends TestCase
{

	/**
	 * Test NullJsonMatcher.
	 */
	@SuppressWarnings("unchecked")
	public void testNullMatcher()
	{
		try
		{
			//Create a gson to match the objects
			Gson g = new Gson();

			//Create a matcher which matches an explicit null
			String expression1Def = "{'foo':null}";

			//Create some test matchers
			String object1Def = "{'foo':null}";
			String object2Def = "{'foo':'x'}";
			String object3Def = "{'bar':'x'}";

			//Create java objects from these definitions
			LinkedHashMap<String, Object> expression1 = g.fromJson(expression1Def, LinkedHashMap.class);

			LinkedHashMap<String, Object> object1 = g.fromJson(object1Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> object2 = g.fromJson(object2Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> object3 = g.fromJson(object3Def, LinkedHashMap.class);

			//Parse the expression into a JsonFL
			JsonFL ex1 = new JsonFL(expression1);

			//Check for expected results
			assertTrue(ex1.matches(object1));
			assertFalse(ex1.matches(object2));
			assertFalse(ex1.matches(object3));
		}
		catch (Exception ex)
		{
			fail("An unexpected exception was thrown while processing");
		}
	}
}
