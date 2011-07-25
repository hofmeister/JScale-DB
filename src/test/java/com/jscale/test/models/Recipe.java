package com.jscale.test.models;

import com.jscale.annotations.JScaleId;
import com.jscale.annotations.JScaleIndexed;
import com.jscale.annotations.JScaleIndexed.*;
import java.util.ArrayList;
import java.util.List;

public class Recipe  {
	private int id;
	private User author;
	private String title;
	private String description;
	private List<RecipePart> parts = new ArrayList<RecipePart>();
	private List<Comment> comments = new ArrayList<Comment>();

	@JScaleId
	public int getId() {
		return id;
	}

	@JScaleId
	public void setId(int id) {
		this.id = id;
	}

	public Recipe(User author, String title) {
		this.author = author;
		this.title = title;
	}

	@JScaleIndexed(types={IndexType.KEY})
	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	@JScaleIndexed(types={IndexType.FULLTEXT})
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<RecipePart> getParts() {
		return parts;
	}

	public void setParts(List<RecipePart> parts) {
		this.parts = parts;
	}
	public void addPart(RecipePart part) {
		parts.add(part);
	}

	@JScaleIndexed(types={IndexType.FULLTEXT})
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public void addComment(Comment comment) {
		comments.add(comment);
	}


	
	
	
	public static class RecipePart {
		private Ingredient ingredient;
		private float amount;
		private Unit unit;
		private String note;
		
		public RecipePart() {
		
		}
		
		public RecipePart(Ingredient ingredient, float amount, Unit unit) {
			this(ingredient, amount, unit, null);
		}
		
		public RecipePart(Ingredient ingredient, float amount, Unit unit, String note) {
			this.ingredient = ingredient;
			this.amount = amount;
			this.unit = unit;
			this.note = note;
		}
		

		@JScaleIndexed(types={IndexType.KEY})
		public float getAmount() {
			return amount;
		}

		public void setAmount(float amount) {
			this.amount = amount;
		}

		@JScaleIndexed(types={IndexType.KEY})
		public Ingredient getIngredient() {
			return ingredient;
		}

		public void setIngredient(Ingredient ingredient) {
			this.ingredient = ingredient;
		}

		
		public String getNote() {
			return note;
		}

		public void setNote(String note) {
			this.note = note;
		}

		@JScaleIndexed(types={IndexType.KEY})
		public Unit getUnit() {
			return unit;
		}

		public void setUnit(Unit unit) {
			this.unit = unit;
		}
	}
	
	public static enum Ingredient {
		MILK,WATER,SALT,PEPPER,MEAT,BREAD
	}
	public static enum Unit {
		L,DL,G,KG
	}
}
