package com.example.petshelper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.Locale;
import java.util.Map;

@Getter
public class RequestResult {
    private final int status;

    private final String content;

    private final Map<String, String> parsedContentObject;

    public RequestResult(MockHttpServletResponse response) throws UnsupportedEncodingException {
        this.status = response.getStatus();
        this.content = response.getContentAsString();

        Type type = new TypeToken<Map<String, String>>() {
        }.getType();
        this.parsedContentObject = (this.content.startsWith("{") && this.content.endsWith("}") && !this.content.contains("["))
                ? new Gson().fromJson(this.content, type) : null;
    }
}
