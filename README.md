# carthage_rest_api
Exposes below rest endpoints to hit aws s3

    - `GET`  `/files` - retrieves a list of files from [AWS S3](https://aws.amazon.com/s3/) bucket
    - `GET`  `/files/:id` - retrieves a specific file
    - `POST` `/files/:id` - uploads file into S3 bucket
    - `DELETE` `/files/:id` - deletes a specific file from S3 bucket
    
### To compile and package the application into a WAR
mvn clean package

### To run the test
mvn clean test

### To execute the application in a standalone mode
mvn exec:java

It will informs you that the application has been started and it's WADL descriptor is available at http://localhost:8888/carthage/application.wadl URL. 
