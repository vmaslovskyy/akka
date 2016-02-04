package akka.file.processing.types;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

public class Work implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7560254898108262336L;
	private Integer lineFrom;
	private Integer lineTo;
	private List<String> lines;
	private ConcurrentMap<String, Integer> resultMap;
	
	public Integer getLineFrom() {
		return lineFrom;
	}
	public void setLineFrom(Integer lineFrom) {
		this.lineFrom = lineFrom;
	}
	public Integer getLineTo() {
		return lineTo;
	}
	public void setLineTo(Integer lineTo) {
		this.lineTo = lineTo;
	}
	public List<String> getLines() {
		return lines;
	}
	public void setLines(List<String> lines) {
		this.lines = lines;
	}
	
	public ConcurrentMap<String, Integer> getResultMap() {
		return resultMap;
	}
	public void setResultMap(ConcurrentMap<String, Integer> resultMap) {
		this.resultMap = resultMap;
	}
	
	public Work(List<String> lines, Integer lineFrom, Integer lineTo, ConcurrentMap<String, Integer> resultMap) 
	{
		this.lineFrom = lineFrom;
		this.lineTo = lineTo;
		this.lines = lines;
		this.resultMap = resultMap;
	}
	
	
	
	
}
