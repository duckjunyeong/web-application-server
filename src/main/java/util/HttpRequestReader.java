package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class HttpRequestReader {
  private String httpRequest;
  private InputStream inputStream;

  public HttpRequestReader(InputStream inputStream){
    this.inputStream = inputStream;
    httpRequest = null;
  }

  public void readHttpRequest() throws IOException {
    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
    StringBuilder stringBuilder = new StringBuilder();

    String line;
    while (true){
      line = bufferedReader.readLine();
      if (line == null || line.isEmpty()) break;
      stringBuilder.append(line).append("\n");
    }
    this.httpRequest =  stringBuilder.toString();
  }

  public String getHttpRequest() {
    return httpRequest;
  }
}
