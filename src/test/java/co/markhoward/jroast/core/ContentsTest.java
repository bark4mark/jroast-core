package co.markhoward.jroast.core;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mockito;

import co.markhoward.jroast.core.oven.Oven;

import com.google.common.io.Resources;

public class ContentsTest {
	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();
	
	@Test
	public void shouldCallWithPath() throws URISyntaxException, IOException{
		File tempDirectory = temporaryFolder.newFolder();
		Path tempPath = tempDirectory.toPath();
		
		URL exampleUrl = Resources.getResource("example");
		File exampleDir = new File(exampleUrl.toURI());
		
		Path contentPath = new File(exampleDir, Roast.CONTENT).toPath();
		
		Oven oven = Mockito.mock(Oven.class);
		Contents contents = new Contents(contentPath, oven);
		contents.compileContents(tempPath);
		Mockito.verify(oven).roast(Mockito.any(Path.class), Mockito.any(Path.class));
	}
}
