package com.jscale.exceptions;

public class JScaleStructureException extends RuntimeException {

	public JScaleStructureException(Throwable thrwbl) {
		super(thrwbl);
	}

	public JScaleStructureException(String string, Throwable thrwbl) {
		super(string, thrwbl);
	}

	public JScaleStructureException(String string) {
		super(string);
	}

	public JScaleStructureException() {
	}

}
