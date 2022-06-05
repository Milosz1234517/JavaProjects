package backend;

public abstract class Templates {

    public static String questionTemplate(String staticPartOfQuestion, String changablePartOFQuestion){
        return String.format(BundleManager.resourceBundle.getString(staticPartOfQuestion), changablePartOFQuestion);
    }

    public static String answerTemplate(String staticPartOfAnswer, String changablePartOFAnswer, String changablePartOFQuestion) {
        return String.format(BundleManager.resourceBundle.getString(staticPartOfAnswer),changablePartOFQuestion, changablePartOFAnswer);
    }

}
