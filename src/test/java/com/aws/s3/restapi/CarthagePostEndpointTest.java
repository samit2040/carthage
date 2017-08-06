package com.aws.s3.restapi;

import javax.validation.constraints.AssertTrue;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.aws.s3.restapi.Main;

import junit.framework.Assert;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class CarthagePostEndpointTest {

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
        
   }

    @AfterClass
    public static void tearDown() throws Exception {
    	server.stop();
    }

    @Before
    public void createTestDataFile() throws IOException {
		
    	uploadFile = createFile("test");
    }

    @After
    public void deleteTestData() {
		// TODO Auto-generated method stub
    	Assert.assertEquals(202, target.path("files/"+uploadFile.getName()).request().delete().getStatus());
    }

	@Test
    public void testPostFilesByID() throws IOException {   
		
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
    private static File createFile(String name) throws IOException {
        File file = File.createTempFile(name,null);
        file.deleteOnExit();        
        return file;
    }
    
    
}
