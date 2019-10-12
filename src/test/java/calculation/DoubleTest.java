package calculation;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;

/**
 * @author zhangjie
 */
public class DoubleTest {
    public static void main(String[] args) {
        BigDecimal decimalFromStr = new BigDecimal("300");
        BigDecimal decimalFromNumber = BigDecimal.valueOf(9);

        System.out.println(decimalFromStr.divide(decimalFromNumber, BigDecimal.ROUND_FLOOR));
        System.out.println(decimalFromNumber.divide(decimalFromStr, RoundingMode.FLOOR));


        List<Object> proportionList = new ArrayList<>();
        proportionList.add("10.01%");
        proportionList.add("20.01%");
        proportionList.add("30.01%");
        proportionList.add("40.01%");
        proportionList.add("5");
        DoubleSummaryStatistics obj = proportionList.stream().mapToDouble(propertionStr -> {
            return Double.parseDouble(propertionStr.toString().replace("%", ""));
        }).summaryStatistics();
        System.out.println(obj.getSum());

        double sum = proportionList.stream().mapToDouble(propertionStr -> {
            return Double.parseDouble(propertionStr.toString().replace("%", ""));
        }).sum();
        System.out.println(sum);
    }
}
