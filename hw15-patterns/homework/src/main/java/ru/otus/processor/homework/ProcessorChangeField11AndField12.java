package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.Processor;

public class ProcessorChangeField11AndField12 implements Processor {
    @Override
    public Message process(Message message) {
        String field11 = message.getField11();
        String field12 = message.getField12();
        Message.Builder builder = message.toBuilder();
        builder.field11(field12);
        builder.field12(field11);

        return builder.build();
    }
}
