package util.handler;

import db.DataBase;
import model.User;
import util.ApiRoutes;
import util.HttpResponseWriter;

import java.io.IOException;

public class PostMethodHandler {

  public static void takeResponse(HttpRequestHandler httpRequestHandler, HttpResponseWriter httpResponseWriter) throws IOException {
    String requestPath = httpRequestHandler.getHttpHeader("Path");


    if (requestPath.startsWith(ApiRoutes.POST_CREATE)){
      generateUser(httpRequestHandler);
      httpResponseWriter.redirectResponse("/");
      return;
    }

    else if (requestPath.equals(ApiRoutes.POST_LOGIN)){
      String userId = httpRequestHandler.getHttpBody("userId");
      String password = httpRequestHandler.getHttpBody("password");

      User user = DataBase.findUserById(userId);
      if (user == null || !user.getPassword().equals(password)) {
          httpResponseWriter.failedLoginResponse(PageHandler.getFailedLoginPage());
          return;
      }
      httpResponseWriter.successLoginResponse("/", user);
      return;
    }
  }



  private static void generateUser(HttpRequestHandler httpRequest) {
      String name = httpRequest.getHttpBody("name");
      String userId = httpRequest.getHttpBody("userId");
      String password = httpRequest.getHttpBody("password");
      String email = httpRequest.getHttpBody("email");

      DataBase.addUser(new User(userId, password, name, email));
    }
}
