package zst.core.entity;

import java.io.Serializable;

/**
 * service层处理的结果封装对象
 * @author Ablert
 * @date 2018-2-1 下午3:26:48
 */
public class ResultBean implements Serializable {

	private static final long serialVersionUID = 4596635830019502889L;

	/**
	 * 处理结果
	 */
	private boolean flag;
	
	/**
	 * 结果说明
	 */
	private String feedBack;
	
	private Integer primaryKey;//新增的数据记录的主键值

	public String getFeedBack() {
		return feedBack;
	}

	public void setFeedBack(String feedBack) {
		this.feedBack = feedBack;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public Integer getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(Integer primaryKey) {
		this.primaryKey = primaryKey;
	}
}
