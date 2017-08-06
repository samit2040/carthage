package com.aws.s3;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;


public class S3Helper {
	
   	public static void deleteObject(AmazonS3 s3, String bucketName, String key) throws Exception  {
		System.out.println("Deleting an object\n");
		s3.deleteObject(bucketName, key);
	}

	public static S3ObjectInputStream getObject(AmazonS3 s3, String bucketName, String key) throws IOException {
		System.out.println("Downloading an object");
		S3Object object = s3.getObject(new GetObjectRequest(bucketName, key));
		return object.getObjectContent();
	}

	public static ArrayList<String> listBuckets(AmazonS3 s3) {
		System.out.println("Listing buckets");
		ArrayList<String> keys = new ArrayList<>();
		for (Bucket bucket : s3.listBuckets()) {
		    System.out.println(" - " + bucket.getName());
		    keys.add(bucket.getName());
		}
		return keys;
	}

    public static ArrayList<String> listObjects(AmazonS3 s3, String bucketName) {
		System.out.println("Listing objects");
		ObjectListing objectListing = s3.listObjects(new ListObjectsRequest()
		        .withBucketName(bucketName));
		ArrayList<String> keys = new ArrayList<>();
		for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
		    System.out.println(" - " + objectSummary.getKey() + "  " +
		                       "(size = " + objectSummary.getSize() + ")");
		    keys.add(objectSummary.getKey());
		  		}
		return keys;
	}

	public static void putObject(AmazonS3 s3, String bucketName, String key,File file) throws IOException {
		s3.putObject(new PutObjectRequest(bucketName, key, file));
	}
	public static AmazonS3 getAmazons3(){
		//AWSCredentials  credentials;
		BasicAWSCredentials awsCreds;
		try {
			 awsCreds = new BasicAWSCredentials("AKIAJCOK6DBGY5U6Y3RA", "yq7KqBt157WbXn7vCYc0IbSZniB62+lHVIthd0NI");
			  //credentials = new ProfileCredentialsProvider("default").getCredentials();
        } catch (Exception e) {
            throw new AmazonClientException(
                    "Cannot load the credentials from the credential profiles file. " +
                    "Please make sure that your credentials file is at the correct " +
                    "location (C:\\Users\\admin\\.aws\\credentials), and is in valid format.",
                    e);
        }

        
        AmazonS3 s3 = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .withRegion(Regions.AP_SOUTHEAST_1)
                .build();
        return s3;
	}

}
