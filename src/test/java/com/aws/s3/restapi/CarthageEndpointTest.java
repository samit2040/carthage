package com.aws.s3.restapi;

import javax.validation.constraints.AssertTrue;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.glassfish.grizzly.http.server.HttpServer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.aws.s3.restapi.Main;

import junit.framework.Assert;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

public class CarthageEndpointTest {

    private HttpServer server;
    private WebTarget target;

    @Before
    public void setUp() throws Exception {
        // start the server
        server = Main.startServer();
        // create the client
        Client c = ClientBuilder.newClient();

        // uncomment the following line if you want to enable
        // support for JSON in the client (you also have to uncomment
        // dependency on jersey-media-json module in pom.xml and Main.startServer())
        // --
        // c.configuration().enable(new org.glassfish.jersey.media.json.JsonJaxbFeature());

        target = c.target(Main.BASE_URI);
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    /**
     * Test to see that the message "Got it!" is sent in the response.
     */
    @Test
    public void testGetFiles() {
        ArrayList<String> responseMsg = (ArrayList<String>) target.path("files").request().get(List.class);
        Assert.assertTrue(responseMsg.contains("a.txt"));
    }
    @Test
    public void testGetFilesByIDNotPresent() {
            
        Assert.assertEquals(404 , target.path("files/fileNotPresentinS3").request().get().getStatus());
    }
    
}
