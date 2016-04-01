package co.markhoward.jroast.core.oven;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import co.markhoward.jroast.core.Roast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Resources;

public class ContentCompilerTest {
	@Rule
	public TemporaryFolder tempFolder = new TemporaryFolder();

	@Test
	public void shouldCompileContentAndTemplate() throws Exception {
		URL exampleUrl = Resources.getResource("example");
		File exampleDir = new File(exampleUrl.toURI());
		Path contentPath = new File(exampleDir, Roast.CONTENT).toPath();
		Path destinationPath = tempFolder.newFolder().toPath();
		ObjectMapper objectMapper = new ObjectMapper();
		Path templatesPath = new File(exampleDir, Roast.TEMPLATES).toPath();
		ContentCompiler contentCompiler = new ContentCompiler(contentPath, destinationPath, templatesPath, objectMapper);
		File file = contentCompiler.call();
		Assert.assertTrue(file.exists());
	}

}
