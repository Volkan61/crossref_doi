package com.zahangirbd.crossrefdoi;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;


public class DoiMetaXmlParser {
	public static int PRETTY_PRINT_INDENT_FACTOR = 4;
	
	//Read file content into string with - Files.lines(Path path, Charset cs)	 
    private static String readFile(String filePath) {
        StringBuilder contentBuilder = new StringBuilder(); 
        try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }
    
    public DoiItem prepareDoiItemFromXmlStr(String xmlMeta) {
    	try {
    		JSONObject doiMetaJSONObj = XML.toJSONObject(xmlMeta);
    		return prepareDoiItem(doiMetaJSONObj);
    	} catch (JSONException je) {
            System.out.println(je.toString());
        }
    	//TODO: need to throw Exception
    	return null;
    }

    public DoiItem prepareDoiItemFromJsonStr(String jsonStr) {
    	try {
    		JSONObject doiMetaJSONObj = new JSONObject(jsonStr);
    		return prepareDoiItem(doiMetaJSONObj);
    	}catch (JSONException je) {
            System.out.println(je.toString());
        }
    	//TODO: need to throw Exception
    	return null;
    }

    private DoiItem prepareDoiItem(JSONObject doiMetaJSONObj) {
    	DoiItem doiItem = new DoiItem();
    	try {
            String jsonPrettyPrintString = doiMetaJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);
            System.out.println(jsonPrettyPrintString);
            
            JSONObject queryObj = doiMetaJSONObj.getJSONObject("crossref_result").getJSONObject("query_result").getJSONObject("body").getJSONObject("query");
            String status = queryObj.getString("status");
            if("resolved".equalsIgnoreCase(status)) {
            	JSONArray crmItems = queryObj.getJSONArray("crm-item");
            	JSONObject doiObj = queryObj.getJSONObject("doi");
            	String type = doiObj.getString("type");
            	System.out.println("type = " + type);
            	doiItem.setType(type);
            	
            	String doiNum = doiObj.getString("content");
            	System.out.println("doiNum = " + doiNum);
            	doiItem.setDoi(doiNum);
            	
            	if("journal_article".equalsIgnoreCase(type)) {
            		JSONObject journalArtObj = queryObj.getJSONObject("doi_record").getJSONObject("crossref").getJSONObject("journal").getJSONObject("journal_article");
            		String title = journalArtObj.getJSONObject("titles").getString("title");
            		System.out.println("title = " + title);
            		List<String> titles = new ArrayList<String>();
            		titles.add(title);
            		doiItem.setTitle(titles);            		
            	}
            	
            	for(int i=0; i<crmItems.length(); i++) {
            		JSONObject crmItemObj = crmItems.getJSONObject(i);
            		String nm = crmItemObj.getString("name");
            		if("publisher-name".equalsIgnoreCase(nm)) {
            			System.out.println("publisher = " + crmItemObj.getString("content"));
            			doiItem.setPublisher(crmItemObj.getString("content"));
            		} else if("member-id".equalsIgnoreCase(nm)) {
            			System.out.println("memeber = " + crmItemObj.getString("content"));
            			doiItem.setMember(crmItemObj.getString("content"));            			
            		} else if("citedby-count".equalsIgnoreCase(nm)) {
            			System.out.println("IsReferencedByCount = " + crmItemObj.getInt("content"));
            			doiItem.setIsReferencedByCount(crmItemObj.getInt("content"));
            		}
            	}
            	            	
            }
            
        } catch (JSONException je) {
            System.out.println(je.toString());
        }
    	
    	return doiItem;
    }
	
	public static void main(String[] args) {
	
		String filePath = "DoiResponse.json";	
		String data = readFile( filePath );
        System.out.println( data );
        
        DoiMetaXmlParser parser = new DoiMetaXmlParser();
        parser.prepareDoiItemFromJsonStr(data);
	}
}
