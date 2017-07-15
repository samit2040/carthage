#PREBUILD COMMAND : docker rm -f pcr
#PREBUILD COMMAND : docker rm -f pcr-intermediate


#BUILD COMMAND: docker build -t pcr:1.2.0-intermediate --build-arg pcrVersion=1.2.0 -f PCR-onestep .

#INTERMEDIATE RUN COMMAND: docker run -d --privileged=true --name=pcr-intermediate pcr:1.2.0-intermediate systemd.unit=pcrSetup.service
#Note a WAIT of few seconds might be needed for completely setting up pcr inside the running container.
#INTERMEDIATE COMMAND: docker stop pcr-intermediate
#INTERMEDIATE COMMAND: docker commit pcr-intermediate peproxy.rds.lexmark.com:5003/pcr:1.2.0

#FINAL RUN COMMAND: docker run -d -p 80:80 --privileged=true --name=pcr peproxy.rds.lexmark.com:5003/pcr:1.2.0 systemd.unit=perceptiveconnectruntime.service
#docker pull dockerbase/tomcat8


#TOMCAT_VERSION 8.5.16

#BUILD COMMAND: docker build -t carthage:1.0.0 .
#RUN COMMAND: docker run -it --rm -p 8888:8080 carthage:1.0.0
#INTERMEDIATE COMMAND: docker stop pcr-intermediate
#INTERMEDIATE COMMAND: docker commit pcr-intermediate peproxy.rds.lexmark.com:5003/pcr:1.2.0



FROM tomcat:8.5
#Author:Amit Sharma


COPY target/*.war /usr/local/tomcat/webapps/
