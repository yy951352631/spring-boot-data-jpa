package com.jpa.repository;

import com.jpa.domain.LinkMan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author Wtq
 * @date 2019/9/9 - 9:17
 */
public interface LinkManRepository extends JpaSpecificationExecutor<LinkMan>, JpaRepository<LinkMan, Long> {
}
