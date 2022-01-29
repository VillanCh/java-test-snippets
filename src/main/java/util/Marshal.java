package util;

import com.sun.xml.internal.ws.util.ByteArrayBuffer;

import java.io.ByteArrayInputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Base64;

public class Marshal {
    public static String exec(Object i) throws Exception {
        ByteArrayBuffer out = new ByteArrayBuffer();
        new ObjectOutputStream(out).writeObject(i);
        return new String(Base64.getEncoder().encode(out.toByteArray()));
    }

    public static Object read(String base64enc) throws Exception {
        ByteArrayInputStream in = new ByteArrayInputStream(Base64.getDecoder().decode(base64enc));
        ObjectInputStream oi = new ObjectInputStream(in);
        return oi.readObject();
    }
}
