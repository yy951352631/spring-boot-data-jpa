
import com.jpa.domain.Customer;
import com.jpa.domain.LinkMan;
import com.jpa.repository.CustomerRepository;
import com.jpa.repository.LinkManRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Wtq
 * @date 2019/9/9 - 9:19
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml") //指定spring容器的配置信息
public class MyTest {
    @Autowired
    private CustomerRepository ctRepository;
    @Autowired
    private LinkManRepository lmRepository;

    @Test
    @Transactional
    @Rollback(value = false)
    public void testAdd() {
        LinkMan lm = new LinkMan();
        lm.setLkmName("Jerry鼠");
        Customer customer = new Customer();
        customer.setCustName("Tom猫"); //由于配置了一的一方到多的一方的关联关系，这里会发送一条update语句
        customer.getLinkMans().add(lm); //由于配置了多的一方到一的一方的关联关系，所以当保存的时候就会对外键赋值
        /*
        以上两个语句都执行时会有一条多余的update语句
            *由于一的一方可以维护外键，因而会发送update语句
            * 想要解决此问题，只需要在一的一方放弃维护外键即可（放弃维护权）
         */
        lm.setCustomer(customer);
        ctRepository.save(customer);
        lmRepository.save(lm);
    }

    /**
     * 级联添加：保存一个客户的时候，保存他的所有联系人
     */
    @Test
    @Transactional
    @Rollback(value = false)
    public void testCascadeAdd(){

        LinkMan lm = new LinkMan();
        lm.setLkmName("Jerry鼠");
        Customer customer = new Customer();
        customer.setCustName("Tom猫");
        lm.setCustomer(customer);
        customer.getLinkMans().add(lm);
        ctRepository.save(customer);
    }

    /**
     * 级联删除：删除一个客户的时候，删除他的所有联系人
     */
    @Test
    @Transactional
    @Rollback(value = false)
    public void testCascadeDelete(){
        Customer customer = ctRepository.findOne(6L);
        ctRepository.delete(customer);
    }
}
