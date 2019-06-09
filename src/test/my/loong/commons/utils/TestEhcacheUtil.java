package my.loong.commons.utils;

import com.loong.modules.commons.utils.EhcacheUtil;
import org.junit.Test;

/**
 * @program: ssm
 * @description:
 * @AUTHOR: tlw
 * @create: 2019-06-07 15:03
 */
public class TestEhcacheUtil {

    @Test
    public void test(){
        System.out.println(System.getProperty("java.io.tmpdir"));//C:\Users\ADMINI~1\AppData\Local\Temp\
        EhcacheUtil ehcacheUtil = EhcacheUtil.getInstance();
        ehcacheUtil.put("userCache","aaa","123");
        System.out.println(ehcacheUtil.get("userCache","aaa"));//123
    }

}