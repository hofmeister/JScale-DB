package com.jscale.server;
import com.jscale.server.protocol.JScaleRequest;
import com.jscale.server.protocol.JScaleResponse;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JScaleNode {
	private Selector selector;
	private final int port;
	private final String host;
	private boolean running = false;
	private Handler[] handlers;
	private Listener listener;

	public JScaleNode(int port) {
		this(port,null);
	}
	public JScaleNode(int port, String host) {
		this.port = port;
		this.host = host;
	}
	public boolean isLocal() {
		return host == null 
				|| host.equalsIgnoreCase("localhost") 
				|| host.equals("0.0.0.0")
				|| host.equals("127.0.0.1");
	}

	
	
	public void start() throws IOException {
		if (!isLocal()) 
			throw new UnsupportedOperationException("Cannot start non-local nodes directly");
		selector = Selector.open();
		running = true;
		handlers  = new Handler[] {
			new Handler(port)
		};
		listener.start();
		
	}
	public void stop() throws IOException {
		if (!isLocal()) 
			throw new UnsupportedOperationException("Cannot stop non-local nodes directly");
		running = false;
		try {
			
			for(Handler handler:handlers) {
				handler.close();
			}
			listener.interrupt();
			listener.join();
		} catch (InterruptedException ex) {}
		
	}

	public void listen() throws IOException {
		start();
		try {
			listener.join();
		} catch (InterruptedException ex) {
			stop();
		}
	}

	public JScaleResponse request(JScaleRequest req) throws IOException {
		ObjectInputStream in = null;
		Socket socket = null;
		try {
			socket = new Socket(host, port);
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			out.writeObject(req);
			Object respObj = in.readObject();
			JScaleResponse resp = null;
			if (respObj != null)
				resp = (JScaleResponse) respObj;
			return resp;
		} catch (Throwable ex) {
			throw new IOException(ex);
		} finally {
			try {
				in.close();
				socket.close();
			} catch (IOException ex) {
				Logger.getLogger(JScaleNode.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
	private class Listener extends Thread {
		ByteBuffer buf;
		@Override
		public void run() {
			try {
				buf = ByteBuffer.allocateDirect(1024);
				while(running) {
					if (Thread.interrupted()) {
						gracefulExit();
						return;
					}
					
					try {
						selector.select();
						Iterator<SelectionKey> it = selector.selectedKeys().iterator();
						while(it.hasNext()) {
							SelectionKey key = it.next();
							it.remove();
							if (key.isAcceptable()) {
								SocketChannel channel = ((ServerSocketChannel) key.channel()).accept();
								if (channel == null)
									continue;
								handleRequest(channel);
							}
							
						}
					} catch (Throwable ex) {
						Logger.getLogger(JScaleNode.class.getName()).log(Level.SEVERE, null, ex);
					}
				}
			} finally {
				buf.clear();
			}
		}
		private void gracefulExit() {
			try {
				JScaleNode.this.stop();
			} catch (IOException ex) {
				Logger.getLogger(JScaleNode.class.getName()).log(Level.SEVERE, null, ex);
			}
		}

		private void handleRequest(SocketChannel channel) throws IOException {
			buf.clear();
			List<Byte> bytes = new ArrayList<Byte>();
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			while(true) {
				int bytesRead = channel.read(buf);
				if (bytesRead > 0) {
					bout.write(buf.array());
				} else {
					break;
				}
			}
			buf.clear();
			ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
			ObjectInputStream in = new ObjectInputStream(bin);
			
		}
	}
	protected class Handler {
		private ServerSocketChannel ssc;

		private Handler(int port) throws IOException {
			ssc = ServerSocketChannel.open();
			ssc.socket().bind(new InetSocketAddress(port));
			ssc.configureBlocking(false);
			ssc.register(selector,SelectionKey.OP_ACCEPT);
		}
		private void close() throws IOException {
			ssc.close();
		}
		
	}
}
