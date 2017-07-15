#TOMCAT_VERSION 8.5.16

#BUILD COMMAND: docker build -t carthage:1.0.0 . (from the root dir of the project)
#RUN COMMAND: docker run -it --rm -p 8888:8080 carthage:1.0.0

FROM tomcat:8.5
#Author:Amit Sharma
COPY target/*.war /usr/local/tomcat/webapps/
