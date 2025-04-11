package util.handler;

import util.HttpRequestReader;

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

  public String getHttpHeader(String key){
    return httpRequestReader.getHttpHeader(key);
  }

  public String getHttpBody(String key){
    return httpRequestReader.getHttpBody(key);
  }

  public String getAllHttpRequest() {
    return httpRequestReader.getAllHttpRequest();
  }
}
