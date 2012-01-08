package com.ocforums.application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

public class HtmlHelper {
    TagNode rootNode;

	static CookieStore cookieStore = new BasicCookieStore();
    static // Create local HTTP context
    HttpContext localContext = new BasicHttpContext();
    // Bind custom cookie store to the local context



	public static HttpContext getcontext(){
		localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
		return localContext;	
	}
	public static void setcontext(HttpContext newcontext){
		
		localContext = newcontext;
		
	}
	
    public HtmlHelper(String htmlPage) throws IOException
    {
    	HttpClient client = new DefaultHttpClient();
    	HttpGet httpget = new HttpGet(htmlPage);
    	HttpResponse response = client.execute(httpget, localContext);
    	HttpEntity entity = response.getEntity();
        HtmlCleaner cleaner = new HtmlCleaner();
        rootNode = cleaner.clean(entity.getContent());
        client.getConnectionManager().closeIdleConnections(1, TimeUnit.MILLISECONDS);
    }

    List<TagNode> getLinksByClass(String CSSClassname)
    {
        List<TagNode> linkList = new ArrayList<TagNode>();

        TagNode linkElements[] = rootNode.getElementsByName("a", true);
        for (int i = 0; linkElements != null && i < linkElements.length; i++)
        {
            String classType = linkElements[i].getAttributeByName("class");
            
            if (classType != null && classType.equals(CSSClassname))
            {
                linkList.add(linkElements[i]);
            }
        }

        return linkList;
    }
    
    List<TagNode> getLinksByClassT(String CSSClassname)
    {
        List<TagNode> linkList = new ArrayList<TagNode>();

        TagNode linkElements[] = rootNode.getElementsByName("span", true);
        for (int i = 0; linkElements != null && i < linkElements.length; i++)
        {
            String classType = linkElements[i].getAttributeByName("class");
            
            if (classType != null && classType.equals(CSSClassname))
            {
                linkList.add(linkElements[i]);
            }
        }

        return linkList;
    }
    
    List<TagNode> getLinksByStyle(String CSSClassname)
    {
        List<TagNode> linkList = new ArrayList<TagNode>();

        TagNode linkElements[] = rootNode.getElementsByName("a", true);
        for (int i = 0; linkElements != null && i < linkElements.length; i++)
        {
            String classType = linkElements[i].getAttributeByName("style");
            
            if (classType != null && classType.equals(CSSClassname))
            {
                linkList.add(linkElements[i]);
            }
        }

        return linkList;
    }
    
    List<TagNode> getLinksByid(String CSSClassname)
    {
        List<TagNode> linkList = new ArrayList<TagNode>();

        TagNode linkElements[] = rootNode.getElementsByName("a", true);
        for (int i = 0; linkElements != null && i < linkElements.length; i++)
        {
            String classType = linkElements[i].getAttributeByName("id");
            
            if (classType != null && classType.matches(".*"+CSSClassname+".*"))
            {
                linkList.add(linkElements[i]);
            }
        }

        return linkList;
    }
    
    List<TagNode> getLinksByOnClick(String CSSClassname)
    {
        List<TagNode> linkList = new ArrayList<TagNode>();

        TagNode linkElements[] = rootNode.getElementsByName("a", true);
        for (int i = 0; linkElements != null && i < linkElements.length; i++)
        {
            String classType = linkElements[i].getAttributeByName("onclick");
            
            if (classType != null && classType.equals(CSSClassname))
            {
                linkList.add(linkElements[i]);
            }
        }

        return linkList;
    }
    
    List<TagNode> getForumStatus(String CSSClassname)
    {
        List<TagNode> linkList = new ArrayList<TagNode>();

        TagNode linkElements[] = rootNode.getElementsByName("img", true);
        for (int i = 0; linkElements != null && i < linkElements.length; i++)
        {
            String classType = linkElements[i].getAttributeByName("src");
            
            if (classType != null && classType.equals(CSSClassname))
            {
                linkList.add(linkElements[i]);
            }
        }

        return linkList;
    }
    
    List<TagNode> getPost(String CSSClassname)
    {
        List<TagNode> linkList = new ArrayList<TagNode>();

        TagNode linkElements[] = rootNode.getElementsByName("table", true);
        for (int i = 0; linkElements != null && i < linkElements.length; i++)
        {
            String classType = linkElements[i].getAttributeByName("id");
            
            if (classType != null && classType.matches(".*"+CSSClassname+".*"))
            {
                linkList.add(linkElements[i]);
            }
        }

        return linkList;
    }
    
    List<TagNode> getPage(String CSSClassname)
    {
        List<TagNode> linkList = new ArrayList<TagNode>();

        TagNode linkElements[] = rootNode.getElementsByName("td", true);
        for (int i = 0; linkElements != null && i < linkElements.length; i++)
        {
            String classType = linkElements[i].getAttributeByName("style");
            
            if (classType != null && classType.equals(CSSClassname))
            {
                linkList.add(linkElements[i]);
            }
        }

        return linkList;
    }
    
    List<TagNode> getThreadUrl(String CSSClassname)
    {
        List<TagNode> linkList = new ArrayList<TagNode>();

        TagNode linkElements[] = rootNode.getElementsByName("td", true);
        for (int i = 0; linkElements != null && i < linkElements.length; i++)
        {
            String classType = linkElements[i].getAttributeByName("title");
            
            if (classType != null && classType.matches(".*"+CSSClassname+".*"))
            {
                linkList.add(linkElements[i]);
            }
        }

        return linkList;
    }
    
}

