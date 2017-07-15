#!/bin/sh
#Author:Amit Sharma
#set -x

carthageVersion=$1
BUILD_NUMBER=$2

#build carthage Image
docker build -t carthage:$carthageVersion-$BUILD_NUMBER .

#installing carthage in the container
docker run -d --privileged=true  --name=testcarthagecontainer -p 8080 carthage:$carthageVersion-$BUILD_NUMBER

#Note a WAIT of few seconds might be needed for completely setting up carthage inside the running container.
sleep 10

#get the random port assigned by docker
assignedPort=$(docker port testcarthagecontainer | cut -d':' -f2)
echo $assignedPort



# Pre-requiste 
# If the development environment is Windows:
# Use: hitCarthageUrl="curl -sI http://$(docker-machine ip):$assignedPort/carthage-$carthageVersion/files/test |  grep 200 "
# For linux :
# Use: hitCarthageUrl="curl -sI http://localhost:$assignedPort/carthage-$carthageVersion/files/test  |  grep 200 "

hitCarthageUrl="curl -sI http://$(docker-machine ip):$assignedPort/carthage-$carthageVersion/files/test |  grep 200 "

#hitCarthageUrl="curl -sI http://localhost:$assignedPort/carthage-$carthageVersion/files/test  |  grep 200 "

httpStatus=$(echo $(eval $hitCarthageUrl))

echo "Status code From carthage :- "$httpStatus
maxloop=5
while [ -z "$httpStatus" ] && [ "$maxloop" -gt 0 ];do
	sleep 1
	echo "sleeping .... waiting for the carthage $maxloop"
	httpStatus=$(echo $(eval $hitCarthageUrl))
	echo "Status code From carthage :- "$httpStatus
	maxloop=$((maxloop - 1))
done

if [ -z "$httpStatus" ]; then
	echo ----------------------------------------------------------------
	echo ------------FATAL ERROR: carthage NOT UP ----------------------------
	echo ----------------------------------------------------------------
	docker rm -f testcarthagecontainer
	
	exit 1
else 
	echo ----------------------------------------------------------------
	echo ----------------carthage IS UP---------------------------------------
	echo ----------------------------------------------------------------
fi




#commiting the carthage Image
DOCKER_ID_USER="samit2040"
carthageImage="carthage:$carthageVersion-$BUILD_NUMBER"

docker tag $carthageImage $DOCKER_ID_USER/$carthageImage 
docker tag $carthageImage $DOCKER_ID_USER/carthage:latest
echo "-----------------pushing to dockerhub--------------------"
#docker push to dockerhub
docker push $DOCKER_ID_USER/$carthageImage
docker push $DOCKER_ID_USER/carthage:latest

#cleaning up
docker rm -f testcarthagecontainer
#docker rm -f carthage-intermediate

