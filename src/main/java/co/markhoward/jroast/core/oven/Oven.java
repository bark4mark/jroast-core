package co.markhoward.jroast.core.oven;

import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Oven {
	private final ExecutorService service;
	private final ObjectMapper objectMapper;
	private final Path templatesPath;
	public Oven(Path templatesPath){
		this.service = Executors.newCachedThreadPool();
		this.templatesPath = templatesPath;
		this.objectMapper = new ObjectMapper();
	}
	
	public void roast(Path contentPath, Path destinationPath){
		ContentCompiler contentCompiler = new ContentCompiler(contentPath, destinationPath, templatesPath, objectMapper);
		service.submit(contentCompiler);
	}
	
	public void complete(){
		service.shutdown();
	}
}
