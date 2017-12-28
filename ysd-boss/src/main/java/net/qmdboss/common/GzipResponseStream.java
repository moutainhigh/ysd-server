package net.qmdboss.common;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class GzipResponseStream extends ServletOutputStream {
	
	private OutputStream outputStream;

    public GzipResponseStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void write(int b) throws IOException {
    	outputStream.write(b);
    }

    public void write(byte[] b) throws IOException {
    	outputStream.write(b);
    }

    public void write(byte[] b, int off, int len) throws IOException {
    	outputStream.write(b, off, len);
    }

}