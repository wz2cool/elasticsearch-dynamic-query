package io.github.wz2cool.exception;

/**
 * json 运行异常
 *
 * @author Frank
 */
public class JsonRuntimeException extends RuntimeException {

    /**
     * 构造
     *
     * @param message 错误消息
     */
    public JsonRuntimeException(String message) {
        super(message);
    }

    /**
     * 构造
     *
     * @param message 错误消息
     * @param cause   错误
     */
    public JsonRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 构造
     *
     * @param cause 错误
     */
    public JsonRuntimeException(Throwable cause) {
        super(cause);
    }
}
