package akka.file.processing.actors;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.lang.math.NumberUtils;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.file.processing.types.Result;
import akka.file.processing.types.Work;

public class Worker extends UntypedActor
{
	LoggingAdapter log = Logging.getLogger(getContext().system(), this);
	
	@Override
	public void preStart() {
		log.debug("Starting Worker");
	}
	
	/**
	 * 
	 * @param lines
	 * @param lineFrom
	 * @param lineTo
	 * @param resultMap
	 * @return
	 * @throws IOException
	 */
	private ConcurrentMap<String, Integer> countIds(List<String> lines, Integer lineFrom, Integer lineTo, ConcurrentMap< String, Integer> resultMap) throws IOException
	{
		for(Integer index = lineFrom; index <= lineTo; index++)
		{
			String line = lines.get(index);
			String[] currentLineData = line.split(";");
			String id = currentLineData[0];
			Integer currentValue = NumberUtils.toInt(currentLineData[1], 0);
			Integer previousValue = resultMap.putIfAbsent(id, currentValue);
			if(previousValue != null)
				resultMap.replace(id, previousValue + currentValue);
		}
		return resultMap;
	}
	
	@Override
	public void onReceive(Object message) throws Exception {
		if (message instanceof Work) 
		{
			log.info("Received Work");
			Work work = (Work) message;
			ConcurrentMap<String, Integer> result = countIds(work.getLines(), work.getLineFrom(), work.getLineTo(), work.getResultMap());
		    getSender().tell(new Result(result), getSelf());
		} 
		else 
		{	
			log.warning("Received unknown message: {}", message);  
			unhandled(message);
		}
	}
}
