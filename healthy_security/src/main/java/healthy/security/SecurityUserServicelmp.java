package healthy.security;

import it.healthy.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

public class SecurityUserServicelmp implements UserDetailsService {
    @Qualifier("encoder")
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("进入了该方法");
        //1.根据用户名查询数据库,获得User
        User user = findByUsername(username);
        //String username, 用户名
        //String password, 密码
        //Collection<? extends GrantedAuthority> authorities 集合，List,set
        //3.把用户名,数据库的密码,以及查询出来的权限访问,创建UserDetails对象返回
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        // String role 角色名/权限名
        authorities.add(new SimpleGrantedAuthority("add"));
        authorities.add(new SimpleGrantedAuthority("delete"));
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

        // 登陆用户信息
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(),"{noop}"+user.getPassword(),authorities);
        //return null; // 认证失败
        return userDetails;
    }
    /**
     * 通过用户名查询数据库
     * 模拟
     */
    private User findByUsername(String username){
        if("admin".equals(username)) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(encoder.encode("123456"));
            System.out.println(encoder.encode("123456"));
            return user;
        }
        return null;
    }
}
