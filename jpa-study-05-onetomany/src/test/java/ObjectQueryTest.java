import com.jpa.domain.Customer;
import com.jpa.domain.LinkMan;
import com.jpa.repository.CustomerRepository;
import com.jpa.repository.LinkManRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * @author Wtq
 * @date 2019/9/9 - 13:51
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml") //指定spring容器的配置信息
public class ObjectQueryTest {
    @Autowired
    private CustomerRepository ctRepository;
    @Autowired
    private LinkManRepository lmRepository;

    /**
     * 测试对象导航查询：
     * 查询一个对象时，通过这个对象查询出所有的关联对象
     * 由于我们是在junit单元测试中执行这段代码，所有的事物并不能在一个事物中完成会报错如下
     * org.hibernate.LazyInitializationException: could not initialize proxy [com.jpa.domain.Customer#1] - no Session
     * 解决：在测试方法上添加注解@Transactional
     */
    @Transactional //解决在java代码中的nosession问题
    @Test
    public void testQuery1() {
        Customer one = ctRepository.getOne(1L);
        for (LinkMan linkMan : one.getLinkMans()) {
            System.out.println(linkMan);
        }
    }

    /**
     * 对象导航查询默认是使用延迟加载形式进行查询的
     *      也就是说：调用findOne方法并不会立即查询,而是我们在使用关联属性的时候发送sql语句查询（延迟加载）
     *      *如果我们不希望在导航查询中使用延迟加载：
     *          修改配置：将延迟加载改为立即加载即可
     *              fetch：需要配置到多表映射关系的注解上
     */
    @Transactional //解决在java代码中的nosession问题
    @Test
    public void testQuery2() {
//        Customer customer = ctRepository.getOne(1L);
//        LinkMan linkMan  = new LinkMan();
//        linkMan.setLkmName("盖伦");
//        linkMan.setCustomer(customer);
//        lmRepository.save(linkMan);

        Customer one = ctRepository.findOne(1L);
        Set<LinkMan> list = one.getLinkMans();
        System.out.println(list.size());
    }
    /**
     * 从多的一方（联系人）查询他所属的客户（多的一方）
     *      *从多的一方查询一的一方，默认使用立即加载的方式
     * 改为延迟加载：
     *    在多的一方（LinkMan）
     *      添加属性 fetch = FetchType.LAZY
     */
    @Transactional
    @Test
    public void testQuery3() {
        LinkMan lm = lmRepository.findOne(1L);
        Customer customer = lm.getCustomer();
        System.out.println("***"+customer);
    }
}
