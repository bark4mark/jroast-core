package co.markhoward.jroast.core;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import co.markhoward.jroast.core.oven.Oven;

public class Roast {
	public void init(Path path){
		createDirectoryStructure(path);
	}
	
	public void roast(Path path) throws IOException{
		File currentDirectory = path.toFile();
		File templatesDirectory = new File(currentDirectory, Roast.TEMPLATES);
		File contentDirectory = new File(currentDirectory, Roast.CONTENT);
		File outputDirectory = new File(currentDirectory, Roast.OUTPUT);
		File assetsDirectory = new File(currentDirectory, Roast.ASSETS);
		
		Assets assets = new Assets(assetsDirectory.toPath());
		File assetsOutputDirectory = new File(outputDirectory, Roast.ASSETS);
		if(!assetsOutputDirectory.exists())
			assetsOutputDirectory.mkdirs();
		assets.copyAssets(new File(outputDirectory, Roast.ASSETS).toPath());
		
		Oven oven = new Oven(templatesDirectory.toPath());
		Contents contents = new Contents(contentDirectory.toPath(), oven);
		contents.compileContents(outputDirectory.toPath());
		oven.complete();
	}
	
	public void clean(Path path) throws IOException{
		Path outputPath = path.resolve(Roast.OUTPUT);
		Files.walkFileTree(outputPath, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				Files.delete(file);
				return FileVisitResult.CONTINUE;
			}
			@Override
			public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
				Files.delete(dir);
				return FileVisitResult.CONTINUE;
			}
		});
	}
	
	private void createDirectoryStructure(Path path) {
		File assetsDirectory = new File(path.toFile(), ASSETS);
		assetsDirectory.mkdir();
		File templatesDirectory = new File(path.toFile(), TEMPLATES);
		templatesDirectory.mkdir();
		File contentDirectory = new File(path.toFile(), CONTENT);
		contentDirectory.mkdir();
	}
	
	public static final String ASSETS = "assets";
	public static final String TEMPLATES = "templates";
	public static final String CONTENT = "content";
	public static final String CONTENT_EXTENSION = "content";
	public static final String TEMPLATES_EXTENSION = "template";
	public static final String ROAST_EXTENSION = "html";
	public static final String INDEX = "index.html";
	public static final String SEPARATOR = "~~~~~";
	public static final String OUTPUT = "output";
}
