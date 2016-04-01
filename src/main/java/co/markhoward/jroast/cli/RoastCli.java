package co.markhoward.jroast.cli;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import co.markhoward.jroast.core.Roast;

public class RoastCli {
	private final Screen screen;
	private final Roast roast;
	
	public RoastCli(Screen screen, Roast roast){
		this.screen = screen;
		this.roast = roast;
	}
	
	public static void main(String[] args) throws IOException{
		Screen screen = new Screen();
		Roast roast = new Roast();
		RoastCli roastCli = new RoastCli(screen, roast);
		roastCli.start(args);
	}
	
	public void start(String[] args) throws IOException{
		String arg = HELP;
		if(args.length == 0){
			screen.writeTextFromFile(INTRO_FILE);
			return;
		}
		
		arg = args[0];
		Path currentPath = Paths.get("").toAbsolutePath();
		
		switch(arg){
		case(INIT):
			roast.init(currentPath);
			break;
		case(ROAST):
			roast.roast(currentPath);
			break;
		case(HELP):
			screen.writeTextFromFile(INTRO_FILE);
			break;
		case(CLEAN):
			roast.clean(currentPath);
			break;
		}
	}
	
	public static final String INIT = "-i";
	public static final String ROAST = "-r";
	public static final String HELP = "-h";
	public static final String CLEAN = "-c";

	private static final String INTRO_FILE = "intro.txt";
}
