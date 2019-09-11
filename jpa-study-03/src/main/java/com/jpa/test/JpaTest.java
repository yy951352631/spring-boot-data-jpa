package com.jpa.test;

import com.jpa.domain.Customer;
import com.jpa.repository.CustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author Wtq
 * @date 2019/9/5 - 14:32
 */
//声明spring提供的单元测试环境
@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml") //指定spring容器的配置信息
public class JpaTest {

    @Autowired
    private CustomerRepository customerRepository;

    /**
     * findOne根据id查询
     */
    @Test
    public void testFindOne(){
        Customer customer = customerRepository.findOne(4L);
        System.out.println("***customer****"+customer);
    }

    /**
     * save方法：
     *      保存或更新：
     *      根据你传递的对象是否存在主键id，如果没有id主键属性则进行保存操作，如果存在id 的主键属性，
     *      那么就会根据id查询数据库然后更新数据
     */
    @Test
    public void testSave(){
        Customer customer = new Customer();
        customer.setCustName("宇宙中的守望者55665");
        customer.setCustLevel("宇宙级vip");
        customer.setCustId(8L);//有id时进行保存操作
        customerRepository.save(customer);
    }
    /**
     * 测试我们的删除操作
     */
    @Test
    public void testDelete(){
        customerRepository.delete(8l);
    }

    /**
     * 查询所有
     */
    @Test
    public void testFindAll(){
        List<Customer> all = customerRepository.findAll();
        for (Customer item:all
             ) {
            System.out.println("?***?"+item);
        }
    }
    /**
     * 查询我们的客户数量
     */
    @Test
    public void testCount(){
        long count = customerRepository.count();
        System.out.println("总数："+count);
    }
    /**
     * 判断id为4的客户是否存在
     *      1.可以查询id为7的客户
     *      如果值为空则不存在，不空则存在
     *      2.判断id为7客户的数量，数量为0不存在，>0 则存在
     *
     */
    @Test
    public void testExists(){
        boolean exists = customerRepository.exists(7L);
        System.out.println("id为7的客户是否存在"+exists);
    }
    /**
     * 根据id查询客户
     *      @Transactional:保证getOne正常运行
     *
     * findOne   ： em.find()            : 立即加载
     * getOne    ：  em.getReference()   :延迟加载  -->  *返回的是一个动态代理对象; *什么时候用，什么时候发sql语句查询
     * getOne：
     */
    @Transactional
    @Test
    public void testGetOne(){
        Customer one = customerRepository.getOne(7L);
        System.out.println("getOne--------:"+one);
    }




}
