package com.y3tu.yao.common.security;

import com.y3tu.tool.core.util.StrUtil;
import com.y3tu.yao.common.enums.UserStatusEnum;
import com.y3tu.yao.feign.vo.RoleVO;
import com.y3tu.yao.feign.vo.UserVO;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * security 用户对象
 *
 * @author y3tu
 */
@Data
@NoArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private static final long serialVersionUID = -2636609458742965698L;

    private Integer userId;
    private String username;
    private String password;
    private Integer status;
    private RoleVO roleVO;


    public UserDetailsImpl(UserVO userVo) {
        this.userId = userVo.getId();
        this.username = userVo.getUsername();
        this.password = userVo.getPassword();
        this.status = userVo.getState();
    }

    public UserDetailsImpl(UserVO userVo, RoleVO roleVO) {
        this.userId = userVo.getId();
        this.username = userVo.getUsername();
        this.password = userVo.getPassword();
        this.status = userVo.getState();
        this.roleVO = roleVO;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority(roleVO.getCode()));
        return authorityList;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !StrUtil.equals(UserStatusEnum.LOCK.getCode() + "", status + "");
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return StrUtil.equals(UserStatusEnum.NORMAL.getCode() + "", status + "");
    }
}
