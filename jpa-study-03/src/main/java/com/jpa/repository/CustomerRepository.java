package com.jpa.repository;

import com.jpa.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author Wtq
 * @date 2019/9/5 - 14:16
 */

/**
 * 符合SpringDataJpa规范
 * JpaRepository<操作的实体类类型，实体类中的主键类型>
 * *封装了简单的crud操作
 * JpaSpecificationExecutor<操作的实体类类型>
 * *封装了复杂查询操作（如分页操作）
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {
    /**
     * 案例一，根据姓名查询客户
     * 根据客户名称查询客户
     * 使用jpql形式查询
     * jpql：from Customer where custName = ?
     * 配置jpql语句，使用的是@Query注解
     */
    @Query(value = "from Customer where custName = :custName")
    public List<Customer> findJpql(@Param("custName") String custName);

    /**
     * 案例二，根据客户名称和客户id查询客户
     * jpql：from Customer where custName = :custName and custId = : custId
     *  对于多个占位符参数
     *  赋值的时候默认情况下，占位符的顺序需要与函数中参数的顺序保持一致
     *  我们也可以指定占位符的位置  通过 ?*的方式
     */
//    @Query(value = "from Customer where custName = :custName and custId = :custId")  //注意 :custId :与变量共同构成占位符
//    public Customer finCustByNameAndId(@Param("custName") String custName, @Param("custId") Long custId);
    @Query(value = "from Customer where custName = ?1 and custId = ?2")  //注意 :custId :与变量共同构成占位符
    public Customer finCustByNameAndId(String custName,  Long custId);

    /**
     * 使用Jpql完成更新操作
     *      案例：根据id对客户进行更新操作
     *          更新7号顾客的名称，将名称改为：啦啦啦德玛西亚
     *              sql： update cst_customer set cust_name = '啦啦啦德玛西亚' where cust_id = 7
     *              jpql: update Customer set custName = "啦啦啦德玛西亚" where custId = 7
     *   @Query 注解代表进行查询
     *      *声明此方法是用来进行更新操作的
     *   @Modifying注解：代表当前执行的是一个更新操作
     *   注意！：
     *   *SpringDataJpa中使用jpql完成 更新/删除操作 ，需要我们的事物支持 @Transactional
     *   *默认执行结束之后回滚事物，（这种情况会造成看似更新成功（sql语句正常发出）但是数据库信息没有修改的情况）
     *   @Rollback : 是否自动回滚事物   false |true  不回滚事物  | 回滚事物
     *
     *   如果使用spring-test进行单元测试，它会自动回滚即@Rollback(value = true)
     *  但是我在使用过程无论value为true/false，它都无作用
     *  最后，因为spring没有取得事务控制，即aop没有，不能执行回滚
     *
     */
    @Query(value = "update Customer set custName = :custName where custId = :custId")
    @Modifying
    @Transactional
    @Rollback(value = false)
    public int updateCustomer(@Param("custName") String custName,@Param("custId") Long custId);

    /**
     * 使用sql的形式进行查询
     *      查询全部客户
     *      sql：SELECT * FROM cst_customer
     * @Query
     *      value ： sql语句
     *      nativeQuery
     *          true : 代表进行本地查询，即使用sql语句进行查询
     *          false （默认）:代表jpql查询
     *
     */
//    @Query(value = "SELECT * FROM cst_customer",nativeQuery = true)
    @Query(value = "SELECT * FROM cst_customer WHERE cust_name like ?1",nativeQuery = true)
    public List<Object []> findSql(String name);
    /**
     * 方法名的约定：
     *      findBy 开头：查询
     *          对象中的属性名称：（首字母大写）：查询的条件
     *          *默认情况下：使用等于的方式进行查询
     *              *特殊查询方式
     *findByCustName :表示 根据客户名称查询
     * 在springDataJpa的运行阶段，会根据方法名称进行解析
     *      findBy  （翻译）--> from xxx（实体类）
     *      属性名称  where custName = ？
     *
     * 1.findBy +属性名称  ：代表根据属性名称进行完全匹配查询（=）
     * 2.findBy + 属性名称 + “查询方式 （like isnull）”
     *      exp: findByCustNameLike
     * 3.多条件的查询
     *      findBy + 属性名 + “查询方式” + “多条件的连接符（and / or）” + 其他的属性名 +  “其他的查询方式”
     */

    public Customer findByCustName(String name);

    public List<Customer> findByCustNameLike(String name);

    //使用客户名称模糊匹配+ 客户id精准匹配 (顺序要与定义函数方法名称的先后一一对应)
    public Customer findByCustNameLikeAndCustId(String name,Long id);

}
