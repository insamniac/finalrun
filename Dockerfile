FROM java:8-alpine
MAINTAINER Your Name <you@example.com>

ADD target/uberjar/finalrun.jar /finalrun/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/finalrun/app.jar"]
