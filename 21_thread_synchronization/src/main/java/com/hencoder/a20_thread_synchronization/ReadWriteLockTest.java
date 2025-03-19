package com.hencoder.a20_thread_synchronization;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁分离
 */
public class ReadWriteLockTest {
    ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
    ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();


    private int x = 0;

    private void count(){
        writeLock.lock();
        try {
            x++;
        }finally {
           writeLock.unlock();
        }
    }

    private void print(int time){

        readLock.lock();
        try {
            for (int i = 0; i < time; i++) {
                System.out.println(x+" ");
            }
            System.out.println();
        }finally {
            readLock.unlock();
        }


    }
}
