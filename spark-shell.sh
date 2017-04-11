#!/usr/bin/env bash

set -e

SPARK_MASTER_NAME="sparkMaster"
SPARK_MASTER="$(docker inspect --format '{{ .NetworkSettings.IPAddress }}' $SPARK_MASTER_NAME)"

docker exec -it $SPARK_MASTER_NAME /usr/local/spark/bin/spark-shell --master spark://$SPARK_MASTER:7077 --packages org.infinispan:infinispan-spark_2.11:0.5
