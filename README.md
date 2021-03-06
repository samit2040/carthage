# carthage_rest_api
Exposes below rest endpoints to hit aws s3

Live instance at :  http://ec2-54-149-114-0.us-west-2.compute.amazonaws.com:8888/carthage-1.0.0/files

AWS S3 Bucket where files are served :  https://s3-ap-southeast-1.amazonaws.com/samit2040-created

    - `GET`    `/files` - retrieves a list of files from [AWS S3](https://aws.amazon.com/s3/) bucket
    - `GET`    `/files/:id` - retrieves a specific file
    - `POST`   `/files` - uploads file into S3 bucket
    - `DELETE` `/files/:id` - deletes a specific file from S3 bucket
    - `GET`    `/files/test` - to test if the application is up 
    
### To get a provisioned box and a Jenkins setup for it refer to [Provision_Carthage](https://github.com/samit2040/provision_carthage.git)
    
### To compile and package the application into a WAR
mvn clean package

### To run the test
mvn clean test

### To execute the application in a standalone mode
mvn exec:java

It will informs you that the application has been started and it's WADL descriptor is available at http://localhost:8888/carthage/application.wadl URL. 


## Docker: To create and publish carthage's docker image onto [dockerhub](https://hub.docker.com/r/samit2040/carthage/)
`sh buildDockerImage.sh <CARTHAGE_VERSION> <BUILD_NUMBER>` 

### To pull down an image 
`docker pull samit2040/carthage:<CARTHAGE_VERSION>-<BUILD_NUMBER>`

### To run the container from the pulled image
`docker run -d --privileged=true  --name=carthagecontainer -p 8888:8080 samit2040/carthage:<CARTHAGE_VERSION>-<BUILD_NUMBER>`

### To test the application hit 
`curl http://localhost:8888/carthage-<CARTHAGE_VERSION>/files/test`
