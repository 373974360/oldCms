package com.baidu.ueditor.hunter;

import java.io.File;
import java.util.*;

import org.apache.commons.io.FileUtils;

import com.baidu.ueditor.PathFormat;
import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.MultiState;
import com.baidu.ueditor.define.State;
import com.deya.util.UploadManager;

public class FileManager {

	private String dir = null;
	private String rootPath = null;
	private String[] allowFiles = null;
	private int count = 0;
	
	public FileManager ( Map<String, Object> conf ) {

		this.rootPath = (String)conf.get( "rootPath" );
		//this.dir = this.rootPath + (String)conf.get( "dir" );
		this.dir = (String)conf.get( "dir" );
		this.allowFiles = this.getAllowFiles( conf.get("allowFiles") );
		this.count = (Integer)conf.get( "count" );
		
	}
	
	public State listFile ( int index ) {
		File dir = new File( this.dir );
		State state = null;

		if ( !dir.exists() ) {
			return new BaseState( false, AppInfo.NOT_EXIST );
		}
		
		if ( !dir.isDirectory() ) {
			return new BaseState( false, AppInfo.NOT_DIRECTORY );
		}
		
		Collection<File> list = FileUtils.listFiles( dir, this.allowFiles, true );
        ArrayList<File> trueList = new ArrayList<File>();
        Object[] objList = list.toArray();
        File file = null;
        for ( Object obj : objList ) {
            if ( obj == null ) {
                break;
            }
            file = (File)obj;
            String fileUrl = PathFormat.format( this.getPath( file ) );
            if(!fileUrl.contains("_s") && !fileUrl.contains("_b"))
            {
                trueList.add(file);
            }
        }

		if ( index < 0 || index > list.size() ) {
			state = new MultiState( true );
		} else {
            if(trueList != null && trueList.size() > 0)
            {
                Collections.sort(trueList,new FileModifyTimeDESC());
            }
			Object[] fileList = Arrays.copyOfRange( trueList.toArray(), index, index + this.count );
			state = this.getState( fileList );
		}

		state.putInfo( "start", index );
		state.putInfo( "total", trueList.size() );
		
		return state;
		
	}
	
	private State getState ( Object[] files ) {
		
		MultiState state = new MultiState( true );
		BaseState fileState = null;
		
		File file = null;
		
		for ( Object obj : files ) {
			if ( obj == null ) {
				break;
			}
			file = (File)obj;
			fileState = new BaseState( true );
			String fileUrl = PathFormat.format( this.getPath( file ) );
			if(fileUrl.startsWith("//"))
			{
				fileUrl = fileUrl.substring(1);
			}
			fileState.putInfo( "url", fileUrl );
			state.addState(fileState);
		}
		
		return state;
		
	}
	
	private String getPath ( File file ) {
		
		String path = file.getAbsolutePath();
        //System.out.println("***************filePath*******************" + path);
        //System.out.println("***************rootPath*******************" + this.rootPath);
        //System.out.println("***************realPath*******************" + path.replace( this.rootPath, "" ));
		return path.replace( this.rootPath, "" );
		
	}
	
	private String[] getAllowFiles ( Object fileExt ) {
		
		String[] exts = null;
		String ext = null;
		
		if ( fileExt == null ) {
			return new String[ 0 ];
		}
		
		exts = (String[])fileExt;
		
		for ( int i = 0, len = exts.length; i < len; i++ ) {
			
			ext = exts[ i ];
			exts[ i ] = ext.replace( ".", "" );
			
		}
		
		return exts;
		
	}

    public static class FileModifyTimeDESC implements Comparator<Object> {
        public int compare(Object o1, Object o2) {

            File cgb1 = (File) o1;
            File cgb2 = (File) o2;
            if (cgb2.lastModified() > cgb1.lastModified()) {
                return 1;
            }
            else{
                return -1;
            }
        }
    }
	
}
