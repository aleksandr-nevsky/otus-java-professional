package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.Processor;

import java.time.LocalDateTime;

public class ProcessorWithException implements Processor {
    private final DateTimeProvider dateTimeProvider;


    public ProcessorWithException(DateTimeProvider dateTimeProvider) {
        this.dateTimeProvider = dateTimeProvider;
    }

    public ProcessorWithException() {
        dateTimeProvider = LocalDateTime::now;
    }

    @Override
    public Message process(Message message) {
        if ((dateTimeProvider.getDate().getSecond() % 2) == 0) {
            throw new RuntimeException("Second is even.");
        }
        return message;
    }
}
