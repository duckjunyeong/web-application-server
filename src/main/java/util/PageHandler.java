package util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PageHandler {
  private static final String STATIC_FORDLER = "webapp/";

  public static byte[] getIndexPage() throws IOException {
    Path path = Paths.get(STATIC_FORDLER + "index.html");
    if (Files.exists(path)){
      return Files.readAllBytes(path);
    }
    throw new IllegalArgumentException("Invalid file");
  }

  public static byte[] getErrorPage(){
    return "Hello World".getBytes();
  }
}
