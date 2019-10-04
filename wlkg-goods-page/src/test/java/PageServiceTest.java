import com.wlkg.goods.WlkgGoodsPage;
import com.wlkg.goods.service.PageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WlkgGoodsPage.class)
public class PageServiceTest {
    @Autowired
    private PageService pageService;

    @Test
    public void test01(){
        Map<String, Object> map= pageService.loadModel(2L);
        System.out.println(map);
    }
}