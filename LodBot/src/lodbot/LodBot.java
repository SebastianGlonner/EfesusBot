package lodbot;

import common.*;
import javafx.geometry.Pos;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.awt.image.BufferedImage;

public class LodBot {

    static private final Logger log = LogManager.getLogger(LodBot.class);

    static private final int SLEEP_STD = 1100;

    private WindowsBot winBot;
    private AutoWindow bsWin;

    public LodBot(WindowsBot winBot) {
        this.winBot = winBot;
        this.bsWin = winBot.openBlueStacksWindow();
    }

    public void mainLoop() throws InterruptedException {
        prepareWindow();

        checkAtMainMap();

        tryTakeGoldReport();
        doExplorationReport();
        doCollectionDomainTravel();

        for ( int i = 1; i < 30; i++ ) {

            doQuickJourney();
            doQuickJourney();
            doQuickJourney();
            doQuickJourney();

            doExplorationReport();

            checkAtMainMap();

            if ( i % 10 == 0 ) {
                tryTakeGoldReport();
                doCollectionDomainTravel();
            }
        }

        tryTakeGoldReport();
        doCollectionDomainTravel();

    }

    private void prepareWindow() {
        bsWin.winActivate();
        bsWin.winMove(100, 100);
        bsWin.winSize(1280, 720);
        // zoomOut(); doesn't work right now
    }

    private void checkAtMainMap() {
        Point pos = bsWin.findImage(Images.getMainLogo(), SLEEP_STD);
        if ( pos == null ) {
            bsWin.bluestacksReturn(); // try return
            pos = bsWin.findImage(Images.getMainLogo(), SLEEP_STD);
            if ( pos == null )
                throw new RuntimeException("Not at main screen");
        }

        winBot.sleep();
    }

    private void doQuickJourney() {
        if (!bsWin.clickImage(Images.getQuickJourney(), SLEEP_STD, ClickSuccessCheck.ImageGone)) {
            if (!bsWin.clickImage(Images.getQuickJourneyEvent(), SLEEP_STD, ClickSuccessCheck.ImageGone)) {
                throw new RuntimeException("Could not quick journey");
            }
        }
        winBot.sleep(SLEEP_STD);
        clickOk(SLEEP_STD, ClickSuccessCheck.ImageGone);
        checkAtMainMap();
    }

    private void clickOk(int trySeconds) {
        clickOk(trySeconds, ClickSuccessCheck.None);
    }

    private void clickOk(int trySeconds, ClickSuccessCheck clickSuccessCheck) {
        if (!bsWin.clickImage(Images.getButtonOk(), trySeconds, clickSuccessCheck)) {
            throw new CheckSuccessGoneException("Could not click ok");
        }

        winBot.sleep();
    }

    private void doExplorationReport() {
        if ( !bsWin.clickImage(Images.getExplorationReport(), SLEEP_STD, ClickSuccessCheck.ImageGone) ) {
            winBot.sleep();
            return;
        }

        bsWin.findImage(Images.getButtonOk(), 30000);

        winBot.sleep(SLEEP_STD);

        // double click to be doubled sure ...
        bsWin.clickImage(Images.getButtonCancel(), SLEEP_STD);
        winBot.sleep();
        bsWin.clickImage(Images.getButtonCancel(), SLEEP_STD);

        winBot.sleep();

        if ( bsWin.findImage(Images.getExplorReportMonsterFoundZero(), SLEEP_STD) == null ) {
            if (bsWin.clickImage(Images.getButtonYellowAction(), SLEEP_STD, ClickSuccessCheck.ImageGone)) {
                winBot.sleep();
                clickOk(SLEEP_STD);
                winBot.sleep();
            }
        }

        clickOk(SLEEP_STD, ClickSuccessCheck.ImageGone);
    }

    private void doExplorationReportAdvanced() {
        if ( !bsWin.clickImage(Images.getExplorationReport(), SLEEP_STD, ClickSuccessCheck.ImageGone) ) {
            winBot.sleep();
            return;
        }

        bsWin.findImage(Images.getButtonOk(), 30000);

        if ( bsWin.findImage(Images.getExplorReportMonsterFoundZero(), SLEEP_STD) == null ) {
            if (bsWin.clickImage(Images.getButtonYellowAction(), SLEEP_STD, ClickSuccessCheck.ImageGone)) {
                winBot.sleep();

                if (bsWin.clickImage(Images.getButtonCaptureNow(), 3000)) {
                    winBot.sleep(1500);
                    if (bsWin.findImage(Images.getButtonCaptureNow(), 1000) == null) {
                        // nothing to quick capture therefore still this screen

                    } else if (bsWin.clickImage(Images.getButtonRedRelease(), 3000)) {
                        winBot.sleep(1500);
                        if (bsWin.findImage(Images.getButtonRedRelease(), 1000) == null) {
                            bsWin.setAreaMask(new Area(
                                    new Point(300, 200),
                                    new Point(500, 300)
                            ));
                            clickOk(3000);
                            bsWin.resetAreaMask();
                        } else {
                            clickOk(3000);
                        }
                    }
                }

                clickOk(3000);
            }
        }

        bsWin.clickImage(Images.getButtonCancel(), 3000);
        // TODO check for dungeons and beep if not all abandoned?

//        if ( bsWin.findImage(Images.getExplorReportDungeonFoundZero()) == null ) {
//        }

        clickOk(3000);
    }

    private void tryTakeGoldReport() {
        checkAtMainMap();

        // gold report is top right over the city
        Mat townImg = Images.getTownMax();

        Point pos = bsWin.findImage(townImg, 2000);
        if ( pos == null ) {
            return;
        }

        for ( int i = 0; i < 3; i++ ) {
            bsWin.click(new Point(
                    pos.x + townImg.cols() + 30,
                    pos.y
            ));

            winBot.sleep(2000);

            if ( bsWin.findImage(townImg, 500) == null )
                break;

            winBot.sleep();
        }

        if ( bsWin.findImage(townImg, 2000) != null )
            return;

        bsWin.bluestacksReturn();
    }

    private void zoomOut() {
        long now = System.currentTimeMillis();
        while ( System.currentTimeMillis() - now < 1500 ) {
            winBot.send("^ -");
        }
        winBot.sleep();
    }

    private void doCollectionDomainTravel() {
        bsWin.clickImage(Images.get(Images.IMG_DOMAIN_TRAVEL), SLEEP_STD, ClickSuccessCheck.ImageGone);

        doCollectionDomainTravelTown(
                Images.IMG_TOWN_WINFRED,
                Images.IMG_TOWN_WINFRED_COUNTRY
        );

        doCollectionDomainTravelTown(
                Images.IMG_TOWN_KAYBERN,
                Images.IMG_TOWN_KAYBERN_COUNTRY
        );

        doCollectionDomainTravelTown(
                Images.IMG_TOWN_KORDO,
                Images.IMG_TOWN_KORDO_COUNTRY
        );

        bsWin.clickImage(Images.get(Images.IMG_BUTTON_GO_TO_MAINMAP), SLEEP_STD, ClickSuccessCheck.ImageGone);
    }

    private void doCollectionDomainTravelTown(String town, String townCountry) {
        bsWin.clickImage(Images.get(town), SLEEP_STD, ClickSuccessCheck.ImageGone);

        bsWin.findImage(Images.get(townCountry), SLEEP_STD * 3);

        Point size = bsWin.winGetSize();

        // give up shrines
        bsWin.click(new Point(
                size.x - 195,
                140
        ));

        if ( bsWin.findImage(Images.getButtonOk(), SLEEP_STD * 3) != null ) {

            bsWin.clickImage(Images.getButtonOk(), SLEEP_STD);
            winBot.sleep(SLEEP_STD);

            if ( bsWin.findImage(Images.get(Images.IMG_BUTTON_GIVE_UP_SHRINE_YES), SLEEP_STD * 3) != null ) {
                bsWin.clickImage(Images.get(Images.IMG_BUTTON_GIVE_UP_SHRINE_YES), SLEEP_STD);
                winBot.sleep(SLEEP_STD);
                bsWin.clickImage(Images.getButtonOk(), SLEEP_STD);
            }

            if (bsWin.findImage(Images.get(townCountry), SLEEP_STD) == null) {
                // still not at worldmap (no shrines to give up)
                bsWin.bluestacksReturn();
            }

            bsWin.findImage(Images.get(townCountry), SLEEP_STD);
        }

        // sweep fortresses
        bsWin.click(new Point(
                size.x - 40,
                140
        ));

        bsWin.clickImage(Images.get(Images.IMG_BUTTON_SWEEP), SLEEP_STD, ClickSuccessCheck.ImageGone);

        if ( bsWin.findImage(Images.getButtonOk(), SLEEP_STD) == null ) {
            // nothing to sweep
            bsWin.bluestacksReturn();
            winBot.sleep(SLEEP_STD);
            bsWin.bluestacksReturn();
        } else {
            clickOk(SLEEP_STD, ClickSuccessCheck.ImageGone);
        }

        if ( !bsWin.clickImage(Images.get(Images.IMG_BUTTON_GO_TO_WORLDMAP), SLEEP_STD, ClickSuccessCheck.ImageGone) )
            bsWin.clickImage(Images.get(Images.IMG_BUTTON_GO_TO_WORLDMAP_DARK), SLEEP_STD, ClickSuccessCheck.ImageGone);
    }
}
