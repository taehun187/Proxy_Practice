package com.taehun.arrayenum;

import java.io.BufferedReader;
import java.io.CharArrayReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import static java.lang.System.out;

public class GrowBufferedReader {
	private static final int srcBufSize = 10 * 1024;
	private static char[] src = new char[srcBufSize];
	static {
		src[srcBufSize - 1] = 'x';
	} 
	private static CharArrayReader car = new CharArrayReader(src);

	public static void main(String... args) {
		try {
			BufferedReader br = new BufferedReader(car);

			Class<?> c = br.getClass();
			Field f = c.getDeclaredField("cb");

			// cb는 private 필드입니다.
			f.setAccessible(true);
			char[] cbVal = char[].class.cast(f.get(br));

			char[] newVal = Arrays.copyOf(cbVal, cbVal.length * 2);
			if (args.length > 0 && args[0].equals("grow"))
				f.set(br, newVal);

			for (int i = 0; i < srcBufSize; i++)
				br.read();

			// 새로운 백업 배열이 사용되고 있는지 확인합니다.
			if (newVal[srcBufSize - 1] == src[srcBufSize - 1])
				out.format("Using new backing array, size=%d%n", newVal.length);
			else
				out.format("Using original backing array, size=%d%n", cbVal.length);

			// 실제 코드에서는 이 예외를 더 우아하게 처리해야 합니다.
		} catch (FileNotFoundException x) {
			x.printStackTrace();
		} catch (NoSuchFieldException x) {
			x.printStackTrace();
		} catch (IllegalAccessException x) {
			x.printStackTrace();
		} catch (IOException x) {
			x.printStackTrace();
		}
	}
}
