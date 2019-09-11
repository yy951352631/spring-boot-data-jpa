package com.jpa.test;

import com.jpa.domain.Customer;
import com.jpa.repository.CustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

/**
 * @author Wtq
 * @date 2019/9/5 - 17:39
 */
//生命spring提供的单元测试环境
@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml") //指定spring容器的配置信息
public class JPQLTest {
    @Autowired
    private CustomerRepository customerRepository;

    /**
     * 案例一，根据姓名查询客户
     */
    @Test
    public void testFindJpql() {
        List<Customer> list = customerRepository.findJpql("宇宙中的守望者");
        System.out.println("**********" + list.get(0));
    }

    /**
     * 案例二，根据客户名称和客户id查询客户
     * jpql：from Customer where custName = :custName and custId = : custId
     */
    @Test
    public void testFindByNameAndId() {
        Customer one = customerRepository.finCustByNameAndId("宇宙中的守望者", 7L);
        System.out.println("**********" + one);
    }

    /**
     * 案例三，根据客户id修改客户名称
     * jpql: update Customer set custName = "啦啦啦德玛西亚" where custId = 7
     */
    @Test
    public void updateById() {
        int one = customerRepository.updateCustomer("啦啦啦德玛西亚", 7L);
        System.out.println("**********" + one);
    }

    /**
     *  测试使用sql语句查询所有客户
     */
    @Test
    public void testFindSql() {
        List<Object []> list = customerRepository.findSql("宇宙中的守望者");
        for (Object [] item:list
             ) {
            System.out.println(Arrays.toString(item));
        }
    }

    /**
     * 测试方法命名规则的根据姓名精准查询
     */
    @Test
    public void testNaming() {
       Customer cs = customerRepository.findByCustName("小鱼");
        System.out.println("*-------*"+cs);
    }

    /**
     * 测试方法命名规则的模糊查询
     */
    @Test
    public void testNamingLike() {
        List<Customer> list = customerRepository.findByCustNameLike("%宇宙%");
        for (Customer item:list
                ) {
            System.out.println(item);
        }
    }
    /**
     * 测试方法命名规则的模糊查询
     */
    @Test
    public void testNamingLikeAndId() {
        Customer cs = customerRepository.findByCustNameLikeAndCustId("%宇宙%",10L);
        System.out.println("*-------*"+cs);
    }


}

