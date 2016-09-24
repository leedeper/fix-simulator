package com.leedeper.fixsimultor;

public class SimulatorException extends  Exception {
	private static final long serialVersionUID = 1L;

	public SimulatorException() {
	}

	public SimulatorException(String message) {
		super(message);
	}

	public SimulatorException(Throwable cause) {
		super(cause);
	}
	public SimulatorException(String message, Throwable cause) {
		super(message, cause);
	}

}
