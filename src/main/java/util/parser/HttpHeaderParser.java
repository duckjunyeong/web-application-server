package util.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpHeaderParser {
  private Map<String, String> header;

  public HttpHeaderParser(){
    header = new HashMap<>();
  }

  public void parseHeader(BufferedReader br) throws IOException {

    String line;
    line = br.readLine();
    parseRequest(line);

    while ((line = br.readLine()) != null && !line.isEmpty()){
      String[] httpHeader = line.split(":");
      header.put(httpHeader[0], httpHeader[1].trim());
    }
  }

  public String getHeader(String key){
    return header.get(key);
  }

  private void parseRequest(String line){
    String[] request = line.split(" ");
    if (request.length != 3) throw new IllegalArgumentException("HTTP의 요청문이 올바르지 않습니다.");

    header.put("Method", request[0]);
    header.put("Path", request[1]);
    header.put("HttpVersion", request[2]);
  }

  public String getAllHeaderRequest() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(header.get("Method")).append(" ").append(header.get("Path")).append(" ").append(header.get("HttpVersion")).append("\r\n");

    for (String key : header.keySet()){
      if (key.equals("Method") || key.equals("Path") || key.equals("HttVersion")) continue;;
      stringBuilder.append(key + ": " + header.get(key) + "\r\n");
    }
    return stringBuilder.toString();
  }
}
