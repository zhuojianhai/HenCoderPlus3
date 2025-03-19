package com.hencoder.a20_thread_synchronization;

import java.util.concurrent.atomic.AtomicReference;

public class SynchronizedVolatile4Demo implements TestDemo {

    private int x = 0;
    private int y = 0;
    private String name;

    /**
     * volatile 只能对基本数据做到原子操作
     * 不能保证 ++ 操作是原子性的，
     * AtomicXXXX是可以保证原子性的
     */
    private volatile int index = 0; //让变量值原子操作

    /**
     * 对象原子性
     * */
    AtomicReference<User> userAtomicReference;




    private final Object monitor = new Object();
    private final Object monitor2 = new Object();

    private void count(int newValue) {
        synchronized (monitor2) {
            x = newValue;
            y = newValue;
        }
    }

    private  void minus(int delta) {
        synchronized (monitor2){
            x -= delta;
            y -= delta;
        }

    }

    private void setName(String newName) {
        synchronized (monitor) {
            name = newName;
        }
    }

    @Override
    public void runTest() {

    }

    class User{
        String name;
    }
}
