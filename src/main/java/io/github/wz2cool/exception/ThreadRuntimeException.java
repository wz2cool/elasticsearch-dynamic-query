package io.github.wz2cool.exception;

/**
 * 线程运行时异常
 *
 * @author Frank
 */
public class ThreadRuntimeException extends RuntimeException {

    /**
     * 构造
     *
     * @param message 错误消息
     */
    public ThreadRuntimeException(String message) {
        super(message);
    }

    /**
     * 构造
     *
     * @param message 错误消息
     * @param cause   错误
     */
    public ThreadRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 构造
     *
     * @param cause 错误
     */
    public ThreadRuntimeException(Throwable cause) {
        super(cause);
    }
}
