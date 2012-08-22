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
 * Tests the Exists matcher
 *
 * @author Trent Houliston
 * @version 1.0
 */
public class ExistsMatcherTest extends TestCase
{

	/**
	 * Test ExistsJsonMatcher.
	 */
	@SuppressWarnings("unchecked")
	public void testExistsMatcher()
	{
		try
		{
			//Gson to parse the json
			Gson g = new Gson();

			//Create a definition for both exists and does not exist
			String expression1Def = "{'foo':{'?exists':true}}";
			String expression2Def = "{'foo':{'?exists':false}}";

			//Create some invalid defintions
			String expression3Def = "{'foo':{'?exists':null}}";
			String expression4Def = "{'foo':{'?exists':{}}}";
			String expression5Def = "{'foo':{'?exists':'bar'}}";

			String object1Def = "{'foo':'bar'}";
			String object2Def = "{'foo':{'a':'b','c':'d'}}";
			String object3Def = "{'x':'y'}";
			String object4Def = "{'foo':4}";
			String object5Def = "{'foo':{}}";
			String object6Def = "{'foo':[]}";
			String object7Def = "{'bar':'x'}";

			//Parse these into java objects
			LinkedHashMap<String, Object> expression1 = g.fromJson(expression1Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> expression2 = g.fromJson(expression2Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> expression3 = g.fromJson(expression3Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> expression4 = g.fromJson(expression4Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> expression5 = g.fromJson(expression5Def, LinkedHashMap.class);

			LinkedHashMap<String, Object> object1 = g.fromJson(object1Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> object2 = g.fromJson(object2Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> object3 = g.fromJson(object3Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> object4 = g.fromJson(object4Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> object5 = g.fromJson(object5Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> object6 = g.fromJson(object6Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> object7 = g.fromJson(object7Def, LinkedHashMap.class);

			//Create matchers
			JsonFL ex1 = new JsonFL(expression1);
			JsonFL ex2 = new JsonFL(expression2);

			//Check that the invaild ones throw exceptions
			checkInvalid(expression3, expression3Def);
			checkInvalid(expression4, expression4Def);
			checkInvalid(expression5, expression5Def);

			//Check for expected behavior
			assertTrue(ex1.matches(object1));
			assertTrue(ex1.matches(object2));
			assertFalse(ex1.matches(object3));
			assertTrue(ex1.matches(object4));
			assertTrue(ex1.matches(object5));
			assertTrue(ex1.matches(object6));
			assertFalse(ex1.matches(object7));

			assertFalse(ex2.matches(object1));
			assertFalse(ex2.matches(object2));
			assertTrue(ex2.matches(object3));
			assertFalse(ex2.matches(object4));
			assertFalse(ex2.matches(object5));
			assertFalse(ex2.matches(object6));
			assertTrue(ex2.matches(object7));
		}
		catch (Exception ex)
		{
			fail("An unexpected exception was thrown while processing");
		}
	}

	/**
	 * This method makes sure that the passed definition is invalid by ensuring
	 * that it throws an exception
	 *
	 * @param expression The expression to display
	 * @param definition The definition to display if it fails (the one that
	 *                      made this object)
	 */
	private static void checkInvalid(LinkedHashMap<String, Object> expression, String definition)
	{
		//Try to parse it
		try
		{
			JsonFL jsonFL = new JsonFL(expression);

			//If an exception isn't thrown then fail
			fail(definition + " should have failed");
		}
		catch (InvalidJsonFLException ex)
		{
		}
	}
}
