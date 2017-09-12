package rn.heruijun.com.filedownload;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by heruijun on 2017/9/8.
 */

public class ThreadPoolTest {

    public static void main(String args[]) {

        final LinkedBlockingDeque queue = new LinkedBlockingDeque();   // 无限队列
        // final ArrayBlockingQueue  = new ArrayBlockingQueue<Runnable>(10);    // 有限队列
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 4, 60, TimeUnit.MILLISECONDS, queue);

        for (int i = 0; i < 16; i++) {
            final int index = i;
            threadPoolExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("index " + index + " queue size = " + queue.size());
                }
            });
        }
    }

}
