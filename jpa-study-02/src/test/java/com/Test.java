package com;

import com.study.jpa.entity.Customer;
import com.study.jpa.util.JpaUtils;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

/**
 * @author Wtq
 * @date 2019/9/4 - 14:33
 */
@RunWith(SpringRunner.class)
public class Test {
    /**
     * 测试jpa 的保存
     * 保存一个客户到数据库
     * Jpa的操作步骤
     * 1.加载配置文件工厂（实体管理类工厂）对象
     * 2.通过实体管理类工厂获取实体管理器
     * 3.获取事物对象，开启事物
     * 4.增删改查操作
     * 5.提交事物（回滚事物）
     * 6.释放资源
     */
    @org.junit.Test
    public void testSave() {
        //1.加载配置文件工厂（实体管理类工厂）对象
//        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("myJpa");
        //2.通过实体管理类工厂获取实体管理器
        EntityManager entityManager = JpaUtils.getEntityManager();
        //3.获取事物对象，开启事物
        EntityTransaction ts = entityManager.getTransaction();//获取
        ts.begin();//开启
        //4.保存一个客户到数据库中
        Customer customer = new Customer();
        customer.setCustName("Bob");
        customer.setCustIndustry("航天");
        //保存
        entityManager.persist(customer);
        //5.提交事物
        ts.commit();
        //6.释放资源
        entityManager.close();
//        entityManagerFactory.close();
    }


    /**
     * 根据id查询用户
     * 使用find方法查询：
     * 1.查询的对象就是当前客户对象本身
     * 2.在调用find方法时，就会发送sql 语句查询数据库
     * <p>
     * 立即加载，得到的是对象本身
     */
    @org.junit.Test
    public void testFind() {
        //1.通过工具类获取EntityManager对象
        EntityManager entityManager = JpaUtils.getEntityManager();
        //2.开启事物
        EntityTransaction ts = entityManager.getTransaction();
        ts.begin();
        //3.根据id查询客户
        Customer customer = entityManager.find(Customer.class, 1L);
        System.out.println("*********customer**********:" + customer);
        //4.提交事物
        ts.commit();
        //5.释放资源
        entityManager.close();
    }

    /**
     * 根据id查询用户
     * 使用getReference方法查询：
     * 1.获取的对象是一个动态代理对象
     * 2.调用此方法不会立即发送sql语句查询数据库
     * *当调用查询结果对象的时候才会发送查询的sql语句（即什么时候用，什么时候调用sql语句查询数据库）
     * 延迟加载（懒加载）
     * *得到的是一个动态代理对象
     * *什么时候用，什么时候才会查询
     * *我们在平时的开发过程中，一般使用这种加载方式
     */
    @org.junit.Test
    public void testReference() {
        //1.通过工具类获取EntityManager对象
        EntityManager entityManager = JpaUtils.getEntityManager();
        //2.开启事物
        EntityTransaction ts = entityManager.getTransaction();
        ts.begin();
        //3.根据id查询客户
        Customer customer = entityManager.getReference(Customer.class, 1L);
        System.out.println("*********customer**********:" + customer);
        //4.提交事物
        ts.commit();
        //5.释放资源
        entityManager.close();
    }

    /**
     * 删除客户的案例
     */
    @org.junit.Test
    public void testDelete() {
        //1.通过工具类获取EntityManager对象
        EntityManager entityManager = JpaUtils.getEntityManager();
        //2.开启事物
        EntityTransaction ts = entityManager.getTransaction();
        ts.begin();
        //3.删除客户
        //i根据id查询到用户
        //ii调用remove方法删除该用户
        Customer customer = entityManager.getReference(Customer.class, 2L);
        entityManager.remove(customer);
        System.out.println("*********customer**********删除成功啦");
        //4.提交事物
        ts.commit();
        //5.释放资源
        entityManager.close();
    }

    /**
     * 更新客户的操作
     */
    @org.junit.Test
    public void testUpdate() {
        //1.通过工具类获取EntityManager对象
        EntityManager entityManager = JpaUtils.getEntityManager();
        //2.开启事物
        EntityTransaction ts = entityManager.getTransaction();
        ts.begin();
        //3.删除客户
        //i根据id查询到用户
        Customer customer = entityManager.getReference(Customer.class, 4L);
        //ii调用update方法更新该客户
        customer.setCustLevel("宇宙级会员");
        entityManager.merge(customer);
        System.out.println("*********customer**********删除成功啦");
        //4.提交事物
        ts.commit();
        //5.释放资源
        entityManager.close();
    }

    /**
     * 测试一个分页查询
     * sql：SELECT * FROM cst_customer limit 0,2
     * jpql：from Customer
     */
    @org.junit.Test
    public void testPaged() {
        //1.通过工具类获取EntityManager对象
        EntityManager entityManager = JpaUtils.getEntityManager();
        //2.开启事物
        EntityTransaction ts = entityManager.getTransaction();
        ts.begin();
        //3.分页查询
        //i 查询全部
        String jpql = "from Customer";
        Query query = entityManager.createQuery(jpql);
        //ii 对参数赋值  ---分页参数
        //起始索引
        query.setFirstResult(0);
        //每页查询的条数
        query.setMaxResults(2);//每次查询两条
        List<Customer> resultList = query.getResultList();
        for (Customer item : resultList) {
            System.out.println("*****-----"+item);
        }
        //4.提交事物
        ts.commit();
        //5.释放资源
        entityManager.close();
    }

    /**
     * 条件查询
     *      案例：查询客户职业包含“航”字的客户
     *          sql：SELECT * FROM cst_customer WHERE cust_industry like '%航%'
     *          jpql：from Customer where custIndustry like '%航%'
     *          *jpql也支持占位符
     */
    @org.junit.Test
    public void testCondition() {
        //1.通过工具类获取EntityManager对象
        EntityManager entityManager = JpaUtils.getEntityManager();
        //2.开启事物
        EntityTransaction ts = entityManager.getTransaction();
        ts.begin();
        String jpql = "from Customer where custIndustry like ?1";
        Query query = entityManager.createQuery(jpql);
        //对参数赋值  ---占位符参数
        //占位符的索引位置，比较的取值
        query.setParameter(1,"%航%");
        List<Customer> resultList = query.getResultList();
        for (Customer item : resultList) {
            System.out.println("*****-----"+item);
        }
        //4.提交事物
        ts.commit();
        //5.释放资源
        entityManager.close();
    }


}
