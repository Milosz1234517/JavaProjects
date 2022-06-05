package service.loader;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Variation implements AnalysisService {

    DataSet result;

    @Override
    public void setOptions(String[] options) throws AnalysisException {

    }

    @Override
    public String getName() {
        return "Wariacja";
    }

    boolean status = true;
    @Override
    public void submit(DataSet ds) throws AnalysisException {
        if(status) {
            status = false;
            new Thread(()->{
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String[][] resultTab = new String[ds.getData().length][];
                for (int i = 0; i < ds.getData().length; i++) {
                    resultTab[i] = new String[1];
                    resultTab[i][0] = calculate(ds.getData()[i]);
                    if(i==0) resultTab[i][0] = "Wariacja";
                }
                result = new DataSet();
                result.setData(resultTab);
                status = true;
            }).start();
        }else throw new AnalysisException("Nie skonczono zadania!");
    }

    @Override
    public DataSet retrieve(boolean clear) throws AnalysisException {
        DataSet r = result;
        if(clear) result = null;
        return r;
    }

    private String calculate(String[] data) {
        String result = "";
        int howLong = data.length;
        double[] dane = new double[howLong];
        double srednia = 0;
        for (int i = 0; i < howLong; i++) {
            dane[i] = Double.parseDouble(data[i]);
            srednia += dane[i];
        }
        srednia = srednia / howLong;
        double up = 0;
        for (int i = 0; i < howLong; i++) {
            up += Math.pow((dane[i] - srednia), 2);
        }
        result = String.valueOf(Math.sqrt(up/howLong));
        return result;
    }
}
