package pq.jdev.b001.bookstore.books.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ZipFileServiceImpl implements ZipFileService{

	private List<String> originalFileNames;
	private String sourcePath;
	
	
	
	
	public List<String> getOriginalFileNames() {
		return originalFileNames;
	}


	public void setOriginalFileNames(List<String> originalFileNames) {
		this.originalFileNames = originalFileNames;
	}


	public String getSourcePath() {
		return sourcePath;
	}


	public void setSourcePath(String sourcePath) {
		this.sourcePath = sourcePath;
	}


	public void zipIt(String zipFile) {
        byte[] buffer = new byte[1024];
        try {
            FileOutputStream fos = new FileOutputStream(zipFile);
            ZipOutputStream zos = new ZipOutputStream(fos);

            System.out.println("Output to Zip : " + zipFile);
            
            

            for (String file: originalFileNames) {
                System.out.println("File Added : " + file);
                ZipEntry ze = new ZipEntry(file);
                zos.putNextEntry(ze);
                FileInputStream fin = new FileInputStream(sourcePath + File.separator + file);
                int len;
                    while ((len = fin .read(buffer)) > 0) {
                        zos.write(buffer, 0, len);
                    }
                    fin.close();
                }

            zos.closeEntry();
            System.out.println("Folder successfully compressed");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
	}

	
	public void generateFileList(File node) {
	        // add file only
	        if (node.isFile()) {
	        	originalFileNames.add(generateZipEntry(node.getAbsolutePath().toString()));
	        }

	        if (node.isDirectory()) {
	            String[] subNote = node.list();
	            for (String filename: subNote) {
	                generateFileList(new File(node, filename));
	            }
	        }
	    }
		

	public String generateZipEntry(String file) {
	        return file.substring(sourcePath.length() + 1, file.length());
	    }
	
}
