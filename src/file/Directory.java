package file;

import java.util.*;

public class Directory {
    private String name;
   List<Directory> subdirs;
    NavigableMap<String,File> files;


    public Directory(String name) {
        this.name = name;
        this.subdirs = new ArrayList<>();
        this.files = new TreeMap<>();
    }

    public String getName() {
        return name;
    }

    public boolean addFile(String content,String path){
        assert path!=null;
        Directory requiredDirectory = getDirectoryByPath(path);
        String filename = getFileNameFromPath(path);
        if(requiredDirectory.files.containsKey(filename)){
            System.out.println("Error Directory already has a file in same name");
            return false;
        }
        requiredDirectory.files.put(filename,new File(filename,content));
        return true;
    }
    public void getFileContents(String path){
        Directory requiredDirectory = getDirectoryByPath(path);
        String filename = getFileNameFromPath(path);
        if(requiredDirectory.files.containsKey(filename)){
            System.out.println(" -- " + filename);
            System.out.println(requiredDirectory.files.get(filename).getContent());

        }else{
            System.out.println("file not found");
        }
    }

    private String getFileNameFromPath(String path){
        String[] parts = path.split("/");
        return parts[parts.length-1];
    }

    public void appendFileContent(String path, String content){

        Directory requiredDirectory = getDirectoryByPath(path);
        String filename = getFileNameFromPath(path);
        if(!requiredDirectory.files.containsKey(filename)){
            System.out.println("file not found");
            return;
        }
        requiredDirectory.files.get(filename).addContent(content);
    }

    public Directory getDirectoryByPath(String path){
        String[] parts = path.split("/");
        Directory curr = this;
        for(int i=0;i<parts.length-1;i++){
            curr = curr.getOrCreateSubDirectory(parts[i]);
        }
        return curr;
    }
    public void moveFile(String path1,String path2){
        Directory dir1 = getDirectoryByPath(path1);
        String file1 = getFileNameFromPath(path1);
        Directory dir2 = getCompletePath(path2);

        dir2.files.put(file1,dir1.files.get(file1));
        dir1.files.remove(file1);
    }
    private Directory getCompletePath(String path){
        String[] parts = path.split("/");
        Directory curr = this;
        for(int i=0;i<parts.length;i++){
            curr= curr.getOrCreateSubDirectory(parts[i]);
        }
        return curr;
    }
    private Directory getOrCreateSubDirectory(String parts){
        for(Directory subDir : subdirs){
            if(subDir.getName().equals(parts)){
                return subDir;
            }
        }
        Directory subDir = new Directory(parts);
        subdirs.add(subDir);
        return subDir;
    }
    public boolean createSumbolicLink(String source,String target){
        Directory targetDirectory = getCompletePath(target);
        String filename = getFileNameFromPath(source);
        Directory sourcePath = getDirectoryByPath(source);

        if(targetDirectory.files.containsKey(filename)){
            System.out.println("File already exists: " + filename);
            return false;
        }
        targetDirectory.files.put(filename,sourcePath.files.get(filename));
        targetDirectory.files.get(filename).setSymbolicLink(true,source);
        return true;

    }
    public void backupFile(String path,Long timestamp){
        Directory dir = getDirectoryByPath(path);
        String filename = getFileNameFromPath(path);

        dir.files.get(filename).backupFile(timestamp);
        System.out.println("backup successful");

    }
    public void restoreFile(String path,Long timestamp){
        Directory dir = getDirectoryByPath(path);
        String filename = getFileNameFromPath(path);

        dir.files.get(filename).restore(timestamp);



    }

}
