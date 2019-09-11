package com.study.jpa.util;

/**
 * @author Wtq
 * @date 2019/9/4 - 16:06
 */

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * 解决实体管理类工程创建浪费资源和耗时的问题
 * 通过使用静态代码块的方式，当程序第一次访问工具类的时候，创建一个公共的EntityManagerFactory
 * 第一次访问getEntityManager方法；经过静态代码块创建一个Factory对象，在调用方法创建一个EntityManager对象
 * 第二次调用方法getEntityManager，直接通过一个已经创建好的factory对象，创建EntityManager对象
 *
*/
    public class JpaUtils {
        private static EntityManagerFactory entityManagerFactory;

        static {
            //1.加载配置文件，根据配置文件创建EntityManagerFactory
            entityManagerFactory = Persistence.createEntityManagerFactory("myJpa");
        }
        //获取EntityManager实体对象
        public static EntityManager getEntityManager(){
            return entityManagerFactory.createEntityManager();
        }


}
