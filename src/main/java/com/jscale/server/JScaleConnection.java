package com.jscale.server;

import com.jscale.server.JScaleNode.Handler;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.logging.Level;
import java.util.logging.Logger;

abstract class JScaleConnection extends Thread {
	private SocketChannel channel;
	private Handler listener;

	public JScaleConnection(Handler listener, SocketChannel channel) {
		this.listener = listener;
		this.channel = channel;
	}

	@Override
	public void run() {
		try {
			execute();
		} catch (IOException ex) {
			Logger.getLogger(JScaleConnection.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final JScaleConnection other = (JScaleConnection) obj;
		if (this.channel != other.channel && (this.channel == null || !this.channel.equals(other.channel))) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 79 * hash + (this.channel != null ? this.channel.hashCode() : 0);
		return hash;
	}
	
	
	protected void execute() throws IOException {
		ByteBuffer buf = ByteBuffer.allocateDirect(1024);
		while(true) {
			buf.clear();
			int bytesRead = channel.read(buf);
			if (bytesRead == -1) {
				break;
			} else {
				buf.flip();
			}
		}
	};

}
