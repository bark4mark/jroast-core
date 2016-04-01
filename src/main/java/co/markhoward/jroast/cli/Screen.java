package co.markhoward.jroast.cli;

import java.io.IOException;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

public class Screen {

	public void writeTextFromFile(String file) {
		try {
			URL introTextUrl = Resources.getResource("intro.txt");
			String introText = Resources.toString(introTextUrl, Charsets.UTF_8);
			write(introText);
		} catch (IOException exception) {
			logger.error("There was an error loading the text", exception);
		}
	}
	
	public void write(String text){
		System.out.println(text);
	}
	
	private static Logger logger = LogManager.getLogger(Screen.class);
}
