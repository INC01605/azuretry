package com.trial.AzureJava;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

import com.azure.core.http.ProxyOptions.Type;
import com.azure.core.http.rest.PagedIterable;
import com.azure.storage.common.StorageSharedKeyCredential;
import com.azure.storage.file.datalake.DataLakeDirectoryClient;
import com.azure.storage.file.datalake.DataLakeFileClient;
import com.azure.storage.file.datalake.DataLakeFileSystemClient;
import com.azure.storage.file.datalake.DataLakeServiceClient;
import com.azure.storage.file.datalake.DataLakeServiceClientBuilder;
import com.azure.storage.file.datalake.models.ListPathsOptions;
import com.azure.storage.file.datalake.models.PathItem;
import com.azure.storage.file.datalake.models.AccessControlChangeCounters;
import com.azure.storage.file.datalake.models.AccessControlChangeResult;
import com.azure.storage.file.datalake.models.AccessControlType;
import com.azure.storage.file.datalake.models.PathAccessControl;
import com.azure.storage.file.datalake.models.PathAccessControlEntry;
import com.azure.storage.file.datalake.models.PathPermissions;
import com.azure.storage.file.datalake.models.PathRemoveAccessControlEntry;
import com.azure.storage.file.datalake.models.RolePermissions;
import com.azure.storage.file.datalake.options.PathSetAccessControlRecursiveOptions;


public class App 
{
	
	public static DataLakeServiceClient GetDataLakeServiceClient(String accountName, String accountKey){

	    StorageSharedKeyCredential sharedKeyCredential = new StorageSharedKeyCredential(accountName, accountKey);

	    DataLakeServiceClientBuilder builder = new DataLakeServiceClientBuilder();
	    builder.credential(sharedKeyCredential);
	    builder.endpoint("https://" + accountName + ".dfs.core.windows.net");
	    return builder.buildClient();
	}
	public static DataLakeFileSystemClient CreateFileSystem(DataLakeServiceClient serviceClient){

	    return serviceClient.createFileSystem("testcontainer3");
	}
	
	public static DataLakeDirectoryClient CreateDirectory(DataLakeServiceClient serviceClient, String fileSystemName){

	    DataLakeFileSystemClient fileSystemClient = serviceClient.getFileSystemClient(fileSystemName);

	    DataLakeDirectoryClient directoryClient = fileSystemClient.createDirectory("testdirectory");

	    return directoryClient.createSubdirectory("testsubdirectory");
	}
	
	public static void UploadFile(DataLakeFileSystemClient fileSystemClient) 
		    throws FileNotFoundException{
		    
		    DataLakeDirectoryClient directoryClient = fileSystemClient.getDirectoryClient("testdirectory");

		    DataLakeFileClient fileClient = directoryClient.createFile("uploaded-file.txt");

		    File file = new File("src/mytestfile.txt");

		 //   InputStream targetStream = new FileInputStream(file);
		    InputStream targetStream = new BufferedInputStream(new FileInputStream(file));

		    long fileSize = file.length();

		    fileClient.append(targetStream, 0, fileSize);

		    fileClient.flush(fileSize);
		}
	
	public static void ListFilesInDirectory(DataLakeFileSystemClient fileSystemClient){
	    
	    ListPathsOptions options = new ListPathsOptions();
	    options.setPath("testdirectory");
	 
	    PagedIterable<PathItem> pagedIterable = 
	    fileSystemClient.listPaths(options, null);

	    java.util.Iterator<PathItem> iterator = pagedIterable.iterator();

	   
	    PathItem item = iterator.next();

	    while (item != null)
	    {
	        System.out.println(item.getName());


	        if (!iterator.hasNext())
	        {
	            break;
	        }
	        
	        item = iterator.next();
	    }

	}
	
	
    public static void main( String[] args ) throws FileNotFoundException
    {
	    System.out.println( "Azure Try" );
	    System.out.println( "Connection" );
	    DataLakeServiceClient client = GetDataLakeServiceClient("aaydemostorage", "gonax3TlmXyFE2gzQFtJUn6RpnoC2rN1F6bO3KiCiINAzIg9vbF3nWpZ1V7F2+3rouV3c8G78JS8ZbaN5FQzUg==");
	    System.out.println(client);
	    
	    DataLakeFileSystemClient fsClient = CreateFileSystem(client);
	    System.out.println(fsClient);
	    System.out.print("Creating container ");
	    DataLakeDirectoryClient directory = CreateDirectory(client,"testcontainer3");
	    System.out.println(directory);
	    System.out.print("Uploading File ");
	    UploadFile(fsClient);
	    System.out.print("List of files");
	    
	    ListFilesInDirectory(fsClient);

    	}
}
