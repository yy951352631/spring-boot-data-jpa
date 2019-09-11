package test;

import com.jpa.domain.Customer;
import com.jpa.repository.CustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.criteria.*;
import java.util.List;

/**
 * @author Wtq
 * @date 2019/9/6 - 14:28
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml") //指定spring容器的配置信息
public class SpecTest {
    @Autowired
    private CustomerRepository customerRepository;

    /**
     * 根据条件查询单个对象
     */
    @Test
    public void testSpec() {
//匿名内部类
/**
* 自定义查询条件
*      1.实现Specification接口需要提供泛型（即我们需要查询对象的类型）
*      2.需要实现一个toPredicate方法（构造查询条件）
*      3.需要借助方法参数中的两个参数
*           root：获取需要查询的对象属性
*           CriteriaBuilder：用来构造查询条件的内部封装了很多查询条件（如模糊匹配，精准匹配，不等于 ..等）
* 案例：根据客户名称精准查询，查询客户名为：“小船” 客户
*      查询条件：
*          1.查询方式
*              存在于CriteriaBuilder对象中
*          2.比较属性名称
*              存在root对象中
*/
        Specification<Customer> spec = new Specification<Customer>() {
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                //1.获取比较的属性
                Path<Object> custName = root.get("custName");
                //2.构造查询条件   sql： select * from xxx where name = 'www'
                /**
                 * 第一个属性：需要比较的属性（是一个path对象）
                 * 第二个参数：当前需要比较的取值
                 */
                Predicate predicate = cb.equal(custName, "小船");//进行精准匹配(比较的属性名，比较的属性取值)
                return predicate;
            }
        };
        Customer one = customerRepository.findOne(spec);
        System.out.println("*****-----*****" + one);
    }

    /**
     * 多条件查询
     * 案例：根据客户名和客户所属行业进行查询（Bob，航天）
     */
    @Test
    public void testspec1() {
        /**
         * root:构造属性
         *      1.客户名称
         *      2.所属行业
         * cb：构造查询
         *      1.构造客户名的精准匹配查询
         *      2.构造所属行业的精准匹配查询
         *      3.将以上两个查询联系起来
         */
        Specification<Customer> spec = new Specification<Customer>() {
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Path<Object> custName = root.get("custName");
                Path<Object> custIndustry = root.get("custIndustry");

                //构造查询
                //1.构造客户名的精准匹配查询
                Predicate p1 = cb.equal(custName, "Bob");
                //2.构造所属行业的精准匹配查询
                Predicate p2 = cb.equal(custIndustry, "航天");
                //3.将多个查询条件组合到一起（根据业务需求进行组合  如：满足条件1并且满足条件2 ---与关系，满足条件1或者满足条件2 --- 或关系）
                Predicate and = cb.and(p1, p2);//以与的形式拼接多个查询条件
//                cb.or()//以或的形式拼接多个查询条件
                return and;
            }
        };
        Customer one = customerRepository.findOne(spec);
        System.out.println("*****-----*****" + one);
    }

    /**
     * 案例：根据客户姓名进行模糊匹配查询并返回客户列表
     * 客户名包含“航天”的客户
     * equals：直接得到path对象（属性），然后进行比较即可
     * <p>
     * gt,lt,le,like : 不能直接用path对象去比较，我们需要得到path对象，
     * 然后根据得到的path对象去指定比较的参数类型然后再去进行比较
     * *指定参数类型的方式：path.as(类型的字节码对象)
     */
    @Test
    public void testSpec2() {
        //构造查询条件
        Specification<Customer> spec = new Specification<Customer>() {
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                //1)查询属性：属性名
                Path<Object> custName = root.get("custName");

                //2)查询方式：模糊匹配
                Predicate like = cb.like(custName.as(String.class), "%宇宙%");
                return like;
            }
        };
//        List<Customer> list = customerRepository.findAll(spec);
//        for (Customer item : list) {
//            System.out.println("*****like*****" + item);
//        }

        //添加排序
        //创建排序对象,需要调用构造方法实例化sort对象
        /**
         * Sort.Direction.DESC : 倒序
         * Sort.Direction.ASC ：升序
         */
        Sort sort = new Sort(Sort.Direction.DESC,"custId");
        List<Customer> list = customerRepository.findAll(spec,sort);
        for (Customer item : list) {
            System.out.println("*****like*****" + item);
        }
    }
    /**
     * 分页查询
     *      findAll(Specification,Pageable)方法
     *          Specification：查询条件
     *          Pageable：分页参数
     *              分页参数：包含了查询的页面以及每页查询的条数
     *      findAll(Pageable) ：没有条件的分页
     * 上述两个方法都返回一个Page对象（SpringDataJpa为我们封装好的pageBean，我们可以通过这个方法获取到数据列表，总条数等。。）
            */
    @Test
    public void testSpec4(){
        Specification<Customer> spec = new Specification<Customer>() {
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return null;
            }
        };
        //PageRequest 是Pageable接口的实现类
        /**
         * 在创建PageRequest的过程中，我们需要调用它的构造方法传入两个参数
         *      1.当前查询的页数（从0开始）
         *      2.没有查询的数量
         */
        Pageable pageable = new PageRequest(0,2);
        Page<Customer> all = customerRepository.findAll(pageable);
        System.out.println(all.getContent());//得到数据集合列表
        System.out.println(all.getTotalElements());//得到总条数
        System.out.println(all.getTotalPages());//得到总页数
    }
}
