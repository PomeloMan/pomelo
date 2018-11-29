package pomelo.core.module.project.enums;

public enum Relation {

	/** 0 */ Child(0, 1),
	/** 1 */ Parent(1, 0),
	/** 2 */ DuplicateOf(2, 3),
	/** 3 */ Duplicate(3, 2),
	/** 4 */ Successor(4, 5), // 接班人
	/** 5 */ Predecessor(5, 4), // 前任
	/** 6 */ Related(6, 6);

	private int code;
	private int reverse;

	private Relation(int code, int reverse) {
		this.code = code;
		this.reverse = reverse;
	}

	public int getCode() {
		return code;
	}

	public Relation getReverse() {
		return valueOf(reverse);
	}

	public static Relation valueOf(int value) {
		switch (value) {
		case 0:
			return Child;
		case 1:
			return Parent;
		case 2:
			return DuplicateOf;
		case 3:
			return Duplicate;
		case 4:
			return Successor;
		case 5:
			return Predecessor;
		case 6:
			return Related;
		default:
			return Related;
		}
	}
}
