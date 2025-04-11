package webserver;

import java.io.*;
import java.net.Socket;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.*;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    private ResponseGenerator responseGenerator;
    private HttpRequestHandler httpRequestHandler;

    public RequestHandler(Socket connectionSocket) throws IOException {
        this.connection = connectionSocket;
        this.responseGenerator = new ResponseGenerator(new DataOutputStream(connection.getOutputStream()));
        this.httpRequestHandler = new HttpRequestHandler(connection.getInputStream());
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            httpRequestHandler.readHttpRequest();

            log.debug("Client Http Request");
            for (String request : httpRequestHandler.getHttpRequest()){
                System.out.println(request);
            }
            takeResponse();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void takeResponse() throws IOException {
        String requestMethod = httpRequestHandler.getMethod();
        String requestPath = httpRequestHandler.getPath();

        if (requestMethod.equals("GET")){
            if (requestPath.equals(ApiRoutes.INDEX_PAGE)){
                responseGenerator.sendPageResponse(PageHandler.getIndex());
                return;
            }
            else if (requestPath.equals(ApiRoutes.LOGIN_PAGE)){
                responseGenerator.sendPageResponse(PageHandler.getLogin());
                return;
            }
            else if (requestPath.equals(ApiRoutes.SIGNUP_PAGE)){
                responseGenerator.sendPageResponse(PageHandler.getSignUp());
                return;
            }
            else{
                responseGenerator.sendPageResponse(PageHandler.getError());
                return;
            }
        }
        else if (requestMethod.equals("POST")){
            if (requestPath.startsWith(ApiRoutes.POST_CREATE)){
                generateUser(httpRequestHandler);
                responseGenerator.sendRedirectResponse("/");
                return;
            }
            else if (requestPath.equals(ApiRoutes.POST_LOGIN)){
                String userId = httpRequestHandler.getQuery("userId");
                String password = httpRequestHandler.getQuery("password");

                User user = DataBase.findUserById(userId);
                if (user == null || !user.getPassword().equals(password)) {
                    responseGenerator.failedLoginResponse(PageHandler.getFailedLogin());
                    return;
                }
                responseGenerator.successLoginResponse("/", user);
                return;
            }
        }
        throw new IllegalArgumentException("Invalid Path");
    }

    private void generateUser(HttpRequestHandler httpRequest) {
        String name = httpRequest.getQuery("name");
        String userId = httpRequest.getQuery("userId");
        String password = httpRequest.getQuery("password");
        String email = httpRequest.getQuery("email");

        DataBase.addUser(new User(userId, password, name, email));
    }
}
