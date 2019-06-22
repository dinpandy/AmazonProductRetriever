package config;
import java.io.IOException;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileToString {
	
	public static void main(String args[]) throws IOException
	{
	String content = readFile("src/test/resources/testdata.json", "UTF-8");
		
		System.out.println(content);
	}
	

public static String readFile(String path, String string) 
			  throws IOException 
			{
			  byte[] encoded = Files.readAllBytes(Paths.get(path));
			  return new String(encoded, string);
			}

}
