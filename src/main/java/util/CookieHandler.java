package util;

import java.util.HashMap;
import java.util.Map;

public class CookieHandler {
  private Map<String, String> cookie;

  public CookieHandler(String cookieStr){
    cookie = new HashMap<>();

    String[] splitedCookieStr = cookieStr.split(";");
    for (String str : splitedCookieStr){
      String[] extractKeyValue = str.split("=");
      cookie.put(extractKeyValue[0], extractKeyValue[1]);
    }
  }

  public String getCookie(String key){
    return cookie.get(key);
  }
}
