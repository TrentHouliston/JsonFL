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
 * Tests the OrJsonMatcher 
 * 
 * @author Trent Houliston
 * @version 1.0
 */
public class OrJsonMatcherTest extends TestCase
{

	/**
	 * Test OrJsonMatcher.
	 */
	@SuppressWarnings("unchecked")
	public void testOrMatcher()
	{
		try
		{
			//Create a new Gson
			Gson g = new Gson();

			//Declare some expressions
			String expression1Def = "{'!or':{'foo':'bar','test':'string'}}";
			String expression2Def = "{'!or':{'foo':'bar','test':'string','test2':'data'}}";

			//Declare some invalid expressions
			String expression3Def = "{'!or':null}";
			String expression4Def = "{'!or':'a'}";

			//Declare some objects to test against
			String object1Def = "{'foo':'bar','test':'string'}";
			String object2Def = "{'foo':'bar'}";
			String object3Def = "{'foo':'bar','test':'string','test2':'data'}";
			String object4Def = "{'foo':'stuff','test':'blah','test2':'data'}";
			String object5Def = "{'foo':'x'}";
			String object6Def = "{'a':'b','c':'d','foo':'bar','test':'string','test2':'data'}";

			//Parse these into java objects
			LinkedHashMap<String, Object> expression1 = g.fromJson(expression1Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> expression2 = g.fromJson(expression2Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> expression3 = g.fromJson(expression3Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> expression4 = g.fromJson(expression4Def, LinkedHashMap.class);

			LinkedHashMap<String, Object> object1 = g.fromJson(object1Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> object2 = g.fromJson(object2Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> object3 = g.fromJson(object3Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> object4 = g.fromJson(object4Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> object5 = g.fromJson(object5Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> object6 = g.fromJson(object6Def, LinkedHashMap.class);

			//Create the expressions
			JsonFL ex1 = new JsonFL(expression1);
			JsonFL ex2 = new JsonFL(expression2);

			//Check the invalid expressions throw an exception
			checkInvalid(expression3, expression3Def);
			checkInvalid(expression4, expression4Def);

			//Check for expected behavior
			assertTrue(ex1.matches(object1));
			assertTrue(ex1.matches(object2));
			assertTrue(ex1.matches(object3));
			assertFalse(ex1.matches(object4));
			assertFalse(ex1.matches(object5));
			assertTrue(ex1.matches(object6));

			assertTrue(ex2.matches(object1));
			assertTrue(ex2.matches(object2));
			assertTrue(ex2.matches(object3));
			assertTrue(ex2.matches(object4));
			assertFalse(ex2.matches(object5));
			assertTrue(ex2.matches(object6));
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
