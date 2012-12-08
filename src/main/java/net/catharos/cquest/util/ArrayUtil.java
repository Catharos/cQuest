package net.catharos.cquest.util;

public class ArrayUtil {
	/**
	 * Join array elements with a string
	 * 
	 * @param glue The join string
	 * @param pieces The array of strings to implode
	 * @return String The joined String
	 */
	public static String implode(String glue, String[] pieces) {
		if(pieces.length < 1) return "";
		
		StringBuilder builder = new StringBuilder(pieces[0]);
		for(int i = 1; i < pieces.length; i++) builder.append(glue).append(pieces[i]);
		
		return builder.toString();
	}
}
