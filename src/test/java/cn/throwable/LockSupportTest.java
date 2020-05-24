package cn.throwable;

import org.junit.Test;

import java.util.concurrent.locks.LockSupport;

public class LockSupportTest {

    @Test
    public void testParkAndUnpark() throws InterruptedException {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                long last = System.nanoTime();
                StringBuilder builder = new StringBuilder();
                while(i < 1000) {
                    long current = System.nanoTime();
                    builder.append((current - last) / 1000000.0 + " " + i++ + "\n");
                    last = current;
                    if (i == 500) {
                        // 暂停当前线程的调度
                        LockSupport.park(this);
                    }
                }
                System.out.println(builder.toString());
            }
        });
        t1.setPriority(Thread.MAX_PRIORITY);
        t1.setDaemon(true);
        t1.start();
        // sleep 5秒
        // Thread.sleep(5000);
        // 恢复t1的调度
        LockSupport.unpark(t1);
        t1.join();
    }
}
