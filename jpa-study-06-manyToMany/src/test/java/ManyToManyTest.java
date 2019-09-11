import com.jpa.domain.Role;
import com.jpa.domain.User;
import com.jpa.repository.RoleRepository;
import com.jpa.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author Wtq
 * @date 2019/9/9 - 11:41
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml") //指定spring容器的配置信息
public class ManyToManyTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Test
    @Transactional
    @Rollback(value = false)
    public void testAdd() {
        User user = new User();
        user.setUserName("阿狸");

        Role role = new Role();
        role.setRoleName("法师");

        //配置用户到角色关系，可以对中间表的数据进行维护
        /**
         * 如果设置双向的关系，第二次插入时两个键值所构成的联合属性会导致报错（键值冲突）
         * 因而我们需要一方放弃外键的维护权
         *  * 放弃原则：被动的一方放弃  如在本案例中： 角色被选择，则放弃角色的外键维护权
         */
        user.getRoles().add(role);
        role.getUsers().add(user);

        userRepository.save(user);
        roleRepository.save(role);
    }


    @Test
    @Transactional
    @Rollback(value = false)
    public void testCascade() {
        User user = new User();
        user.setUserName("阿狸");

        Role role = new Role();
        role.setRoleName("法师");

        user.getRoles().add(role);
        role.getUsers().add(user);
        //级联保存，通过保存用户保存角色
        userRepository.save(user);
    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void testRemove() {
        User user = userRepository.findOne(1L);
        userRepository.delete(user);
    }
}
