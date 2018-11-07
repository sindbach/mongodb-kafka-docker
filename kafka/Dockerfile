FROM ubuntu:18.04

ENV DEBIAN_FRONTEND=noninteractive

RUN apt-get update && apt-get install -y gnupg sudo

RUN apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 9DA31620334BD75D9DCB49F368818C72E52529D4

RUN echo "deb [ arch=amd64 ] http://repo.mongodb.org/apt/ubuntu bionic/mongodb-org/4.0 multiverse" |  tee /etc/apt/sources.list.d/mongodb-org-4.0.list

RUN apt-get update -y && apt-get install -y \
default-jdk \
maven \
nano \
mongodb-org-shell \ 
wget && \
apt-get clean && \
rm -rf /var/lib/apt/lists/*

RUN useradd --create-home --shell /bin/bash ubuntu

ENV JAVA_HOME /usr/lib/jvm/java-11-openjdk-amd64
ENV HOME /home/ubuntu
ENV KAFKA_VERSION 1.1.1
ENV SCALA_VERSION 2.11

WORKDIR ${HOME}

ENV KAFKA_HOME ${HOME}/kafka_${SCALA_VERSION}-${KAFKA_VERSION}
ENV PATH ${PATH}:${KAFKA_HOME}/bin

RUN mkdir /home/ubuntu/workspace
COPY workspace/ /home/ubuntu/workspace/

RUN chown -R ubuntu:ubuntu /home/ubuntu/*
USER ubuntu

# get kafka
RUN wget http://apache.mirror.digitalpacific.com.au/kafka/${KAFKA_VERSION}/kafka_${SCALA_VERSION}-${KAFKA_VERSION}.tgz && \
tar xvf kafka_${SCALA_VERSION}-${KAFKA_VERSION}.tgz

RUN rm -fv kafka_${SCALA_VERSION}-${KAFKA_VERSION}.tgz

CMD ["/bin/bash"]  
