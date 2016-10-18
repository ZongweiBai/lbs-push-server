package com.guotion.pushserver.service.enums;

public enum DBOperateResult {

    OBJECT_ADD_SUCCESSFUL("添加成功", 0),
    OBJECT_ALREADY_EXIST("对象已经存在", 1);

    private String name;
    private int index;

    DBOperateResult(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}