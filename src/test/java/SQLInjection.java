import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

/**
 * @author zhangjie
 */
public class SQLInjection {
    private static final String MP_GENERAL_PARAMNAME = "MPGENVAL";
    private static final String MYBATIS_PLUS_TOKEN = "#{%s.paramNameValuePairs.%s}";
    private static final String PLACE_HOLDER = "{%s}";
    private AtomicInteger paramNameSeq = new AtomicInteger(1);
    private Map<String, Object> paramNameValuePairs = new HashMap<>();

    @Test
    public void formatString() {
        String genParamName = MP_GENERAL_PARAMNAME + paramNameSeq.incrementAndGet();
        String sqlStr = "{0}".replace(String.format(PLACE_HOLDER, 0),
                String.format(MYBATIS_PLUS_TOKEN, "ew", genParamName));
        paramNameValuePairs.put(genParamName, "1\' or 1=1 or name=\'");
        System.out.println(paramNameValuePairs);
    }
}
