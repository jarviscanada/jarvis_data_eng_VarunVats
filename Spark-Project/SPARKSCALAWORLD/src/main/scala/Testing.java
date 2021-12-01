import org.apache.hadoop.hive.ql.metadata.Hive;
 package org.scalajs.testsuite.javalib.time.chrono

import java.time.LocalDate
import java.time.chrono.ChronoPeriod

import org.junit.Test
import org.junit.Assert.assertEquals

public class Testing {

    class ChronoPeriodTest {
        @Test def test_between(): Unit = {
            val ds = Seq(LocalDate.MIN, LocalDate.of(2011, 2, 28), LocalDate.MAX)
            for {
                d1 <- ds
                d2 <- ds
            } {
                assertEquals(source.until(), ChronoPeriod.between(source, backup))
            }
        }
    }



}
