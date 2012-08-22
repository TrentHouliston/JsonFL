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
 * Tests the RegexJsonMatcher
 *
 * @author Trent Houliston
 * @version 1.0
 */
public class RegexJsonMatcherTest extends TestCase
{

	/**
	 * Test RegexJsonMatcher.
	 */
	@SuppressWarnings("unchecked")
	public void testRegexMatcher()
	{
		try
		{
			//Create a new gson object to parse the json
			Gson g = new Gson();

			//Create some expressions to test
			String expression1Def = "{'foo':{'?regex':'b..r'}}";
			String expression2Def = "{'foo':{'?regex':'^b..r$'}}";
			String expression3Def = "{'foo':{'?regex':'4'}}";

			//Create some invalid expressions to test
			String expression4Def = "{'foo':{'?regex':'(totesvalid!{3,4]'}}";

			//Have some objects to test against
			String object1Def = "{'foo':'bear'}";
			String object2Def = "{'foo':'beer'}";
			String object3Def = "{'foo':'lots of bears'}";
			String object4Def = "{'foo':4}";
			String object5Def = "{'foo':{}}";
			String object6Def = "{'foo':[]}";
			String object7Def = "{'bar':'x'}";

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
			LinkedHashMap<String, Object> object7 = g.fromJson(object7Def, LinkedHashMap.class);

			//Parse these into JsonFL Expression objects
			JsonFL ex1 = new JsonFL(expression1);
			JsonFL ex2 = new JsonFL(expression2);
			JsonFL ex3 = new JsonFL(expression3);

			//Ensure that invalid regex is rejected
			checkInvalid(expression4, expression4Def);

			//Check for expected behavior
			assertTrue(ex1.matches(object1));
			assertTrue(ex1.matches(object2));
			assertTrue(ex1.matches(object3));
			assertFalse(ex1.matches(object4));
			assertFalse(ex1.matches(object5));
			assertFalse(ex1.matches(object6));
			assertFalse(ex1.matches(object7));

			assertTrue(ex2.matches(object1));
			assertTrue(ex2.matches(object2));
			assertFalse(ex2.matches(object3));
			assertFalse(ex2.matches(object4));
			assertFalse(ex2.matches(object5));
			assertFalse(ex2.matches(object6));
			assertFalse(ex2.matches(object7));

			assertFalse(ex3.matches(object1));
			assertFalse(ex3.matches(object2));
			assertFalse(ex3.matches(object3));
			assertTrue(ex3.matches(object4));
			assertFalse(ex3.matches(object5));
			assertFalse(ex3.matches(object6));
			assertFalse(ex3.matches(object7));
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
