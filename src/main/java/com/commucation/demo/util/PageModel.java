package com.commucation.demo.util;

/**
 * Created by
 *
 * @Author:DanielSmith
 * @Date: 2020/08/16
 * @Time：20:56
 */
import java.util.List;

public class PageModel<T> {
    /***
     * 当前页
     */
    private int page = 1;

    /***
     * 总页数
     */
    public int totalPages = 0;

    /***
     * 每页数据条数
     */
    private int pageRecorders;

    /***
     * 总页数
     */
    private int totalRows = 0;

    /***
     * 每页的起始数
     */
    private int pageStartRow = 0;

    /***
     * 每页显示数据的终止数
     */
    private int pageEndRow = 0;

    /***
     * 数据集合
     */
    private List<T> list;


    public PageModel(List<T> list, int pageRecorders) {
        // 通过对象集，记录总数划分
        init(list, pageRecorders);
    }

    /**
     * 初始化list，并告之该list每页的记录数
     * @param list 数据几个
     * @param pageRecorders 一页显示多少数据
     */
    public void init(List<T> list, int pageRecorders) {
        this.pageRecorders = pageRecorders;
        this.list = list;
        totalRows = list.size();

        if ((totalRows % pageRecorders) == 0) {
            totalPages = totalRows / pageRecorders;
        } else {
            totalPages = totalRows / pageRecorders + 1;
        }


        if (totalRows < pageRecorders) {
            this.pageStartRow = 0;
            this.pageEndRow = totalRows;
        } else {
            this.pageStartRow = 0;
            this.pageEndRow = pageRecorders;
        }
    }


    public void description() {

        String description = "共有数据数:" + this.getTotalRows() +

                "共有页数: " + this.getTotalPages() +

                "当前页数为:" + this.getPage() +

                " 开始行数:" + this.getPageStartRow() +

                " 终止行数:" + this.getPageEndRow();

        System.out.println(description);
    }

    /**
     * 展现所有当前页面的数据
     * @return
     */
    public List getThisPage() {

        disposePage();

        System.out.println("用户凋用的是第" + page + "页");
        this.description();
        return getObjects(page);
    }

    /**
     * 处理分页
     */
    private void disposePage() {

        if (page == 0) {
            page = 1;
        }
    }

    /**
     * 获取第几页的内容
     *
     * @param page 当前页面
     * @return
     */
    public List<T> getObjects(int page) {
        if(page == 0){
            this.setPage(1);
        }
        else{
            this.setPage(page);
        }
        this.disposePage();
        if (page * pageRecorders < totalRows) {
            // 判断是否为最后一页
            pageEndRow = page * pageRecorders;
            pageStartRow = pageEndRow - pageRecorders;
        } else {
            pageEndRow = totalRows;
            pageStartRow = pageRecorders * (totalPages - 1);
        }

        List<T> objects = null;
        if (!list.isEmpty()) {
            objects = list.subList(pageStartRow, pageEndRow);
        }
        this.description();
        return objects;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageEndRow() {
        return pageEndRow;
    }

    public int getPageStartRow() {
        return pageStartRow;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getTotalRows() {
        return totalRows;
    }

}