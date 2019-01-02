package com.ce.notebook.domain;

import com.ce.notebook.entity.SysUser;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: ce
 * @create: 2018-10-23 15:47
 **/
@Repository
public interface SysUserRepository extends BaseRepository<SysUser, Long> {

    List<SysUser> findByUsername (String username);
}
