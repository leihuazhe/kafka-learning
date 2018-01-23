import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.net.URL;

/**
 * Desc: SpringKafkaTest
 * @author: maple
 * @Date: 2018-01-22 18:49
 */
public class SpringKafkaTest {

    @Test
    public void test() {

        URL url =  SpringKafkaTest.class.getClassLoader().getResource("services.xml");

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(url.toString());

        context.start();

    }

}
