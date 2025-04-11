package util;

public enum HttpStatus {

  OK("200", "OK"), MOVED_PERMANENTLY("301", "Moved Permanently"), FOUND("302", "Found"),
  NOT_MODIFIED("304", "Not Modified"), BAD_REQUEST("400", "Bad Request"), UNAUTHORIZED("401", "Unauthorized");

  private String code;
  private String name;

  HttpStatus(String code, String name){
    this.code = code;
    this.name = name;
  }

  public String getCode() {
    return code;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return code + " " + name;
  }
}
