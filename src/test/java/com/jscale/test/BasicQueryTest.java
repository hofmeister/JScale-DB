/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jscale.test;

import com.jscale.api.JScaleRepository;
import com.jscale.server.JScaleCluster;
import com.jscale.server.JScaleNode;
import com.jscale.test.models.Comment;
import com.jscale.test.models.Recipe;
import com.jscale.test.models.Recipe.Ingredient;
import com.jscale.test.models.Recipe.RecipePart;
import com.jscale.test.models.Recipe.Unit;
import com.jscale.test.models.User;
import junit.framework.TestCase;

/**
 *
 * @author henrik
 */
public class BasicQueryTest extends TestCase {
	private JScaleCluster cluster = new JScaleCluster();
	
	private JScaleRepository db = new JScaleRepository(cluster,"db");
	private User user1 = new User("Henrik","henrik@newdawn.dk");
	private User user2 = new User("Tester","test@newdawn.dk");
	private Recipe recipe = new Recipe(user1,"Lasagne");
	
	@Override
	protected void setUp() throws Exception {
		db.addModel(User.class);
		db.addModel(Recipe.class);
		db.addModel(Comment.class);
		
		cluster.addNode(new JScaleNode(6301)); 
		cluster.addNode(new JScaleNode(6311)); 
		cluster.addNode(new JScaleNode(6321)); 
		cluster.addNode(new JScaleNode(6331)); 
		cluster.start();
		//Init recipe
		recipe.setDescription("Cook it!");
		recipe.addPart(new RecipePart(Ingredient.MILK,1, Unit.L));
		recipe.addPart(new RecipePart(Ingredient.MEAT,500,Unit.G));
		recipe.addComment(new Comment(user2,"It rocks!"));
	}
	
	
	@Override
	protected void tearDown() throws Exception {
		cluster.stop();
	}
	public void test_inserts() {
		db.insert(recipe);
		assertEquals("Row inserted",1,db.count(Recipe.class));
	}
	
	public void test_get_by_id() {
		db.insert(recipe);
		assertEquals("Row found",recipe,db.getDocument(Recipe.class,recipe.getId()));
	}
	public void test_simple_select() {
		db.insert(recipe);
		assertEquals("Row found",recipe,db.fetchRow(Recipe.class,"SELECT Id FROM Recipe WHERE Id = %s",recipe.getId()));
	}
}
