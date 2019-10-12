package serialization;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

/**
 * @author zhangjie
 */
public class MutablePeriod {
    // A period instance
    private final Period period;
    // period's start field, to which we shouldn't have access
    private final Date start;
    // period's end field, to which we shouldn't have access
    private final Date end;

    private MutablePeriod() {
        try {
            ByteArrayOutputStream bos =
                    new ByteArrayOutputStream();
            ObjectOutputStream out =
                    new ObjectOutputStream(bos);
// Serialize a valid serialization.Period instance
            out.writeObject(new Period(new Date(), new Date()));
            /**
             * Append rogue "previous object refs" for internal
             * Date fields in serialization.Period. For details, see "Java
             * Object Serialization Specification," Section 6.4.
             *
             * 为Period实例的内部成员添加“流氓对象引用”
             * todo
             */
            byte[] ref = {0x71, 0, 0x7e, 0, 5}; // Ref #5
            bos.write(ref); // The start field
            ref[4] = 4; // Ref # 4
            bos.write(ref); // The end field

// Deserialize serialization.Period and "stolen" Date references
            ObjectInputStream in = new ObjectInputStream(
                    new ByteArrayInputStream(bos.toByteArray()));
            period = (Period) in.readObject();
            start = (Date) in.readObject();//序列化出自己写入的二进制
            end = (Date) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new AssertionError(e);
        }
    }

    public static void main(String[] args) {
        MutablePeriod mp = new MutablePeriod();
        Period p = mp.period;
        Date pEnd = mp.end;

        System.out.println(p);
        System.out.println(p.getEnd());
        System.out.println(pEnd);

// Let's turn back the clock
        pEnd.setYear(78);
        System.out.println(p);
// Bring back the 60s!
        pEnd.setYear(69);
        System.out.println(p);
    }
}
