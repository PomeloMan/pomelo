package pomeloman.core.module.project.enums;

public enum Visibility {

	/** 0 */ Public(0),
	/** 1 */ Private(1);

	private int code;

	private Visibility(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public static Visibility valueOf(int value) {
		switch (value) {
		case 0:
			return Public;
		case 1:
			return Private;
		default:
			return Public;
		}
	}
}
