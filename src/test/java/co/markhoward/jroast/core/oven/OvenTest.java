package co.markhoward.jroast.core.oven;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import co.markhoward.jroast.core.Roast;
import co.markhoward.jroast.core.oven.Oven;

import com.google.common.io.Resources;

public class OvenTest {
	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();
	
	@Test
	public void shouldCompileContentWithTemplate() throws IOException, URISyntaxException{
		File tempDirectory = temporaryFolder.newFolder();
		Path tempPath = tempDirectory.toPath();
		
		URL exampleUrl = Resources.getResource("example");
		File exampleDir = new File(exampleUrl.toURI());
		
		Path contentPath = new File(exampleDir, Roast.CONTENT).toPath();
		Path templatesPath = new File(exampleDir, Roast.TEMPLATES).toPath();
		
		Oven oven = new Oven(templatesPath);
		oven.roast(contentPath, tempPath);
	}
}
