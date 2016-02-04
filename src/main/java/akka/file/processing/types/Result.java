package akka.file.processing.types;

import java.util.concurrent.ConcurrentMap;

public class Result 
{
	private ConcurrentMap<String, Integer> value;

	public ConcurrentMap<String, Integer> getValue() {
		return value;
	}

	public Result(ConcurrentMap<String, Integer> value) 
	{
	      this.value = value;
	}
}
