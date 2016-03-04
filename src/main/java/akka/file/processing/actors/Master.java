package akka.file.processing.actors;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.file.processing.EndProcessing;
import akka.file.processing.types.Count;
import akka.file.processing.types.Result;
import akka.file.processing.types.Work;
import akka.routing.RoundRobinPool;

public class Master extends UntypedActor
{
	LoggingAdapter log = Logging.getLogger(getContext().system(), this);
	
	private Integer lineFrom = 0;
	private Integer lineTo = -1;
	private List<String> lines;
	private ConcurrentMap<String, Integer> resultMap = new ConcurrentHashMap<String, Integer>();

	private ActorRef listener;
	private ActorRef workerRouter;
	
	private Integer quantityOfResults = 0;
	final private Integer numberOfWorkers = Runtime.getRuntime().availableProcessors();
	 
	@Override
	public void preStart() {
		log.debug("Starting Master");
	}
	
	public Master(List<String> lines, ActorRef listener) 
	{
		this.lines = lines;
		this.listener = listener;
		workerRouter = getContext().actorOf(new RoundRobinPool(numberOfWorkers).props(Props.create(Worker.class)), "workerRouter");
	}

	
	@Override
	public void onReceive(Object message) throws Exception {
		if (message instanceof Count) 
		{
			log.info("Received Count");
			Integer step = lines.size() / numberOfWorkers;
	        for(int i = 0; i < numberOfWorkers; i++)
	        {
	        	lineFrom = lineTo + 1;
	        	lineTo = (i == numberOfWorkers - 1 ? lines.size() - 1 : lineFrom + step - 1);
	        	List<String> linesChunk = lines.subList(lineFrom, lineTo);
	        	workerRouter.tell(new Work(linesChunk, resultMap), getSelf());
	        }

	      } else if (message instanceof Result) 
	      {
	    	log.info("Received Result");
	        quantityOfResults ++;
	        if (quantityOfResults == numberOfWorkers) 
	        {
	          listener.tell(new EndProcessing(resultMap), getSelf());
	          getContext().stop(getSelf());
	        }
	      }
	      else 
	      {
	    	log.warning("Received unknown message: {}", message);  
	        unhandled(message);
	      }
	}
}
