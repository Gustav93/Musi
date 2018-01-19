package db_test;

import static org.junit.Assert.assertEquals;

import java.util.List;

public class Assert 
{
	public static <T> void assertListasIguales(List<T> expected, List<T> actual)
	{
		assertEquals(expected.size(), actual.size());
		
		for(int i=0; i < expected.size(); i++)
			assertEquals(expected.get(i), actual.get(i));

	}
}
