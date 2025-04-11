package util;

import model.User;

import java.io.DataOutputStream;
import java.io.IOException;

public class HttpResponseWriter {
  private static final String DOMAIN_ROUTE = "http://localhost:8080";

  private final DataOutputStream dos;

  public HttpResponseWriter(DataOutputStream dos) {
    this.dos = dos;
  }

  public void sucessPageResponse(byte[] page, String extension) {
    try{
      dos.writeBytes("HTTP/1.1 200 OK \r\n");
      dos.writeBytes("Content-Type: text/" + extension  + ";charset=utf-8\r\n");
      dos.writeBytes("Content-Length: " + page.length + "\r\n");
      dos.writeBytes("\r\n");
      dos.write(page, 0, page.length);
      dos.flush();
    } catch (Exception e) {
      // 로그를 이용해서 에러를 출력하기
    }
  }

  public void failedPageResponse(){
    try{
      dos.writeBytes("HTTP/1.1 400 Bad Request \r\n");
      dos.writeBytes("\r\n");
    } catch (Exception e) {
      // 로그를 통해 에러를 출력하기
    }
  }

  public void redirectResponse(String redirect) throws IOException {
    dos.writeBytes("HTTP/1.1 302 OK \r\n");
    dos.writeBytes("Location: " + DOMAIN_ROUTE + redirect + "\r\n");
    dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
    dos.writeBytes("\r\n");
  }

  public void successLoginResponse(String redirect, User user) {
    // User에 대한 정보를 set-Cookie에 담고 리다렉트 해주고

  }

  public void failedLoginResponse(byte[] page){
    // set-Cookie에 logined = false담고 page전송해주고
  }


}
