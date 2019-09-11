package com;

/**
 * @author Wtq
 * @date 2019/9/4 - 17:30
 */

import com.study.jpa.entity.Customer;
import com.study.jpa.util.JpaUtils;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

/**
 * 测试jpql查询
 */
public class JpqlTest {
    /**
     * 查询全部
     * jpql：from Customer
     * sql:SELECT * FROM cst_customer;
     */
    @Test
    public void testFindAll() {
        //获取实体类管理类
        EntityManager entityManager = JpaUtils.getEntityManager();
        //开启事物
        EntityTransaction ts = entityManager.getTransaction();
        ts.begin();
        //下面执行我们的查询所有操作
        String jpql = "from Customer";
        Query query = entityManager.createQuery(jpql);//创建Query对象，query对象才是执行jpql的对象
        //发送查询,并封装结果集
        List<Customer> customerList = query.getResultList();
        for (Customer item : customerList) {
            System.out.println(item);
        }
        //提交事物
        ts.commit();
        //释放资源
        entityManager.close();
    }

    /**
     * 排序查询：倒序查询全部用户（根据id）
     * sql：SELECT * FROM cst_customer ORDER BY cust_id DESC
     * jpql：from Customer order by custId desc
     * <p>
     * 进行jpql查询
     * 1.创建query查询对象
     * 2.对参数进行赋值
     * 3.查询，并得到返回结果集
     */
    @Test
    public void testOrder() {
        //获取实体类管理类
        EntityManager entityManager = JpaUtils.getEntityManager();
        //开启事物
        EntityTransaction ts = entityManager.getTransaction();
        ts.begin();
        //下面执行我们的查询所有操作
        String jpql = "from Customer order by custId desc";
        Query query = entityManager.createQuery(jpql);//创建Query对象，query对象才是执行jpql的对象
        //发送查询,并封装结果集
        List<Customer> customerList = query.getResultList();
        for (Customer item : customerList) {
            System.out.println("customerList***" + item);
        }
        //提交事物
        ts.commit();
        //释放资源
        entityManager.close();
    }

    /**
     * 使用jpql查询，统计客户的总数
     * sql：SELECT COUNT(cust_id) from cst_customer
     * jpql：select count(custId) from cst_customer
     */
    @Test
    public void testCount() {
        //获取实体类管理类
        EntityManager entityManager = JpaUtils.getEntityManager();
        //开启事物
        EntityTransaction ts = entityManager.getTransaction();
        ts.begin();
        //下面执行我们的查询客户个数操作
        String jpql = "select count(custId) from Customer ";
        Query query = entityManager.createQuery(jpql);//创建Query对象，query对象才是执行jpql的对象
        //getResultList：将查询结果封装为list集合
        //getSingleResult()：得到唯一的结果集

        Object singleResult = query.getSingleResult();
        System.out.println("***********count:" + singleResult);
        //提交事物
        ts.commit();
        //释放资源
        entityManager.close();
    }

}
