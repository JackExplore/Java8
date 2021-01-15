package com.mashibing.disruptor;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;

/**
 * 为了 lambda 表达式的写法
 */
public class Main03 {

    public static void main(String[] args) {


        int bufferSize = 1024;

        // Construct the Disruptor
        Disruptor<LongEvent> disruptor = new Disruptor<LongEvent>(LongEvent::new, bufferSize, DaemonThreadFactory.INSTANCE);

        // Connect the handler
        disruptor.handleEventsWith(((longEvent, l, b) -> System.out.println("Event : " + longEvent)));

        // Start the Disruptor, starts all threads running
        disruptor.start();

        // Get the ring buffer from the Disruptor to be used for publishing
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();


//        ringBuffer.publishEvent(((longEvent, l) -> longEvent.set(10000L)));

        ringBuffer.publishEvent(((longEvent, l, o) -> longEvent.set(o)), 10000L);

        ringBuffer.publishEvent(((longEvent, l, o, o2) -> longEvent.set(o + o2)), 10000L, 10000L);

    }
}
