package akka.file.processing;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.file.processing.actors.Listener;
import akka.file.processing.actors.Master;
import akka.file.processing.types.Count;

public class StartProcessing
{
	
	private final String PATH = "files";
	
	private final String SOURCE_FILE_NAME = "ids.txt";

	final ActorSystem system = ActorSystem.create("fileProcessingSystem");
	
	LoggingAdapter log = Logging.getLogger(system, this);
	/**
	 * 
	 * @throws IOException
	 */
	public void read() throws IOException
	{
		log.info("Start to process file");
		
		List<String> lines = Files.readAllLines(Paths.get(getSourceFilePath()), Charset.forName("UTF-8"));
		
		final ActorRef listener = system.actorOf(Props.create(Listener.class), "listener");
		 
	    ActorRef master = system.actorOf(Props.create(Master.class, lines, listener), "master");
	    
	    master.tell(new Count(), ActorRef.noSender());
	    
	}
	
	/**
	 * 
	 * @return
	 */
	private String getSourceFilePath()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(PATH);
		stringBuilder.append("/");
		stringBuilder.append(SOURCE_FILE_NAME);
		
		return stringBuilder.toString();
	}
	
	public static void main(String[] args) throws IOException
	{
		StartProcessing startProcessing = new StartProcessing();
		startProcessing.read();
	}
}
