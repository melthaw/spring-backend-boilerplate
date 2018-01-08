from clouthinkin/jre

ADD build/libs/openapi-server-1.0.0.SNAPSHOT.jar /
ADD container_files/ /

RUN chmod +x /*.sh

EXPOSE 8081
WORKDIR /
ENTRYPOINT /docker-entrypoint.sh
