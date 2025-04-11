package util;

import model.User;

import java.io.DataOutputStream;
import java.io.IOException;

public class ResponseGenerator {
  private static final String DOMAIN_ROUTE = "http://localhost:8080";
  private DataOutputStream dos;

  public ResponseGenerator(DataOutputStream dos){
    this.dos = dos;
  }

  public void sendPageResponse(byte[] bytes) throws IOException {
    response200Header(bytes.length);
    responseBody(bytes);
  }

  public void sendRedirectResponse(String redirect) throws IOException {
    response302Header(redirect);
  }

  public void successLoginResponse(String redirect, User user) throws IOException {
    successLoginResponseHeader(redirect, user);
    responseUserInfo(user);

  }

  public void failedLoginResponse(byte[] bytes) throws IOException {
    failedLoginResponseHeader(bytes);
    responseBody(bytes);
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

  public void successLoginResponseHeader(String redirect, User user) throws IOException {
    dos.writeBytes("HTTP/1.1 301 ok \r\n");
    dos.writeBytes("Location: " + DOMAIN_ROUTE + redirect + "\r\n");
    dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
    dos.writeBytes("Set-Cookie: session_id=" + user.getUserId() + "; Path=/; Max-Age=15\r\n");
    dos.writeBytes("Set-Cookie: logined=true; Path=/; Max-Age=15\r\n");
    dos.writeBytes("\r\n");
  }

  public void failedLoginResponseHeader(byte[] bytes) throws IOException {
    dos.writeBytes("HTTP/1.1 301 ok \r\n");
    dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
    dos.writeBytes("Set-Cookie: logined=false; Path=/; Max-Age=15\r\n");
    dos.writeBytes("\r\n");
  }

  private void responseUserInfo(User user) throws IOException {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("userInfo: ");
    stringBuilder.append("userId: ").append(user.getUserId());
    stringBuilder.append("name: ").append(user.getName());
    stringBuilder.append("email: ").append(user.getEmail());

    byte[] userInfo = stringBuilder.toString().getBytes();
    dos.write(userInfo, 0, userInfo.length);
    dos.flush();
  }
}
