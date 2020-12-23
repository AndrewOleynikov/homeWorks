package Main;

public enum Carry {

	M, 	N, 	C;

	public static Carry opp (Carry x, Carry y) {
		if (x == N && y == N) {
			return N;
		}
		if (x == N && y == M) {
			return N;
		}
		if (x == N && y == C) {
			return C;
		}
		if (x == M && y == N) {
			return N;
		}
		if (x == M && y == M) {
			return M;
		}
		if (x == M && y == C) {
			return C;
		}
		if (x == C && y == N) {
			return N;
		}
		if (x == C && y == M) {
			return C;
		}
		return C;
	}
}
