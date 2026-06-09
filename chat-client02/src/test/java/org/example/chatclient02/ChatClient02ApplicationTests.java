package org.example.chatclient02;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;

@SpringBootTest
class ChatClient02ApplicationTests {

    @Test
    void testChatClient(@Autowired ChatClient.Builder chatClientBuilder) {
        String content = chatClientBuilder.build()
                .prompt()
                .user("你好")
                .call()
                .content();

        System.out.println(content);
    }

    @Test
    void testStreamChatClient(@Autowired ChatClient.Builder chatClientBuilder) {
        Flux<String> content = chatClientBuilder.build()
                .prompt()
                .user("你好")
                .stream()
                .content();

        content.toIterable().forEach(System.out::println);
    }
}
