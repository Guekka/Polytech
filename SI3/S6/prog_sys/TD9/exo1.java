import java.lang.management.ManagementFactory;

class exo1 {
    public static native int nativePid();

    static {
        System.loadLibrary("exo1");
    }

    public static void main(String []args) {
              String runtime = ManagementFactory.getRuntimeMXBean().getName();
          long pid = ProcessHandle.current().pid();

          System.out.println("runtime: " + runtime);
          System.out.println("pid: " + pid);
          System.out.println("native pid: " + nativePid());

    }
}