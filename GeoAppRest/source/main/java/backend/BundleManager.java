package backend;

import java.text.ChoiceFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class BundleManager {

    static public Preferences pref = Preferences.userNodeForPackage(Pref.class);
    static public String lang = pref.get("lang", "pl");
    static public String country = pref.get("country", "PL");
    static public ResourceBundle resourceBundle = ResourceBundle.getBundle("Language", new Locale(lang, country));

    public static String numbersSpeling(int points){
        String s = Integer.toString(points);
        char f = s.charAt(s.length() - 1);
        char g = '0';
        if(s.length() > 1)
            g = s.charAt(s.length() - 2);
        int j = Integer.parseInt(Character.toString(g));
        int u = Integer.parseInt(Character.toString(f));
        if(points > 19 && j!=1) {
            ChoiceFormat fmt = new ChoiceFormat("0#" + resourceBundle.getString("p3") + "| 1#" + resourceBundle.getString("p3") + "| 1<"+ resourceBundle.getString("p2")+ "| 4<"+ resourceBundle.getString("p3"));
            return points + " " + fmt.format(u);
        }
        else if(j == 1){
            ChoiceFormat fmt = new ChoiceFormat("0#" + resourceBundle.getString("p3"));
            return points + " " + fmt.format(u);
        }else{
            ChoiceFormat fmt = new ChoiceFormat("0#" + resourceBundle.getString("p3") + "| 1#" + resourceBundle.getString("p1") + "| 1<"+ resourceBundle.getString("p2")+ "| 4<"+ resourceBundle.getString("p3"));
            return points + " " + fmt.format(points);
        }
    }

    public static void main(String[] args) {
        pref.remove("lang");
        pref.remove("country");
    }

}
