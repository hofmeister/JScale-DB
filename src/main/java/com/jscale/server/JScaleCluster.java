package com.jscale.server;

import com.jscale.server.protocol.JScaleRequest;
import com.jscale.server.protocol.JScaleRequest.AdminCommand;
import com.jscale.server.protocol.JScaleRequest.AdminCommand.AdminCommandType;
import com.jscale.server.protocol.JScaleResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JScaleCluster {
	private Set<JScaleNode> nodes = new HashSet<JScaleNode>();
	
	public void addNode(JScaleNode node) {
		nodes.add(node);
	}
	
	protected Set<JScaleNode> getNodes() {
		return Collections.unmodifiableSet(nodes);
	}
	
	public <R extends JScaleResponse> R request(final JScaleRequest req,final Class<R> expectedResponse) {
		Thread[] threads = new Thread[nodes.size()];
		final List<R> responses = new ArrayList<R>();
		int i = 0;
		for(final JScaleNode node:nodes) {
			threads[i] = new Thread() {

				@Override
				public void run() {
					try {
						responses.add(request(node,req,expectedResponse));
					} catch (IOException ex) {
						Logger.getLogger(JScaleCluster.class.getName()).log(Level.SEVERE, null, ex);
					}
				}
			};
			threads[i].start();
			
			
		}
		for(Thread thread:threads) {
			try {
				thread.join();
			} catch (InterruptedException ex) {
				Logger.getLogger(JScaleCluster.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		
		R firstResponse = null;
		for(R response:responses) {
			if (firstResponse == null)
				firstResponse = response;
			else
				firstResponse.append(response);
		}
		
		return firstResponse;
	}
	
	public void request(JScaleRequest req) {
		request(req,JScaleResponse.class);
	}
	
	public <R extends JScaleResponse> R request(JScaleNode node,JScaleRequest req,Class<R> expectedResponse) throws IOException {
		return (R) node.request(req);
	}
	
	public void request(JScaleNode node,JScaleRequest req) throws IOException {
		request(node, req, JScaleResponse.class);
	}
	
	public void start() {
		request(new AdminCommand(AdminCommandType.STARTNODE));
	}
	
	public void stop() {
		request(new AdminCommand(AdminCommandType.STOPNODE));
	}
}
