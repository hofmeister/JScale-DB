package com.jscale;

import com.jscale.server.JScaleNode;
import java.io.IOException;

public class JScale {
	public static void main(String[] args) throws IOException {
		JScaleNode server = new JScaleNode(2222);
		server.listen();
	}
}
