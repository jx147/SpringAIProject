package org.example.quickstart;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.deepseek.DeepSeekAssistantMessage;
import org.springframework.ai.deepseek.DeepSeekChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;

@SpringBootTest
class QuickStartTest {

    @Test
    void testDeepseek(@Autowired DeepSeekChatModel model) {
        String content = model.call("你好");
        System.out.println(content);
    }

    @Test
    void testDeepseekStream(@Autowired DeepSeekChatModel model) {
        Flux<String> content = model.stream("你好");
        content.toIterable().forEach(System.out::println);
    }

    @Test
    void testDeepseekReasoner(@Autowired DeepSeekChatModel model) {
        Prompt prompt = new Prompt("你好你是谁");
        DeepSeekAssistantMessage output = (DeepSeekAssistantMessage) model.call(prompt).getResult().getOutput();
        System.out.println(output.getReasoningContent());
        System.out.println("===================================");
        System.out.println(output.getText());
    }

    @Test
    void testDeepseekStreamReasoner(@Autowired DeepSeekChatModel model) {
        Prompt prompt = new Prompt("你好你是谁");
        Flux<ChatResponse> stream = model.stream(prompt);
        stream.toIterable().forEach(chatResponse -> {
            String reasoningContent = ((DeepSeekAssistantMessage) chatResponse.getResult().getOutput()).getReasoningContent();
            if (reasoningContent != null) {
                System.out.print(reasoningContent);
            }
        });
        stream.toIterable().forEach(chatResponse -> {
            String content = chatResponse.getResult().getOutput().getText();
            if (content != null) {
                System.out.print(content);
            }
        });
    }
}
