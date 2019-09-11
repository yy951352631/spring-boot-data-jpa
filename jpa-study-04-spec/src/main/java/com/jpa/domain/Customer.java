package com.jpa.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Wtq
 * @date 2019/9/4 - 11:12
 */

/**
 * 客户实体类
 * 配置映射关系
 * 1.实体类与数据库表的映射关系
 *
 * @Entity：声明此类是一个实体类
 * @Table():配置实体类与表的映射关系 name属性：配置实体类对的数据表的名称
 * 2.实体类中属性与表中字段的映射关系
 * @Id:声明主键配置
 * @GeneratedValue：配置主键的生成策略
 *      strategy
 *          GenerationType.IDENTITY：自增（推荐）
 *              *底层数据库必须支持自动增长
 *          GenerationType.SEQUENCE:序列（推荐）
 *              *底层数据库必须支持序列
 *          GenerationType.TABLE：jpa提供的一种机制，通过一张数据表的方式完成主键自增
 *          GenerationType.AUTO:由程序自动的帮助我们选择主键生成策略
 *
 * @Column:配置属性与字段的映射关系 name属性：代表当前属性对应的数据库中的字段名称
 */
@Entity
@Table(name = "cst_customer")
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cust_id")
    private Long custId; //客户主键

    @Column(name = "cust_name")
    private String custName;
    @Column(name = "cust_source")
    private String custSource;//客户来源
    @Column(name = "cust_industry")
    private String custIndustry;//客户所属行业
    @Column(name = "cust_level")
    private String custLevel;//客户级别
    @Column(name = "cust_address")
    private String custAddress;
    @Column(name = "cust_phone")
    private String custPhone;

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustSource() {
        return custSource;
    }

    public void setCustSource(String custSource) {
        this.custSource = custSource;
    }

    public String getCustIndustry() {
        return custIndustry;
    }

    public void setCustIndustry(String custIndustry) {
        this.custIndustry = custIndustry;
    }

    public String getCustLevel() {
        return custLevel;
    }

    public void setCustLevel(String custLevel) {
        this.custLevel = custLevel;
    }

    public String getCustAddress() {
        return custAddress;
    }

    public void setCustAddress(String custAddress) {
        this.custAddress = custAddress;
    }

    public String getCustPhone() {
        return custPhone;
    }

    public void setCustPhone(String custPhone) {
        this.custPhone = custPhone;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "custId=" + custId +
                ", custName='" + custName + '\'' +
                ", custSource='" + custSource + '\'' +
                ", custIndustry='" + custIndustry + '\'' +
                ", custLevel='" + custLevel + '\'' +
                ", custAddress='" + custAddress + '\'' +
                ", custPhone='" + custPhone + '\'' +
                '}';
    }
}