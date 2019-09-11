package com.jpa.repository;

import com.jpa.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author Wtq
 * @date 2019/9/6 - 14:31
 */
public interface CustomerRepository extends JpaSpecificationExecutor<Customer>,JpaRepository<Customer,Long> {
}
