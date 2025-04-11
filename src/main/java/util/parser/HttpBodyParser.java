package util.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpBodyParser {
  private Map<String, String> body;

  public HttpBodyParser(){
    body = new HashMap<>();
  }

  public void parseBody(BufferedReader bufferedReader, int contentLength) throws IOException {
    if (contentLength > 0){
      char[] queryData = new char[contentLength];
      bufferedReader.read(queryData, 0, contentLength);

      String[] splitedQuery = new String(queryData).split("&");
      for (String query : splitedQuery){
        String[] keyValues = query.split("=");
        body.put(keyValues[0], keyValues[1]);
      }
    }
  }

  public String getBody(String key){
    return body.get(key);
  }

  public String getAllBodyRequest() {
    StringBuilder stringBuilder = new StringBuilder();
    for (String key : body.keySet()){
      stringBuilder.append(key + "=" + body.get(key) + "&");
    }
    return stringBuilder.toString();
  }
}
