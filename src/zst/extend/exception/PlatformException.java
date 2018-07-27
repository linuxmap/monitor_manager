package zst.extend.exception;

/**
 * 自定义异常类
 * @author: liuyy
 * @date: 2017-5-23 下午4:22:30
 */
public class PlatformException extends Exception {

	private static final long serialVersionUID = 1L;

	// 异常信息
	public String message;

	public PlatformException(String message) {
		super(message);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
