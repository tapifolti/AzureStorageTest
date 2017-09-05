# no glibc, non-server, ubuntu
#FROM isuper/java-oracle
# with glibc, non-server, alpine
#FROM frolvlad/alpine-oraclejdk8
# with glibc, server, alpine
# FROM martinseeler/oracle-server-jre
# FROM fiadliel/java8-jre

# needs Alpine 3.5 or higher to get libstdc++.so.6
FROM frolvlad/alpine-oraclejdk8

# this is 100MB should be eliminated except libstdc++.so.6
# for C++ only
# RUN apk add --no-cache build-base

COPY target/azurestorage-1.0-SNAPSHOT.jar /usr/src/app/
COPY config.yml /usr/src/app/

# this is the Linux standard if 'loadLibrary' refers to 'WebTest'
# for c++ only
# COPY native/libWebTest.so /usr/src/app/

EXPOSE 8080 8081

# for c++ only
# ENV LD_LIBRARY_PATH=$LD_LIBRARY_PATH:/usr/src/app

# log location
RUN cd /var/tmp

# "-XshowSettings:properties" - to display env
# "-Djava.library.path=/usr/src/app" -- does not work on Linux
CMD ["java", "-jar", "/usr/src/app/azurestorage-1.0-SNAPSHOT.jar", "server", "/usr/src/app/config.yml"]

# Build
# docker build -t tapifolti/azurestorage .

# Run
# docker run --rm -p 8080:8080 -p 8081:8081 --name storagedeploy tapifolti/azurestorage