import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JobTest {
    @Test
    public void s(){
        new ClassPathXmlApplicationContext("applicationContext-jobs.xml");
    }
}
