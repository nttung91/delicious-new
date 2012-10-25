package model.pojo;
// Generated Oct 21, 2012 11:19:59 PM by Hibernate Tools 3.2.1.GA



/**
 * TagLink generated by hbm2java
 */
public class TagLink  implements java.io.Serializable {


     private TagLinkId id;
     private Tag tag;
     private SaveLink saveLink;
     private String info;

    public TagLink() {
    }

	
    public TagLink(TagLinkId id, Tag tag, SaveLink saveLink) {
        this.id = id;
        this.tag = tag;
        this.saveLink = saveLink;
    }
    public TagLink(TagLinkId id, Tag tag, SaveLink saveLink, String info) {
       this.id = id;
       this.tag = tag;
       this.saveLink = saveLink;
       this.info = info;
    }
   
    public TagLinkId getId() {
        return this.id;
    }
    
    public void setId(TagLinkId id) {
        this.id = id;
    }
    public Tag getTag() {
        return this.tag;
    }
    
    public void setTag(Tag tag) {
        this.tag = tag;
    }
    public SaveLink getSaveLink() {
        return this.saveLink;
    }
    
    public void setSaveLink(SaveLink saveLink) {
        this.saveLink = saveLink;
    }
    public String getInfo() {
        return this.info;
    }
    
    public void setInfo(String info) {
        this.info = info;
    }




}

