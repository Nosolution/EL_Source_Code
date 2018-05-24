package client.pc;

import java.util.ArrayList;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;

public class AppSchedule extends AppController {
    //一坨控件
    public ImageView back;
    public Label mode;
    public ImageView astronomy;
    public ImageView careOfMagicalCreatures;
    public ImageView charms;
    public ImageView defenceAgainstTheDarkArts;
    public ImageView divination;
    public ImageView flyingLessons;
    public ImageView herbology;
    public ImageView historyOfMagic;
    public ImageView muggleStudies;
    public ImageView potions;
    public ImageView transfiguration;
    public ImageView selfStudy;
    public ImageView userDefined;
    public Label member0;
    public Label member1;
    public Label member2;
    public Label member3;
    public ImageView kick1;
    public ImageView kick2;
    public ImageView kick3;
    public ImageView curriculumSchedule;
    public ImageView empty1;
    public ImageView astronomy1;
    public ImageView careOfMagicalCreatures1;
    public ImageView charms1;
    public ImageView defenceAgainstTheDarkArts1;
    public ImageView divination1;
    public ImageView flyingLessons1;
    public ImageView herbology1;
    public ImageView historyOfMagic1;
    public ImageView muggleStudies1;
    public ImageView potions1;
    public ImageView transfiguration1;
    public ImageView empty2;
    public ImageView astronomy2;
    public ImageView careOfMagicalCreatures2;
    public ImageView charms2;
    public ImageView defenceAgainstTheDarkArts2;
    public ImageView divination2;
    public ImageView flyingLessons2;
    public ImageView herbology2;
    public ImageView historyOfMagic2;
    public ImageView muggleStudies2;
    public ImageView potions2;
    public ImageView transfiguration2;
    public ImageView empty3;
    public ImageView astronomy3;
    public ImageView careOfMagicalCreatures3;
    public ImageView charms3;
    public ImageView defenceAgainstTheDarkArts3;
    public ImageView divination3;
    public ImageView flyingLessons3;
    public ImageView herbology3;
    public ImageView historyOfMagic3;
    public ImageView muggleStudies3;
    public ImageView potions3;
    public ImageView transfiguration3;
    public ImageView empty4;
    public ImageView astronomy4;
    public ImageView careOfMagicalCreatures4;
    public ImageView charms4;
    public ImageView defenceAgainstTheDarkArts4;
    public ImageView divination4;
    public ImageView flyingLessons4;
    public ImageView herbology4;
    public ImageView historyOfMagic4;
    public ImageView muggleStudies4;
    public ImageView potions4;
    public ImageView transfiguration4;
    public ImageView empty5;
    public ImageView astronomy5;
    public ImageView careOfMagicalCreatures5;
    public ImageView charms5;
    public ImageView defenceAgainstTheDarkArts5;
    public ImageView divination5;
    public ImageView flyingLessons5;
    public ImageView herbology5;
    public ImageView historyOfMagic5;
    public ImageView muggleStudies5;
    public ImageView potions5;
    public ImageView transfiguration5;
    public ImageView cancel1;
    public ImageView cancel2;
    public ImageView cancel3;
    public ImageView cancel4;
    public ImageView cancel5;
    public ImageView up1;
    public ImageView up2;
    public ImageView up3;
    public ImageView up4;
    public ImageView up5;
    public ImageView down1;
    public ImageView down2;
    public ImageView down3;
    public ImageView down4;
    public ImageView down5;
    public ImageView submit;
    public ImageView reset;

    //一坨变量
    private Dragboard dragboard;

    //一坨事件
    public void clickCourse(MouseEvent mouseEvent) {
        if (appWheel.appUser.leader) {
            ImageView course = (ImageView) mouseEvent.getSource();
            if (empty1.isVisible())
                show(course.getId(), "empty1");
            else if (empty2.isVisible())
                show(course.getId(), "empty2");
            else if (empty3.isVisible())
                show(course.getId(), "empty3");
            else if (empty4.isVisible())
                show(course.getId(), "empty4");
            else if (empty5.isVisible())
                show(course.getId(), "empty5");
        }
    }
    public void dragDetectedCourse(MouseEvent mouseEvent) {
        if (appWheel.appUser.leader) {
            ImageView course = (ImageView) mouseEvent.getSource();
            dragboard = course.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent clipboardContent = new ClipboardContent();
            clipboardContent.putString(course.getId());
            dragboard.setContent(clipboardContent);
            mouseEvent.consume();
        }
    }
    public void dragOverLesson(DragEvent dragEvent) {
        if (appWheel.appUser.leader) {
            dragEvent.acceptTransferModes(TransferMode.MOVE);
            dragEvent.consume();
        }
    }
    public void dragDroppedLesson(DragEvent dragEvent) {
        if (appWheel.appUser.leader) {
            ImageView lesson = (ImageView) dragEvent.getSource();
            dragboard = dragEvent.getDragboard();
            show(dragboard.getString(), lesson.getId());
            dragEvent.setDropCompleted(true);
            dragEvent.consume();
        }
    }
    public void clickCancel(MouseEvent mouseEvent) {
        ImageView cancel = (ImageView) mouseEvent.getSource();
        String id = cancel.getId().substring(6);
        show("empty", getLesson(id).getId());
    }
    public void clickUp(MouseEvent mouseEvent) {
        ImageView up = (ImageView) mouseEvent.getSource();
        String id = up.getId().substring(2);
        if (id.equals("2"))
            swap("1", "2");
        if (id.equals("3"))
            swap("2", "3");
        if (id.equals("4"))
            swap("3", "4");
        if (id.equals("5"))
            swap("4", "5");
    }
    public void clickDown(MouseEvent mouseEvent) {
        ImageView up = (ImageView) mouseEvent.getSource();
        String id = up.getId().substring(4);
        if (id.equals("1"))
            swap("1", "2");
        if (id.equals("2"))
            swap("2", "3");
        if (id.equals("3"))
            swap("3", "4");
        if (id.equals("4"))
            swap("4", "5");
    }
    public void clickReset() {
        show("empty", getLesson("1").getId());
        show("empty", getLesson("2").getId());
        show("empty", getLesson("3").getId());
        show("empty", getLesson("4").getId());
        show("empty", getLesson("5").getId());
    }

    //一坨函数
    void show(String courseID, String lessonID) {
        String id = lessonID.substring(lessonID.length() - 1);
        getLesson(lessonID).setVisible(false);
        getLesson(courseID + id).setVisible(true);
        if (courseID.equals("empty")) {
            getLesson("cancel" + id).setVisible(false);
            getLesson("up" + id).setVisible(false);
            getLesson("down" + id).setVisible(false);
        } else {
            getLesson("cancel" + id).setVisible(true);
            getLesson("up" + id).setVisible(true);
            getLesson("down" + id).setVisible(true);
        }
    }
    private ImageView getLesson(String id) {
        switch (id) {
            case "empty1":
                return empty1;
            case "astronomy1":
                return astronomy1;
            case "careOfMagicalCreatures1":
                return careOfMagicalCreatures1;
            case "charms1":
                return charms1;
            case "defenceAgainstTheDarkArts1":
                return defenceAgainstTheDarkArts1;
            case "divination1":
                return divination1;
            case "flyingLessons1":
                return flyingLessons1;
            case "herbology1":
                return herbology1;
            case "historyOfMagic1":
                return historyOfMagic1;
            case "muggleStudies1":
                return muggleStudies1;
            case "potions1":
                return potions1;
            case "transfiguration1":
                return transfiguration1;
            case "empty2":
                return empty2;
            case "astronomy2":
                return astronomy2;
            case "careOfMagicalCreatures2":
                return careOfMagicalCreatures2;
            case "charms2":
                return charms2;
            case "defenceAgainstTheDarkArts2":
                return defenceAgainstTheDarkArts2;
            case "divination2":
                return divination2;
            case "flyingLessons2":
                return flyingLessons2;
            case "herbology2":
                return herbology2;
            case "historyOfMagic2":
                return historyOfMagic2;
            case "muggleStudies2":
                return muggleStudies2;
            case "potions2":
                return potions2;
            case "transfiguration2":
                return transfiguration2;
            case "empty3":
                return empty3;
            case "astronomy3":
                return astronomy3;
            case "careOfMagicalCreatures3":
                return careOfMagicalCreatures3;
            case "charms3":
                return charms3;
            case "defenceAgainstTheDarkArts3":
                return defenceAgainstTheDarkArts3;
            case "divination3":
                return divination3;
            case "flyingLessons3":
                return flyingLessons3;
            case "herbology3":
                return herbology3;
            case "historyOfMagic3":
                return historyOfMagic3;
            case "muggleStudies3":
                return muggleStudies3;
            case "potions3":
                return potions3;
            case "transfiguration3":
                return transfiguration3;
            case "empty4":
                return empty4;
            case "astronomy4":
                return astronomy4;
            case "careOfMagicalCreatures4":
                return careOfMagicalCreatures4;
            case "charms4":
                return charms4;
            case "defenceAgainstTheDarkArts4":
                return defenceAgainstTheDarkArts4;
            case "divination4":
                return divination4;
            case "flyingLessons4":
                return flyingLessons4;
            case "herbology4":
                return herbology4;
            case "historyOfMagic4":
                return historyOfMagic4;
            case "muggleStudies4":
                return muggleStudies4;
            case "potions4":
                return potions4;
            case "transfiguration4":
                return transfiguration4;
            case "empty5":
                return empty5;
            case "astronomy5":
                return astronomy5;
            case "careOfMagicalCreatures5":
                return careOfMagicalCreatures5;
            case "charms5":
                return charms5;
            case "defenceAgainstTheDarkArts5":
                return defenceAgainstTheDarkArts5;
            case "divination5":
                return divination5;
            case "flyingLessons5":
                return flyingLessons5;
            case "herbology5":
                return herbology5;
            case "historyOfMagic5":
                return historyOfMagic5;
            case "muggleStudies5":
                return muggleStudies5;
            case "potions5":
                return potions5;
            case "transfiguration5":
                return transfiguration5;
            case "cancel1":
                return cancel1;
            case "cancel2":
                return cancel2;
            case "cancel3":
                return cancel3;
            case "cancel4":
                return cancel4;
            case "cancel5":
                return cancel5;
            case "up1":
                return up1;
            case "up2":
                return up2;
            case "up3":
                return up3;
            case "up4":
                return up4;
            case "up5":
                return up5;
            case "down1":
                return down1;
            case "down2":
                return down2;
            case "down3":
                return down3;
            case "down4":
                return down4;
            case "down5":
                return down5;
            default:
                return checkLesson(id);
        }
    }
    private ImageView checkLesson(String id) {
        if (getLesson("astronomy" + id).isVisible())
            return getLesson("astronomy" + id);
        else if (getLesson("careOfMagicalCreatures" + id).isVisible())
            return getLesson("careOfMagicalCreatures" + id);
        else if (getLesson("charms" + id).isVisible())
            return getLesson("charms" + id);
        else if (getLesson("defenceAgainstTheDarkArts" + id).isVisible())
            return getLesson("defenceAgainstTheDarkArts" + id);
        else if (getLesson("divination" + id).isVisible())
            return getLesson("divination" + id);
        else if (getLesson("flyingLessons" + id).isVisible())
            return getLesson("flyingLessons" + id);
        else if (getLesson("herbology" + id).isVisible())
            return getLesson("herbology" + id);
        else if (getLesson("historyOfMagic" + id).isVisible())
            return getLesson("historyOfMagic" + id);
        else if (getLesson("muggleStudies" + id).isVisible())
            return getLesson("muggleStudies" + id);
        else if (getLesson("potions" + id).isVisible())
            return getLesson("potions" + id);
        else if (getLesson("transfiguration" + id).isVisible())
            return getLesson("transfiguration" + id);
        else
            return getLesson("empty" + id);
    }
    private void swap(String id1, String id2) {
        String temp1 = checkLesson(id1).getId();
        String temp2 = checkLesson(id2).getId();
        show(temp1.substring(0, temp1.length() - 1), temp2);
        show(temp2.substring(0, temp2.length() - 1), temp1);
    }
    void upAll() {
        ArrayList<String> arrayList = new ArrayList<>();
        String temp;
        temp = getLesson("1").getId();
        if (!temp.equals("empty1"))
            arrayList.add(temp.substring(0, temp.length() - 1));
        temp = getLesson("2").getId();
        if (!temp.equals("empty2"))
            arrayList.add(temp.substring(0, temp.length() - 1));
        temp = getLesson("3").getId();
        if (!temp.equals("empty3"))
            arrayList.add(temp.substring(0, temp.length() - 1));
        temp = getLesson("4").getId();
        if (!temp.equals("empty4"))
            arrayList.add(temp.substring(0, temp.length() - 1));
        temp = getLesson("5").getId();
        if (!temp.equals("empty5"))
            arrayList.add(temp.substring(0, temp.length() - 1));
        if (arrayList.isEmpty())
            appWheel.appUser.lesson = null;
        else
            appWheel.appUser.lesson = arrayList.get(0);
        for (int i = 0; i < 5; i++) {
            temp = String.valueOf(i + 1);
            if (arrayList.isEmpty())
                show("empty", temp);
            else {
                show(arrayList.get(0), temp);
                arrayList.remove(0);
            }
        }
    }
}
