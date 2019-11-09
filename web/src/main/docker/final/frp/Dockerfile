FROM java:8
#VOLUME /tmp
ADD frp.jar app.jar
ADD lib ./lib
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-Dloader.path=/lib", "-jar","/app.jar"]
