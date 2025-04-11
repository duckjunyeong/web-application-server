package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;


public class HttpRequestReader {
  private String[] request;
  private QueryHandler queryHandler;
  private CookieHandler cookieHandler;
  private InputStream inputStream;

  public HttpRequestReader(InputStream inputStream){
    this.inputStream = inputStream;
    this.cookieHandler = null;
    request = null;
  }

  public void readHttpRequest() throws IOException {
    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

    int contentLength = readRequestHeader(bufferedReader);
    readQueryString(contentLength, bufferedReader);
  }

  private int readRequestHeader(BufferedReader bufferedReader) throws IOException {
    StringBuilder stringBuilder = new StringBuilder();

    String line;
    int contentLength = 0;
    while ((line = bufferedReader.readLine()) != null && !line.isEmpty()){
      stringBuilder.append(line).append("\r\n");
      if (line.startsWith("Content-Length")){
        contentLength = Integer.parseInt(line.substring(15).trim());
      }
      else if(line.startsWith("Cookie")){
        cookieHandler = new CookieHandler(line.substring(7).replace(" ", ""));
      }
    }
    this.request =  (stringBuilder.toString()).split("\r\n");
    return contentLength;
  }

  private void readQueryString(int contentLength, BufferedReader bufferedReader) throws IOException {
    if (contentLength > 0){
      char[] queryData = new char[contentLength];
      bufferedReader.read(queryData, 0, contentLength);
      this.queryHandler = new QueryHandler(new String(queryData));
    }
  }

  public String[] getHttpRequest() {
    return request;
  }

  public String getQuery(String key){
    return queryHandler.getQuery(key);
  }

  public String getCookie(String key) {
    if (cookieHandler == null) return null;
    return cookieHandler.getCookie(key);
  }
}
