build-image:
	docker build -t felixpeters/spark:latest .

run-master:
	docker run -it --name spark-master --hostname spark-master -p 7077:7077 -p 8080:8080 --network spark_network felixpeters/spark:latest /bin/sh

run-worker:
	docker run -it --name spark-worker --hostname spark-worker --network spark_network felixpeters/spark:latest /bin/sh

run-cluster:
	docker-compose up --scale spark-worker=$(NUM_WORKERS)
