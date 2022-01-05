package by.grsu.course.exception;

public class TopicAlreadyExistException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public TopicAlreadyExistException(String message) {
        super(message);
    }
}
