package com.mashibing.jmh;

import org.openjdk.jmh.annotations.*;

/**
 * 这是一个非常专业的测试工具 - 测试开发
 *
 */
public class PSTest {

/*    @Benchmark
    @Warmup(iterations = 1, time = 3)   // Warmup 预热，由于JVM中对于特定代码会存在优化（本地化），预热对于测试结果很重要
    @Fork(5)                            // 起多少个线程去执行
    @BenchmarkMode(Mode.Throughput)     // 基准测试的模式, 吞吐量
    @Measurement(iterations = 1, time = 3)  // 总共执行多少次测试
    public void TestForEach(){
        PS.foreach();
    }*/



    @Benchmark
    @Warmup(iterations = 1, time = 3)   // Warmup 预热，由于JVM中对于特定代码会存在优化（本地化），预热对于测试结果很重要
    @Fork(5)                            // 起多少个线程去执行
    @BenchmarkMode(Mode.Throughput)     // 基准测试的模式, 吞吐量
    @Measurement(iterations = 1, time = 3)  // 总共执行多少次测试
    public void TestParallel(){
        PS.parallel();
    }


}
