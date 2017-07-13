package com.aws.s3.restapi;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import com.amazonaws.services.s3.AmazonS3;
import com.aws.s3.S3Helper;
import com.google.gson.Gson;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("files")
public class CarthageEndpoint {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
	
	AmazonS3 s3 = S3Helper.getAmazons3();
	//String bucketName = "samit2040-carthage";
	String bucketName = "samit2040-created";
	
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getFiles() {
    	ArrayList<String> files = S3Helper.listObjects(s3, bucketName);
    	String json = new Gson().toJson(files);
    	System.out.println(json);
        return json;
    }
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getFilesId(@PathParam("id") String id) throws IOException {
    	final InputStream content = S3Helper.getObject(s3, bucketName, id);
    	StreamingOutput output = new StreamingOutput() {
            @Override
            public void write(OutputStream outputStream) throws IOException, WebApplicationException {
                int length;
                byte[] buffer = new byte[1024];
                while ((length = content.read(buffer)) != -1){
                    outputStream.write(buffer, 0, length);
                }
                outputStream.flush();
                outputStream.close();
                

            }
        };
        content.close();
        return Response.ok(output, MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-Disposition", "attachment; filename=\"" + id + "\"" )
                .build();
    	
    }
    
    
    
    
    
    @DELETE
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response deleteFilesId(@PathParam("id") String id) {
    	//ArrayList<String> files = S3Sample.listObjects(s3, bucketName);
    	try{
    		S3Helper.deleteObject(s3, bucketName, id);    		
    	}catch (Exception e) {
			// TODO: handle exception
    		System.out.println(e.getMessage());
    		return Response.status(409).build();
    		
		}
    	return Response.status(204).build();
        
    }
    
   
}
