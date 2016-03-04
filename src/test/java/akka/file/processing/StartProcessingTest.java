package akka.file.processing;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import akka.actor.ActorSystem;
import akka.testkit.JavaTestKit;

public class StartProcessingTest {

	static ActorSystem system;
	
	@BeforeClass
	public static void setup() 
	{
		system = ActorSystem.create("fileProcessingTestSystem");
	}
	
	@AfterClass
	public static void teardown() 
	{
	   JavaTestKit.shutdownActorSystem(system);
	   system = null;
	}
	
	@Test
	public void testRead() throws IOException 
	{
		new JavaTestKit(system)
		{{
			StartProcessing startProcessing = new StartProcessing();
			startProcessing.read();
		}};
		
	}

}
