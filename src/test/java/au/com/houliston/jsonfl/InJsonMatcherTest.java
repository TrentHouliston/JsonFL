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
 * This tests the InJsonMatcher
 *
 * @author Trent Houliston
 * @version 1.0
 */
public class InJsonMatcherTest extends TestCase
{

	/**
	 * Test InJsonMatcher.
	 */
	@SuppressWarnings("unchecked")
	public void testInMatcher()
	{
		try
		{
			//Create a new gson to parse the strings
			Gson g = new Gson();

			//Decalare some expressions to test the in matcher
			String expression1Def = "{'foo':{'?in':[1,5,'A','Dog']}}";
			String expression2Def = "{'foo':{'?in':[4,6,'x']}}";
			String expression3Def = "{'foo':{'?in':[['a','b','c'],{'?in':['Dog']},{'a':'b','x':'y'}]}}";

			//Declare some expressions that should fail
			String expression4Def = "{'foo':{'?in':[]}}";
			String expression5Def = "{'foo':{'?in':{}}}";
			String expression6Def = "{'foo':{'?in':'a'}}";

			//Declare some objects to test against
			String object1Def = "{'foo':'Dog'}";
			String object2Def = "{'foo':'1'}";
			String object3Def = "{'foo':1}";
			String object4Def = "{'foo':[]}";
			String object5Def = "{'foo':['a','b','c']}";
			String object6Def = "{'foo':'bar'}";
			String object7Def = "{'foo':{'a':'b','x':'y'}}";
			String object8Def = "{'foo':{'a':'b'}}";
			String object9Def = "{'bar':'x'}";

			//Parse the json into java objects
			LinkedHashMap<String, Object> expression1 = g.fromJson(expression1Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> expression2 = g.fromJson(expression2Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> expression3 = g.fromJson(expression3Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> expression4 = g.fromJson(expression4Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> expression5 = g.fromJson(expression5Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> expression6 = g.fromJson(expression6Def, LinkedHashMap.class);

			LinkedHashMap<String, Object> object1 = g.fromJson(object1Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> object2 = g.fromJson(object2Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> object3 = g.fromJson(object3Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> object4 = g.fromJson(object4Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> object5 = g.fromJson(object5Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> object6 = g.fromJson(object6Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> object7 = g.fromJson(object7Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> object8 = g.fromJson(object8Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> object9 = g.fromJson(object9Def, LinkedHashMap.class);

			//Create matchers
			JsonFL ex1 = new JsonFL(expression1);
			JsonFL ex2 = new JsonFL(expression2);
			JsonFL ex3 = new JsonFL(expression3);

			//Make sure that the invald expressions fail
			checkInvalid(expression4, expression4Def);
			checkInvalid(expression5, expression5Def);
			checkInvalid(expression6, expression6Def);

			//Check to ensure that the matchers behave correctly
			assertTrue(ex1.matches(object1));
			assertFalse(ex1.matches(object2));
			assertTrue(ex1.matches(object3));
			assertFalse(ex1.matches(object4));
			assertFalse(ex1.matches(object5));
			assertFalse(ex1.matches(object6));
			assertFalse(ex1.matches(object7));
			assertFalse(ex1.matches(object8));
			assertFalse(ex1.matches(object9));

			assertFalse(ex2.matches(object1));
			assertFalse(ex2.matches(object2));
			assertFalse(ex2.matches(object3));
			assertFalse(ex2.matches(object4));
			assertFalse(ex2.matches(object5));
			assertFalse(ex2.matches(object6));
			assertFalse(ex2.matches(object7));
			assertFalse(ex2.matches(object8));
			assertFalse(ex2.matches(object9));

			assertTrue(ex3.matches(object1));
			assertFalse(ex3.matches(object2));
			assertFalse(ex3.matches(object3));
			assertFalse(ex3.matches(object4));
			assertTrue(ex3.matches(object5));
			assertFalse(ex3.matches(object6));
			assertTrue(ex3.matches(object7));
			assertFalse(ex3.matches(object8));
			assertFalse(ex3.matches(object9));
		}
		catch (Exception ex)
		{
			fail("An unexpected exception was thrown while processing");
		}
	}

	private static void checkInvalid(LinkedHashMap<String, Object> expression, String definition)
	{
		try
		{
			JsonFL jsonFL = new JsonFL(expression);
			fail(definition + " should have failed");
		}
		catch (InvalidJsonFLException ex)
		{
		}
	}
}
