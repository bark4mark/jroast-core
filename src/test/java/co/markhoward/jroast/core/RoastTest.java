package co.markhoward.jroast.core;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class RoastTest {
	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();
	@Test
	public void shouldInitializeASite() throws IOException{
		File tempDirectory = temporaryFolder.newFolder();
		Path tempPath = tempDirectory.toPath();
		Roast roast = new Roast();
		roast.init(tempPath);
		Assert.assertTrue(new File(tempDirectory, Roast.ASSETS).exists());
		Assert.assertTrue(new File(tempDirectory, Roast.TEMPLATES).exists());
		Assert.assertTrue(new File(tempDirectory, Roast.CONTENT).exists());
	}
	
	
}
