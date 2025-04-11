package util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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

  private static byte[] translateToBytes(Path path) throws IOException {
    if (Files.exists(path)){
      return Files.readAllBytes(path);
    }
    throw new IllegalArgumentException("Invalid file");
  }

  public static byte[] getError(){
    return "Hello World".getBytes();
  }

}
