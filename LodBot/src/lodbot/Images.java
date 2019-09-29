package lodbot;

import common.Settings;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.util.HashMap;

public class Images {

    static public final String IMG_DOMAIN_TRAVEL = "domain-travel.png";
    static public final String IMG_TOWN_KORDO = "town-kordo.png";
    static public final String IMG_TOWN_KAYBERN = "town-kaybern.png";
    static public final String IMG_TOWN_WINFRED = "town-winfred.png";
    static public final String IMG_BUTTON_SWEEP = "button-sweep.png";
    static public final String IMG_TOWN_KORDO_COUNTRY = "town-kordo-country.png";
    static public final String IMG_TOWN_KAYBERN_COUNTRY = "town-kaybern-country.png";
    static public final String IMG_TOWN_WINFRED_COUNTRY = "town-winfred-country.png";
    static public final String IMG_BUTTON_GIVE_UP_SHRINE_YES = "give-up-shrine-yes.png";
    static public final String IMG_BUTTON_GO_TO_WORLDMAP = "go-to-worldmap.png";
    static public final String IMG_BUTTON_GO_TO_WORLDMAP_DARK = "go-to-worldmap-dark.png";
    static public final String IMG_BUTTON_GO_TO_MAINMAP = "go-to-mainmap.png";

    static private String imagePath;

    static private String getImagePath() {
        if ( imagePath == null ) {
            imagePath = Settings.getProjectPath() + "LodBot/images/";
        }

        return imagePath;
    }

    static private HashMap<String, Mat> cache = new HashMap<String, Mat>();

    static public Mat get(String imageName) {
        Mat img = cache.get(imageName);
        if ( img == null ) {
            img = Imgcodecs.imread(getImagePath() + imageName);
            cache.put(imageName, img);
        }

        return img;
    }

    static private Mat mainLogo;
    static public Mat getMainLogo() {
        if ( mainLogo == null )
            mainLogo = Imgcodecs.imread(getImagePath() + "main-logo-3.png");

        return mainLogo;
    }

    static private Mat quickJourney;
    static public Mat getQuickJourney() {
        if ( quickJourney == null )
            quickJourney = Imgcodecs.imread(getImagePath() + "quick-journey.png");

        return quickJourney;
    }

    static private Mat quickJourneyEvent;
    static public Mat getQuickJourneyEvent() {
        if ( quickJourneyEvent == null )
            quickJourneyEvent = Imgcodecs.imread(getImagePath() + "quick-journey-event.png");

        return quickJourneyEvent;
    }

    static private Mat btnOk;
    static public Mat getButtonOk() {
        if ( btnOk == null )
            btnOk = Imgcodecs.imread(getImagePath() + "button-ok.png");

        return btnOk;
    }

    static private Mat btnOkDark;
    static public Mat getButtonOkDark() {
        if ( btnOkDark == null )
            btnOkDark = Imgcodecs.imread(getImagePath() + "button-ok-dark.png");

        return btnOkDark;
    }

    static private Mat btnCancel;
    static public Mat getButtonCancel() {
        if ( btnCancel == null )
            btnCancel = Imgcodecs.imread(getImagePath() + "button-red-action.png");

        return btnCancel;
    }

    static private Mat btnYellowAction;
    static public Mat getButtonYellowAction() {
        if ( btnYellowAction == null )
            btnYellowAction = Imgcodecs.imread(getImagePath() + "button-yellow-action.png");

        return btnYellowAction;
    }

    static private Mat explorationReport;
    static public Mat getExplorationReport() {
        if ( explorationReport == null )
            explorationReport = Imgcodecs.imread(getImagePath() + "explorer-reporting.png");

        return explorationReport;
    }

    static private Mat btnCaptureNow;
    static public Mat getButtonCaptureNow() {
        if ( btnCaptureNow == null )
            btnCaptureNow = Imgcodecs.imread(getImagePath() + "button-capture-now-2.png");

        return btnCaptureNow;
    }

    static private Mat btnRedRelease;
    static public Mat getButtonRedRelease() {
        if ( btnRedRelease == null )
            btnRedRelease = Imgcodecs.imread(getImagePath() + "button-click-release.png");

        return btnRedRelease;
    }

    static private Mat explorReportDungeonFoundZero;
    static public Mat getExplorReportDungeonFoundZero() {
        if ( explorReportDungeonFoundZero == null )
            explorReportDungeonFoundZero = Imgcodecs.imread(getImagePath() + "explor-report-dungeon-found-0.png");

        return explorReportDungeonFoundZero;
    }

    static private Mat explorReportMonsterFoundZero;
    static public Mat getExplorReportMonsterFoundZero() {
        if ( explorReportMonsterFoundZero == null )
            explorReportMonsterFoundZero = Imgcodecs.imread(getImagePath() + "explor-report-monster-found-0.png");

        return explorReportMonsterFoundZero;
    }

    static private Mat townMax;
    static public Mat getTownMax() {
        if ( townMax == null )
            townMax = Imgcodecs.imread(getImagePath() + "town-max.png");

        return townMax;
    }
}
