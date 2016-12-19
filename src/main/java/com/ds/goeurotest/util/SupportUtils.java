package com.ds.goeurotest.util;
import java.util.Random;

public class SupportUtils {
	
	public static int getRandom(int upperLimit) {
		return new Random().nextInt(upperLimit);
	}

}
