package org.openstack4j.api;

import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.openstack4j.openstack.OSFactory;
import org.openstack4j.openstack.identity.domain.KeystoneAccess;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;

import java.io.IOException;
import java.io.InputStream;

/**
 * Base Test class which handles Mocking a Webserver to fullfill and test against JSON response objects
 * from an OpenStack deployment
 *
 * @author Jeremy Unruh
 */
public abstract class AbstractTest {

    protected enum Service {
        IDENTITY(5000),
        NETWORK(9696),
        COMPUTE(8774);

        private final int port;

        private Service(int port) {
            this.port = port;
        }

    }

    protected static final String JSON_ACCESS = "/identity/access.json";

    private OSClient os;
    protected MockWebServer server = new MockWebServer();

    /**
     * @return the service the API is using
     */
    protected abstract Service service();

    @BeforeClass
    protected void startServer() {
        try {
            server.play(service().port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * The path to the expected JSON results
     *
     * @param resource the resource
     * @throws IOException Signals that an I/O exception has occurred.
     */
    protected void respondWith(String resource) throws IOException {
        MockResponse r = new MockResponse();
        InputStream is = getClass().getResourceAsStream(resource);
        r.setBody(is, is.available());
        r.setHeader("Content-Type", "application/json");
        server.enqueue(r);
    }

    /**
     * Responds with negative based response code and no body
     *
     * @param statusCode the status code to respond with
     */
    protected void respondWith(int statusCode) {
        MockResponse r = new MockResponse();
        r.setResponseCode(statusCode);
        server.enqueue(r);
    }

    protected String authURL(String path) {
        return String.format("http://127.0.0.1:%d%s", service().port, path);
    }

    @AfterTest
    protected void afterTest() {
        try {
            server.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void associateClient(OSClient os) {
        this.os = os;
    }

    protected OSClient os() {
        if (os == null) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationConfig.Feature.WRAP_ROOT_VALUE);
            mapper.enable(DeserializationConfig.Feature.UNWRAP_ROOT_VALUE);
            mapper.enable(DeserializationConfig.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
            mapper.disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);

            try {
                KeystoneAccess a = mapper.readValue(getClass().getResourceAsStream(JSON_ACCESS), KeystoneAccess.class);
                a.setEndpoint("http://127.0.0.1");
                os = OSFactory.clientFromAccess(a);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return os;
    }
}
