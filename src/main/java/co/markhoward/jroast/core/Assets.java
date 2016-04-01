package co.markhoward.jroast.core;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;

public class Assets {
	private final Path assetsPath;
	public Assets(Path assetsPath){
		this.assetsPath = assetsPath;
	}
	public void copyAssets(final Path destinationPath) throws IOException{
		Files.walkFileTree(assetsPath, new SimpleFileVisitor<Path>(){
			@Override
			public FileVisitResult preVisitDirectory(Path dir,
					BasicFileAttributes attrs) throws IOException {
				Path destination = destinationPath.resolve(assetsPath.relativize(dir));
				File destinationDirectory = destination.toFile();
				if(!destinationDirectory.exists())
					Files.copy(dir, destinationPath.resolve(assetsPath.relativize(dir)), StandardCopyOption.REPLACE_EXISTING);
				return FileVisitResult.CONTINUE;
			}
			@Override
			public FileVisitResult visitFile(Path file,
					BasicFileAttributes attrs) throws IOException {
				Files.copy(file, destinationPath.resolve(assetsPath.relativize(file)), StandardCopyOption.REPLACE_EXISTING);
				return FileVisitResult.CONTINUE;
			}
		});
	}
}
