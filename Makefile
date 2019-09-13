build-image:
	docker build -t felixpeters/spark:latest .

build-sbt-image:
	docker build -t ls12styler/scala-sbt:latest github.com/ls12styler/scala-sbt

run-master:
	docker run -it --name spark-master --hostname spark-master -p 7077:7077 -p 8080:8080 --network spark_network felixpeters/spark:latest /bin/sh

run-worker:
	docker run -it --name spark-worker --hostname spark-worker --network spark_network felixpeters/spark:latest /bin/sh

run-cluster:
	docker-compose up --scale spark-worker=$(NUM_WORKERS)

run-submitter:
	docker run --rm -it -e SPARK_MASTER="spark://spark-master:7077" -v `pwd`:/project -v `pwd`:/local --network spark-docker_spark_network felixpeters/spark:latest /bin/bash

run-builder:
	docker run -it --rm -v `pwd`:/project spikerlabs/scala-sbt /bin/bash
