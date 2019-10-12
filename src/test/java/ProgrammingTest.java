import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

import org.junit.Test;

/**
 * @author zhangjie
 */
public final class ProgrammingTest implements Cloneable {

    final private List a = new ArrayList();

    @Test
    public void implCloneable() throws CloneNotSupportedException {
        ProgrammingTest test = new ProgrammingTest();
        System.out.println(test);
        ProgrammingTest clone = test.clone();
        System.out.println(clone);
        System.out.println(clone.a == test.a);
    }

    @Override
    protected ProgrammingTest clone() throws CloneNotSupportedException {
        return (ProgrammingTest) super.clone();
    }

    @Test
    public void enumSet() {
        EnumSet<Face> set = EnumSet.allOf(Face.class);
        set.removeIf(a -> a.name().equals("ONE"));
        for (Face face : set) {
            for (Face facetwo : set) {
                System.out.println(face.name() + "," + facetwo.name());
            }
        }
    }

    @Test
    public void binaryFloattingPoint() {
//        double funds = 1.00;
//        int itemsBought = 0;
//        for (double price = 0.10; funds >= price; price += 0.10) {
//            funds -= price;
//            itemsBought++;
//        }
//        System.out.println(itemsBought + " items bought.");
//        System.out.println("Change: $" + funds);

        BigDecimal TEN_CENTS = new BigDecimal(".10");
        int itemsBought = 0;
        BigDecimal funds = new BigDecimal("1.00");
        for (BigDecimal price = TEN_CENTS;
             funds.compareTo(price) >= 0;
             price = price.add(TEN_CENTS)) {
            funds = funds.subtract(price);
            itemsBought++;
        }
        System.out.println(itemsBought + " items bought.");
        System.out.println("Money left over: $" + funds);
    }

    @Test
    public void shiftDescriptor() {
        //java中的进制数表示：
        // 二进制： 数值前加 0b;
        // 八进制： 数值前加 0;
        // 十进制： 默认为十进制;
        // 十六进制： 数值前加 0x;
        //int 占 4个字节，共 4*8=32 bit.
//        int num = 8;//8=0b0000 0000 ... 0000 1000=010=0x8
//        byte numbyte = (byte) num;//截取 int值 的最后8位 = 0b0000 10000 = 8
//        int a = -1 << 8;//0b1000 0000 0...0 0000 0001 << 8  ==‘-1’带符号左移8位得到==>  0b1000 0000 0...0 0000 0001 0000 0000= -256
//        byte abyte = (byte) a;//截取 int值 的最后8位 = 0
//        System.out.println(num + " = " + numbyte + "\n 转换后 " + a + " = " + abyte);


        //java 只有三种移位操作：
        // << : 符号左移。不带符号位的左移，移动是带符号位的，在末尾补0。
        // >> ：符号左移。不带符号位的左移，高位按照正负数补对应的符号位（或者说符号位不动，仅数值位移动）。
        // >>> ：无符号右移。符号位一同移动，最高位补0。
        //todo why?? java 不可无符号左移<<<
        //int 数值范围 [-(2 pow 31)] ~ [(2 pow 31) -1]   因为最高位为符号位，所以只有31位用于数值表示
//        int b = -1 <<< 8;
        BigDecimal gPositive = new BigDecimal(2);
        BigDecimal gNegitive = new BigDecimal(-2);
        BigDecimal c = gPositive.pow(30);//intValueExact = 0b0100 0...0
        BigDecimal d = gPositive.pow(31);
        BigDecimal e = gPositive.pow(32);
        BigDecimal f = gNegitive.pow(31);//intValueExact = 0b1000 0...0
//        gNegitive.pow(30),指数为偶数，计算值为正数
        BigDecimal g = gNegitive.pow(29);//intValueExact = 0b1010 0...0

        System.out.println(" 2 pow 30 = " + c.intValueExact());
        System.out.println(" 2 pow 31 = ");
        System.out.println(d);//2147483648
        System.out.println(d.intValue());// -2147483648 .int 的整数最大值为 2147483648-1=2147483647。因此使用long， 截取低位四位byte数值：0b1000 0...0 = -2147483648
//        System.out.println(d.intValueExact());//报错 超出数值范围。因为int值不存在数值：正数2147483648
        System.out.println(" 2 pow 32 = " + e.intValue());
        System.out.println(" -2 pow 31 = " + f.intValueExact());
        System.out.println(" -2 pow 29 = " + g.intValueExact());
        System.out.println();
        System.out.println(" shift operator");
        System.out.println(c.intValueExact() << 1);//0b1000 0..0
        System.out.println(f.intValueExact() << 1);//0b0000 0..0
        System.out.println(g.intValueExact() << 1);//0b1100 0..0//todo
        System.out.println(g.intValueExact() << 2);//0b1000 0..0
        System.out.println(g.intValueExact() << -1);//0b0000 0..0


        //todo 移位符右边的参数可以为负数吗（数可以移动负数位吗）
        // JLS 举例说明，int类型数的左移位数应用int数二进制底五位表示。意思是，只能位移0~31位，只能位移正数位？
        int h = 2 >> -1;// 0000 0010  / (2)-1
//        System.out.println(h);
//        System.out.println(8 >>> -1);
//        System.out.println(8 >> -2);


        //舍弃位不会循环
        //todo int类型移32位 是例外？？？？？
//        System.out.println(1 >> 1);
//        System.out.println(1 >> 32);
//        System.out.println(1 >> 33);
//        System.out.println(1 >>> 34);
//        System.out.println(1 >> 31);
    }

    @Test
    public void ObjectUtilForCheck() {
//        Objects.checkFromIndexSize(0,2,2);


        int length = 2;
        int fromIndex = -3;
        int size = 0;
        if ((length | fromIndex) < 0) {
            System.out.println("sss");
        }
    }

    @Test
    public void regxEmpty() {
        if (!Pattern.compile("^[\\.\\w\\u4e00-\\u9fa5]{1,}$").matcher(null).matches()) {
            System.out.println("不匹配");
        }
    }

    @Test
    public void ramdom() {
        ThreadLocalRandom localRandom = ThreadLocalRandom.current();
//        localRandom.ints();
        int n = localRandom.nextInt();
        System.out.println(n);
    }

    @Test
    public void x() {
        long value = Long.MAX_VALUE / (1_000_000L);
        System.out.println(value);
    }

    enum Face {ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN}
}
