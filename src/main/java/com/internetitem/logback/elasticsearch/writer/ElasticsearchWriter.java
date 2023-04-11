//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.internetitem.logback.elasticsearch.writer;

import com.internetitem.logback.elasticsearch.config.HttpRequestHeader;
import com.internetitem.logback.elasticsearch.config.HttpRequestHeaders;
import com.internetitem.logback.elasticsearch.config.Settings;
import com.internetitem.logback.elasticsearch.util.ErrorReporter;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

public class ElasticsearchWriter implements SafeWriter {
    private final StringBuilder sendBuffer;
    private final ErrorReporter errorReporter;
    private final Settings settings;
    private final Collection<HttpRequestHeader> headerList;
    private boolean bufferExceeded;

    public ElasticsearchWriter(ErrorReporter errorReporter, Settings settings, HttpRequestHeaders headers) {
        this.errorReporter = errorReporter;
        this.settings = settings;
        this.headerList = headers != null && headers.getHeaders() != null ? headers.getHeaders() : Collections.emptyList();
        this.sendBuffer = new StringBuilder();
    }

    public void write(char[] cbuf, int off, int len) {
        if (!this.bufferExceeded) {
            this.sendBuffer.append(cbuf, off, len);
            if (this.sendBuffer.length() >= this.settings.getMaxQueueSize()) {
                this.errorReporter.logWarning("Send queue maximum size exceeded - log messages will be lost until the buffer is cleared");
                this.bufferExceeded = true;
            }

        }
    }

    public void sendData() throws IOException {
        if (this.sendBuffer.length() > 0) {
            HttpURLConnection urlConnection = (HttpURLConnection)((HttpURLConnection)this.settings.getUrl().openConnection());

            try {
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                urlConnection.setReadTimeout(this.settings.getReadTimeout());
                urlConnection.setConnectTimeout(this.settings.getConnectTimeout());
                urlConnection.setRequestMethod("POST");
                String body = this.sendBuffer.toString();
                if (!this.headerList.isEmpty()) {
                    Iterator var3 = this.headerList.iterator();

                    while(var3.hasNext()) {
                        HttpRequestHeader header = (HttpRequestHeader)var3.next();
                        urlConnection.setRequestProperty(header.getName(), header.getValue());
                    }
                }

                if (this.settings.getAuthentication() != null) {
                    this.settings.getAuthentication().addAuth(urlConnection, body);
                }

                Writer writer = new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8");
                writer.write(body);
                writer.flush();
                writer.close();
                int rc = urlConnection.getResponseCode();
                if (rc != 200) {
                    String data = slurpErrors(urlConnection);
                    throw new IOException("Got response code [" + rc + "] from server with data " + data);
                }
            } finally {
                urlConnection.disconnect();
            }

            this.sendBuffer.setLength(0);
            if (this.bufferExceeded) {
                this.errorReporter.logInfo("Send queue cleared - log messages will no longer be lost");
                this.bufferExceeded = false;
            }

        }
    }

    public boolean hasPendingData() {
        return this.sendBuffer.length() != 0;
    }

    private static String slurpErrors(HttpURLConnection urlConnection) {
        try {
            InputStream stream = urlConnection.getErrorStream();
            if (stream == null) {
                return "<no data>";
            } else {
                StringBuilder builder = new StringBuilder();
                InputStreamReader reader = new InputStreamReader(stream, "UTF-8");
                char[] buf = new char[2048];

                int numRead;
                while((numRead = reader.read(buf)) > 0) {
                    builder.append(buf, 0, numRead);
                }

                return builder.toString();
            }
        } catch (Exception var6) {
            return "<error retrieving data: " + var6.getMessage() + ">";
        }
    }
}
