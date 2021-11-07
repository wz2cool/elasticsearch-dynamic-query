package io.github.wz2cool.exception;

/**
 * 加密时异常
 *
 * @author xionglei
 */
public class EncryptRuntimeException extends RuntimeException {
    /**
     * 构造
     *
     * @param message 错误消息
     */
    public EncryptRuntimeException(String message) {
        super(message);
    }

    /**
     * 构造
     *
     * @param message 错误消息
     * @param cause   错误
     */
    public EncryptRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 构造
     *
     * @param cause 错误
     */
    public EncryptRuntimeException(Throwable cause) {
        super(cause);
    }
}
