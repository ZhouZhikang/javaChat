package Exception;
/**
 * 
 * 数据库操作错误
 */
public class DBOperatorException extends Exception {
	public DBOperatorException(String message) {
		super(message);
	}
}
