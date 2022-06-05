package project;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LoaderClass extends ClassLoader{

    String canonicalPackage = "procesors";

    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] buf = new byte[0];
        try {
            buf = Files.readAllBytes(Path.of(name));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return defineClass(canonicalPackage + "." + Path.of(name).getFileName().toString().replace(".class", ""), buf, 0, buf.length);
    }
}
