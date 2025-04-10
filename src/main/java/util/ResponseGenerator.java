package util;

import java.io.DataOutputStream;
import java.io.IOException;

public class ResponseGenerator {
  private static final String DOMAIN_ROUTE = "http://localhost:8080";
  private DataOutputStream dos;

  public ResponseGenerator(DataOutputStream dos){
    this.dos = dos;
  }

  public void send200Response(byte[] bytes) throws IOException {
    response200Header(bytes.length);
    responseBody(bytes);
  }

  public void send302Response(String redirect) throws IOException {
    response302Header(redirect);
  }

  private void response200Header(int lengthOfBodyContent) throws IOException {
      dos.writeBytes("HTTP/1.1 200 OK \r\n");
      dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
      dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
      dos.writeBytes("\r\n");
  }

  private void response302Header(String redirect) throws IOException {
      dos.writeBytes("HTTP/1.1 302 OK \r\n");
      dos.writeBytes("Location: " + DOMAIN_ROUTE + redirect + "\r\n");
      dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
      dos.writeBytes("\r\n");
  }

  private void responseBody(byte[] body) throws IOException {
      dos.write(body, 0, body.length);
      dos.flush();
  }


}
