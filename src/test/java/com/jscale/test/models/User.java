package com.jscale.test.models;

import com.jscale.annotations.JScaleId;
import com.jscale.annotations.JScaleIndexed;
import com.jscale.annotations.JScaleIndexed.*;
import com.jscale.api.JScaleIdGenerator.*;


public class User {
	private String username;
	private String password;
	private String name;
	private String email;

	public User(String name, String email) {
		this.name = name;
		this.email = email;
	}

	@JScaleId(generator=NONE.class)
	@JScaleIndexed(types={IndexType.FULLTEXT, IndexType.KEY})
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@JScaleIndexed(types={IndexType.FULLTEXT, IndexType.KEY})
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@JScaleIndexed(types={IndexType.FULLTEXT, IndexType.KEY})
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@JScaleIndexed(types={IndexType.FULLTEXT, IndexType.KEY})
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
