package util.handler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PageHandler {
  private static final String STATIC_FORDLER = "webapp";
  private static final String INDEX_HTML = "/index.html";

  public static byte[] getPage(String requestPath) throws IOException {
    Path path;
    if (requestPath.equals("/")){
      path = Paths.get(STATIC_FORDLER + INDEX_HTML);
    }
    else{
      path = Paths.get(STATIC_FORDLER + requestPath);
    }

    return translateToBytes(path);
  }




  private static byte[] translateToBytes(Path path) throws IOException {
    if (Files.exists(path)){
      return Files.readAllBytes(path);
    }
    throw new IllegalArgumentException("Invalid file");
  }

}
