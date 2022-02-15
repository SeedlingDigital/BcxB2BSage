package com.plennegy.io;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DirectoryContents {

    public Path getCsvDirectoryPath(String path, String subFolder) throws IOException
    {


        try
        {
            Path documentsPath = subFolder != null ? Paths.get(path + '/' + subFolder) : Paths.get(path);
            return documentsPath;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;

    }//End Of getDirectoryInfo


}
