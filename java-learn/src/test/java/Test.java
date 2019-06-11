import org.joda.time.DateTime;

import java.math.BigInteger;
import java.nio.charset.Charset;

/**
 * ${DESCRIPTION}
 *
 * @author qun.zheng
 * @create 2018/11/29
 **/
public class Test {
    public static void main(String[] args) {
        String h = "h";
        byte[] bytes = h.getBytes(Charset.defaultCharset());
        System.out.println(new BigInteger(1, bytes).toString(2));
        System.out.println(Integer.toBinaryString(0xef));
    }
}
