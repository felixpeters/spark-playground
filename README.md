# Apache Spark Playground

This repository can be used to test Apache Spark applications on a local cluster
consisting of Docker containers. The cluster architecture is largely inspired
by this [awesome tutorial](https://towardsdatascience.com/a-journey-into-big-data-with-apache-spark-part-2-4511aa19a900)
from the _Towards Data Science_ blog on Medium.

## Setup

Running the cluster only requires a working Docker installation (see [official website](https://docs.docker.com/install/) for installation instructions).

After installing Docker, you should build the required images using the following commands:
- Master, worker and submitter image: `make build-image`
- Builder image: `make build-sbt-image`

## Run cluster

To run a local Spark cluster, simply run `docker-compose up --scale spark-worker=2`.
This will start a Docker container representing the _spark-master_ and two containers
representing _spark-worker_ instances. Adjust the `scale` parameter as needed to
receive more workers. You can access the Spark Web UI at `http://localhost:8080/`.

## Run examples

The following examples are included in this repository:
- _MyFirstScalaSpark_: counts lines with _a_'s and _b_'s in this readme file and outputs the result to the console.
- _RossmannSalesForecasting_: loads data from the [Rossmann Kaggle challenge](https://www.kaggle.com/c/rossmann-store-sales),
performs some basic feature engineering and trains a gradient-boosted trees (GBT) model
on the derived features.

Running the included examples requires two steps:
1. Building the executable JAR file from Scala code inside a dedicated container.
   - Start the builder container using `make run-builder`.
   - Inside the container, navigate to `/local/src/main/scala` and start the Scala build tool using the `sbt` command. 
   - In the _sbt_ console, run `package` to build the executable.
2. Submitting the created executable to the Spark cluster.
   - Start the submitter using `make run-submitter`.
   - Inside the container, run one of the commands from `commands.txt` to start the respective application.
