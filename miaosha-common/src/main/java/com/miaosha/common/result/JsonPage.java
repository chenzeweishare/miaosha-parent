package com.miaosha.common.result;

import java.io.Serializable;

import lombok.Data;
import org.springframework.data.domain.Page;

@Data
public class JsonPage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer pageIndex;
	
	private Integer pageSize;
	
	private Integer totalPage;
	
	private Long totalSize;
	
	public JsonPage(){}

	public JsonPage(Page<?> page){
		this.pageIndex = page.getNumber() + 1;
		this.pageSize = page.getSize();
		this.totalPage = page.getTotalPages();
		this.totalSize = page.getTotalElements();
	}
}
