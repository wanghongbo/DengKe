package com.dengke.entity.common;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 
 * @author 
 *
 */
public  class Page{
    public static class Paging {

        public int pageNo;
        public int pagesize;
        public int totalCount;
        public int pageCount;
        public int startIndex;

        private Paging(int pageNo, int pagesize, int totalCount) {
            this.totalCount = totalCount;
            this.pagesize = pagesize <= 0 ? 20 : pagesize;
            this.pagesize = this.pagesize > 100 ? 100 : this.pagesize;
            this.pageCount = this.totalCount / this.pagesize + (this.totalCount % this.pagesize > 0 ? 1 : 0);
            this.pageNo = pageNo > pageCount ? pageCount : pageNo;
            this.pageNo = this.pageNo <= 0 ? 1 : this.pageNo;
            this.startIndex = (this.pageNo - 1) * this.pagesize;
        }
    }

    /**
     * 获取分页信息，用于分页查询，内部做了容错处理
     */
    public static Paging getPaging(int pageNo, int pagesize, int totalCount) {
        return new Paging(pageNo, pagesize, totalCount);
    }

    /**
     * 专用于分页返回的数据结构
     */
    public static class PagedData<T> implements Serializable {

		private static final long serialVersionUID = -4965284159108734830L;
		
		/** 当前页码 */
        public int pageNo;
        /** 总页数 */
        public int pageCount;
        /** 总记录数 */
        public int totalCount;
        /** 本页的数据 */
        public List<T> page;

        public PagedData(Paging p, List<T> page) {
            this.pageNo = p.pageNo;
            this.pageCount = p.pageCount;
            this.totalCount = p.totalCount;
            this.page = page;
        }

        public <U> PagedData(PagedData<U> other) {
            this.pageNo = other.pageNo;
            this.pageCount = other.pageCount;
            this.totalCount = other.totalCount;
            this.page = Collections.emptyList();
        }
    }
}
