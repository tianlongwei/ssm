package my.loong.system.service;

import com.loong.modules.system.entity.Role;
import com.loong.modules.system.service.RoleService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * @program: ssm
 * @description:
 * @author: tlw
 * @create: 2019-12-30 16:21
 */
public class TestRoleService {

    private ClassPathXmlApplicationContext context;
    private RoleService roleService;

    @Before
    public void init(){
        context=new ClassPathXmlApplicationContext("classpath:spring-context-*.xml");
        roleService=context.getBean(RoleService.class);
    }

    @Test
    public void testGetAll(){
        List<Role> roles = roleService.getAll();
        for (Role r:roles
             ) {
            System.out.println(r.getName());
        }
    }



    @After
    public void close(){
        context.close();
    }

}