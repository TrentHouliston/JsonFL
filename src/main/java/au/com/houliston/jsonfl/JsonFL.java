package au.com.houliston.jsonfl;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This Class implements the Javascript Object Notation Filtering Language It will
 * receive a HashMap describing the query and from this it will build a series
 * of tree based matcher objects. These will be used to match any other hashmap
 * which is put into the system
 *
 * @author Trent Houliston
 * @author HyperX-Systems
 *
 * @version 1.0
 */
public class JsonFL
{
	//TODO put an example in the comments for each
	//TODO write some tests
	//TODO re read all comments
	//TODO move all the generation code into another class, Move to JsonMatcher class
	//TODO deal with the double question marks
	private final AndJsonMatcher root;

	public JsonFL(Map<String, Object> query) throws InvalidJsonQueryException
	{
		root = new AndJsonMatcher(query);
	}

	public boolean matches(LinkedHashMap<String, Object> json)
	{
		try
		{
			return root.match(json);
		}
		catch (Exception ex)
		{
			return false;
		}
	}
}
