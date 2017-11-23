FROM anapsix/alpine-java
VOLUME /tmp
ARG JAR_FILE
ADD target/${JAR_FILE} /app.jar
ENV JAVA_OPTS=""
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/urandom -jar /app.jar