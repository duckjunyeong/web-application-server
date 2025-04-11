package util.handler;

import util.HttpResponseWriter;

import java.io.IOException;

public class GetMethodHandler {

  public static void takeResponse(HttpRequestHandler httpRequestHandler, HttpResponseWriter httpResponseWriter) throws IOException {
    String requestPath = httpRequestHandler.getHttpHeader("Path");

    // 여기서 해당 요청이 페이지를 요청하는건지, query를 보낸건지 파악해야함.

    try{
      byte[] page = getResponsePage(requestPath);
      String extension = getExtension(requestPath);
      httpResponseWriter.sucessPageResponse(page, extension);

    } catch (IOException e) {
      httpResponseWriter.failedPageResponse();
    }
  }

  private static byte[] getResponsePage(String requestPath) throws IOException {
    return PageHandler.getPage(requestPath);
  }

  private static String getExtension(String requestPath) {
    if (requestPath.equals("/")) return "html";

    int lastDotIndex = requestPath.lastIndexOf(".");
    if (lastDotIndex > 0 && lastDotIndex != requestPath.length() - 1){
      return requestPath.substring(lastDotIndex + 1).toLowerCase();
    }
    return null;
  }
}
