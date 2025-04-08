package ie.atu.sw;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner; 
/**
*@author Samuel Cassiano Nunes
*
 */
public class Runner {
	private static Map<String, Integer> wordCountMap = new HashMap<>();
	private static Map<String, Integer> suffixMap = new HashMap<>();
	private static String inputTextFile = "";
	private static String outputFilePath = "./out.txt";
	
	
	
	public static void main(String[] args)  {
	
		Scanner scanner = new Scanner(System.in);
		boolean running = true;
		
		while(running) {
		System.out.println("************************************************************");
		System.out.println("*     ATU - Dept. of Computer Science & Applied Physics    *");
		System.out.println("*                                                          *");
		System.out.println("*              Encoding Words with Suffixes                *");
		System.out.println("*                                                          *");
		System.out.println("************************************************************");
		System.out.println("(1) Specify Mapping File");
		System.out.println("(2) Specify Text File to Encode");
		System.out.println("(3) Specify Output File (default: ./out.txt)");
		System.out.println("(4) Encode Text File");
		System.out.println("(5) Decode Text File");
		System.out.println("(0) Quit");
		
		System.out.print("Select Option [1-?]>");
		System.out.println();
		
		String input = scanner.nextLine();
		
		switch(input) {
		case "0":
			running = false;
			System.out.println("Exiting the program");
			break;
			
		case "1":
			System.out.println("Specify the mapping file: ");
			String mappingPath = scanner.nextLine();
			
			try {
				List<String> lines = Files.readAllLines(Paths.get(mappingPath));
				wordCountMap.clear();
				suffixMap.clear();
				
				for(String line: lines) {
					String[]parts = line.trim().split(",");
					if(parts.length != 2) continue;
					
					String key = parts[0].trim().toLowerCase();
					int value = Integer.parseInt(parts[1].trim());
					
					  if (key.startsWith("@@")) {
					        suffixMap.put(key.substring(2), value);
					    } else {
					    	wordCountMap.put(key, value);
					    }
				}
				
				   System.out.println("Mapping file loaded.");
			        System.out.println("Word count entries: " + wordCountMap.size());
			        System.out.println("Suffix entries: " + suffixMap.size());
				
			}catch (IOException e) {
		        System.out.println("Could not read the file");
		    }
			
			break;
			
		case "2":
			System.out.println("What do you want to encode? ");
			inputTextFile = scanner.nextLine();
			if(!Files.exists(Paths.get(inputTextFile))) {
				System.out.println("File not found");
			}else
				System.out.println("Input text file set to: " + inputTextFile);
			
			break;
			
		case "3":
			System.out.println("Especify the outputfile:  (./out.txt)");
			String userInput = scanner.nextLine().trim();
			 if (!userInput.isEmpty()) {
			        outputFilePath = userInput;
			    }
			 
			 System.out.println("Output file set to: " + outputFilePath);
			break;
			
		case "4":
			System.out.println("Encode text file: ");
			try {
				List<String> lines = Files.readAllLines(Paths.get(inputTextFile));
				List<String> encodedOutput = new ArrayList<>();
				
				for(String line : lines) {
					List<Integer> encodedLine = Encoder.encodeLine(line, wordCountMap, suffixMap);
					
				String encodedString = encodedLine.stream().map(String::valueOf).reduce((a, b) -> a + " " + b).orElse("");
				encodedOutput.add(encodedString);
                           

				}
				
				 Files.write(Paths.get(outputFilePath), encodedOutput);
			        System.out.println("✅ Text file successfully encoded and saved to: " + outputFilePath);
			        
			}catch (IOException e) {
		        System.out.println("❌ Error during file read/write: " + e.getMessage());
			}
			break;
			
		case "5":
		    System.out.println("Enter the path of the encoded file: ");
		    String encodedFilePath = scanner.nextLine();

		    if (!Files.exists(Paths.get(encodedFilePath))) {
		        System.out.println("❌ Encoded file not found.");
		        break;
		    }

		    System.out.println("Enter the output file path for decoded text (default: ./decoded.txt): ");
		    String decodedOutputFile = scanner.nextLine().trim();
		    if (decodedOutputFile.isEmpty()) {
		        decodedOutputFile = "./decoded.txt";
		    }

		  
		    Map<Integer, String> reverseWordMap = new HashMap<>();
		    Map<Integer, String> reverseSuffixMap = new HashMap<>();

		    for (Map.Entry<String, Integer> entry : wordCountMap.entrySet()) {
		        reverseWordMap.put(entry.getValue(), entry.getKey());
		    }

		    for (Map.Entry<String, Integer> entry : suffixMap.entrySet()) {
		        reverseSuffixMap.put(entry.getValue(), entry.getKey());
		    }

		   
		    try {
		        List<String> encodedLines = Files.readAllLines(Paths.get(encodedFilePath));
		        List<String> decodedLines = new ArrayList<>();

		        for (String line : encodedLines) {
		            String decodedLine = decoder.decodeLine(line, reverseWordMap, reverseSuffixMap);
		            decodedLines.add(decodedLine);
		        }

		        Files.write(Paths.get(decodedOutputFile), decodedLines);
		        System.out.println("✅ Decoded text saved to: " + decodedOutputFile);

		    } catch (IOException e) {
		        System.out.println("❌ Failed to decode file: " + e.getMessage());
		    }

		    break;

		}
		}
	}
}
			
			
	
