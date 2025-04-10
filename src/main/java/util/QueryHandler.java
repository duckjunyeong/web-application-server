package util;

import java.util.HashMap;
import java.util.Map;

public class QueryHandler {
  private Map<String, String> query;

  public QueryHandler(String queryString){
    query = new HashMap<>();
    parseQuery(queryString);
  }

  public String getQuery(String key){
    return query.get(key);
  }

  private void parseQuery(String queryString) {
    int queryStart = queryString.indexOf("?") + 1;
    String[] splitedQuery = (queryString.substring(queryStart)).split("&");

    for (int i = 0; i < splitedQuery.length; i++){
      String[] getMethodPath = splitedQuery[i].split("=");
      query.put(getMethodPath[0], getMethodPath[1]);
    }
  }




}
