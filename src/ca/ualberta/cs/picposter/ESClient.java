package ca.ualberta.cs.picposter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import ca.ualberta.cs.picposter.model.PicPostModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ESClient {
    /**
     * Taken from
     * https://github.com/homynyk/ESDemo/blob/master/ESDemo/src/ca/ualberta
     * /cs/CMPUT301/chenlei/ESClient.java search by keywords
     */

    // Http Connector
    private static HttpClient client = new DefaultHttpClient();

    // JSON Utilities
    private static Gson gson = new Gson();

    public static void searchPicPosts(String str)
	    throws ClientProtocolException, IOException {
	HttpGet searchRequest = new HttpGet(
		"http://cmput301.softwareprocess.es:8080/testing/homynyk/_search?pretty=1&q="
			+ java.net.URLEncoder.encode(str, "UTF-8"));
	HttpResponse response = client.execute(searchRequest);
	String status = response.getStatusLine().toString();
	System.out.println(status);

	String json = getEntityContent(response);

	Type elasticSearchSearchResponseType = new TypeToken<ElasticSearchSearchResponse<PicPostModel>>() {
	}.getType();
	ElasticSearchSearchResponse<PicPostModel> esResponse = gson.fromJson(
		json, elasticSearchSearchResponseType);
	System.err.println(esResponse);
	for (ElasticSearchResponse<PicPostModel> r : esResponse.getHits()) {
	    PicPostModel recipe = r.getSource();
	    System.err.println(recipe);
	}
    }

    static String getEntityContent(HttpResponse response) throws IOException {
	BufferedReader br = new BufferedReader(new InputStreamReader(
		(response.getEntity().getContent())));
	String output;
	System.err.println("Output from Server -> ");
	String json = "";
	while ((output = br.readLine()) != null) {
	    System.err.println(output);
	    json += output;
	}
	System.err.println("JSON:" + json);
	return json;
    }
}
