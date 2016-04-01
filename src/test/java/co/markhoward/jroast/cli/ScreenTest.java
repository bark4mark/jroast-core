package co.markhoward.jroast.cli;

import org.junit.Test;
import org.mockito.Mockito;

public class ScreenTest {
	@Test
	public void shouldDisplayIntroText(){
		Screen screen = Mockito.spy(new Screen());
		screen.writeTextFromFile("intro.txt");
		Mockito.verify(screen).write(Mockito.anyString());
	}
}
