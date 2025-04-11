package webserver;

import java.io.*;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.handler.HttpRequestHandler;
import util.handler.HttpResponseHandler;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    private HttpRequestHandler httpRequestHandler;
    private HttpResponseHandler httpResponseHandler;

    public RequestHandler(Socket connectionSocket) throws IOException {
        this.connection = connectionSocket;
        this.httpRequestHandler = new HttpRequestHandler(connection.getInputStream());
        this.httpResponseHandler = new HttpResponseHandler(httpRequestHandler, new DataOutputStream(connection.getOutputStream()));
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            httpRequestHandler.readHttpRequest();

            log.debug("Client Http Request");
            System.out.println(httpRequestHandler.getAllHttpRequest());

            httpResponseHandler.takeResponse(); // 페이지를 보내던, 뭐를 하던 응답을 해주는거임 request기반으로
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

//    private void takeResponse() throws IOException {
//        String requestMethod = httpRequestHandler.getHttpHeader("Method");
//        String requestPath = httpRequestHandler.getHttpHeader("Path");
//
//        if (requestMethod.equals("GET")){
//            byte[] bytes = Files.readAllBytes(new File("webapp" + requestPath).toPath());
//            if (requestPath.startsWith("/css")){
//                responseGenerator.sendCssFileResponse(bytes);
//                return;
//            }
//            else{
//                responseGenerator.sendPageResponse(bytes);
//                return;
//            }
//            if (requestPath.equals(ApiRoutes.INDEX_PAGE)){
//                responseGenerator.sendPageResponse(PageHandler.getIndex());
//                return;
//            }
//            else if (requestPath.equals(ApiRoutes.LOGIN_PAGE)){
//                responseGenerator.sendPageResponse(PageHandler.getLogin());
//                return;
//            }
//            else if (requestPath.equals(ApiRoutes.SIGNUP_PAGE)){
//                responseGenerator.sendPageResponse(PageHandler.getSignUp());
//                return;
//            }
//            else if (requestPath.equals(ApiRoutes.LIST_PAGE)){
//                String isLogined = httpRequestHandler.getCookie("logined");
//                String userId = httpRequestHandler.getCookie("session_id");
//                User user = DataBase.findUserById(userId);
//                if (isLogined != null && user != null && isLogined.equals("true")){
//                    responseGenerator.sendPageResponse(PageHandler.getList(user));
//                    return;
//                }
//                responseGenerator.sendRedirectResponse("/");
//                return;
//            }
//            else{
//                responseGenerator.sendPageResponse(PageHandler.getError());
//                return;
//            }
        }
//        else if (requestMethod.equals("POST")){
//            if (requestPath.startsWith(ApiRoutes.POST_CREATE)){
//                generateUser(httpRequestHandler);
//                responseGenerator.sendRedirectResponse("/");
//                return;
//            }
//            else if (requestPath.equals(ApiRoutes.POST_LOGIN)){
//                String userId = httpRequestHandler.getQuery("userId");
//                String password = httpRequestHandler.getQuery("password");
//
//                User user = DataBase.findUserById(userId);
//                if (user == null || !user.getPassword().equals(password)) {
//                    responseGenerator.failedLoginResponse(PageHandler.getFailedLogin());
//                    return;
//                }
//                responseGenerator.successLoginResponse("/", user);
//                return;
//            }
//        }
//        throw new IllegalArgumentException("Invalid Path");
//    }

//    private void generateUser(HttpRequestHandler httpRequest) {
//        String name = httpRequest.getQuery("name");
//        String userId = httpRequest.getQuery("userId");
//        String password = httpRequest.getQuery("password");
//        String email = httpRequest.getQuery("email");
//
//        DataBase.addUser(new User(userId, password, name, email));
//    }
//}


// class HttpResponseHandler
//      -
