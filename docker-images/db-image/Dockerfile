FROM ubuntu:16.04

#Installing MongoDB Version 3.4.2
RUN apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 0C49F3730359A14518585931BC711F9BA15703C6
RUN echo "deb [ arch=amd64,arm64,ppc64el,s390x ] http://repo.mongodb.com/apt/ubuntu xenial/mongodb-enterprise/3.4 multiverse" | tee /etc/apt/sources.list.d/mongodb-enterprise.list
RUN apt-get update
RUN apt-get install -y mongodb-enterprise

#Install git
RUN apt-get install git

#Mount mongodb data directory as a volume
VOLUME /var/lib/mongodb

RUN mkdir git-repo
WORKDIR /git-repo
RUN git clone https://github.com/AffanHasan/lisaan_al_arab.git
#CMD mongod --conf /etc/mongod.conf