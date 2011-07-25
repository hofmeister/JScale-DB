package com.jscale.exceptions;

public class JScaleQueryException extends RuntimeException {

	public JScaleQueryException(Throwable thrwbl) {
		super(thrwbl);
	}

	public JScaleQueryException(String string, Throwable thrwbl) {
		super(string, thrwbl);
	}

	public JScaleQueryException(String string) {
		super(string);
	}

	public JScaleQueryException() {
	}

}
