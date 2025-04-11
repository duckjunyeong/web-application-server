package util;

import util.parser.HttpBodyParser;
import util.parser.HttpHeaderParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;


public class HttpRequestReader {
  private HttpHeaderParser httpHeaderParser;
  private HttpBodyParser httpBodyParser;
  private InputStream inputStream;

  public HttpRequestReader(InputStream inputStream){
    this.inputStream = inputStream;
    this.httpHeaderParser = new HttpHeaderParser();
    this.httpBodyParser = new HttpBodyParser();
  }

  public void readHttpRequest() throws IOException {
    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

    httpHeaderParser.parseHeader(bufferedReader);

    String contentLength = httpHeaderParser.getHeader("Content-Length");
    if (contentLength != null){
      httpBodyParser.parseBody(bufferedReader, Integer.parseInt(contentLength));
    }
  }

  public String getHttpHeader(String key) {
    return httpHeaderParser.getHeader(key);
  }

  public String getHttpBody(String key){
    return httpBodyParser.getBody(key);
  }

  public String getAllHttpRequest() {
      return httpHeaderParser.getAllHeaderRequest() + httpBodyParser.getAllBodyRequest();
  }
}
