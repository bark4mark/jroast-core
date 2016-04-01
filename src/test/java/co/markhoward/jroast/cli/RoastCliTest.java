package co.markhoward.jroast.cli;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import co.markhoward.jroast.core.Roast;

public class RoastCliTest {
	private Screen screen;
	private Roast roast;
	
	@Before
	public void setup(){
		screen = Mockito.mock(Screen.class);
		roast = Mockito.mock(Roast.class);
	}
	
	@Test
	public void shouldDisplayIntro() throws IOException{
		String[] args = {};
		RoastCli roastCli = new RoastCli(screen, roast);
		roastCli.start(args);
		Mockito.verify(screen).writeTextFromFile(Mockito.anyString());
	}
	
	@Test
	public void shouldCallInit() throws IOException{
		String[] args = {RoastCli.INIT};
		RoastCli roastCli = new RoastCli(screen, roast);
		roastCli.start(args);
		Mockito.verify(roast).init(Mockito.any(Path.class));
	}
	
	@Test
	public void shouldCallClean() throws IOException{
		String[] args = {RoastCli.CLEAN};
		RoastCli roastCli = new RoastCli(screen, roast);
		roastCli.start(args);
		Mockito.verify(roast).clean(Mockito.any(Path.class));
	}
	
	@Test
	public void shouldCallRoast() throws IOException{
		String[] args = {RoastCli.ROAST};
		RoastCli roastCli = new RoastCli(screen, roast);
		roastCli.start(args);
		Mockito.verify(roast).roast(Mockito.any(Path.class));
	}
	
	@Test
	public void shouldCallHelp() throws IOException{
		String[] args = {RoastCli.HELP};
		RoastCli roastCli = new RoastCli(screen, roast);
		roastCli.start(args);
		Mockito.verify(screen).writeTextFromFile(Mockito.anyString());
	}
}
