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
 * Tests the IndexedArrayJsonMatcher
 *
 * @author Trent Houliston
 * @version 1.0
 */
public class IndexedArrayJsonMatcherTest extends TestCase
{

	/**
	 * Test IndexedArrayJsonMatcher.
	 */
	@SuppressWarnings("unchecked")
	public void testIndexedArrayMatcher()
	{
		try
		{
			//Create a new Gson
			Gson g = new Gson();

			//Create some valid expressions
			String expression1Def = "{'foo':{'?indexed':{'0':'x','4':5}}}";
			String expression2Def = "{'foo':{'?indexed':{'0':'x','2':'dog'}}}";
			String expression3Def = "{'foo':{'?indexed':{'2':{'?in':['dog']}}}}";

			//Create some invalid expressions
			String expression4Def = "{'foo':{'?indexed':['x','bar','dog']}}";
			String expression5Def = "{'foo':{'?indexed':{}}}";
			String expression6Def = "{'foo':{'?indexed':'a'}}";
			String expression7Def = "{'foo':{'?indexed':{'a':'x','r':'b'}}}";
			String expression8Def = "{'foo':{'?indexed':{'-1':'r'}}}";
			String expression9Def = "{'foo':{'?indexed':{'1.34':'r'}}}";

			//Create some objects to test against
			String object1Def = "{'foo':[x,'bar','dog']}";
			String object2Def = "{'foo':[x,'bar','bar2']}";
			String object3Def = "{'foo':['x',2,3,4,5,6,7,8]}";
			String object4Def = "{'foo':{}}";
			String object5Def = "{'foo':'r'}";
			String object6Def = "{'bar':'x'}";

			//Parse these into java objects
			LinkedHashMap<String, Object> expression1 = g.fromJson(expression1Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> expression2 = g.fromJson(expression2Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> expression3 = g.fromJson(expression3Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> expression4 = g.fromJson(expression4Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> expression5 = g.fromJson(expression5Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> expression6 = g.fromJson(expression6Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> expression7 = g.fromJson(expression6Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> expression8 = g.fromJson(expression6Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> expression9 = g.fromJson(expression6Def, LinkedHashMap.class);

			LinkedHashMap<String, Object> object1 = g.fromJson(object1Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> object2 = g.fromJson(object2Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> object3 = g.fromJson(object3Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> object4 = g.fromJson(object4Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> object5 = g.fromJson(object5Def, LinkedHashMap.class);
			LinkedHashMap<String, Object> object6 = g.fromJson(object6Def, LinkedHashMap.class);

			//Create the matchers
			JsonFL ex1 = new JsonFL(expression1);
			JsonFL ex2 = new JsonFL(expression2);
			JsonFL ex3 = new JsonFL(expression3);

			//Check that the invalid expressions fail
			checkInvalid(expression4, expression4Def);
			checkInvalid(expression5, expression5Def);
			checkInvalid(expression6, expression6Def);
			checkInvalid(expression7, expression7Def);
			checkInvalid(expression8, expression8Def);
			checkInvalid(expression9, expression9Def);

			//Check that the matcher behaves as expected
			assertFalse(ex1.matches(object1));
			assertFalse(ex1.matches(object2));
			assertTrue(ex1.matches(object3));
			assertFalse(ex1.matches(object4));
			assertFalse(ex1.matches(object5));
			assertFalse(ex1.matches(object6));

			assertTrue(ex2.matches(object1));
			assertFalse(ex2.matches(object2));
			assertFalse(ex2.matches(object3));
			assertFalse(ex2.matches(object4));
			assertFalse(ex2.matches(object5));
			assertFalse(ex2.matches(object6));

			assertTrue(ex3.matches(object1));
			assertFalse(ex3.matches(object2));
			assertFalse(ex3.matches(object3));
			assertFalse(ex3.matches(object4));
			assertFalse(ex3.matches(object5));
			assertFalse(ex3.matches(object6));
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
