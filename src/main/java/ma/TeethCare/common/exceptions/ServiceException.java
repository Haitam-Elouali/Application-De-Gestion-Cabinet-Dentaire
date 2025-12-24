package ma.TeethCare.common.exceptions;

public class ServiceException extends RuntimeException {
    public ServiceException(String m) {
        super(m);
    }

    public ServiceException(String m, Throwable t) {
        super(m, t);
    }
}
