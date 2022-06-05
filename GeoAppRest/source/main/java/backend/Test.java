package backend;

import java.io.IOException;
import java.util.Locale;
import java.util.Random;

public class Test {

    private Question[] questions;

    public Test(int num) {
        questions = new Question[num];
    }

    public Question[] getQuestions() {
        return questions;
    }

    public void setQuestions(Question[] questions) {
        this.questions = questions;
    }

    public boolean checkAnswer(String answer, String myanswer) {
        myanswer = removeWhiteSigns(myanswer);
        answer = removeWhiteSigns(answer);
        if (answer.equals(myanswer)) {
            return true;
        } else {
            return false;
        }
    }

    public void generateRandomQuestion(int j) throws IOException {
        String continent = RestService.readURL("https://api.teleport.org/api/continents/");
        String country = RestService.readURL("https://api.teleport.org/api/countries/");
        Random random = new Random();
                int i = random.nextInt(5);
                if (j == 4) {
                    int icontinent = random.nextInt(RestService.getCount(continent));
                    questions[j] = generateQuestion(i, RestService.continentNameIndex(continent, icontinent));
                } else {
                    int icountry = random.nextInt(RestService.getCount(country));
                    questions[j] = generateQuestion(i, RestService.countryNameIndex(country, icountry));
                }
    }

    private String removeWhiteSigns(String string) {
        string=string.toLowerCase(Locale.ROOT);
        return string.replaceAll("\\s","");
    }

    public Question generateQuestion(int chooseTemplate, String changablePartOFQuestion){
        Question question = new Question();

        String url = "https://api.teleport.org/api/countries/";
        String href = RestService.getHref(url, changablePartOFQuestion, true);

        switch (chooseTemplate){
            case 0:
                String continent = RestService.matchContinenToCountry(href);
                question.setShortAnswer(continent);
                question.setContents(Templates.questionTemplate("q1", changablePartOFQuestion));
                question.setAnswer(Templates.answerTemplate("q1a", continent, changablePartOFQuestion));
                break;
            case 1:
                long population = RestService.countryPopulation(href);
                question.setContents(Templates.questionTemplate("q2", changablePartOFQuestion));
                question.setShortAnswer(Long.toString(population));
                question.setAnswer(Templates.answerTemplate("q2a", Long.toString(population), changablePartOFQuestion));
                break;
            case 2:
                String currency = RestService.countryCurrency(href);
                question.setContents(Templates.questionTemplate("q3", changablePartOFQuestion ));
                question.setShortAnswer(currency);
                question.setAnswer(Templates.answerTemplate("q3a", changablePartOFQuestion, currency));
                break;
            case 3:
                int admin = RestService.administrativeDivision(href);
                question.setShortAnswer(Integer.toString(admin));
                question.setContents(Templates.questionTemplate("q4",changablePartOFQuestion));
                question.setAnswer(Templates.answerTemplate("q4a", changablePartOFQuestion, Integer.toString(admin)));
                break;
            case 4:
                url = "https://api.teleport.org/api/continents/";
                href = RestService.getHref(url, changablePartOFQuestion, false);
                int countContinent = 0;
                try {
                    countContinent = RestService.getCount(RestService.readURL(href + "countries/"));
                question.setContents(Templates.questionTemplate("q5", changablePartOFQuestion));
                question.setShortAnswer(Long.toString(countContinent));
                question.setAnswer(Templates.answerTemplate("q5a", Long.toString(countContinent), changablePartOFQuestion));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
        return question;
    }
}
