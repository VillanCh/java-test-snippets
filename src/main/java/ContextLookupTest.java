import javax.naming.Context;
import javax.naming.InitialContext;

public class ContextLookupTest {
    public static void main(String[] args) throws Exception {
//        System.out.println("Test Java RMI");
//
//        Registry register = LocateRegistry.getRegistry(59857);
//        register.lookup("tasdfasd");

        Context ctx = new InitialContext();
//        ctx.lookup("rmi://127.0.0.1:56859/tesasdfasdfasdf");
        try {
            ctx.lookup("rmi://127.0.0.1:63622/1");
        } catch (Exception e) {
            System.out.println("1 error");
        }
        try {
            ctx.lookup("rmi://127.0.0.1:63622/2222222");
        } catch (Exception e) {
            System.out.println("unpressed error");
        }

        try {
            ctx.lookup("rmi://127.0.0.1:63622/33333333");
        } catch (Exception e) {
            System.out.println("unpressed 2 error");
            throw e;
        }

    }
}
