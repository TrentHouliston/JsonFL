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
 * Tests the number matcher.
 *
 * @author Trent Houliston
 * @version 1.0
 */
public class NumberJsonMatcherTest extends TestCase
{

	/**
	 * Test NumberJsonMatcher.
	 */
	@SuppressWarnings("unchecked")
	public void testNumberMatcher()
	{
		try
		{
			//Create a new gson to parse the json
			Gson g = new Gson();

			//Create some test expressions with numbers in them
			String expression1Def = "{'foo':1}";
			String expression2Def = "{'foo':1.561}";
			String expression3Def = "{'foo':NaN}";

			//Create some objects to test against
			String object1Def = "{'foo':1}";
			String object2Def = "{'foo':1.561}";
			String object3Def = "{'foo':NaN}";
			String object4Def = "{'foo':'x'}";
			String object5Def = "{'bar':'x'}";

			//Convert these into java objects
			LinkedHashMap<String, Object> expression1 = g.fromJson(expression1Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> expression2 = g.fromJson(expression2Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> expression3 = g.fromJson(expression3Def, LinkedHashMap.class);

			LinkedHashMap<String, Object> object1 = g.fromJson(object1Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> object2 = g.fromJson(object2Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> object3 = g.fromJson(object3Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> object4 = g.fromJson(object4Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> object5 = g.fromJson(object5Def, LinkedHashMap.class);

			//Create a JsonFL based on these
			JsonFL ex1 = new JsonFL(expression1);
			JsonFL ex2 = new JsonFL(expression2);
			JsonFL ex3 = new JsonFL(expression3);

			//Check for expected behavior
			assertTrue(ex1.matches(object1));
			assertFalse(ex1.matches(object2));
			assertFalse(ex1.matches(object3));
			assertFalse(ex1.matches(object4));
			assertFalse(ex1.matches(object5));

			assertFalse(ex2.matches(object1));
			assertTrue(ex2.matches(object2));
			assertFalse(ex2.matches(object3));
			assertFalse(ex2.matches(object4));
			assertFalse(ex2.matches(object5));

			assertFalse(ex3.matches(object1));
			assertFalse(ex3.matches(object2));
			assertTrue(ex3.matches(object3));
			assertFalse(ex3.matches(object4));
			assertFalse(ex3.matches(object5));
		}
		catch (Exception ex)
		{
			fail("An unexpected exception was thrown while processing");
		}
	}
}
