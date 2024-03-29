/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * -Xms1024m -Xmx1024m
 */
package DeliciousCrawler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import lib.tools.HtmlContent;
import lib.tools.Language;
import lib.tools.MD5Convertor;
import model.dao.*;
import model.pojo.*;
import org.hibernate.HibernateException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author THANHTUNG
 */
public class DeliciousHepler {

    public static String getResponeData(String url) throws MalformedURLException, IOException {
        HtmlContent hc = new HtmlContent();
        String res = hc.getHtmlContent(url);
        return res;
    }
    //static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(DeliciousCom.class);

    public static int getRecentTag() throws MalformedURLException, IOException {
        JSONParser jsonParser = new JSONParser();
        Language lang = new Language();

        String jsonDataString = getResponeData(String.format("http://feeds.delicious.com/v2/json/recent?count=1000"));
        if (jsonDataString != null) {
            try {
                JSONArray jsonArray = (JSONArray) jsonParser.parse(jsonDataString);
                int count = 0;
                int count1 = 0;
                // logger.info(String.format("So post lay dc:" + jsonArray.size()));
                System.out.println("So post lay dc:" + jsonArray.size());
                for (int i = 0; i < jsonArray.size(); i++) {

                    JSONObject obj = (JSONObject) jsonArray.get(i);
                    //Set<Tag> tags = new HashSet<>();
                    Timestamp ts = null;
                    if (obj.get("dt") != null) {
                        String date = obj.get("dt").toString();
                        date = date.replace("T", " ").replace("Z", "");
                        // System.out.println(date);
                        ts = Timestamp.valueOf(date);
                    }
                    if (obj.get("t") != null) {

                        JSONArray arrTag = (JSONArray) obj.get("t");
                        //   System.out.println("So tag:"+arrTag.size());
                        for (int j = 0; j < arrTag.size(); j++) {
                            String objtag = (String) arrTag.get(j);
                            TagCollect tag = new TagCollect();
                            tag.setId(TagCollectDAO.nextIndex());
                            tag.setTagName(objtag);
                            if (tag.getTagName().equals(" ") || tag.getTagName().equals("") || tag.getTagName().equals("-")) {
                                continue;
                            }
                            tag.setDateTagged(ts);
                            //neu do dai lon hon 200 thi bo wa
                            if (objtag.length() > 200) {
                                continue;
                            }

                            if (lang.isContainOnlySpecialCharacter(objtag)) {
                                continue;
                            }
                            if (!lang.isContainUnicodeCharacterOnly(objtag)) {
                                continue;
                            }

                            TagCollectDAO dao = new TagCollectDAO();
                            if (dao.checkDuplicate(objtag, ts)) {
                                count++;
                                continue;
                            }
                            count1++;
                            dao.saveOrUpdateObject(tag);
                        }
                    }
                }
                System.out.println("So tag Lay dc/ bi trung:" + count1 + "/" + count);
                int sum = count + count1;
                if (count >= (int) (sum * 0.2)) {
                    return 1;
                } else if (count >= (int) (sum * 0.1)) {
                    return 0;
                }
                return -1;
            } catch (ParseException | NumberFormatException | HibernateException ex) {
                System.out.println("--------------------Error ---------------");
            }

        }
        return -1;
    }

    public static ArrayList<String> getRecentListBookmarkByTag(String tag, int count) throws ParseException, MalformedURLException, IOException {
        JSONParser jsonParser = new JSONParser();
        String bookmarks = getResponeData(String.format("http://feeds.delicious.com/v2/json/tag/%s?count=%d", tag, count));
        if (bookmarks != null) {
            JSONArray jsonArray = (JSONArray) jsonParser.parse(bookmarks);
            ArrayList<String> l = new ArrayList<>();
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject obj = (JSONObject) jsonArray.get(i);
                if (obj.get("u") != null && !obj.get("u").toString().equals("")) {
                    l.add(obj.get("u").toString().trim());
                }

            }
            return l;
        }
        return null;
    }

    public static ArrayList<String> getRecentBookmarks(int count) throws ParseException, MalformedURLException, IOException {
        JSONParser jsonParser = new JSONParser();
        String bookmarks = getResponeData(String.format("http://feeds.delicious.com/v2/json/recent?count=%d", count));
        if (bookmarks != null) {
            JSONArray jsonArray = (JSONArray) jsonParser.parse(bookmarks);
            ArrayList<String> l = new ArrayList<>();
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject obj = (JSONObject) jsonArray.get(i);
                if (obj.get("u") != null && !obj.get("u").toString().equals("")) {
                    l.add(obj.get("u").toString().trim());
                }
            }
            return l;
        }
        return null;
    }

    public static synchronized boolean getAndSaveBookmarkInfo(Link link) throws ParseException, MalformedURLException, IOException {
        JSONParser jsonParser = new JSONParser();
        String bookmark = MD5Convertor.Convert2MD5(link.getUrl());
//        
        String linkInfo = getResponeData(String.format("http://feeds.delicious.com/v2/json/urlinfo/%s", bookmark));
        int totalPost = 0;
        if (linkInfo != null) {
            JSONArray infoArray = (JSONArray) jsonParser.parse(linkInfo);
            if (infoArray.size() == 0) {
                return false;
            }
            totalPost = Integer.parseInt(((JSONObject) infoArray.get(0)).get("total_posts").toString());
            if (((JSONObject) infoArray.get(0)).get("hash") != null) {
                link.setHash(((JSONObject) infoArray.get(0)).get("hash").toString());
            }
            if (((JSONObject) infoArray.get(0)).get("title") != null) {
                link.setTitle(((JSONObject) infoArray.get(0)).get("title").toString());
            }
            link.setTotalPosts(totalPost);

            LinkDAO docdao = new LinkDAO();
            try {
                docdao.saveOrUpdateObject(link);
                //    System.out.println("Da luu Link "+ doc.getLinkId());
                return true;
            } catch (Exception ex) {
                System.out.println("------------------Trung khoa chinh--------------");
            }

            //System.out.println("Save 1 more book mark"); 
        }
        return false;
    }

    public static synchronized boolean getAndSaveBookmarkOnly(String bookmark) throws ParseException {

        int DocID = LinkDAO.nextIndex();
        Link doc = new Link();
        //get info document
        LinkDAO docdao = new LinkDAO();
        int index = docdao.processDuplicate(bookmark);
        if (index != -1) {

            return false;
        }
        doc.setLinkId(DocID);
        doc.setUrl(bookmark);
        try {
            docdao.saveOrUpdateObject(doc);
            //    System.out.println("Da luu Link "+ doc.getLinkId());
            return true;
        } catch (Exception ex) {
            System.out.println("------------------Trung khoa chinh--------------");
        }
        //System.out.println("Save 1 more book mark"); 
        return false;
    }

    private static synchronized SaveLink getSaveLink(Link doc, JSONObject obj) {
        SaveLinkDAO pdao = new SaveLinkDAO();


        SaveLink post = new SaveLink();
        
        if (obj.get("result") != null) {
            System.out.println("---------------------------------------Eror no thing response!");
            return null;
        }


        if (obj.get("a") != null) {
            AuthorDAO auDao = new AuthorDAO();
            Author a = auDao.getObjectByName(obj.get("a").toString());

            if (a != null) {
                a.setIsFollowed(1);

                post.setAuthor(a);
            } else {
                Author au = new Author(AuthorDAO.nextIndex());
                au.setAuthorName(obj.get("a").toString());
                au.setIsFollowed(1);
                au.setIsGetFollowee(0);
                post.setAuthor(au);
            }
            if (obj.get("a").toString().equals("")) {
                return null;
            }
        } else {
            return null;
        }
//        if (obj.get("d") != null) {
//            post.setTitle(obj.get("d").toString());
//        }
//
//        if (obj.get("n") != null) {
//            post.setDescription(obj.get("n").toString());
//        }
        if (obj.get("dt") != null) {
            String date = obj.get("dt").toString();
            date = date.replace("T", " ").replace("Z", "");
            // System.out.println(date);
            post.setDateSave(Timestamp.valueOf(date));
        }
        int res = pdao.checkDuplicateItem(doc.getLinkId(), post.getAuthor().getAuthorId());
        if (res == -1) {
            post.setSaveLinkId(SaveLinkDAO.nextIndex());
        } else {
            System.out.println("Da ton tai !!");
            return null;
        }
        post.setLink(doc);
        try {

            pdao.saveOrUpdateObject(post);
        } catch (HibernateException ex) {
            
            ex.printStackTrace();
            return null;
        }
        return post;
    }

    private static synchronized int getTagLink(JSONObject obj, SaveLink post) {
        if (obj.get("t") != null) {

            JSONArray arrTag = (JSONArray) obj.get("t");
            int count =  arrTag.size();
            //System.out.println("So tag:"+arrTag.size());
            for (int j = 0; j < arrTag.size(); j++) {
                String objtag = (String) arrTag.get(j);
                Tag tag = new Tag(TagDAO.nextIndex(objtag));

                //tag.setTagId(maxTag);
                //neu do dai lon hon 200 thi bo wa
                if (objtag.length() > 200 || objtag.equals("") || Language.isContainOnlySpecialCharacter(objtag) || !Language.isUnicode(objtag) ) {
                    continue;
                }
                tag.setTagName(objtag);
                TagLinkId id = new TagLinkId(post.getSaveLinkId(), tag.getTagId());
                TagLink tfp = new TagLink(id, tag, post);
                TagLinkDAO tfpdao = new TagLinkDAO();
                tfpdao.saveOrUpdateObject(tfp);
            }
            return count;
        }
        return 0;
    }

    public static void getAndSaveBookmarkHistoryByLink(Link doc) throws ParseException, MalformedURLException, IOException {

        String jsonDataString = "";
        String bookmark = MD5Convertor.Convert2MD5(doc.getUrl());
      //  System.out.println("Downloading data......");
        //System.out.println(Calendar.getInstance().getTime().toString());
        jsonDataString = getResponeData(String.format("http://feeds.delicious.com/v2/json/url/%s?count=%d", bookmark, 1000));
        //System.out.println("Complete......");

        //System.out.println(Calendar.getInstance().getTime().toString());
        //  getAndSaveBookmarkHistoryByLink(doc,jsonDataString);
        JSONParser jsonParser = new JSONParser();
        if (jsonDataString != null) {
            try {
                JSONArray jsonArray = (JSONArray) jsonParser.parse(jsonDataString);
                LinkDAO ldao = new LinkDAO();
                // logger.info(String.format("Link #%d Number of author saved:%d/%d\n",doc.getLinkId(),jsonArray.size(),doc.getTotalPosts()));
                System.out.printf("%s - Link #%d Number of author saved :%d/%d\n", Thread.currentThread().getName(), doc.getLinkId(), jsonArray.size(), doc.getTotalPosts());
                if (jsonArray.size() == 0 && doc.getTotalPosts() == 1) {
                    ldao.deleteObject(doc);
                    System.out.println("Da xoa 1 link empty!");
                    return;
                }
                int sum = 0;
                for (int i = 0; i < jsonArray.size(); i++) {
                   // System.out.println(Thread.currentThread().getName() +" Link #"+doc.getLinkId() +" run #"+i);
                            
                    SaveLink post = getSaveLink(doc, (JSONObject)jsonArray.get(i));               
                    if (post !=null) {
                        sum+=getTagLink((JSONObject)jsonArray.get(i), post);
                    }
                    
                }
                System.out.printf("Sum of tags save on link:#%d is %d\n",doc.getLinkId(),sum);
            } catch (ParseException | NumberFormatException | HibernateException ex) {
                ex.printStackTrace();
                //    logger.error("Error at link #"+doc.getLinkId());
                System.out.println("--------------------Error ---------------");
            }

        }
    }

    //get and save follower
    public static synchronized void getFollower(Author a) throws MalformedURLException, IOException {
        JSONParser jsonParser = new JSONParser();
        System.out.println("Downloading");
        String jsonDataString = getResponeData(String.format("http://feeds.delicious.com/v2/json/networkmembers/%s?count=1000", a.getAuthorName()));
        System.out.println("Completed");
        AuthorDAO daoA = new AuthorDAO();
        a.setIsGetFollowee(1);
        //System.out.println(a.getIsGetFollowee());
        daoA.saveOrUpdateObject(a);
       // System.out.println("Da set");
        if (jsonDataString != null) {
            try {
                JSONArray jsonArray = (JSONArray) jsonParser.parse(jsonDataString);

                //  logger.info("Number of follower of Author #"+a.getAuthorId()+":" + jsonArray.size());
                System.out.println(Thread.currentThread().getName() +" Number of following of Author #" + a.getAuthorId() + ":" + jsonArray.size());

                for (int i = 0; i < jsonArray.size(); i++) {

                    JSONObject obj = (JSONObject) jsonArray.get(i);
                    Timestamp ts = null;
                    if (obj.get("dt") != null) {
                        String date = obj.get("dt").toString();
                        date = date.replace("T", " ").replace("Z", "");
                        // System.out.println(date);
                        ts = Timestamp.valueOf(date);
                    }
                    if (obj.get("user") != null) {

                        FollowingDAO daoF = new FollowingDAO();
                        Following fo = new Following();
                        fo.setAuthorByFollower(a);
                        fo.setDateFollow(ts);
                        Author f = daoA.getObjectByName(obj.get("user").toString().trim());
                        if (f != null) {
                            FollowingId id = new FollowingId(a.getAuthorId(), f.getAuthorId());
                            fo.setId(id);
                            fo.setAuthorByFollowee(f);

                        } else {
                            System.out.println("-----------------Moi");
                            f = new Author(AuthorDAO.nextIndexForFollowee());
                            f.setAuthorName(obj.get("user").toString().trim());
                            f.setIsFollowed(0);
                            f.setIsGetFollowee(1);
                            daoA.saveOrUpdateObject(f);
                            System.out.println("-----------------Moi " + f.getAuthorId());
                            FollowingId id = new FollowingId(a.getAuthorId(), f.getAuthorId());
                            fo.setId(id);
                            fo.setAuthorByFollower(f);

                        }
                        daoF.saveOrUpdateObject(fo);

                    }
                }

            } catch (ParseException | NumberFormatException | HibernateException ex) {
                System.out.println("--------------------Error ---------------");
                ex.printStackTrace();
            }

        }
    }
}
