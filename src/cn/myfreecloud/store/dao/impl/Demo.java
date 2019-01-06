package cn.myfreecloud.store.dao.impl;

public class Demo {

    private int id;
    private transient String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static void main(String[] args) {
        //创建了一个对象 通过流的方式 保存到文件中 那么有关键字  不会将此字段 写入文件
    }

}
