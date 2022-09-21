package ir.smartplanning.server;

public class InfrastructureException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2942368232334493886L;

	public InfrastructureException() {
	}

	public InfrastructureException(String message) {
		super(message);
	}

	public InfrastructureException(String message, Throwable cause) {
		super(message, cause);
	}

	public InfrastructureException(Throwable cause) {
		super(cause);
	}
}
