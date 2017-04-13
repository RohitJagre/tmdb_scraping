import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class DownloadImage {
	
	public static void downloadImg(String imdbid) throws IOException 
	{	//Takes the imdbid as argument
		String key = "";  //Your tmdb api_key when you sign up for the tmdb API
		String sUrl = "https://api.themoviedb.org/3/movie/tt"+imdbid+"?api_key="+key;
		URL url = new URL(sUrl);
		HttpURLConnection request = (HttpURLConnection) url.openConnection();
		request.connect();
		
		//Json code 
		//Had to add the Google Gson jar to classpath or add it to Maven POM
		JsonParser jp = new JsonParser();
		JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));	
		JsonObject rootobj = root.getAsJsonObject();
		String posterURL = rootobj.get("poster_path").getAsString(); //I want the poster; if you want anything from the Json place it here
		
		//Here you can select the poster size; Search for more sizes
		String base = "http://image.tmdb.org/t/p/w150";
		String finalURL = base+posterURL;
		
		InputStream is = new URL(finalURL).openStream();
		Files.copy(is, Paths.get("path/to/save/image.jpg"));  //Store the downloaded file locally
	}