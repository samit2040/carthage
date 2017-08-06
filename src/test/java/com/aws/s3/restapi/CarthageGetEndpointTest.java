package com.aws.s3.restapi;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.aws.s3.restapi.Main;

import junit.framework.Assert;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CarthageGetEndpointTest {

    private static HttpServer server;
    private static WebTarget target;
    private static File uploadFile;

    @BeforeClass
    public static void setUp() throws Exception {
        // start the server
        server = Main.startServer();
        // create the client
        Client c = ClientBuilder.newClient();
        c.register(MultiPartFeature.class);
        target = c.target(Main.BASE_URI);
        uploadFile = createFile("test");
        createTestData();
    }

    @AfterClass
    public static void tearDown() throws Exception {
    	deleteTestData();
        server.stop();
    }


    
    private static void deleteTestData() {
		Assert.assertEquals(202, target.path("files/"+uploadFile.getName()).request().delete().getStatus());
	}


    public static void createTestData() throws IOException {    
    	FormDataMultiPart formDataMultiPart = null;
    	FormDataMultiPart multipart = null;
       	try{
       		
        FileDataBodyPart filePart = new FileDataBodyPart("file", uploadFile);
        formDataMultiPart = new FormDataMultiPart();
        multipart = (FormDataMultiPart) formDataMultiPart.bodyPart(filePart);
             
    	Assert.assertEquals(201, target.path("files/"+uploadFile.getName()).request().post(Entity.entity(multipart, multipart.getMediaType())).getStatus());
    	}finally{
    		formDataMultiPart.close();
            multipart.close();
    	}
    	
    }
  
    @Test
    public void testGetFiles() {
        ArrayList<String> responseMsg = (ArrayList<String>) target.path("files").request().get(List.class);
        Assert.assertTrue(responseMsg.contains(uploadFile.getName()));
    }
    @Test
    public void testGetFilesByIDNotPresent() {
            
        Assert.assertEquals(404 , target.path("files/fileNotPresentinS3").request().get().getStatus());
    }
    @Test
    public void testGetFilesByID() {            
    	System.out.println(uploadFile.getName());
        Assert.assertEquals(200 , target.path("files/"+uploadFile.getName()).request().get().getStatus());
    }

    private static File createFile(String name) throws IOException {
        File file = File.createTempFile(name,null);
        file.deleteOnExit();        
        return file;
    }
    
    
}
