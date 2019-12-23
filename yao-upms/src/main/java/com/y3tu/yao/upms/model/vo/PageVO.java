package com.y3tu.yao.upms.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageSerializable;
import lombok.Data;

import java.util.List;

/**
 * ClassName: PageVo
 * Description:
 * date: 2019/11/22 18:37
 *
 * @author zht
 */
@Data
public class PageVO<T> extends PageSerializable<T> {

    /**
     * 当前页
     */
    @JsonProperty(value = "page_num")
    private Integer pageNum;

    /**
     * 每页的数量
     */
    @JsonProperty(value = "page_size")
    private Integer pageSize;

    /**
     * 当前页的数量
     */
    @JsonIgnore
    private Integer size;

    /**
     * 当前页面第一个元素在数据库中的行号
     */
    @JsonIgnore
    private Integer startRow;

    /**
     * 当前页面最后一个元素在数据库中的行号
     */
    @JsonIgnore
    private Integer endRow;

    /**
     * 总页数
     */
    @JsonProperty(value = "total_page")
    private Integer totalPage;

    /**
     * 前一页
     */
    @JsonIgnore
    private Integer prePage;

    /**
     * 下一页
     */
    @JsonIgnore
    private Integer nextPage;

    /**
     * 是否有前一页
     */
    @JsonIgnore
    private Boolean hasPreviousPage = false;

    /**
     * 是否有下一页
     */
    @JsonIgnore
    private Boolean hasNextPage = false;

    /**
     * 导航页码数
     */
    @JsonIgnore
    private Integer navigatePages;

    /**
     * 所有导航页号
     */
    @JsonIgnore
    private Integer[] navigatepageNums;

    /**
     * 导航条上的第一页
     */
    @JsonIgnore
    private Integer navigateFirstPage;

    /**
     * 导航条上的最后一页
     */
    @JsonIgnore
    private Integer navigateLastPage;


    public PageVO() {
    }

    /**
     * 包装Page对象
     *
     * @param list
     */
    public PageVO(List<T> list) {
        this(list, 8);
    }

    /**
     * 包装Page对象
     *
     * @param list          page结果
     * @param navigatePages 页码数量
     */
    private PageVO(List<T> list, int navigatePages) {
        super(list);
        if (list instanceof Page) {
            Page page = (Page) list;
            this.pageNum = page.getPageNum();
            this.pageSize = page.getPageSize();

            this.totalPage = page.getPages();
            this.size = page.size();
            //由于结果是>startRow的，所以实际的需要+1
            if (this.size == 0) {
                this.startRow = 0;
                this.endRow = 0;
            } else {
                this.startRow = page.getStartRow() + 1;
                //计算实际的endRow（最后一页的时候特殊）
                this.endRow = this.startRow - 1 + this.size;
            }
        } else {
            this.pageNum = 1;
            this.pageSize = list.size();

            this.totalPage = this.pageSize > 0 ? 1 : 0;
            this.size = list.size();
            this.startRow = 0;
            this.endRow = list.size() > 0 ? list.size() - 1 : 0;
        }
        this.navigatePages = navigatePages;
    }

}
