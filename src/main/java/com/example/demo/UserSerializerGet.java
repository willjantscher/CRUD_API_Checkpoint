package com.example.demo;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class UserSerializerGet extends StdSerializer<User>{
    public UserSerializerGet() {
        this(null);
    }
    public UserSerializerGet(Class<User> user) {
        super(user);
    }

    @Override
    public void serialize(
            User user, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException {
                jgen.writeStartObject();
                jgen.writeStringField("id", user.getId().toString());
                jgen.writeStringField("email", user.getEmail());
                jgen.writeEndObject();
    }
}
