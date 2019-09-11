package com.jpa.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

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
 * @GeneratedValue：配置主键的生成策略 strategy
 * GenerationType.IDENTITY：自增（推荐）
 * *底层数据库必须支持自动增长
 * GenerationType.SEQUENCE:序列（推荐）
 * *底层数据库必须支持序列
 * GenerationType.TABLE：jpa提供的一种机制，通过一张数据表的方式完成主键自增
 * GenerationType.AUTO:由程序自动的帮助我们选择主键生成策略
 * @Column:配置属性与字段的映射关系 name属性：代表当前属性对应的数据库中的字段名称
 */
@Entity
@Table(name = "cst_customer")
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cust_id")
    private Long custId; //客户主键

    public Customer() {
    }

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
    //配置客户与联系人之间的关系（一个客户对应多个联系人）


    /**
     * 使用注解的形式配置多表关系
     * 1.声明关系
     *
     * @OneToMany: 配置一对多关系
     * targetEntity：对方的字节码对象
     * 2.配置外键（中间表表）
     *   @JoinColumn : 配置外键
     *   name:外键字段的名称
     *   referencedColumnName： 参照的主表的主键字段名称
     *     *在客户实体类上 （一的一方）添加了外键的配置，
     *     *所以对于客户而言，他也具备了维护外键的作用
     *放弃外键的维护权：
     *     mappedBy = "customer"：我的一对多映射参照对方（customer）（对方的属性名称）来做
     *
     * 级联：
        操作一个对象的同时操作他的关联对象

            级联操作：
                1.需要区分操作主体
                2.需要在操作主体的实体类上，添加级联属性（需要添加到多表映射关系的注解上）
                3.cascade（配置级联）

            级联添加，
                案例：当我保存一个客户的同时保存联系人
            级联删除
                案例：当我删除一个客户的同时删除此客户的所有联系人
     *cascade关键字：配置级联（可以配置到设置多表的映射关系的注释上）
     *      取值范围：
     *          CascadeType.ALL  ： 所有
     *          CascadeType.MERGE ： 更新
     *          CascadeType.PERSIST ： 保存
     *          CascadeType.REMOVE ： 删除
     *
     * fetch : 配置关联对象的加载方式
     *       EAGER：立即加载  --> 不推荐这样配置
     *       LAZY ：延迟加载  --> 默认加载方式
     */

//    @OneToMany(targetEntity = LinkMan.class)
//    @JoinColumn(name = "lkm_cust_id", referencedColumnName = "cust_id")
    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL,fetch = FetchType.EAGER) //放弃外键的维护权
    private Set<LinkMan> linkMans = new HashSet<LinkMan>();

    public Set<LinkMan> getLinkMans() {
        return linkMans;
    }

    public void setLinkMans(Set<LinkMan> linkMans) {
        this.linkMans = linkMans;
    }

    public Customer(String custName, String custSource, String custIndustry, String custLevel, String custAddress, String custPhone, Set<LinkMan> linkMans) {
        this.custName = custName;
        this.custSource = custSource;
        this.custIndustry = custIndustry;
        this.custLevel = custLevel;
        this.custAddress = custAddress;
        this.custPhone = custPhone;
        this.linkMans = linkMans;
    }

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