package akka.file.processing;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentMap;

public class EndProcessing 
{
	private ConcurrentMap<String, Integer> resultMap;
	
	private final String PATH = "files";
	
	private final String RESULT_FILE_NAME = "ids_new.txt";
	
	public ConcurrentMap<String, Integer> getResultMap() {
		return resultMap;
	}


	public void setResultMap(ConcurrentMap<String, Integer> resultMap) {
		this.resultMap = resultMap;
	}


	public EndProcessing(ConcurrentMap<String, Integer> resultMap) {
		this.resultMap = resultMap;
	}
	
	/**
	 * 
	 * @return
	 */
	private String getResultFilePath()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(PATH);
		stringBuilder.append("/");
		stringBuilder.append(RESULT_FILE_NAME);
		
		return stringBuilder.toString();
	}
	
	/**
	 * Write result to new file
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	public void writeResultToFile() throws FileNotFoundException, UnsupportedEncodingException
	{
		PrintWriter writer = new PrintWriter(getResultFilePath(), "UTF-8");
		for(Entry<String, Integer> entry: resultMap.entrySet())
		{
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(entry.getKey());
			stringBuilder.append(";");
			stringBuilder.append(entry.getValue().toString());
			stringBuilder.append(";");
			
			writer.println(stringBuilder.toString());
		}
		writer.close();
	}
}
