package config;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;

public class JSONReader {

	public String fileRead() throws IOException {
		FileToString fs = new FileToString();
		 String dataFilePath = "src/test/resources/";
		 String dataFileName ="testdata.json";
		String content = fs.readFile(dataFilePath + dataFileName, "UTF-8");
		return content;
	}

	public String parseJson(String keyword) throws IOException, ParseException, JSONException {
		String value = null;
		try
		{
		String content = fileRead();
		JSONObject obj1 = new JSONObject(content);
		value = obj1.getString(keyword);
		
		}
		catch (Exception ex) {
	        ex.printStackTrace();
	    }
		return value;
	}

	public String parseJsonObj(String Obj, String keyword) throws IOException, ParseException, JSONException {
		String value = null;
		try
		{
		String content = fileRead();
		JSONObject obj1 = new JSONObject(content);

		JSONObject obj2 = obj1.getJSONObject(Obj);

		value = obj2.getString(keyword);

		
	}
	catch (Exception ex) {
        ex.printStackTrace();
    }
		return value;

	}

}