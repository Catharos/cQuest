package net.catharos.cquest.quest;

public class IllegalQuestException extends Exception {
	private static final long serialVersionUID = 1L;

	private final String reason;

	public IllegalQuestException(String r) {
		this.reason = r;
	}

	/** Returns the reason */
	public String getReason() {
		return this.reason;
	}
}
