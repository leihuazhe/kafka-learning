import com.today.basic.annotation.KafkaTest;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;

/**
 * Desc: SpringKafkaTest
 * @author: maple
 * @Date: 2018-01-22 18:49
 */
public class SpringKafkaTest {

    @Test
    public void test() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        URL url =  SpringKafkaTest.class.getClassLoader().getResource("services.xml");

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(url.toString());

        context.start();
        Object obj = context.getBean(KafkaTest.class);

        Method m = obj.getClass().getMethod("test1");
        m.invoke(obj);

    }

}
