package in.clouthink.daas.sbb.shared.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;

/**
 * 
 */
public abstract class IOUtils {
    
    private final static Log logger = LogFactory.getLog(IOUtils.class);
    
    public static final int BUFFER_SIZE = 4096;
    
    /**
     * @param x
     */
    public final static void close(Closeable x) {
        if (x != null) {
            try {
                x.close();
            }
            catch (Exception e) {
                logger.error("close error", e);
            }
        }
    }
    
    /**
     * @param in
     * @return
     * @throws IOException
     */
    public static byte[] copyToByteArray(File in) throws IOException {
        if (in == null) {
            throw new IllegalArgumentException("No input File specified");
        }
        return copyToByteArrayAndClose(new BufferedInputStream(new FileInputStream(in)));
    }

    /**
     * @param in
     * @param out
     * @return
     * @throws IOException
     */
    public static int copy(InputStream in, OutputStream out) throws IOException {
        if (in == null) {
            throw new IllegalArgumentException("No InputStream specified");
        }
        if (out == null) {
            throw new IllegalArgumentException("No OutputStream specified");
        }

        int byteCount = 0;
        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead = -1;
        while ((bytesRead = in.read(buffer)) != -1) {
            out.write(buffer, 0, bytesRead);
            byteCount += bytesRead;
        }
        out.flush();
        return byteCount;
    }

    /**
     * @param in
     * @param out
     * @return
     * @throws IOException
     */
    public static int copyAndClose(InputStream in, OutputStream out) throws IOException {
        try {
            return copy(in, out);
        }
        finally {
            close(in);
            close(out);
        }
    }

    /**
     * @param in
     * @param out
     * @throws IOException
     */
    public static void copy(byte[] in, OutputStream out) throws IOException {
        if (in == null) {
            throw new IllegalArgumentException("No input byte array specified");
        }
        if (out == null) {
            throw new IllegalArgumentException("No OutputStream specified");
        }
        out.write(in);
    }

    /**
     * @param in
     * @param out
     * @throws IOException
     */
    public static void copyAndClose(byte[] in, OutputStream out) throws IOException {
        try {
            copy(in, out);
        }
        finally {
            close(out);
        }
    }

    /**
     * @param in
     * @return
     * @throws IOException
     */
    public static byte[] copyToByteArray(InputStream in) throws IOException {
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream(BUFFER_SIZE);
            copy(in, out);
            return out.toByteArray();
        }
        finally {
            close(out);
        }
    }

    /**
     * @param in
     * @return
     * @throws IOException
     */
    public static byte[] copyToByteArrayAndClose(InputStream in) throws IOException {
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream(BUFFER_SIZE);
            copy(in, out);
            return out.toByteArray();
        }
        finally {
            close(in);
            close(out);
        }
    }

    /**
     * @param in
     * @param out
     * @return
     * @throws IOException
     */
    public static int copy(Reader in, Writer out) throws IOException {
        if (in == null) {
            throw new IllegalArgumentException("No Reader specified");
        }
        if (out == null) {
            throw new IllegalArgumentException("No Writer specified");
        }

        int byteCount = 0;
        char[] buffer = new char[BUFFER_SIZE];
        int bytesRead = -1;
        while ((bytesRead = in.read(buffer)) != -1) {
            out.write(buffer, 0, bytesRead);
            byteCount += bytesRead;
        }
        out.flush();
        return byteCount;
    }

    /**
     * @param in
     * @param out
     * @return
     * @throws IOException
     */
    public static int copyAndClose(Reader in, Writer out) throws IOException {
        try {
            return copy(in, out);
        }
        finally {
            close(in);
            close(out);
        }
    }

    /**
     * @param in
     * @param out
     * @throws IOException
     */
    public static void copy(String in, Writer out) throws IOException {
        if (in == null) {
            throw new IllegalArgumentException("No input String specified");
        }
        if (out == null) {
            throw new IllegalArgumentException("No Writer specified");
        }

        out.write(in);
    }

    /**
     * @param in
     * @param out
     * @throws IOException
     */
    public static void copyAndClose(String in, Writer out) throws IOException {
        try {
            copy(in, out);
        }
        finally {
            close(out);
        }
    }

    /**
     * @param in
     * @return
     * @throws IOException
     */
    public static String copyToString(Reader in) throws IOException {
        StringWriter out = null;
        try {
            out = new StringWriter();
            copy(in, out);
            return out.toString();
        }
        finally {
            close(out);
        }
    }

    /**
     * @param in
     * @return
     * @throws IOException
     */
    public static String copyToStringAndClose(Reader in) throws IOException {
        try {
            return copyToString(in);
        }
        finally {
            close(in);
        }
    }

    /**
     * @param in
     * @return
     */
    public static String readAsString(InputStream in) throws IOException {
        InputStreamReader reader;
        try {
            reader = new InputStreamReader(in, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
        return readAsString(reader);
    }

    /**
     * @param in
     * @return
     */
    public static String readAsStringAndClose(InputStream in) throws IOException {
        try {
            return readAsString(in);
        }
        finally {
            close(in);
        }
    }

    /**
     * @param resource
     * @return
     * @throws IOException
     */
    public static String readAsString(String resource) throws IOException {
        InputStream in = Thread.currentThread()
                               .getContextClassLoader()
                               .getResourceAsStream(resource);
        if (in == null) {
            return null;
        }

        return readAsStringAndClose(in);
    }

    /**
     * @param resource
     * @return
     * @throws IOException
     */
    public static byte[] readAsByteArray(String resource) throws IOException {
        InputStream in = null;
        try {
            in = Thread.currentThread()
                       .getContextClassLoader()
                       .getResourceAsStream(resource);
            if (in == null) {
                return null;
            }

            return readAsByteArray(in);
        }
        finally {
            close(in);
        }
    }

    /**
     * @param input
     * @return
     * @throws IOException
     */
    public static byte[] readAsByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();
            copy(input, out);
            return out.toByteArray();
        }
        finally {
            close(out);
        }
    }

    /**
     * @param input
     * @return
     * @throws IOException
     */
    public static byte[] readAsByteArrayAndClose(InputStream input) throws IOException {
        try {
            return readAsByteArray(input);
        }
        finally {
            close(input);
        }
    }
    
    /**
     * @param reader
     * @return
     */
    public static String readAsString(Reader reader) throws IOException {
        StringWriter writer = new StringWriter();
        try {
            char[] buffer = new char[BUFFER_SIZE];
            int n = 0;
            while (-1 != (n = reader.read(buffer))) {
                writer.write(buffer, 0, n);
            }
            
            return writer.toString();
        }
        finally {
            close(writer);
        }
    }
    
    /**
     * @param reader
     * @return
     */
    public static String readAsStringAndClose(Reader reader) throws IOException {
        try {
            return readAsString(reader);
        }
        finally {
            close(reader);
        }
    }
    
    /**
     * @param reader
     * @param length
     * @return
     */
    public static String readAsString(Reader reader, int length) throws IOException {
        char[] buffer = new char[length];
        
        int offset = 0;
        int rest = length;
        int len;
        while ((len = reader.read(buffer, offset, rest)) != -1) {
            rest -= len;
            offset += len;
            
            if (rest == 0) {
                break;
            }
        }
        
        return new String(buffer, 0, length - rest);
    }
    
    /**
     * @param reader
     * @param length
     * @return
     */
    public static String readAsStringAndClose(Reader reader, int length) throws IOException {
        try {
            return readAsString(reader, length);
        }
        finally {
            close(reader);
        }
    }
    
    /**
     * @param ex
     * @return
     */
    public static String getStackTrace(Throwable ex) {
        StringWriter buf = new StringWriter();
        ex.printStackTrace(new PrintWriter(buf));
        return buf.toString();
    }
    
    /**
     * @param stackTrace
     * @return
     */
    public static String toString(StackTraceElement[] stackTrace) {
        StringBuilder buf = new StringBuilder();
        for (StackTraceElement item : stackTrace) {
            buf.append(item.toString());
            buf.append("\n");
        }
        return buf.toString();
    }
    
}
