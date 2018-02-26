package pl.zientarski;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        final File file = new File(args[0]);
        try(final FileInputStream fileInputStream = new FileInputStream(file)){
            final PageHeader firstPageHeader = new PageHeader(fileInputStream);
            System.out.println(firstPageHeader);
            fileInputStream.read(new byte[firstPageHeader.getPayloadSize()]);

            final PageHeader secondPageHeader = new PageHeader(fileInputStream);
            System.out.println(secondPageHeader);
            fileInputStream.read(new byte[secondPageHeader.getPayloadSize()]);

            final PageHeader thirdPageHeader = new PageHeader(fileInputStream);
            System.out.println(thirdPageHeader);
        }
    }
}
