package org.plastring.util;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/*
* 读取目录文件，生成pdf多级目录列表。
*
* @author Thomas Jay
* */
public class BookmarkUtil {

    private static List<HashMap<String, Object>>  bookmarksList = new ArrayList<HashMap<String, Object>>();
    private static Stack<HashMap<String, Object>> contentStack = new Stack<HashMap<String, Object>>();

    public static HashMap<String, Object> getBookmarksFromFile(File bookmarkFile) {
        HashMap<String, Object> bookmarkMap = new HashMap<>();
        ArrayList<HashMap<String, Object>> bookmarks = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new FileReader(bookmarkFile))){

            String line = br.readLine();

            if (line != null) {
                bookmarkMap.put("offset", line.split("=")[1]);
            } else {
                bookmarkMap.put("offset", 0);
            }

            int lineNum = 2;

            while(true) {
                HashMap<String, Object> map = new HashMap<>();
                line = br.readLine();
                if (line == null) {
                    break;
                }
                String key = line.split("@")[0];
                String value = null;
                try {
                    value = line.split("@")[1];
                } catch (IndexOutOfBoundsException ex) {
                    throw new Exception(String.format("目录格式有误，第 %d 行， 出错目录为：%s %n", lineNum, line), ex);
                }
                map.put("Title", key);
                map.put("Page", value);

                bookmarks.add(map);
                lineNum++;
            }

            bookmarkMap.put("contents", bookmarks);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }

        return bookmarkMap;
    }

    /*
    * 使用栈实现递归生成目录
    *
    * @param bookmarks
    * @param offset
    * @return List<HashMap<String, Object>>
    */
    public static List<HashMap<String, Object>> getBookmarks(List<HashMap<String, Object>> bookmarks, int offset) {

        HashMap<String, Object> current = null;
        List<HashMap<String, Object>> lastKids = null;

        List<HashMap<String, Object>> kids = new ArrayList<HashMap<String, Object>>();;

        for (int i = 0; i < bookmarks.size(); i++) {

            current = new HashMap<>();

            current.put("Action", "GoTo");
            current.put("Title", bookmarks.get(i).get("Title"));
            current.put("Page", generatePage(Integer.parseInt(bookmarks.get(i).get("Page").toString()), offset));
            current.put("Open", false);

            if (i+1 == bookmarks.size()) {
                bookmarksList.add(current);
                break;
            }

            //　判断和下一级目录与当前目录关系，下一级为子目录，则入栈
            if (hasKids(bookmarks, i) > 0) {
                contentStack.push(current);

                if (kids.size() > 0) {
                    lastKids = kids;
                    kids = new ArrayList<>();
                }

                continue;
            }

            // 添加当前作为上一级目录子目录
            if (!contentStack.isEmpty() && isKid(bookmarks.get(i), contentStack.peek())) {
                kids.add(current);
            }

            // 将子目录放入上一级目录中
            if (hasKids(bookmarks, i) < 0 && contentStack.size() > 1) {
                HashMap<String, Object> temp = contentStack.pop();;
                temp.put("Kids", kids);

                if (lastKids != null) {
                    kids = lastKids;
                } else {
                    kids = new ArrayList<>();
                }
                kids.add(temp);

                continue;

            }


            if (contentStack.size() == 1 && countDot(bookmarks.get(i+1).get("Title").toString()) == 0){
                HashMap<String, Object> temp = contentStack.pop();
                temp.put("Kids", kids);
                bookmarksList.add(temp);
                kids = new ArrayList<HashMap<String, Object>>();
                lastKids = null;
            } else if (contentStack.isEmpty()){
                bookmarksList.add(current);
            }
        }

        contentStack = null;

        return bookmarksList;
    }

    private static boolean isKid(HashMap<String, Object> bookmarkMap, HashMap<String, Object> peek) {
        if (countDot(bookmarkMap.get("Title").toString()) - countDot(peek.get("Title").toString())  == 1) {
            return true;
        }

        return false;
    }

    private static int hasKids(List<HashMap<String, Object>> list, int i) {

        return countDot(list.get(i+1).get("Title").toString()) - countDot(list.get(i).get("Title").toString());

    }

    private static int countDot(String str) {
        int count = 0;
        String indexStr = str.split(" ")[0];
        count = indexStr.split("\\.").length -1;
        return count;
    }

    private static String generatePage(int pageNum, int offset) {
        pageNum+=offset;
        String page = pageNum + " XYZ -32 658 1";
        return page;
    }
}
