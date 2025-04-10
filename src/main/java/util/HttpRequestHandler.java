package util;

public class HttpRequestHandler {
  private HttpRequestReader httpRequestReader;
  private String[] httpRequestArrayed;

  public HttpRequestHandler(HttpRequestReader httpRequestReader){
    this.httpRequestReader = httpRequestReader;
    this.httpRequestArrayed = null;
  }

  public void readHttpRequest(){
    try{
      httpRequestReader.readHttpRequest();
      httpRequestArrayed = httpRequestReader.getHttpRequest().split("\\n");
    }
    catch (Exception e){
      throw new IllegalArgumentException("Invalid Http request");
    }
  }

  public String getHttpRequest(){
    return httpRequestReader.getHttpRequest();
  }

  public String getLine(int lineNumber){
    return httpRequestArrayed[lineNumber];
  }

  public String getMethod(){
    isValidHttpRequestArrayed();
    return httpRequestArrayed[0].split(" ")[0];
  }

  public String getPath(){
    isValidHttpRequestArrayed();
    return httpRequestArrayed[0].split(" ")[1];
  }

  public String getQuery(String key){
    return httpRequestReader.getQuery(key);
  }

  private void isValidHttpRequestArrayed() {
    if (httpRequestArrayed == null) throw new IllegalArgumentException("httpRequestArrayed is null");
  }
}
