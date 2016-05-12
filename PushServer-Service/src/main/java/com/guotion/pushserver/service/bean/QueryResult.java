package com.guotion.pushserver.service.bean;

import java.util.List;

public class QueryResult<T> {
    private long totalRecord = 0L;
    private List<T> list;

    public QueryResult() {
    }

    public QueryResult(long totalRecord, List<T> list) {
        this.totalRecord = totalRecord;
        this.list = list;
    }

    public long getTotalRecord() {
        return this.totalRecord;
    }

    public void setTotalRecord(long totalRecord) {
        this.totalRecord = (totalRecord > 0L ? totalRecord : 0L);
    }

    public List<T> getList() {
        return this.list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}