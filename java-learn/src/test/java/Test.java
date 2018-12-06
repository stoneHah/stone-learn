import org.joda.time.DateTime;

/**
 * ${DESCRIPTION}
 *
 * @author qun.zheng
 * @create 2018/11/29
 **/
public class Test {
    public static void main(String[] args) {
        System.out.println(new DateTime(1980,1,1,0,0,0).getMillis());
        System.out.println(new DateTime(2018,1,1,0,0,0).getMillis());
    }
}
