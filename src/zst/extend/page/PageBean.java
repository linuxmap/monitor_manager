package zst.extend.page;

import java.io.Serializable;

import zst.extend.common.Constant;

/**
 * 分页公用BEAN
 * @author: liuyy
 * @date: 2017-5-31 下午2:12:21
 */
public class PageBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String PageSize; // 每页显示的记录数 
	private String PageNum;	 // 当前第几页
	@SuppressWarnings("unused")
	private int start;		 // 起始游标行数
	private int number;		 // 每页游标条数
	private int intPage;	 
	private long totalRows;  //总行数 
	private long totalPages; //总页数 
	
	public long getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public long getTotalRows() {
		totalPages = totalRows/Long.parseLong(PageSize);
		long mod=totalRows%Long.parseLong(PageSize);
		if(mod>0){
			totalPages++;
		}
		
		if(intPage>totalPages){
			intPage = Integer.parseInt(totalPages+"");
			if(intPage == 0)
				intPage = 1;
			getStart();
		}
		return totalRows;
	}
	public void setTotalRows(long totalRows) {
		this.totalRows = totalRows;
		getTotalRows();
	}
	
	public int getIntPage() {
		return intPage;
	}
	
	public void setIntPage(int intPage) {
		this.intPage = intPage;
	}
	
	public int getStart() {
		return (intPage-1)*number;
	}
	
	public void setStart(int start) {
		this.start = start;
	}
	
	public int getNumber() {
		return number;
	}
	
	public void setNumber(int number) {
		this.number = number;
	}
	
	public String getPageSize() {
		return PageSize;
	}
	
	public void setPageSize(String pageSize) {
		PageSize = (pageSize == null || "0".equals(pageSize)|| "".equals(pageSize)) ? Constant.PAGE_SIZE : pageSize;
		number = Integer.parseInt(PageSize);
	}
	
	public String getPageNum() {
		return PageNum;
	}
	
	public void setPageNum(String pageNum) {
		this.PageNum = (pageNum == null || "0".equals(pageNum)|| "".equals(pageNum)|| "NaN".equals(pageNum)|| "NaN,".equals(pageNum)) ? "1" : pageNum;
		intPage = Integer.parseInt(PageNum);
	}
	/**
	 * 设置分页参数
	 * @param rows
	 * @param page
	 * @param totalRows
	 * @return
	 * @author songxiang
	 */
	public static PageBean PageSetParameter(String rows,String page,long totalRows){
		//定义分页对象
		PageBean myPage = new PageBean();
		//设置每页显示的记录数 
		myPage.setPageSize(rows);
		//设置当前第几页
		myPage.setPageNum(page);
		//设置总条数
		myPage.setTotalRows(totalRows);		
		return myPage;
	}
}
