package backend;

import com.jayway.jsonpath.JsonPath;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RestService {

    public static String countryNameIndex(String url, int index){
        return JsonPath.parse(url).read("$['_links']['country:items'][" + index + "]['name']");
    }

    public static String getHref(String url, String name, boolean isCountry){
        try {
            String read = readURL(url);
            int count = getCount(read);
            int index = -1;
            if(isCountry){
                for(int i = 0; i < count; i++){
                    if(name.equals(countryNameIndex(read, i))){
                        index = i;
                    }
                }
                if(index == -1) return null;
                return JsonPath.parse(read).read("$['_links']['country:items'][" + index + "]['href']");
            }else{
                for(int i = 0; i < count; i++){
                    if(name.equals(continentNameIndex(read, i))){
                        index = i;
                    }
                }
                if(index == -1) return null;
                return JsonPath.parse(read).read("$['_links']['continent:items'][" + index + "]['href']");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return null;
    }

    public static int countryPopulation(String url){
        try {
            return JsonPath.parse(readURL(url)).read("$['population']");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return -1;
    }

    public static String countryCurrency(String url){
        try {
            return JsonPath.parse(readURL(url)).read("$['currency_code']");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return null;
    }

    public static int administrativeDivision(String url){
        try {
            String a = JsonPath.parse(readURL(url)).read("$['_links']['country:admin1_divisions']['href']");
            return JsonPath.parse(readURL(a)).read("$['count']");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return -1;
    }

    public static String matchContinenToCountry(String url){
        try {
            return JsonPath.parse(readURL(url)).read("$['_links']['country:continent']['name']");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return null;
    }

    public static int getCount(String url){
        return JsonPath.parse(url).read("$['count']");
    }

    public static String continentNameIndex(String url, int index){
        return JsonPath.parse(url).read("$['_links']['continent:items'][" + index + "]['name']");
    }

    public static String readURL(String url) throws IOException {
        try {
            URL urlQ1 = new URL(url);
            HttpURLConnection conQ1 = (HttpURLConnection) urlQ1.openConnection();
            conQ1.setRequestMethod("GET");
            BufferedReader bufferedReaderQ1 = new BufferedReader(new InputStreamReader(conQ1.getInputStream()));
            return bufferedReaderQ1.readLine();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return null;
    }

}
