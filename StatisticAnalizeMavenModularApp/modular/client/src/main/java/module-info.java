import service.loader.api.AnalysisException;
import service.loader.api.AnalysisService;
import service.loader.api.DataSet;

module client {
    requires api;
    requires java.desktop;
    uses AnalysisService;
    uses AnalysisException;
    uses DataSet;
}