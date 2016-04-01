package co.markhoward.jroast.core;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.google.common.io.Resources;

public class AssetsTest {
	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();
	@Test
	public void shouldCopyAssets() throws IOException, URISyntaxException{
		File tempDirectory = temporaryFolder.newFolder(Roast.ASSETS);
		Path tempPath = tempDirectory.toPath();
		URL boilerplateUrl = Resources.getResource("boilerplate");
		File boilerplateDir = new File(boilerplateUrl.toURI());
		Assets assets = new Assets(boilerplateDir.toPath());
		assets.copyAssets(tempPath);
		Assert.assertTrue(tempPath.toFile().list().length > 0);
	}
}
