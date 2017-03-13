/**
 * 
 */
package net.wmann.session.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.wmann.session.Application;
import net.wmann.session.entity.SessionEntity;
import net.wmann.session.repository.SessionRepository;
import net.wmann.session.testutil.Testutils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class)
@WebAppConfiguration
@TestPropertySource(properties = {"sessionservice.session.timeout=2"})
public class SessionControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    
    @Autowired
    private SessionRepository sessionRepository;

    private MockMvc mockMvc;
    
    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();
    }
    
    @Test
    public void getSession() throws Exception {
    	SessionEntity session = Testutils.createSessionEntity();
    	session = sessionRepository.save(session);
    	
    	String responseContent = mockMvc.perform(get("/session/" + session.getId()))
        		.andExpect(status().isOk())
        		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        		.andReturn().getResponse().getContentAsString();
    	
    	Assert.assertEquals("Wrong session returned by controller", session, (mapper.readValue(responseContent, SessionEntity.class)));
    	sessionRepository.delete(session);
    }

    @Test
    public void getSessionNoSessionId() throws Exception {
        mockMvc.perform(get("/session/"))
                .andExpect(status().isMethodNotAllowed());
    }
    
    @Test
    public void postSessionNewSession() throws Exception {
    	Assert.assertEquals(0, sessionRepository.count());
    	SessionEntity session = Testutils.createSessionEntity();
    	session.setId(null);
    	String responseContent = mockMvc.perform(post("/session").content(mapper.writeValueAsString(session)).contentType(MediaType.APPLICATION_JSON_UTF8))
    			.andExpect(status().isOk())
    			.andReturn().getResponse().getContentAsString();
    	
    	SessionEntity returnedSession = mapper.readValue(responseContent, SessionEntity.class);
    	Assert.assertNotNull("Session id should not be empty", returnedSession.getId());
    	Assert.assertEquals(1, sessionRepository.count());
    	sessionRepository.delete(returnedSession.getId());
    }
    
    @Test
    public void postSessionUpdateSession() throws Exception {
    	String newUsername = "changedUsername";
    	SessionEntity session = Testutils.createSessionEntity();
    	session = sessionRepository.save(session);
    	Assert.assertEquals(1, sessionRepository.count());
    	
    	session.setUsername(newUsername);
    	String responseContent = mockMvc.perform(post("/session").content(mapper.writeValueAsString(session)).contentType(MediaType.APPLICATION_JSON_UTF8))
    			.andExpect(status().isOk())
    			.andReturn().getResponse().getContentAsString();
    	
    	SessionEntity returnedSession = mapper.readValue(responseContent, SessionEntity.class);
    	Assert.assertEquals("User name should be changed in response", newUsername, returnedSession.getUsername());
    	Assert.assertEquals("User name should be changed in db", newUsername, sessionRepository.findOne(session.getId()).getUsername());
    	Assert.assertEquals(1, sessionRepository.count());
    	sessionRepository.delete(returnedSession.getId());
    }
    
    @Test
    public void postSessionInvalidSessionId() throws Exception {
    	Assert.assertEquals(0, sessionRepository.count());
    	SessionEntity session = Testutils.createSessionEntity();
    	session.setId("invalidId");
    	mockMvc.perform(post("/session").content(mapper.writeValueAsString(session)).contentType(MediaType.APPLICATION_JSON_UTF8))
    			.andExpect(status().isNotFound());
    	
    	Assert.assertEquals(0, sessionRepository.count());
    }

    
}
