package net.dzikoysk.dynamiclogger.utils;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.function.Consumer;

/**
 * Redirect text delimited by new line separator to the provided message consumer
 */
public class RedirectedOutputStream extends OutputStream {

    private final Consumer<String> consumer;
    private final StringBuilder content = new StringBuilder();

    /**
     * Creates stream instance
     *
     * @param consumer consumer of messages delimited by new line separator
     */
    public RedirectedOutputStream(Consumer<String> consumer) {
        this.consumer = consumer;
    }

    @Override
    public void write(int b) {
        byte[] bytes = new byte[1];
        bytes[0] = (byte) (b & 0xff);
        content.append(new String(bytes));

        if (content.toString().endsWith("\n")) {
            content.setLength(content.length() - 1);
            flush();
        }
    }

    @Override
    public void flush() {
        consumer.accept(content.toString());
        content.setLength(0);
    }

    /**
     * Create {@link java.io.PrintStream} using this output stream
     *
     * @return the associated print stream
     */
    public PrintStream toPrintStream() {
        return new PrintStream(this);
    }

}
