package service;

import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;

public class StorageService
{

    private String rootPath;

    public StorageService(String rootPath) throws IOException
    {
        this.rootPath = rootPath;
        createRootDir();
    }

    public void createRootDir() throws IOException
    {
        try
        {
            createDirectory("");
        }
        catch (FileAlreadyExistsException ignore)
        {
        }
    }

    public boolean isFileOrDirExists(String filename)
    {
        Path path = createPathIntance(filename);
        return Files.exists(path, LinkOption.NOFOLLOW_LINKS);
    }

    public File createFileInstance(String filename)
    {
        return new File(getResultPath(filename));
    }

    public Path createPathIntance(String filename)
    {
        return Paths.get(getResultPath(filename));
    }

    public String getResultPath(String filename)
    {
        return rootPath + File.separator + filename;
    }

    public void removeFileOrDir(String filename) throws IOException
    {
        Files.deleteIfExists(Paths.get(getResultPath(filename)));
    }

    public void createDirectory(String filename) throws IOException
    {
        Path path = createPathIntance(filename);
        if(Files.exists(path))
            throw new FileAlreadyExistsException("Directory already exists!");
        else
            Files.createDirectory(path);
    }

    public ResponseEntity<Resource> getHttpFile(String filename) throws Exception
    {
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(createPathIntance(filename)));

        return ResponseEntity.ok()
                .contentLength(resource.contentLength())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }

}
