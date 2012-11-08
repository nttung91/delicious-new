package model.pojo;
// Generated Oct 21, 2012 11:19:59 PM by Hibernate Tools 3.2.1.GA


import java.util.HashSet;
import java.util.Set;

/**
 * Author generated by hbm2java
 */
public class Author  implements java.io.Serializable {


     private int authorId;
     private String authorName;
     private int isFollowed;
     private int isGetFollowee;

    public int getIsGetFollowee() {
        return isGetFollowee;
    }

    public void setIsGetFollowee(int isGetFollowee) {
        this.isGetFollowee = isGetFollowee;
    }
     

    public int getIsFollowed() {
        return isFollowed;
    }

    public void setIsFollowed(int isFollowed) {
        this.isFollowed = isFollowed;
    }
     private Set saveLinks = new HashSet(0);
     private Set followingsForFollowee = new HashSet(0);
     private Set followingsForFollower = new HashSet(0);

    public Author() {
    }

	
    public Author(int authorId) {
        this.authorId = authorId;
    }
    public Author(int authorId, String authorName, Set saveLinks, Set followingsForFollowee, Set followingsForFollower) {
       this.authorId = authorId;
       this.authorName = authorName;
       this.saveLinks = saveLinks;
       this.followingsForFollowee = followingsForFollowee;
       this.followingsForFollower = followingsForFollower;
    }
   
    public int getAuthorId() {
        return this.authorId;
    }
    
    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }
    public String getAuthorName() {
        return this.authorName;
    }
    
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
    public Set getSaveLinks() {
        return this.saveLinks;
    }
    
    public void setSaveLinks(Set saveLinks) {
        this.saveLinks = saveLinks;
    }
    public Set getFollowingsForFollowee() {
        return this.followingsForFollowee;
    }
    
    public void setFollowingsForFollowee(Set followingsForFollowee) {
        this.followingsForFollowee = followingsForFollowee;
    }
    public Set getFollowingsForFollower() {
        return this.followingsForFollower;
    }
    
    public void setFollowingsForFollower(Set followingsForFollower) {
        this.followingsForFollower = followingsForFollower;
    }




}


