package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.

            byte[] buffer = new byte[1024];
            int byteRead = in.read(buffer);
            String strBuffer = new String(buffer, StandardCharsets.UTF_8);
            String[] requestMethodAndPath = extractMethodAndPath(strBuffer);

            String requestMethod = requestMethodAndPath[0];
            String requestPath = requestMethodAndPath[1];

            generateResponse(out, requestMethod, requestPath);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void generateResponse(OutputStream out, String requestMethod, String requestPath) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);
        if (requestMethod.equals("GET")){
            if (requestPath.equals(INDEX_PAGE)){
                indexPage(dos);
            }
            else{
                errorPage(dos);
            }
        }
        throw new IllegalArgumentException("Invalid Path");
    }

    private String[] extractMethodAndPath(String strBuffer) {
        String[] splitedBuffer = strBuffer.split("\\n");
        return splitedBuffer[0].split(" ");
    }

    private void indexPage(DataOutputStream dos) throws IOException {
        Path path = Paths.get(STATIC_FORDLER + "index.html");
        if (Files.exists(path)){
            byte[] htmlBytes = Files.readAllBytes(path);
            handleResponse(dos, htmlBytes);
        }
        throw new IllegalArgumentException("Invalid file");
    }

    private void errorPage(DataOutputStream dos) {
        byte[] body = "Hello World".getBytes();
        handleResponse(dos, body);
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
