package util;

import java.io.InputStream;

public class HttpRequestHandler {
  private HttpRequestReader httpRequestReader;

  public HttpRequestHandler(InputStream in){
    this.httpRequestReader = new HttpRequestReader(in);
  }

  public void readHttpRequest(){
    try{
      httpRequestReader.readHttpRequest();
    }
    catch (Exception e){
      throw new IllegalArgumentException("Invalid Http request");
    }
  }

  public String[] getHttpRequest(){
    return httpRequestReader.getHttpRequest();
  }

  public String getLine(int lineNumber){
    return httpRequestReader.getHttpRequest()[lineNumber];
  }

  public String getMethod(){
    isValidHttpRequestArrayed();
    return  httpRequestReader.getHttpRequest()[0].split(" ")[0];
  }

  public String getPath(){
    isValidHttpRequestArrayed();
    return  httpRequestReader.getHttpRequest()[0].split(" ")[1];
  }

  public String getQuery(String key){
    return httpRequestReader.getQuery(key);
  }

  private void isValidHttpRequestArrayed() {
    if ( httpRequestReader.getHttpRequest() == null) throw new IllegalArgumentException("httpRequestArrayed is null");
  }
}
