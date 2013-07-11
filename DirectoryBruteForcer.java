import java.util.*;
import java.io.*;
import java.io.UnsupportedEncodingException; 
import org.apache.commons.lang3.StringUtils;

class DirectoryBruteForcer
{
    public static void main(String[] args)
    {
		//Written by 323, because I'm bored and need something to do
		//Protip to CMU staff, if you're reading this:
		//The secure auth token is easily defeated by some stringUtils, just saying.
		//Started on 7/10/2013, still majorly in-progress
		//Also non-functioning at the moment
		
        System.out.println("-Carnegie Mellon University-");
        System.out.println("---Directory Brute Forcer---");
        System.out.println("-----------By 323-----------");
        System.out.println("");
        
        HTTPRequest request = new HTTPRequest();
        request.setURL("http://directory.andrew.cmu.edu/search/basic");
        
        String source;
        
        System.out.println("[~] Current URL:");
        System.out.println("[~] directory.andrew.cmu.edu");
        
        source = request.get();
        
        //grab auth token and set it to a variable using apache commons lang library to parse source
        String authtoken = StringUtils.substringBetween(source, "<input name=\"authenticity_token\" type=\"hidden\" value=\"", "\" />");
        System.out.println("[~] Auth Token: " + authtoken);
        System.out.println("[~] Searching for User IDs....");
		
		//will be turned into bruteforcing all triple-char plus wild card combinations
		//eg: aab* aac* aad* joh* oue* zzz* etc etc etc.
        String searchstring = "aaa*";
        
        //post to the form with the current string to search
        String postData = "?authenticity_token" + "=" + authtoken +
                "&" + "search_generic_search_terms" + "=" + searchstring +
                "&" + "commit" + "=" + "Search";
        System.out.println("Post Data: " + postData);
        source = request.post("http://directory.andrew.cmu.edu/search/basic/results", postData);
        
        System.out.println(source);
        
        //grab the usernames
        try {
            PrintWriter out = new PrintWriter(new FileWriter("UserNames.txt"));
            //write values to text file
            String usernamebatch = StringUtils.substringBetween(source, "<h2 id=\"vend_item_title\">", "</h2>");
            String usernamebatch2 = StringUtils.substringBetween(usernamebatch, "</td><td nowrap width=\"15%\">", "</td><td nowrap width=\"20%\">");
            String username = StringUtils.substringBetween(usernamebatch2, "\">", "</a>");
            if (username != null )
            {
                out.println(username);
            }
            out.close();
        }
        catch ( IOException error ) {
            System.err.println( "Error writing to output file: " + error );
        }
        System.out.println("[!] Program Finished");
    }
}