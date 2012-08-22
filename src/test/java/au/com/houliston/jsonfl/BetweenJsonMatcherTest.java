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
 * This is used for testing the between matcher
 *
 * @author Trent Houliston
 * @version 1.0
 */
public class BetweenJsonMatcherTest extends TestCase
{

	/**
	 * Test BetweenJsonMatcher.
	 */
	@SuppressWarnings("unchecked")
	public void testBetweenMatcher()
	{
		try
		{
			//Get a new Gson
			Gson g = new Gson();

			//Declare some valid expressions
			String expression1Def = "{'foo':{'?between':[5,10]}}";
			String expression2Def = "{'foo':{'?between':['q','t']}}";

			//Declare some invalid expressions
			String expression3Def = "{'foo':{'?between':[10,5]}}";
			String expression4Def = "{'foo':{'?between':'x'}}";
			String expression5Def = "{'foo':{'?between':[1,'y']}}";
			String expression6Def = "{'foo':{'?between':[1,2,3]}}";
			String expression7Def = "{'foo':{'?between':[]}}";
			String expression8Def = "{'foo':{'?between':{}}}";

			//Declare some test objects
			String object1Def = "{'foo':7}";
			String object2Def = "{'foo':-5}";
			String object3Def = "{'foo':5}";
			String object4Def = "{'foo':10}";
			String object5Def = "{'foo':'s'}";
			String object6Def = "{'foo':'z'}";
			String object7Def = "{'foo':'q'}";
			String object8Def = "{'foo':'t'}";
			String object9Def = "{'foo':'quest'}";
			String object10Def = "{'foo':'zoom'}";
			String object11Def = "{'foo':'7'}";
			String object12Def = "{'foo':'roger'}";
			String object13Def = "{'bar':'x'}";

			//Convert them to java objects
			LinkedHashMap<String, Object> expression1 = g.fromJson(expression1Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> expression2 = g.fromJson(expression2Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> expression3 = g.fromJson(expression3Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> expression4 = g.fromJson(expression4Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> expression5 = g.fromJson(expression5Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> expression6 = g.fromJson(expression6Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> expression7 = g.fromJson(expression7Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> expression8 = g.fromJson(expression8Def, LinkedHashMap.class);

			LinkedHashMap<String, Object> object1 = g.fromJson(object1Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> object2 = g.fromJson(object2Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> object3 = g.fromJson(object3Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> object4 = g.fromJson(object4Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> object5 = g.fromJson(object5Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> object6 = g.fromJson(object6Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> object7 = g.fromJson(object7Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> object8 = g.fromJson(object8Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> object9 = g.fromJson(object9Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> object10 = g.fromJson(object10Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> object11 = g.fromJson(object11Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> object12 = g.fromJson(object12Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> object13 = g.fromJson(object13Def, LinkedHashMap.class);

			//Build the two expressions
			JsonFL ex1 = new JsonFL(expression1);
			JsonFL ex2 = new JsonFL(expression2);

			//Test to make sure that the invalid definitions throw exceptions
			checkInvalid(expression3, expression3Def);
			checkInvalid(expression4, expression4Def);
			checkInvalid(expression5, expression5Def);
			checkInvalid(expression6, expression6Def);
			checkInvalid(expression7, expression7Def);
			checkInvalid(expression8, expression8Def);

			//Test that the objects match correctly
			assertTrue(ex1.matches(object1));
			assertFalse(ex1.matches(object2));
			assertTrue(ex1.matches(object3));
			assertTrue(ex1.matches(object4));
			assertFalse(ex1.matches(object5));
			assertFalse(ex1.matches(object6));
			assertFalse(ex1.matches(object7));
			assertFalse(ex1.matches(object8));
			assertFalse(ex1.matches(object9));
			assertFalse(ex1.matches(object10));
			assertFalse(ex1.matches(object11));
			assertFalse(ex1.matches(object12));
			assertFalse(ex1.matches(object13));

			assertFalse(ex2.matches(object1));
			assertFalse(ex2.matches(object2));
			assertFalse(ex2.matches(object3));
			assertFalse(ex2.matches(object4));
			assertTrue(ex2.matches(object5));
			assertFalse(ex2.matches(object6));
			assertTrue(ex2.matches(object7));
			assertTrue(ex2.matches(object8));
			assertTrue(ex2.matches(object9));
			assertFalse(ex2.matches(object10));
			assertFalse(ex2.matches(object11));
			assertTrue(ex2.matches(object12));
			assertFalse(ex2.matches(object13));
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
