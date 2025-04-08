package ie.atu.sw;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

//Time Complexity: O(n)
public class decoder {
	public static Map<Integer, String>[] loadReverseMaps(String csvFilePath) throws IOException {
	    Map<Integer, String> wordCountMap = new HashMap<>();
	    Map<Integer, String> suffixMap = new HashMap<>();

	    List<String> lines = Files.readAllLines(Paths.get(csvFilePath));
	    for (String line : lines) {
	        String[] parts = line.trim().split(",");
	        if (parts.length != 2) continue;

	        String key = parts[0].trim().toLowerCase();
	        int value = Integer.parseInt(parts[1].trim());

	        if (key.startsWith("@@")) {
	            suffixMap.put(value, key.substring(2));
	        } else {
	            wordCountMap.put(value, key);
	        }
	    }

	    return new Map[] { wordCountMap, suffixMap };
	}
	//Time Complexity: O(1)
	public static String decodeWord(List<Integer> codes, Map<Integer, String> wordCountMap, Map<Integer, String> suffixMap) {
	    if (codes.size() == 1) {
	        int code = codes.get(0);
	        return wordCountMap.getOrDefault(code, "[???]");
	    } else if (codes.size() == 2) {
	        String root = wordCountMap.getOrDefault(codes.get(0), "");
	        String suffix = suffixMap.getOrDefault(codes.get(1), "");
	        if (!root.isEmpty() || !suffix.isEmpty()) return root + suffix;
	    }

	    return "[???]";
	}
	// Time Complexity: O(n)
	public static String decodeLine(String encodedLine, Map<Integer, String> wordMap, Map<Integer, String> suffixMap) {
	    String[] tokens = encodedLine.trim().split("\\s+"); 
	    List<String> decodedWords = new ArrayList<>();

	    for (int i = 0; i < tokens.length; ) {
	        try {
	            int code = Integer.parseInt(tokens[i]);

	            if (code == 0) {
	                decodedWords.add("[???]");
	                i++;
	            } else if (i + 1 < tokens.length) {
	                int nextCode = Integer.parseInt(tokens[i + 1]);
	                // decoding as a root+suffix pair
	                String root = wordMap.get(code);
	                String suffix = suffixMap.get(nextCode);
	                if (root != null && suffix != null) {
	                    decodedWords.add(root + suffix);
	                    i += 2;
	                } else {
	                   
	                    decodedWords.add(wordMap.getOrDefault(code, "[???]"));
	                    i++;
	                }
	            } else {
	               
	                decodedWords.add(wordMap.getOrDefault(code, "[???]"));
	                i++;
	            }

	        } catch (NumberFormatException e) {
	            decodedWords.add("[???]");
	            i++;
	        }
	    }

	    return String.join(" ", decodedWords);
	}
	
	public static void decodeFile(String inputPath, String outputPath, Map<Integer, String> wordMap, Map<Integer, String> suffixMap) throws IOException {
	    List<String> lines = Files.readAllLines(Paths.get(inputPath));
	    List<String> decodedOutput = new ArrayList<>();

	    for (String line : lines) {
	        decodedOutput.add(decodeLine(line, wordMap, suffixMap));
	    }

	    Files.write(Paths.get(outputPath), decodedOutput);
	    System.out.println("Decoded output written to: " + outputPath);
	}

}
