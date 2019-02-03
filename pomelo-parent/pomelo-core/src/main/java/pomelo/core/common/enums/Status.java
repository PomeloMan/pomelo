package pomelo.core.common.enums;

public enum Status {

	FAILURE(0),
	SUCCESS(1);

	private int value;

	Status(int value) {
		this.value = value;
	}

	public int value() {
		return this.value;
	}
}
