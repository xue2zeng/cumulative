FROM java:8
MAINTAINER xue.zeng <xue.zeng@accenture.com>

ENV	APP_NAME=eureka-server
ENV	VERSION=0.0.1-SNAPSHOT
ENV PORT=8761
ENV APP_SOURCE=/app
ENV JAVA_OPTS="-Dspring.profiles.active=native"
RUN ln -snf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo Asia/Shanghai > /etc/timezone

RUN mkdir $APP_SOURCE
COPY ./target/$APP_NAME-$VERSION.jar $APP_SOURCE/$APP_NAME.jar
WORKDIR $APP_SOURCE
CMD java -Xmx2G -Xms2G $JAVA_OPTS -Xdebug -Xrunjdwp:transport=dt_socket,address=8014,server=y,suspend=n -jar $APP_SOURCE/$APP_NAME.jar

EXPOSE $PORT
EXPOSE 8014