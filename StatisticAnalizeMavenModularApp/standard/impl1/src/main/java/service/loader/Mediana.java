package service.loader;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Mediana implements AnalysisService {

    private DataSet result;
    private boolean status = true;

    @Override
    public void setOptions(String[] options) throws AnalysisException {

    }

    @Override
    public String getName() {
        return "Mediana";
    }

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
                    if(i==0) resultTab[i][0] = getName();
                }
                result = new DataSet();
                result.setData(resultTab);
                status = true;
            }).start();
        }else throw new AnalysisException("Task not ended!");
    }

    @Override
    public DataSet retrieve(boolean clear) throws AnalysisException {
        DataSet r = result;
        if(clear) result = null;
        return r;
    }

    public String calculate(String[] data) {
        String result = "";
        int howLong = data.length;
        double[] dane = new double[howLong];
        for (int i = 0; i < howLong; i++) {
            dane[i] = Double.parseDouble(data[i]);
        }
        Arrays.sort(dane);
        if (howLong % 2 == 0) {
            result = String.valueOf((dane[howLong / 2] + dane[(howLong / 2) - 1]) / 2);
        } else {
            result = String.valueOf(dane[howLong / 2]);
        }
        return result;
    }
}
