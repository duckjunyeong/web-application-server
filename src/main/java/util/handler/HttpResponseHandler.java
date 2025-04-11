package util.handler;

import util.HttpResponseWriter;

import java.io.DataOutputStream;
import java.io.IOException;

public class HttpResponseHandler {

  private HttpRequestHandler httpRequestHandler;
  private DataOutputStream dos;
  private HttpResponseWriter httpResponseWriter;

  public HttpResponseHandler(HttpRequestHandler httpRequestHandler, DataOutputStream dos) {
    this.httpRequestHandler = httpRequestHandler;
    this.httpResponseWriter = new HttpResponseWriter(dos);
    this.dos = dos;
  }

  public void takeResponse() throws IOException {
    String requestMethod = httpRequestHandler.getHttpHeader("Method");

    switch (requestMethod){
      case "GET":
        GetMethodHandler.takeResponse(httpRequestHandler, httpResponseWriter);
        break;
      case "POST":
        PostMethodHandler.takeResponse(httpRequestHandler, httpResponseWriter);
        break;
    }

  }
}
