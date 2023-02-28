package task7;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import jakarta.transaction.Transactional;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/application-test.properties")
@TestExecutionListeners({
     TransactionalTestExecutionListener.class,
     DependencyInjectionTestExecutionListener.class,
     DbUnitTestExecutionListener.class
})
@Transactional
public class AbstractTest {

}
