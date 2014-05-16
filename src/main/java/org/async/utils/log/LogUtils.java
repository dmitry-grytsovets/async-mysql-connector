package org.async.utils.log;

import java.nio.ByteBuffer;

public class LogUtils {
	public static String dump(ByteBuffer buffer,int radix,int sep,int newLine,int numberLength) {
		StringBuilder sb = new StringBuilder();
		int count=0;
		int nums=0;
		for(int i=0,e=buffer.limit();i<e;i++) {
			String v = Integer.toString(buffer.get(i)&0xFF,radix).toUpperCase();
			if(v.length()<numberLength) {
				for(int j=0;j<numberLength-v.length();j++) {
					sb.append("0");
				}
			}
			sb.append(v);
			sb.append(" ");
			count++;
			nums++;
			if(count==sep) {
				count=0;
				sb.append("| ");
			}
			if(nums%newLine==0) {
				sb.append("\n");
			}

		}
		return sb.toString();
	}
}
