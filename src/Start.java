
import java.io.File;
import java.util.ArrayList;

import org.codehaus.jackson.map.ObjectMapper;

public class Start extends Thread {
	
	private static ObjectMapper mapper;
	private static Jenkins[] jenkinses;

	public static void main(String[] args) throws Exception {

		System.out.println("Start analyse Jenkinses...");
		

        mapper = new ObjectMapper();
        if (args.length==0) {
        	jenkinses = new ObjectMapper().readValue(mapper.readTree(new File(Global.JENKINSES_INPUT)), Jenkins[].class);
        } else {
        	String path = args[0] + Global.JENKINSES_INPUT;
        	Global.JSON_FILE_PATH = args[0];
        	jenkinses = new ObjectMapper().readValue(mapper.readTree(new File(path)), Jenkins[].class);
        }
        Thread thread;
        ArrayList<Thread> threads = new ArrayList<Thread>();
        for (Jenkins jenkins:jenkinses) {
            ProcessJenkins pr = new ProcessJenkins();
            pr.setJenkins(jenkins);
            thread = new Thread(pr);
            threads.add(thread);
            thread.start();
        }
        for (Thread t: threads) {
        	t.join();
        }
        System.out.println("Jenkinses analysys has been completed!");
	}
}
