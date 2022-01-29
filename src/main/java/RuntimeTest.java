public class RuntimeTest {
    public static void main(String[] args) throws Exception {
        java.lang.Runtime.getRuntime().exec("touch /tmp/java-test");
    }
}
