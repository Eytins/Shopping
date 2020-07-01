import com.zte.shopping.util.MD5;
import org.junit.Test;


/**
 * Created by Eytins
 */

public class test {

    @Test
    public void makeMD5() {
        MD5 md5 = new MD5();
        System.out.println(md5.md5Change("admin"));
    }
}
