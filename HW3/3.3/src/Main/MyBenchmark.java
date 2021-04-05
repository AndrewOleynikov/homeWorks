/*
 * Copyright (c) 2014, Oracle America, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package Main;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 0)
@Fork(1)
public class MyBenchmark {
   public int[] smallValue = {9, 9, 9, 9, 9};
   public int[] mediumValue = {9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9};
   public int[] bigValue = {9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9};
   @Param({"1", "2", "4"})
   public int procs;
   @Param({"Big" ,  "Medium", "Small" })
   public String type;
   //@Threads(Threads.MAX)
   @Benchmark

   @Warmup(iterations = 2, time = 50, timeUnit = TimeUnit.MILLISECONDS)
   @Measurement(iterations = 1, time = 50, timeUnit = TimeUnit.MILLISECONDS)
   public void test() {
     // System.out.println(2+2);
      if (procs == 1 && type == "Big") {System.out.println(ParallelScan.oneThreadSolution(smallValue, smallValue, smallValue.length)); return;}
      if (procs == 1 && type == "Medium") {System.out.println(ParallelScan.oneThreadSolution(mediumValue, mediumValue, mediumValue.length)); return;}
      if (procs == 1 && type == "Small") {System.out.println(ParallelScan.oneThreadSolution(smallValue, smallValue, smallValue.length)); return;}
      switch (type) {
         case "Small": System.out.println(ParallelScan.compute(smallValue, smallValue, smallValue.length, procs));
         case "Medium": System.out.println(ParallelScan.compute(bigValue, mediumValue, mediumValue.length, procs));
         case "Big": System.out.println(ParallelScan.compute(bigValue, bigValue, bigValue.length, procs));
      }

      // System.out.println(2+2);
   // System.out.println("Counter: " + counterTAS.getCount());
   }



}