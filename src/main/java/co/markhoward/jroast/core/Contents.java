package co.markhoward.jroast.core;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import co.markhoward.jroast.core.oven.Oven;

public class Contents {
	private final Path contentPath;
	private final Oven oven;
	public Contents(Path contentPath, Oven oven){
		this.contentPath = contentPath;
		this.oven = oven;
	}
	
	public void compileContents(Path destinationPath) throws IOException{
		Files.walkFileTree(contentPath, new SimpleFileVisitor<Path>(){
			@Override
			public FileVisitResult preVisitDirectory(Path currentPath,
					BasicFileAttributes attrs) throws IOException {
				Path destination = destinationPath.resolve(contentPath.relativize(currentPath));
				if(!destination.equals(destinationPath))
					Files.copy(currentPath, destination, StandardCopyOption.REPLACE_EXISTING);
				oven.roast(currentPath, destination);
				return FileVisitResult.CONTINUE;
			}
		});
	}
	
	Logger logger = LogManager.getLogger(this.getClass());
}
