package my.loong.system.service;

import com.loong.modules.system.entity.User;
import com.loong.modules.system.service.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * @program: ssm
 * @description:
 * @author: tlw
 * @create: 2019-12-30 16:40
 */
public class TestUserService {

    private ClassPathXmlApplicationContext context;
    private UserService userService;

    @Before
    public void init(){
         context = new ClassPathXmlApplicationContext("classpath:spring-context-*.xml");
         userService=context.getBean(UserService.class);
    }

    @Test
    public void testGetAll(){
        List<User> users = userService.getAll();
        for (int i = 0; i < users.size(); i++) {
            System.out.println(users.get(i).getUsername());
        }
    }


    @After
    public void close(){
        context.close();
    }
}