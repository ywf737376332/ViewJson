package com.ywf.framework.utils;


import javax.swing.*;
import java.util.List;

/**
 * TODO
 *
 * @Author YWF
 * @Date 2023/12/13 11:52
 */
public class SwingWorkerUtils<T, P extends T> {

    private P p;

    volatile private static SwingWorkerUtils instance = null;

    static {
    }


    private SwingWorkerUtils() {
        SwingWorker<Boolean, T> swingWorker = new SwingWorker<Boolean, T>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                publish(p);
                T result = backgroundWorker();
                publish(result);
                return true;
            }

            /**
             * 该方法在事件调度线程（也就是GUI线程）上执行，用于更新和处理界面元素。它会接收从doInBackground()方法中发布的数据，并对这些数据进行处理
             *
             * 如果执行swingWorker.get()。需要等待结果执行完毕，则此方法一次性返回chunks数组的值
             * 如果不需要等待，则chunks数组会循环打印出每一个值
             * @param chunks
             */
            @Override
            protected void process(List<T> chunks) {
                updateUI(chunks);
            }

            /**
             * 在所有待处理的数据都已发布后调用，通常用于清理资源或通知其他部分任务已完成
             */
            @Override
            protected void done() {
                success();
            }
        };
        swingWorker.execute();
    }

    public static SwingWorkerUtils getInstance() {
        if (instance == null) {
            synchronized (SwingWorkerUtils.class) {
                if (instance == null) {
                    instance = new SwingWorkerUtils();
                }
            }
        }
        return instance;
    }

    public SwingWorkerUtils(P p) {
        this.p = p;

    }

    private void success() {
        System.out.println("后台耗时任务已经执行完毕");
        ;
    }

    private void updateUI(List<T> chunks) {
        System.out.println("chunks:" + chunks);
    }

    private T backgroundWorker() {

        return p;
    }

    public static void main(String[] args) {
        new SwingWorkerUtils().backgroundWorker();
    }

}
