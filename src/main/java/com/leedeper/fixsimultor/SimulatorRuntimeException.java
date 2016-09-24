package com.leedeper.fixsimultor;

public class SimulatorRuntimeException extends  RuntimeException {
	private static final long serialVersionUID = 1L;

	public SimulatorRuntimeException() {
	}

	public SimulatorRuntimeException(String message) {
		super(message);
	}

	public SimulatorRuntimeException(Throwable cause) {
		super(cause);
	}
	public SimulatorRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

}
