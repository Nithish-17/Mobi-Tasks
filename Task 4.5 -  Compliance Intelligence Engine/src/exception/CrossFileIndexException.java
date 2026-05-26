package exception;

public class CrossFileIndexException extends Exception {

    public CrossFileIndexException(String file, String path) {

        super("Missing File : " + file + " Expected Path : " + path);
    }
}