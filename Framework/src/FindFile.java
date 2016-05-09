import java.io.*;
import java.util.*;
public class FindFile 
{
   
    public File[] finder( String dirName){
    	try
    	{
	    	File dir = new File(dirName);
	
	    	return dir.listFiles(new FilenameFilter() { 
	    	         public boolean accept(File dir, String filename)
	    	              { return filename.endsWith(".java"); }
	    	} );
    	
    	}catch(Exception e)
    	{
    		System.out.println("EXCEPTION FILE: "+e);
    		return null;
    	}
    	
    }
}