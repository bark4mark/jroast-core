package co.markhoward.jroast.core.oven;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import co.markhoward.jroast.core.Content;
import co.markhoward.jroast.core.Roast;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.io.ByteSink;
import com.google.common.io.Files;

public class ContentCompiler implements Callable<File> {
	private final Path contentPath;
	private final Path destinationPath;
	private final ObjectMapper objectMapper;
	private final Path templatesPath;

	public ContentCompiler(Path contentPath, Path destinationPath, Path templatesPath,
			ObjectMapper objectMapper) {
		this.contentPath = contentPath;
		this.destinationPath = destinationPath;
		this.objectMapper = objectMapper;
		this.templatesPath = templatesPath;
	}

	@Override
	public File call() throws Exception {
		File contentDirectory = contentPath.toFile();
		File[] contentFiles = contentDirectory.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				String extension = Files.getFileExtension(name);
				if (Roast.CONTENT_EXTENSION.equalsIgnoreCase(extension))
					return true;
				return false;
			}
		});

		StringBuilder contentHtml = new StringBuilder();
		List<Content> contents = prepareContents(contentFiles);
		String template = null;
		for(Content content: contents){
			if(!Content.CONTENT_PUBLISHED.equalsIgnoreCase(content.getStatus()))
				continue;
			if(template == null){
				template = content.getTemplate();
			}
			contentHtml.append(content.getContent());
		}
		
		File templateDirectory = templatesPath.toFile();
		String templateCode = Files.toString(new File(templateDirectory, String.format("%s.%s", template, Roast.TEMPLATES_EXTENSION)), Charsets.UTF_8);
		VelocityContext context = new VelocityContext();
		context.put("content", contentHtml);
		StringWriter stringWriter = new StringWriter();
		Velocity.evaluate(context, stringWriter, template, templateCode);
		
		File destinationDirectory = destinationPath.toFile();
		File desintationFile = new File(destinationDirectory, Roast.INDEX);
		ByteSink sink = Files.asByteSink(desintationFile);
		sink.write(stringWriter.toString().getBytes());
		return desintationFile;
	}

	private List<Content> prepareContents(File[] contentFiles) {
		List<Content> contents = new ArrayList<>();
		for (File contentFile : contentFiles)
			contents.add(prepareContent(contentFile));
		Collections.sort(contents);
		return contents;
	}

	private Content prepareContent(File contentFile) {
		Content content = new Content();
		try {
			String fullContent = Files.toString(contentFile, Charsets.UTF_8);
			String[] splitContent = fullContent.split(Roast.SEPARATOR);
			String metaDataJson = splitContent[0];
			String contentValue = splitContent[1];
			Map<String, String> metaData = objectMapper.readValue(metaDataJson,
					new TypeReference<Map<String, String>>() {
					});
			content.setCreated(getContentDate(contentFile, metaData));
			content.setTemplate(metaData.get(Content.TEMPLATE));
			content.setSequence(Integer.valueOf(metaData.get(Content.SEQUENCE)));
			content.setTitle(metaData.get(Content.TITLE));
			content.setContent(contentValue);
			content.setStatus(metaData.get(Content.STATUS));
		} catch (IOException exception) {
			logger.error("Cannot read content from file: {}", contentFile,
					exception);
		}
		return content;
	}

	private Date getContentDate(File contentFile, Map<String, String> metaData) {
		String dateAsString = metaData.get(Content.CREATED);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"HH:mm a - dd MMM yyyy");
		try {
			return simpleDateFormat.parse(dateAsString);
		} catch (ParseException exception) {
			logger.error("An error has occurred parsing the date, using last modified date");
			return new Date(contentFile.lastModified());
		}
	}

	private Logger logger = LogManager.getLogger(this.getClass());
}
