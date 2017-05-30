FROM ubuntu:16.04

RUN apt-get update -y && apt-get install -y \
default-jdk \
maven \
nano \
wget && \
apt-get clean && \
rm -rf /var/lib/apt/lists/*

RUN useradd --create-home --shell /bin/bash ubuntu

ENV HOME /home/ubuntu
ENV KAFKA_VERSION 0.10.2.0
ENV SCALA_VERSION 2.11

WORKDIR ${HOME}

ENV KAFKA_HOME ${HOME}/kafka_${SCALA_VERSION}-${KAFKA_VERSION}.tgz
ENV PATH ${PATH}:${KAFKA_HOME}/bin

RUN chown -R ubuntu:ubuntu /home/ubuntu/*
USER ubuntu

# get kafka
RUN wget http://apache.uberglobalmirror.com/kafka/${KAFKA_VERSION}/kafka_${SCALA_VERSION}-${KAFKA_VERSION}.tgz && \
tar xvf kafka_${SCALA_VERSION}-${KAFKA_VERSION}.tgz

RUN rm -fv kafka_${SCALA_VERSION}-${KAFKA_VERSION}.tgz
