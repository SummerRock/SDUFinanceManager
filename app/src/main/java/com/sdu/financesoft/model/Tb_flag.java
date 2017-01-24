package com.sdu.financesoft.model;

public class Tb_flag// 备忘信息实体类
{
    private int _id;// 存储备忘编号
    private String flag;// 存储备忘信息

    public Tb_flag()// 默认构造函数
    {
        super();
    }

    // 定义有参构造函数，用来初始化备忘信息实体类中的各个字段
    public Tb_flag(int id, String flag) {
        super();
        this._id = id;// 为备忘号赋值
        this.flag = flag;// 为备忘信息赋值
    }

    public int getid()// 设置备忘编号的可读属性
    {
        return _id;
    }

    public void setid(int id)// 设置备忘编号的可写属性
    {
        this._id = id;
    }

    public String getFlag()// 设置备忘信息的可读属性
    {
        return flag;
    }

    public void setFlag(String flag)// 设置备忘信息的可写属性
    {
        this.flag = flag;
    }
}
