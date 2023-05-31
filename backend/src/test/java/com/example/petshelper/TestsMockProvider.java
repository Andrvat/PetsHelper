package com.example.petshelper;

import com.example.petshelper.model.Role;
import com.example.petshelper.model.UserCredential;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Collections;
import java.util.HashSet;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TestsMockProvider {
    public static RequestResult getResultFromParametrizedPostRequest(MockMvc mockMvc, String url, String content) throws Exception {
        MvcResult result = (content != null)
                ? mockMvc.perform(post(url)
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)).andReturn()
                : mockMvc.perform(post(url)).andReturn();
        return new RequestResult(result.getResponse());
    }

    public static RequestResult getResultFromParametrizedGetRequest(MockMvc mockMvc, String url, String content) throws Exception {
        MvcResult result = (content != null)
                ? mockMvc.perform(get(url)
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)).andReturn()
                : mockMvc.perform(get(url)).andReturn();
        return new RequestResult(result.getResponse());
    }

    public static void setUpMockSpringAuth(String roleName) {
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(new UserCredential() {{
            setRoles(new HashSet<>(Collections.singleton(new Role() {{
                setName(roleName);
            }})));
        }});
        SecurityContextHolder.setContext(securityContext);
    }

    public static void setUpMockSpringAuth(String roleName, Long id) {
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(new UserCredential() {{
            setId(id);
            setRoles(new HashSet<>(Collections.singleton(new Role() {{
                setName(roleName);
            }})));
        }});
        SecurityContextHolder.setContext(securityContext);
    }
}
