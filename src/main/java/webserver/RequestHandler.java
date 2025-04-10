package webserver;

import java.io.*;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestHandler;
import util.HttpRequestReader;
import util.PageHandler;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    private static final String STATIC_FORDLER = "webapp/";
    private static final String INDEX_PAGE = "/";

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

            String requestMethod = httpRequest.getMethod();
            String requestPath = httpRequest.getPath();

            generateResponse(out, requestMethod, requestPath);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void generateResponse(OutputStream out, String requestMethod, String requestPath) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);
        if (requestMethod.equals("GET")){
            if (requestPath.equals(INDEX_PAGE)){
                handleResponse(dos, PageHandler.getIndexPage());
                return;
            }
            else{
                handleResponse(dos, PageHandler.getErrorPage());
                return;
            }
        }
        throw new IllegalArgumentException("Invalid Path");
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
