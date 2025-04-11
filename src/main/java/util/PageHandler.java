package util;

import model.User;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class PageHandler {
  private static final String STATIC_FORDLER = "webapp/";

  public static byte[] getIndex() throws IOException {
    Path path = Paths.get(STATIC_FORDLER + "index.html");
    return translateToBytes(path);
  }

  public static byte[] getSignUp() throws IOException {
    Path path = Paths.get(STATIC_FORDLER + "/user/form.html");
    return translateToBytes(path);
  }

  public static byte[] getLogin() throws IOException {
    Path path = Paths.get(STATIC_FORDLER + "/user/login.html");
    return translateToBytes(path);
  }

  public static byte[] getFailedLogin() throws IOException {
    Path path = Paths.get(STATIC_FORDLER + "/user/login_failed.html");
    return translateToBytes(path);
  }

  public static byte[] getList(User user) throws IOException {
    Path path = Paths.get(STATIC_FORDLER + "/user/list.html");
    byte[] bytes = translateToBytes(path);
    String htmlToString = new String(bytes, StandardCharsets.UTF_8);
    Map<String, String> map = user.getMap();

    for (String key : map.keySet()) {
      htmlToString = htmlToString.replace("{" + key + "}", map.get(key));
    }

    return htmlToString.getBytes();
  }

  public static byte[] getError(){
    return "Hello World".getBytes();
  }

  private static byte[] translateToBytes(Path path) throws IOException {
    if (Files.exists(path)){
      return Files.readAllBytes(path);
    }
    throw new IllegalArgumentException("Invalid file");
  }
}
