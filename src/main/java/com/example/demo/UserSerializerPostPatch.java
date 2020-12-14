package com.example.demo;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class UserSerializerPostPatch extends StdSerializer<User>{
    public UserSerializerPostPatch() {
        this(null);
    }
    public UserSerializerPostPatch(Class<User> user) {
        super(user);
    }

    @Override
    public void serialize(
            User user, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        jgen.writeStringField("email", user.getEmail());
        jgen.writeStringField("password", user.getPassword());
        jgen.writeEndObject();
    }


}
