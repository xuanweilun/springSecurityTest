package com.brt.xwl.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.brt.xwl.entity.Role;



/**
 * @ClassName: RoleRepository
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author xuanweilun
 * @date 2019年12月26日 下午17:48:85
 */
public interface RoleRepository extends JpaRepository<Role, Long>{

}