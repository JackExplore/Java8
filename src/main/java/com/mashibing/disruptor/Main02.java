package com.mashibing.disruptor;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;

/**
 * 为了 lambda 表达式的写法
 */
public class Main02 {

    public static void main(String[] args) {

        // The factory for the event
        LongEventFactory factory = new LongEventFactory();

        // Specify the size of the ring buffer, must be power of 2.
        int bufferSize = 1024;

        // Construct the Disruptor
        Disruptor<LongEvent> disruptor = new Disruptor<LongEvent>(factory, bufferSize, DaemonThreadFactory.INSTANCE);

        // Connect the handler
        disruptor.handleEventsWith(new LongEventHandler());

        // Start the Disruptor, starts all threads running
        disruptor.start();

        // Get the ring buffer from the Disruptor to be used for publishing
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

        EventTranslator<LongEvent> translator1 = new EventTranslator<LongEvent>() {
            @Override
            public void translateTo(LongEvent longEvent, long l) {
                longEvent.set(8888L);
            }
        };
        ringBuffer.publishEvent(translator1);

        //-------------------------------------------------

        EventTranslatorOneArg<LongEvent, Long> translator2 = new EventTranslatorOneArg<LongEvent, Long>() {
            @Override
            public void translateTo(LongEvent longEvent, long l, Long aLong) {
                longEvent.set(l);
            }
        };
        ringBuffer.publishEvent(translator2, 7777L);

        //-------------------------------------------------

        EventTranslatorTwoArg<LongEvent, Long, Long> translator3 = new EventTranslatorTwoArg<LongEvent, Long, Long>() {
            @Override
            public void translateTo(LongEvent longEvent, long l, Long aLong, Long aLong2) {
                longEvent.set(l + aLong + aLong2);
            }
        };
        ringBuffer.publishEvent(translator3, 6666L, 5555L);

        //----------------------------------------------------------

        EventTranslatorVararg<LongEvent> translatorVararg = new EventTranslatorVararg<LongEvent>() {
            @Override
            public void translateTo(LongEvent longEvent, long l, Object... objects) {
                long result = 0;
                for (Object o : objects) {
                    long ll = (Long)o;
                    result += ll;
                }
                longEvent.set(result);
            }
        };
        ringBuffer.publishEvent(translatorVararg, 10000L, 10000L, 10000L, 10000L, 10000L, 10000L);
    }
}
