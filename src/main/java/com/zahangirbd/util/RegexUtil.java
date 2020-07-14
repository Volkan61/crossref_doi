package com.zahangirbd.util;

public class RegexUtil {
	
	//A DOI is made up of a prefix, which is "10." followed by a string of numbers, followed by a slash, followed by a suffix which is a sequence of any printable unicode characters.
    public static boolean isValidDoiId(String doiItemId) {
    	if(doiItemId == null) throw new IllegalArgumentException("Invalid DOI ID");
    	String regex = "10\\.\\d+\\/[a-zA-Z0-9]+";
    	return doiItemId.matches(regex);   	
    }
    
    public static void main(String[] args) {
    	System.out.println("Atcual=true, result="  + isValidDoiId("10.5555/12345678"));
    	System.out.println("Atcual=true, result="  + isValidDoiId("10.1/abcd123"));
    	System.out.println("Atcual=true, result="  + isValidDoiId("10.1/abcd"));
    	System.out.println("Atcual=true, result="  + isValidDoiId("10.2/2"));    	
    	System.out.println("Atcual=false, result="  + isValidDoiId("12313413412"));    	
    	System.out.println("Atcual=false, result="  + isValidDoiId("9.5555/12345678"));
    	System.out.println("Atcual=false, result="  + isValidDoiId("95555/12345678"));  
    	System.out.println("Atcual=false, result="  + isValidDoiId("/"));  
 	}

}
