package util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UrlQueryHandler {

  private String url;
  private List<String[]> query;

  public UrlQueryHandler(String url){
    this.url = url;
    extractQuery(url);
  }

  public String getQuery(String key){
    for (String[] keyValue : query){
      if (keyValue[0].equals(key)){
        return keyValue[1];
      }
    }
    return null;
  }

  private void extractQuery(String url) {
    int queryStart = url.indexOf("?") + 1;
    String[] splited = (url.substring(queryStart)).split("&");
    query = Arrays.stream(splited)
        .map(str -> str.split("="))
        .collect(Collectors.toList());
  }
}
