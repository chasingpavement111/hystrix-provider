import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.Test;

/**
 * 
 *  
 * @author zhangjie
 * 
 */
public final class ProgrammingTest implements Cloneable{

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
    public void enumSet(){
        EnumSet<Face> set = EnumSet.allOf(Face.class);
        set.removeIf(a -> a.name().equals("ONE"));
        for(Face face : set){
            for(Face facetwo : set){
            System.out.println(face.name() +","+facetwo.name());
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
    public void ObjectUtilForCheck(){
//        Objects.checkFromIndexSize(0,2,2);


        int length = 2;
        int fromIndex = -3;
        int size = 0;
        if((length | fromIndex) < 0){
            System.out.println("sss");
        }
    }

    @Test
    public void DefenciveCopy(){
        Date start = new Date();
        Date end = new Date();
        Period period = new Period(start,end);
        end.setYear(10);
    }

    @Test
    public void ramdom(){
        ThreadLocalRandom localRandom = ThreadLocalRandom.current();
//        localRandom.ints();
        int n = localRandom.nextInt();
        System.out.println(n);
    }
    enum Face{ONE,TWO,THREE,FOUR,FIVE,SIX,SEVEN}
}

class Period {
    private static int a;
    private final Date start;
    private final Date end;

    Period(Date start, Date end) {
//        if(start.compareTo(end)>0){
//            throw new IllegalArgumentException("exceed");
//        }
        this.start = new Date(start.getTime());
        this.end = new Date(end.getTime());
        if(this.start.compareTo(this.end)>0){
            throw new IllegalArgumentException("exceed");
        }
    }
}
