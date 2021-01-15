package com.mashibing.disruptor;

import com.lmax.disruptor.EventHandler;

public class LongEventHandler implements EventHandler<LongEvent> {
    /**
     *
     * @param longEvent
     * @param sequence      RingBuffer 的序号
     * @param endOfbatch    是否最后一个元素
     * @throws Exception
     */
    @Override
    public void onEvent(LongEvent longEvent, long sequence, boolean endOfbatch) throws Exception {
        System.out.println(longEvent);
    }
}
