package file;

public class MainDirectory {
    public static void main(String[] args) {
        String path1 = "home/user/academic/file1";
        String path2 = "home/user/docs/file2";
        Directory dir = new Directory("home");

        long time1 = System.currentTimeMillis();
        long time2 = time1+100;
        long time3 = time2+100;
        dir.addFile("hello",path1);
        dir.getFileContents(path1);
        dir.appendFileContent(path1," world");
        dir.backupFile(path1,time1);
        dir.getFileContents(path1);
        dir.appendFileContent(path1," new text");
        dir.backupFile(path1,time2);
        System.out.println("----------------------------------------------------------------");
        dir.restoreFile(path1,time1);
        dir.restoreFile(path1,time3+100);
    }
}
