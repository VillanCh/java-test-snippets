package util;

import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;

public class TemplateUtil {
    public static TemplatesImpl getTemplateObj(byte[] code) throws Exception {
        TemplatesImpl obj = new TemplatesImpl();

        Reflections.setFieldValue(obj, "_bytecodes", new byte[][]{code});
        Reflections.setFieldValue(obj, "_name", "_");
        Reflections.setFieldValue(obj, "_tfactory", new TransformerFactoryImpl());
        return obj;
    }
}
