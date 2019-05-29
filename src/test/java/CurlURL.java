import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * 
 *  
 * @author zhangjie
 * 
 */
public class CurlURL {

    public static void main(String[] args) throws IOException {
        try (InputStream in = new URL(args[0]).openStream()) {
//            in.transferTo(new FileOutputStream(new File("test.html")));//todo test jdk 9 新增的方法
        }
    }
}