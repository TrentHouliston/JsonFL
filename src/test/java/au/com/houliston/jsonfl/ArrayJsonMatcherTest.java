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
 * This is for testing the Array matcher, Array matcher is used whenever an
 * explicit array is defined
 *
 * @author Trent Houliston
 * @version 1.0
 */
public class ArrayJsonMatcherTest extends TestCase
{

	/**
	 * Test ArrayJsonMatcher
	 */
	@SuppressWarnings("unchecked")
	public void testArrayMatcher()
	{
		try
		{
			//Get a gson to parse our strings
			Gson g = new Gson();

			//Create some test expressions
			String expression1Def = "{'foo':[1,2,3,4,5]}";
			String expression2Def = "{'foo':['a',1,'b','c']}";
			String expression3Def = "{'foo':[]}";

			//Create some test object defintions
			String object1Def = "{'foo':[1,2,3,4,5]}";
			String object2Def = "{'foo':[1,2,3,4,5,6]}";
			String object3Def = "{'foo':['a',1,'b','c']}";
			String object4Def = "{'foo':'bar'}";
			String object5Def = "{'foo':[]}";
			String object6Def = "{'x':'y'}";

			//Convert them into java objects
			LinkedHashMap<String, Object> expression1 = g.fromJson(expression1Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> expression2 = g.fromJson(expression2Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> expression3 = g.fromJson(expression3Def, LinkedHashMap.class);

			LinkedHashMap<String, Object> object1 = g.fromJson(object1Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> object2 = g.fromJson(object2Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> object3 = g.fromJson(object3Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> object4 = g.fromJson(object4Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> object5 = g.fromJson(object5Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> object6 = g.fromJson(object6Def, LinkedHashMap.class);

			//Make our matchers
			JsonFL ex1 = new JsonFL(expression1);
			JsonFL ex2 = new JsonFL(expression2);
			JsonFL ex3 = new JsonFL(expression3);

			//Test if they match correctly
			assertTrue(ex1.matches(object1));
			assertFalse(ex1.matches(object2));
			assertFalse(ex1.matches(object3));
			assertFalse(ex1.matches(object4));
			assertFalse(ex1.matches(object5));
			assertFalse(ex1.matches(object6));

			assertFalse(ex2.matches(object1));
			assertFalse(ex2.matches(object2));
			assertTrue(ex2.matches(object3));
			assertFalse(ex2.matches(object4));
			assertFalse(ex2.matches(object5));
			assertFalse(ex2.matches(object6));

			assertFalse(ex3.matches(object1));
			assertFalse(ex3.matches(object2));
			assertFalse(ex3.matches(object3));
			assertFalse(ex3.matches(object4));
			assertTrue(ex3.matches(object5));
			assertFalse(ex3.matches(object6));


		}
		catch (Exception ex)
		{
			fail("An unexpected exception was thrown while processing");
		}
	}
}
