package utils;

public final class CommonUtils {

	public static boolean checkBitPattern(int pattern, int mask) {
		return (pattern & mask) != 0;
	}
}
