package bilboard;

import java.time.Duration;

public class Advert {
    public int id;
    public String advertText;
    public Duration leftSec;

    public Advert(int id, String advertText, Duration leftSec) {
        this.id = id;
        this.advertText = advertText;
        this.leftSec = leftSec;
    }

    public String getAdvertText() {
        return advertText;
    }

    public Duration getLeftSec() {
        return leftSec;
    }
}
