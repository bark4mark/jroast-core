package co.markhoward.jroast.core;

import java.util.Date;

import lombok.Data;

@Data
public class Content implements Comparable<Content>{
	private Date created;
	private int sequence;
	private String template;
	private String title;
	private String content;
	private String status;
	
	@Override
	public int compareTo(Content other) {
		if(this.sequence == other.sequence)
			return 0;
		if(this.sequence > other.sequence)
			return 1;
		return -1;
	}
	
	public static final String TITLE = "title";
	public static final String STATUS = "status";
	public static final String TEMPLATE = "template";
	public static final String CREATED = "created";
	public static final String SEQUENCE = "sequence";
	
	public static final String CONTENT_PUBLISHED = "published";
	public static final String CONTENT_PENDING = "pending";
}
