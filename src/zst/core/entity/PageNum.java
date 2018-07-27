package zst.core.entity;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import zst.extend.page.PageBean;

/**
 * 
 * @author: liuyy
 * @date: 2017-5-31 下午2:15:48
 */
public class PageNum implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int pagestart;	// 起始 行数
	private int pageend;	// 结束条数
	
	private String keywords;//关键字查询
	
	public int getPagestart() {
		return pagestart;
	}
	
	public void setPagestart(int pagestart) {
		this.pagestart = pagestart;
	}
	
	public int getPageend() {
		return pageend;
	}
	
	//设置的 pageend
	public  void setPageend(int pageend) {
		this.pageend = pageend;
	}
	
	public PageBean setPage(String rows,String page){
		PageBean myPage = new PageBean();
    	myPage.setPageSize(rows);
    	myPage.setPageNum(page);
    	setPagestart(myPage.getStart()); 
        setPageend(Integer.parseInt(myPage.getPageSize()));
        return myPage;
	}
	
	public  void setTotalRows(HttpServletRequest request,PageBean myPage ,long num) {
		myPage.setTotalRows(num);
    	request.setAttribute("myPage", myPage);
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

}
