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
 * This test is run to test higher level features of the Language which do not
 * relate to any specific class
 *
 * @author Trent Houliston
 * @version 1.0
 */
public class JsonFLTest extends TestCase
{

	/**
	 * Tests escaped expressions using the double question mark.
	 */
	public void testEscapedExpressions()
	{
		try
		{
			//Create a new Gson object to parse the json
			Gson g = new Gson();

			//Create some test expressions
			String expression1Def = "{'!!and':'x'}";
			String expression2Def = "{'???and':'x'}";
			String expression3Def = "{'key':{'!!indexed':'value'}}";
			String expression4Def = "{'key':{'???indexed':'value'}}";

			//Create some test objects
			String object1Def = "{'!and':'x'}";
			String object2Def = "{'??and':'x'}";
			String object3Def = "{'key':{'!indexed':'value'}}";
			String object4Def = "{'key':{'??indexed':'value'}}";

			//Parse these into java objects
			LinkedHashMap<String, Object> expression1 = g.fromJson(expression1Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> expression2 = g.fromJson(expression2Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> expression3 = g.fromJson(expression3Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> expression4 = g.fromJson(expression4Def, LinkedHashMap.class);

			LinkedHashMap<String, Object> object1 = g.fromJson(object1Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> object2 = g.fromJson(object2Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> object3 = g.fromJson(object3Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> object4 = g.fromJson(object4Def, LinkedHashMap.class);

			//Make JsonFL Expression objects
			JsonFL ex1 = new JsonFL(expression1);
			JsonFL ex2 = new JsonFL(expression2);
			JsonFL ex3 = new JsonFL(expression3);
			JsonFL ex4 = new JsonFL(expression4);

			//Test for expected behavior
			assertTrue(ex1.matches(object1));
			assertFalse(ex1.matches(object2));
			assertFalse(ex1.matches(object3));
			assertFalse(ex1.matches(object4));

			assertFalse(ex2.matches(object1));
			assertTrue(ex2.matches(object2));
			assertFalse(ex2.matches(object3));
			assertFalse(ex2.matches(object4));

			assertFalse(ex3.matches(object1));
			assertFalse(ex3.matches(object2));
			assertTrue(ex3.matches(object3));
			assertFalse(ex3.matches(object4));

			assertFalse(ex4.matches(object1));
			assertFalse(ex4.matches(object2));
			assertFalse(ex4.matches(object3));
			assertTrue(ex4.matches(object4));
		}
		catch (Exception ex)
		{
			fail("An unexpected exception was thrown while processing");
		}
	}

	public void testNestedGroups()
	{

		try
		{
			//Create a new Gson object to parse the json
			Gson g = new Gson();

			//Create some test expressions
			String expression1Def = "{'!or':{'!and':[{'foo':'bar','key':'value'},{'x':'y','a':'b'}]}}";
			String expression2Def = "{'!not':{'!or':{'foo':'bar','x':'y'}}}";

			//Create some test objects
			String object1Def = "{'foo':'bar','key':'value'}";
			String object2Def = "{'x':'y','a':'b'}";
			String object3Def = "{'a':'b','key':'value'}";

			//Parse these into java objects
			LinkedHashMap<String, Object> expression1 = g.fromJson(expression1Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> expression2 = g.fromJson(expression2Def, LinkedHashMap.class);

			LinkedHashMap<String, Object> object1 = g.fromJson(object1Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> object2 = g.fromJson(object2Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> object3 = g.fromJson(object3Def, LinkedHashMap.class);

			//Make JsonFL Expression objects
			JsonFL ex1 = new JsonFL(expression1);
			JsonFL ex2 = new JsonFL(expression2);

			//Test for expected behavior
			assertTrue(ex1.matches(object1));
			assertTrue(ex1.matches(object2));
			assertFalse(ex1.matches(object3));

			assertFalse(ex2.matches(object1));
			assertFalse(ex2.matches(object2));
			assertTrue(ex2.matches(object3));
		}
		catch (Exception ex)
		{
			fail("An unexpected exception was thrown while processing");
		}
	}
}
