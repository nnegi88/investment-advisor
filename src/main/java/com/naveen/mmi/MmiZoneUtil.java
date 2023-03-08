package com.naveen.mmi;

/**
 * @author Naveen Singh Negi
 * @created 08/03/23
 */
public class MmiZoneUtil {
    private MmiZoneUtil() {
    }

    public static MmiZone getMmiZone(float mmi) {
        if (mmi < 30) {
            return MmiZone.EXTREME_FEAR;
        } else if (mmi < 50) {
            return MmiZone.FEAR;
        } else if (mmi < 70) {
            return MmiZone.GREED;
        } else {
            return MmiZone.EXTREME_GREED;
        }
    }

    public static RecommendedAction getRecommendedAction(MmiZone zone) {
        return switch (zone) {
            case EXTREME_FEAR -> RecommendedAction.BUY;
            case EXTREME_GREED -> RecommendedAction.SELL;
            default -> RecommendedAction.HOLD;
        };
    }
}
