package GUI.Forms;

import GUI.Support.Job;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

import static GUI.Forms.Constants.*;

public class MainGridPane extends GridPane {
    private ConsoleOut consoleOut;
    private boolean consoleOpen = false;
    public ImageView ivLogo = newImageView(Constants.IMG_MAIN_GUI_BANNER, .45);
    public ProgressBar pBar = pBar();
    public ListView listView = listView();
    public final ImageView ivLink = newImageView(Constants.IMG_LINK_LABEL, .7);
    public final ImageView ivDir = newImageView(Constants.IMG_DIR_LABEL,.7);
    public final ImageView ivFilename = newImageView(Constants.IMG_FILENAME_LABEL,.7);
    public final ImageView ivAutoPaste = newImageView(Constants.IMG_AUTO_PASTE_LABEL,.7);
    public final ImageView ivBtnStart = imageViewButton(Constants.IMG_START_UP, Constants.IMG_START_DOWN, .45);
    public final ImageView ivBtnSave = imageViewButton(Constants.IMG_SAVE_UP, Constants.IMG_SAVE_DOWN, .45);
    public final ImageView ivBtnConsole = imageToggle(.45);

    public final CheckBox cbAutoPaste = new CheckBox();

    private final HBox boxAutoPaste = boxAutoPaste();
    private final HBox boxLogo = newHBox(ivLogo);

    public final Label lblLinkOut = newLabel();
    public final Label lblDirOut = newLabel();
    public final Label lblFilenameOut = newLabel();
    public final Label lblDownloadInfo = newLabel();

    public final TextField tfLink = newTextField();
    public final TextField tfDir = newTextField();
    public final TextField tfFilename = newTextField();

    public MainGridPane() {
        super();
        addGUI();
    }

    private void addGUI() {
        this.setHgap(20);
        this.setVgap(10);
        setColumnSpan(boxLogo, 4);
        setColumnSpan(pBar, 4);
        setColumnSpan(boxAutoPaste, 2);
        setColumnSpan(ivDir, 3);
        setColumnSpan(ivFilename, 3);
        setColumnSpan(tfLink, 3);
        setColumnSpan(tfDir, 3);
        setColumnSpan(tfFilename, 3);
        setColumnSpan(lblLinkOut, 3);
        setColumnSpan(lblDirOut, 3);
        setColumnSpan(lblFilenameOut, 3);
        setColumnSpan(lblDownloadInfo, 3);
        setRowSpan(listView,11);
        add(boxLogo, 0, 0);
        add(pBar, 0, 1);

        add(listView, 0, 3);

        add(ivLink, 1, 3);
        add(boxAutoPaste, 2, 3);
        add(tfLink, 1, 4);
        add(lblLinkOut, 1, 5);

        add(ivDir, 1, 6);
        add(tfDir, 1, 7);
        add(lblDirOut, 1, 8);

        add(ivFilename, 1, 9);
        add(tfFilename, 1, 10);
        add(lblFilenameOut, 1, 11);
        add(lblDownloadInfo, 1, 12);

        add(ivBtnSave, 1, 13);
        add(ivBtnStart, 3, 13);

        setPrefWidth(Constants.SCREEN_WIDTH * .4);
        setPrefHeight(Constants.SCREEN_HEIGHT * .4);
        setVgrow(pBar,Priority.ALWAYS);
        setVgrow(lblDirOut,Priority.ALWAYS);
        setVgrow(lblFilenameOut,Priority.ALWAYS);
        setVgrow(lblLinkOut,Priority.ALWAYS);
        setVgrow(ivBtnStart,Priority.ALWAYS);
        setVgrow(ivBtnSave,Priority.ALWAYS);
        setVgap(5);
        setHgap(15);
        for (int colIndex = 0; colIndex < 3; colIndex++) {
            ColumnConstraints constraints = new ColumnConstraints();
            constraints.setHgrow(Priority.ALWAYS);
            getColumnConstraints().add(constraints);
        }
        ColumnConstraints constraints = new ColumnConstraints();
        constraints.setMaxWidth(250);
        getColumnConstraints().set(0,constraints);
        GridPane.setMargin(boxLogo, new Insets(0,0,50,0));
        GridPane.setMargin(pBar, new Insets(0,0,25,0));
    }

    public void bindToWorker(final Worker<ObservableList<Long>> worker) {
        lblDownloadInfo.textProperty().bind(worker.messageProperty());
        pBar.progressProperty().bind(worker.progressProperty());
    }

    private ListView listView() {
        ListView<Job> listView = new ListView<>();
        listView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Job item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    // Make empty cells not selectable
                    setDisable(true);
                    setText("");
                }
                else {
                    // Set the text for non-empty cells
                    setDisable(false);
                    setText(item.toString());
                }
            }
        });

        listView.setMaxWidth(250);
        return listView;
    }

    private Label newLabel() {
        Label label = new Label("");
        label.setFont(new Font(MONACO_TTF.toExternalForm(), 20 * .75));
        label.setPrefWidth(Double.MAX_VALUE);
        return label;
    }

    private TextField newTextField() {
        TextField tf = new TextField("");
        tf.setPrefWidth(Double.MAX_VALUE);
        return tf;
    }
    private ProgressBar pBar(){
        ProgressBar pb = new ProgressBar(0.0);
        pb.setPrefWidth(Double.MAX_VALUE);
        pb.setMinHeight(25);
        return pb;
    }

    private HBox newHBox(Node node) {
        HBox box = new HBox(node);
        box.setAlignment(Pos.CENTER);
        return box;
    }
    private HBox boxAutoPaste() {
        HBox box = new HBox(10,ivAutoPaste, cbAutoPaste);
        box.setAlignment(Pos.CENTER_RIGHT);
        return box;
    }

    private ImageView newImageView(Image image, double scale) {
        ImageView iv = new ImageView(image);
        double width = image.getWidth();
        iv.setPreserveRatio(true);
        iv.setFitWidth(width * scale);
        return iv;
    }

    private ImageView imageViewButton(Image imageUp, Image imageDown, double scale) {
        ImageView imageView = new ImageView(imageUp);
        double width = imageUp.getWidth();
        imageView.setOnMouseReleased(e -> imageView.setImage(imageUp));
        imageView.setOnMousePressed(e -> imageView.setImage(imageDown));
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(width * scale);
        return imageView;
    }

    private ImageView imageToggle(double scale) {
        ImageView imageView = new ImageView(Constants.IMG_UP_UP);
        imageView.setOnMousePressed(e -> imageView.setImage(Constants.IMG_UP_DOWN));
        imageView.setOnMouseReleased(e -> imageView.setImage(Constants.IMG_UP_UP));
        imageView.setOnMouseClicked(e-> toggleConsole(false));
        double width = Constants.IMG_UP_UP.getWidth();
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(width * scale);
        return imageView;
    }

    private void toggleConsole(boolean close) {
        if (consoleOpen || close) {
            ivBtnConsole.setImage(Constants.IMG_UP_UP);
            ivBtnConsole.setOnMousePressed(e -> ivBtnConsole.setImage(Constants.IMG_UP_DOWN));
            ivBtnConsole.setOnMouseReleased(e -> ivBtnConsole.setImage(Constants.IMG_UP_UP));
            consoleOut.hide();
            consoleOpen = false;
        } else {
            ivBtnConsole.setImage(Constants.IMG_DOWN_UP);
            ivBtnConsole.setOnMousePressed(e -> ivBtnConsole.setImage(Constants.IMG_DOWN_DOWN));
            ivBtnConsole.setOnMouseReleased(e -> ivBtnConsole.setImage(Constants.IMG_DOWN_UP));
            consoleOut.show();
            consoleOpen = true;
        }
    }
}
