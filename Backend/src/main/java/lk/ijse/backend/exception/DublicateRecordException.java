package lk.ijse.backend.exception;

import org.hibernate.service.spi.ServiceException;

public class DublicateRecordException extends ServiceException {
    public DublicateRecordException(String message) {
        super(message);
    }
}
