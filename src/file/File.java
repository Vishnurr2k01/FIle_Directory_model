package file;

import java.util.Map;
import java.util.TreeMap;

public class File {
    private StringBuilder name;
    private StringBuilder content;
    private boolean isSymbolicLink;
    private String symbolicLink;
    private TreeMap<Long,String> history = new TreeMap<>();

    public File(String name, String content) {
        this.name = new StringBuilder(name);
        this.content = new StringBuilder(content);
        this.isSymbolicLink=false;
        this.symbolicLink=null;
    }

    public File(String name, String symbolicLink, boolean isSymbolicLink) {
        this.name = new StringBuilder(name);

        this.symbolicLink = symbolicLink;
        this.isSymbolicLink = isSymbolicLink;
    }

    public String getName() {
        return name.toString();
    }

    public void setName(String name) {
        this.name = new StringBuilder(name);

    }

    public StringBuilder getContent() {
        return content;
    }

    public void setContent(StringBuilder content) {
        this.content = content;
    }

    public void addContent(String content){
        this.content.append(content);
    }
    public boolean isSymbolicLink() {
        return isSymbolicLink;
    }

    public String getSymbolicLinkTarget() {
        return symbolicLink;
    }

    public void setSymbolicLink(boolean issymbolicLink,String symbolicLink) {
        isSymbolicLink = issymbolicLink;
        this.symbolicLink=symbolicLink;
    }

    public void backupFile(long timestamp){
        history.put(timestamp,this.content.toString());
    }
    public void restore(long time){
        if(history.floorEntry(time)==null){
            System.out.println("not backup found at this time");
        }
        System.out.printf("%s found at time %.6f \n",history.floorEntry(time).getValue(),(double)time/1000000);
        System.out.println();
    }


}
