package com.hangulhunting.Korean_Hunting.file.util;

import java.io.*;

public class BufferedInputStreamDecorator extends FilterInputStream {
    private static final int DEFAULT_BUFFER_SIZE = 4096; // 기본 버퍼 크기

    private byte[] buffer;
    private int bufferPosition;
    private int bytesRead;

    public BufferedInputStreamDecorator(InputStream in) {
        this(in, DEFAULT_BUFFER_SIZE);
    }

    public BufferedInputStreamDecorator(InputStream in, int bufferSize) {
        super(in);
        if (bufferSize <= 0) {
            throw new IllegalArgumentException("Buffer size must be positive");
        }
        this.buffer = new byte[bufferSize];
    }

    @Override
    public int read() throws IOException {
        if (bufferPosition >= bytesRead) {
            fillBuffer();
            if (bytesRead == -1) {
                return -1; // 읽을 데이터가 없을 때
            }
        }
        return buffer[bufferPosition++];
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        if (bufferPosition >= bytesRead) {
            fillBuffer();
            if (bytesRead == -1) {
                return -1; // 읽을 데이터가 없을 때
            }
        }
        int bytesToRead = Math.min(len, bytesRead - bufferPosition);
        System.arraycopy(buffer, bufferPosition, b, off, bytesToRead);
        bufferPosition += bytesToRead;
        return bytesToRead;
    }

    private void fillBuffer() throws IOException {
        bytesRead = in.read(buffer, 0, buffer.length);
        bufferPosition = 0;
    }

    // 예외 처리 등을 추가로 필요한 경우에 대비하여 close 메서드 오버라이드
    @Override
    public void close() throws IOException {
        try {
            super.close();
        } finally {
            buffer = null;
        }
    }
}
