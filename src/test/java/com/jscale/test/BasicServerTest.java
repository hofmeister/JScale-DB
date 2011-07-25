/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jscale.test;

import com.jscale.server.JScaleCluster;
import com.jscale.server.JScaleNode;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import junit.framework.TestCase;

/**
 *
 * @author henrik
 */
public class BasicServerTest extends TestCase {
	private JScaleCluster cluster = new JScaleCluster();

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		cluster.addNode(new JScaleNode(6301)); 
		cluster.addNode(new JScaleNode(6311)); 
		cluster.addNode(new JScaleNode(6321)); 
		cluster.addNode(new JScaleNode(6331)); 
		cluster.start();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		cluster.stop();
	}
	
	
	
	public void test_cluster_members_can_see_eachother() {
		
	}
}
