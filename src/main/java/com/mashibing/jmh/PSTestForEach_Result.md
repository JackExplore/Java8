"C:\Program Files\Java\jdk1.8.0_261\bin\java.exe" -Dfile.encoding=UTF-8 -classpath C:\Users\Administrator\IdeaProjects\Test8\target\test-classes;C:\Users\Administrator\IdeaProjects\Test8\target\classes;C:\Users\Administrator\.m2\repository\org\openjdk\jmh\jmh-core\1.21\jmh-core-1.21.jar;C:\Users\Administrator\.m2\repository\net\sf\jopt-simple\jopt-simple\4.6\jopt-simple-4.6.jar;C:\Users\Administrator\.m2\repository\org\apache\commons\commons-math3\3.2\commons-math3-3.2.jar;C:\Users\Administrator\.m2\repository\org\openjdk\jmh\jmh-generator-annprocess\1.21\jmh-generator-annprocess-1.21.jar org.openjdk.jmh.Main com.mashibing.jmh.PSTest.TestForEach
# JMH version: 1.21
# VM version: JDK 1.8.0_261, Java HotSpot(TM) 64-Bit Server VM, 25.261-b12
# VM invoker: C:\Program Files\Java\jdk1.8.0_261\jre\bin\java.exe
# VM options: -Dfile.encoding=UTF-8
# Warmup: 5 iterations, 10 s each
# Measurement: 5 iterations, 10 s each
# Timeout: 10 min per iteration
# Threads: 1 thread, will synchronize iterations
# Benchmark mode: Throughput, ops/time
# Benchmark: com.mashibing.jmh.PSTest.TestForEach

# Run progress: 0.00% complete, ETA 00:08:20
# Fork: 1 of 5
# Warmup Iteration   1: 0.789 ops/s
# Warmup Iteration   2: 0.805 ops/s
# Warmup Iteration   3: 0.815 ops/s
# Warmup Iteration   4: 0.809 ops/s
# Warmup Iteration   5: 0.823 ops/s
Iteration   1: 0.812 ops/s
Iteration   2: 0.818 ops/s
Iteration   3: 0.823 ops/s
Iteration   4: 0.823 ops/s
Iteration   5: 0.810 ops/s

# Run progress: 20.00% complete, ETA 00:07:20
# Fork: 2 of 5
# Warmup Iteration   1: 0.809 ops/s
# Warmup Iteration   2: 0.821 ops/s
# Warmup Iteration   3: 0.818 ops/s
# Warmup Iteration   4: 0.809 ops/s
# Warmup Iteration   5: 0.818 ops/s
Iteration   1: 0.819 ops/s
Iteration   2: 0.819 ops/s
Iteration   3: 0.819 ops/s
Iteration   4: 0.819 ops/s
Iteration   5: 0.819 ops/s

# Run progress: 40.00% complete, ETA 00:05:30
# Fork: 3 of 5
# Warmup Iteration   1: 0.819 ops/s
# Warmup Iteration   2: 0.803 ops/s
# Warmup Iteration   3: 0.810 ops/s
# Warmup Iteration   4: 0.802 ops/s
# Warmup Iteration   5: 0.813 ops/s
Iteration   1: 0.813 ops/s
Iteration   2: 0.771 ops/s
Iteration   3: 0.801 ops/s
Iteration   4: 0.786 ops/s
Iteration   5: 0.793 ops/s

# Run progress: 60.00% complete, ETA 00:03:39
# Fork: 4 of 5
# Warmup Iteration   1: 0.783 ops/s
# Warmup Iteration   2: 0.784 ops/s
# Warmup Iteration   3: 0.777 ops/s
# Warmup Iteration   4: 0.785 ops/s
# Warmup Iteration   5: 0.784 ops/s
Iteration   1: 0.786 ops/s
Iteration   2: 0.752 ops/s
Iteration   3: 0.780 ops/s
Iteration   4: 0.774 ops/s
Iteration   5: 0.786 ops/s

# Run progress: 80.00% complete, ETA 00:01:48
# Fork: 5 of 5
# Warmup Iteration   1: 0.800 ops/s
# Warmup Iteration   2: 0.790 ops/s
# Warmup Iteration   3: 0.791 ops/s
# Warmup Iteration   4: 0.786 ops/s
# Warmup Iteration   5: 0.799 ops/s
Iteration   1: 0.799 ops/s
Iteration   2: 0.786 ops/s
Iteration   3: 0.797 ops/s
Iteration   4: 0.803 ops/s
Iteration   5: 0.799 ops/s


Result "com.mashibing.jmh.PSTest.TestForEach":
  0.800 ±(99.9%) 0.014 ops/s [Average]
  (min, avg, max) = (0.752, 0.800, 0.823), stdev = 0.019
  CI (99.9%): [0.786, 0.815] (assumes normal distribution)


# Run complete. Total time: 00:08:55

REMEMBER: The numbers below are just data. To gain reusable insights, you need to follow up on
why the numbers are the way they are. Use profilers (see -prof, -lprof), design factorial
experiments, perform baseline and negative tests that provide experimental control, make sure
the benchmarking environment is safe on JVM/OS/HW level, ask for reviews from the domain experts.
Do not assume the numbers tell you what you want them to tell.

Benchmark            Mode  Cnt  Score   Error  Units
PSTest.TestForEach  thrpt   25  0.800 ± 0.014  ops/s

Process finished with exit code 0
