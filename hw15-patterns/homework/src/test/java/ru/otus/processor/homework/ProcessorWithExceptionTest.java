package ru.otus.processor.homework;

import org.junit.jupiter.api.Test;
import ru.otus.handler.ComplexProcessor;
import ru.otus.model.Message;
import ru.otus.processor.Processor;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class ProcessorWithExceptionTest {

    @Test
    void processWithOk() {
        List<Processor> processors = List.of(new ProcessorWithException(() -> LocalDateTime.of(2022, 11, 23, 19, 6, 5)));

        var complexProcessor = new ComplexProcessor(processors, ex -> {
            throw new RuntimeException(ex.getMessage());
        });

        var message = new Message.Builder(1L)
                .field10("field10")
                .field11("field11")
                .field12("field12")
                .build();

        var result = complexProcessor.handle(message);
        assertThat(result).isEqualTo(message);

    }

    @Test
    void processWithException() {
        List<Processor> processors = List.of(new ProcessorWithException(() -> LocalDateTime.of(2022, 11, 23, 19, 6, 10)));

        var complexProcessor = new ComplexProcessor(processors, ex -> {
            throw new RuntimeException(ex.getMessage());
        });

        var message = new Message.Builder(1L)
                .field10("field10")
                .field11("field11")
                .field12("field12")
                .build();

        assertThatExceptionOfType(RuntimeException.class).isThrownBy(() -> complexProcessor.handle(message));
    }
}