import scala.io._

val source = Source.fromFile("/usr/share/dict/cracklib-words").getLines.toSeq

val kvrdd = sc.parallelize(source.toSeq).zipWithIndex.map(_.swap)

import org.infinispan.spark.config._
import org.infinispan.spark._

val cfg = new ConnectorConfiguration().setServerList("172.17.0.3")
kvrdd.writeToInfinispan(cfg)


import org.infinispan.spark.rdd._

val irdd = new InfinispanRDD[Long,String](sc, cfg)
irdd.values.filter(p => p.forall(_.isLetter)).map(p => (p.head, 1)).reduceByKey(_+_).sortBy(_._2).collect.foreach { case (char, count) => println(s"$char ->  $count") }
