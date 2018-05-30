package practise.string;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Properties;

public class IOMergeFiles {


	public static void mergeFiles(String existingFile, String toUpdateFile, String newFile) {
		try {
		Properties propsExisting = loadFromFile(existingFile);
		Properties propsToBeUpdated = loadFromFile(toUpdateFile);
		mergeProperties(propsExisting, propsToBeUpdated);
//		saveToFile(propsToBeUpdated, newFile);
		saveToFile2(propsToBeUpdated, newFile, toUpdateFile);
		Properties prop3 = loadFromFile(newFile);
		displayProperties(prop3);
		} catch (IOException ioex) {
			ioex.printStackTrace();
		}
	}

	private static void mergeProperties (Properties p1, Properties p2) {
		for(Object key: p2.keySet()) {
			if(p1.containsKey(key)) {
				p2.setProperty((String)key, p1.getProperty((String)key));
			}
		}
	}
	 private static Properties loadFromFile(String filename) throws IOException {
	        Path configLocation = Paths.get(filename);
	        try (InputStream stream = Files.newInputStream(configLocation)) {
	            Properties config = new OrderedProperties();
	            config.load(stream);
	            return config;
	        }
	}
	 private static void displayProperties(Properties props) {
		 props.entrySet().parallelStream().forEach(System.out::println);
	 }
	 private static Path saveToFile(Properties config, String newFileLocation) throws IOException {
		 		Files.deleteIfExists(Paths.get(newFileLocation));
	        Path file = Files.createFile(Paths.get(newFileLocation));
	        try (OutputStream stream = Files.newOutputStream(file)) {
	            config.store(stream, "Writing to a file using OutputStream:");
	        }
	        return file;
	    }

	 private static Path saveToFile2(Properties config, String newFileLocation, String toBeupdatedFileLocation) throws IOException {
     BufferedReader bis = Files.newBufferedReader(Paths.get(toBeupdatedFileLocation));
     StringBuilder propertyBuilder = new StringBuilder();
     String nextLineChar = "\n";
     String keyPropDelimiter = "=";
     while(bis.ready()) {
    	 String line = bis.readLine();
    	 if(line.startsWith("#")) {
    		 propertyBuilder.append(line);
    	 } else {
    		 String key = line.split(keyPropDelimiter)[0].trim();
    		 if(config.containsKey(key)) {
    			 propertyBuilder.append(key).append(keyPropDelimiter).append(config.getProperty(key).trim());
    		 }
    	 }
    	 propertyBuilder.append(nextLineChar);
     }
	 Files.deleteIfExists(Paths.get(newFileLocation));
     Path file = Files.createFile(Paths.get(newFileLocation));


     try (OutputStream stream = Files.newOutputStream(file)) {

    	 stream.write(propertyBuilder.toString().getBytes());
    //     config.store(stream, "Writing to a file using OutputStream:");
     }
     return file;
 }


	  static class OrderedProperties extends Properties {
	     private final LinkedHashSet<Object> keyOrder = new LinkedHashSet<>();

	     @Override
	     public synchronized Enumeration<Object> keys() {
	         return Collections.enumeration(keyOrder);
	     }

	     @Override
	     public synchronized Object put(Object key, Object value) {
	         keyOrder.add(key);
	         return super.put(key, value);
	     }
	 }
	 public static void main(String[] args) {
		 String existingFile = "/Users/junas01/Documents/workspace/practise-java8/src/main/resources/verifier_1.properties";
		 String toUpdateFile = "/Users/junas01/Documents/workspace/practise-java8/src/main/resources/verifier_2.properties";
		 String newFileLoc = "/Users/junas01/Documents/workspace/practise-java8/src/main/resources/verifier_2.properties";
		 mergeFiles(existingFile, toUpdateFile, newFileLoc);
	 }
}
