package ie.atu.sw;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;

public class Encoder {
public static void main(String[]args) {
	   String filePath = "";
	  
	   Map<String,Integer> wordCountMap = new HashMap<>();
	   Map<String,Integer>suffixMap = new HashMap<>();
	
	try {
		
		List<String> lines = Files.readAllLines(Paths.get(filePath));
		for (String line : lines) {
			String[] parts = line.trim().split(",");
		    if (parts.length != 2) continue;

		    String key = parts[0].trim().toLowerCase();
		    int value = Integer.parseInt(parts[1].trim());

		    if (key.startsWith("@@")) {
		        suffixMap.put(key.substring(2), value);
		    } else {
		    	wordCountMap.put(key, value);
		    }
		}

		
		
         
		System.out.println("Loaded wordCountMap size: " + wordCountMap.size());
		System.out.println("Loaded suffixMap size: " + suffixMap.size());
		
		String testLine = "Unhappiness grows quickly!";
		System.out.println("Encoded line: " + encodeLine(testLine, wordCountMap, suffixMap));

         
	}catch (IOException e) {
		e.printStackTrace();
	}
	

}
//Time Complexity: O(n)
public static List<Integer> encodeWord(String word,Map<String,Integer> wordCountMap, Map<String,Integer>suffixMap){
	word = word.toLowerCase();
			if(wordCountMap.containsKey(word)) {
		return Arrays.asList(wordCountMap.get(word));	//converts a single number into a list
	}
	
	for(int i=1; i< word.length();i++) {
		String root = word.substring(0,i);
		String suffix = word.substring(i);
		
		if(wordCountMap.containsKey(root)&& suffixMap.containsKey(suffix)) {
			return Arrays.asList(wordCountMap.get(root), suffixMap.get(suffix));
		}
	}
	
	return Arrays.asList(0);
}

//Time Complexity: O(n * m)
public static List<Integer> encodeLine(String line, Map<String,Integer> wordCountMap,Map<String,Integer>suffixMap){
	List<Integer> encoded = new ArrayList<>();
	String[] words = line.toLowerCase().split("\\s+");
	
	for(String word :words) {
		String cleaned = word.replaceAll("[^a-z]", "");
		if(!cleaned.isEmpty()) {
			List<Integer> wordEncoding = encodeWord(cleaned,wordCountMap,suffixMap);
			encoded.addAll(wordEncoding);
		}
	}
	return encoded;
}



}
