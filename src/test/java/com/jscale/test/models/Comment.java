package com.jscale.test.models;

import com.jscale.annotations.JScaleId;
import com.jscale.annotations.JScaleIndexed;
import com.jscale.annotations.JScaleIndexed.IndexType;
import java.util.Date;

public class Comment {
	private int id;
	private User author;
	private Date created;
	private String content;
	

	public Comment() {
	
	}

	public Comment(User author, String content) {
		this.author = author;
		this.content = content;
		created = new Date();
	}

	
	public int getId() {
		return id;
	}

	@JScaleId()
	public void setId(int id) {
		this.id = id;
	}
	
	@JScaleIndexed(types={IndexType.KEY})
	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	@JScaleIndexed(types={IndexType.RANGE})
	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	@JScaleIndexed(types={IndexType.FULLTEXT})
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
