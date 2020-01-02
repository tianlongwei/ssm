package my.loong.system.entity;

import com.loong.modules.system.entity.Role;
import com.loong.modules.system.entity.User;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: ssm
 * @description:
 * @author: tlw
 * @create: 2019-12-31 17:57
 */
public class TestUser {

    @Test
    public void testGetRoleIdList(){
        User user=new User();
        Role role=new Role();
        role.setId("dddddddddddd");
        List<Role> roles=new ArrayList<>();
        roles.add(role);
        user.setRoleList(roles);
        List<String> roleIdList = user.getRoleIdList();


        System.out.println(roleIdList.size());
        for (int i = 0; i < roleIdList.size(); i++) {
            System.out.println(roleIdList.get(i));
        }
    }
}