package com.jscale.api;

import java.util.List;

public class JScaleQuery {

	public static JScaleQuery fromString(String query) {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	public static JScaleQuery fromString(String query, Object ... args) {
		throw new UnsupportedOperationException("Not yet implemented");
	}
	public static class Select extends JScaleQuery {
		private List<JScaleField> docFields;
	}
}
