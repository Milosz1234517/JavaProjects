import service.loader.api.AnalysisService;
import service.loader.impl2.Variation;

module impl2 {
    requires api;
    exports service.loader.impl2;
    provides AnalysisService
            with Variation;
}