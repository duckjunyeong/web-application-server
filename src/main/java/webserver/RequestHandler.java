package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestHandler;
import util.HttpRequestReader;
import util.PageHandler;
import util.UrlQueryHandler;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    private static final String INDEX_PAGE = "/";
    private static final String SIGNUP_PAGE = "/user/form.html";
    private static final String CREATE_USER = "/user/create";

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequestReader httpRequestReader = new HttpRequestReader(in);
            HttpRequestHandler httpRequest = new HttpRequestHandler(httpRequestReader);
            httpRequest.readHttpRequest();

            log.debug("Client Http Request : {} ", httpRequest.getHttpRequest());

            generateResponse(in, out, httpRequest);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void generateResponse(InputStream in, OutputStream out, HttpRequestHandler httpRequest) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);
        String requestMethod = httpRequest.getMethod();
        String requestPath = httpRequest.getPath();

        if (requestMethod.equals("GET")){
            if (requestPath.equals(INDEX_PAGE)){
                handleResponse(dos, PageHandler.getIndex());
                return;
            }
            else if (requestPath.equals(SIGNUP_PAGE)){
                handleResponse(dos, PageHandler.getSignUp());
                return;
            }
            else{
                handleResponse(dos, PageHandler.getError());
                return;
            }
        }
        else if (requestMethod.equals("POST")){
            if (requestPath.startsWith(CREATE_USER)){
                User user = generateUser(httpRequest);
                //System.out.println("user Create! " + user.getName());
                handleResponse(dos, PageHandler.getIndex());
                return;
            }
        }
        throw new IllegalArgumentException("Invalid Path");
    }

    private User generateUser(HttpRequestHandler httpRequest) {
        String name = httpRequest.getQuery("name");
        String userId = httpRequest.getQuery("userId");
        String password = httpRequest.getQuery("password");
        String email = httpRequest.getQuery("email");

        return new User(userId, password, name, email);
    }

    private void handleResponse(DataOutputStream dos, byte[] bytes) {
        response200Header(dos, bytes.length);
        responseBody(dos, bytes);
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
