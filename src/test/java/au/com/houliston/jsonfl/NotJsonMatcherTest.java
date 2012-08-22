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
 * Tests the behavior of the NotJsonMatcher
 *
 * @author Trent Houliston
 * @version 1.0
 */
public class NotJsonMatcherTest extends TestCase
{

	/**
	 * Test of match method, of class NotJsonMatcher.
	 */
	@SuppressWarnings("unchecked")
	public void testNotMatcher()
	{
		try
		{
			//Create a gson to parse the json
			Gson g = new Gson();

			//Create some test expressions
			String expression1Def = "{'!not':{'foo':'bar','test':'string'}}";
			String expression2Def = "{'!not':{'foo':'bar','test':'string','test2':'data'}}";

			//Create some objects to test against
			String object1Def = "{'foo':'bar','test':'string'}";
			String object2Def = "{'foo':'bar'}";
			String object3Def = "{'foo':'bar','test':'string','test2':'data'}";
			String object4Def = "{'foo':'bar','test':'blah','test2':'data'}";
			String object5Def = "{'foo':'x'}";
			String object6Def = "{'a':'b','c':'d','foo':'bar','test':'string','test2':'data'}";

			//Parse these into java objects
			LinkedHashMap<String, Object> expression1 = g.fromJson(expression1Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> expression2 = g.fromJson(expression2Def, LinkedHashMap.class);

			LinkedHashMap<String, Object> object1 = g.fromJson(object1Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> object2 = g.fromJson(object2Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> object3 = g.fromJson(object3Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> object4 = g.fromJson(object4Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> object5 = g.fromJson(object5Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> object6 = g.fromJson(object6Def, LinkedHashMap.class);

			//Make JsonFL expressions
			JsonFL ex1 = new JsonFL(expression1);
			JsonFL ex2 = new JsonFL(expression2);

			//Check for expected behavior
			assertFalse(ex1.matches(object1));
			assertTrue(ex1.matches(object2));
			assertFalse(ex1.matches(object3));
			assertTrue(ex1.matches(object4));
			assertTrue(ex1.matches(object5));
			assertFalse(ex1.matches(object6));

			assertTrue(ex2.matches(object1));
			assertTrue(ex2.matches(object2));
			assertFalse(ex2.matches(object3));
			assertTrue(ex2.matches(object4));
			assertTrue(ex2.matches(object5));
			assertFalse(ex2.matches(object6));
		}
		catch (Exception ex)
		{
			fail("An unexpected exception was thrown while processing");
		}
	}
}
