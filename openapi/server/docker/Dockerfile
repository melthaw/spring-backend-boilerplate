from clouthinkin/jre

ADD build/libs/openapi-server-1.0.0-SNAPSHOT.jar /
ADD docker/container_files/ /

RUN chmod +x /*.sh

EXPOSE 8080
EXPOSE 8090
WORKDIR /
ENTRYPOINT /docker-entrypoint.sh
