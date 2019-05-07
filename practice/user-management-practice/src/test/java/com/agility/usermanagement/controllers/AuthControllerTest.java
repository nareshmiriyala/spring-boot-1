package com.agility.usermanagement.controllers;

import com.agility.usermanagement.constants.RoleName;
import com.agility.usermanagement.models.Role;
import com.agility.usermanagement.models.User;
import com.agility.usermanagement.securities.AuthenticationRequest;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static com.agility.usermanagement.exceptions.CustomError.BAD_CREDENTIALS;
import static com.agility.usermanagement.utils.ConvertUtil.convertObjectToJsonBytes;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class AuthControllerTest extends BaseControllerTest {

    private User user;
    private AuthenticationRequest credentialRequest;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders
            .webAppContextSetup(webApplicationContext)
            .addFilter(filterChainProxy)
            .build();

        user = new User();
        user.setId(1L);
        user.setUsername("user");
        user.setPassword(passwordEncoder.encode("user"));

        credentialRequest = new AuthenticationRequest();
        credentialRequest.setUsername("user");
        credentialRequest.setPassword("user");
    }

    /* ========================== Test sign up ======================= */

    /**
     * Test login with correct credential
     */
    @Test
    public void testSignUpWithCorrectCredential() throws Exception {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.ofNullable(user));

        mockMvc.perform(post("/v1/auths")
            .contentType(MediaType.APPLICATION_JSON)
            .content(convertObjectToJsonBytes(credentialRequest)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(header().string(securityConfig.getHeaderString(),
                CoreMatchers.containsString(securityConfig.getTokenPrefix())));
    }

    /**
     * Test sign up not success when username not found
     */
    @Test
    public void testSignUpShouldReturnFailWhenUserNameNotFound() throws Exception {
        // Set username not found in system
        credentialRequest.setUsername("admin");

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.ofNullable(null));

        mockMvc.perform(post("/v1/auths")
            .contentType(MediaType.APPLICATION_JSON)
            .content(convertObjectToJsonBytes(credentialRequest)))
            .andDo(print())
            .andExpect(status().isUnauthorized());
    }

    /**
     * Test sign up should throw bad account credential exception when returned user null id
     */
    @Test
    public void testSignUpShouldThrowBadAccountCredentialExceptionWhenUserNullId() throws Exception {
        user.setId(null);

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.ofNullable(user));

        mockMvc.perform(post("/v1/auths")
            .contentType(MediaType.APPLICATION_JSON)
            .content(convertObjectToJsonBytes(credentialRequest)))
            .andDo(print())
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.code", is(BAD_CREDENTIALS.code())))
            .andExpect(jsonPath("$.message", is(BAD_CREDENTIALS.message())));
    }
}
