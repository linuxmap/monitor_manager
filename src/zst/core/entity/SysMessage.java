package zst.core.entity;

/**
 * 用于短信的配置，封装短信信息
 * @author Ablert
 * @date 2018-3-8 下午7:09:42
 */
public class SysMessage extends PageNum {

	private static final long serialVersionUID = 1L;

	private String levelIds;//短信告警等级
	
	private String phones;//手机号
	
	private String content;//短信内容

	public String getPhones() {
		return phones;
	}

	public void setPhones(String phones) {
		this.phones = phones;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getLevelIds() {
		return levelIds;
	}

	public void setLevelIds(String levelIds) {
		this.levelIds = levelIds;
	}
}
