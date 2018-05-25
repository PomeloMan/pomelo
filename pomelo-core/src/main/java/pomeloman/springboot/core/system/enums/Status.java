package pomeloman.springboot.core.system.enums;

public enum Status {

	/** 0 */ Init(0),
	/** 1 */ Valid(1),
	/** 2 */ Invalid(2),
	/** -1 */ Expired(-1);

	private int code;

	private Status(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public static Status valueOf(int value) {
		switch (value) {
		case 0:
			return Init;
		case 1:
			return Valid;
		case 2:
			return Invalid;
		case -1:
			return Expired;
		default:
			return Init;
		}
	}
}
