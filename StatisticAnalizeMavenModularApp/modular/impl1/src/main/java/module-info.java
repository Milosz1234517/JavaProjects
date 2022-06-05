import service.loader.api.AnalysisService;
import service.loader.impl1.Mediana;

module impl1 {
    requires api;
    exports service.loader.impl1;
    provides AnalysisService
            with Mediana;
}