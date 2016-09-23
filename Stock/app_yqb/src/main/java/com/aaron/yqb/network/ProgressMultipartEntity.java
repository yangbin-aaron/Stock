package com.aaron.yqb.network;

import org.apache.http.entity.mime.MultipartEntity;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by bvin on 2015/10/20.
 */
public class ProgressMultipartEntity extends MultipartEntity{

    private final ProgressListener listener;

    public ProgressMultipartEntity(ProgressListener listener) {
        this.listener = listener;
    }

    @Override
    public void writeTo(OutputStream outstream) throws IOException {
        super.writeTo(new ProgressOutputStream(outstream,listener));
    }

    public interface ProgressListener{
        public void onProgressChanged(long progress);
    }

    public class ProgressOutputStream extends FilterOutputStream{

        private final ProgressListener listener;
        private long transferred;


        /**
         * Constructs a new {@code FilterOutputStream} with {@code out} as its
         * target stream.
         *
         * @param out the target stream that this stream writes to.
         * @param listener
         */
        public ProgressOutputStream(OutputStream out, ProgressListener listener) {
            super(out);
            this.listener = listener;
            transferred = 0;
        }

        @Override
        public void write(byte[] buffer) throws IOException {
            super.write(buffer);
            this.transferred++;
            this.listener.onProgressChanged(this.transferred);
        }

        @Override
        public void write(byte[] buffer, int offset, int length) throws IOException {
            super.write(buffer, offset, length);
            this.transferred += length;
            this.listener.onProgressChanged(this.transferred);
        }
    }
}
