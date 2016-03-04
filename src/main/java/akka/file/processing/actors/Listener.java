package akka.file.processing.actors;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.file.processing.EndProcessing;

public class Listener extends UntypedActor
{
	LoggingAdapter log = Logging.getLogger(getContext().system(), this);
	
	@Override
	public void preStart() {
		log.debug("Starting Listener");
	}
	
	@Override
	public void onReceive(Object message) throws Exception {
		if (message instanceof EndProcessing) 
		{
			log.info("Received EndProcessing");
			EndProcessing endProcessing = (EndProcessing) message;
			endProcessing.writeResultToFile();
			log.info("File writed");
	        getContext().system().shutdown();
	    } 
		else 
	    {
			log.warning("Received unknown message: {}", message);  
	        unhandled(message);
	    }
		
	}

}
