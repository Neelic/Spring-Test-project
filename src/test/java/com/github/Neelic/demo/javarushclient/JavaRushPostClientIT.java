package com.github.Neelic.demo.javarushclient;

import com.github.Neelic.demo.javarushclient.dto.PostInfo;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.List;

public class JavaRushPostClientIT {

    private final JavaRushPostClient postClient = new JavaRushPostClientImpl(JavaRushGroupClientTest.JAVARUSH_API_PATH);

    @Test
    public void shouldProperlyGetNew15Posts() {
        List<PostInfo> newPosts = postClient.findNewPosts(30, 2935);

        Assertions.assertEquals(15, newPosts.size());
    }
}