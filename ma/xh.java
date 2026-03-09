package ma;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.Group;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.*;

public class xh extends Application {

    private TilePane fotoPane;
    private List<ImageView> listaFotove = new ArrayList<>();
    private Map<ImageView, String> pershkrimeMap = new HashMap<>();
    private ImageView fotoSelekutuar = null;

    private TextArea pershkrimTextArea;
    private Button ruajPershkrimBtn;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Albumi Fotografik");

        Button shtoFotoBtn = new Button("Shto Foto");
        Button fshijFotoBtn = new Button("Fshij Foto");
        Button zoomInBtn = new Button("Zoom In");
        Button zoomOutBtn = new Button("Zoom Out");
        Button rrotulloBtn = new Button("Rrotullo");

        HBox butonaPane = new HBox(10, shtoFotoBtn, fshijFotoBtn, zoomInBtn, zoomOutBtn, rrotulloBtn);
        butonaPane.setPadding(new Insets(10));

        fotoPane = new TilePane();
        fotoPane.setPadding(new Insets(10));
        fotoPane.setHgap(10);
        fotoPane.setVgap(10);

        ScrollPane scrollPane = new ScrollPane(fotoPane);

        pershkrimTextArea = new TextArea();
        pershkrimTextArea.setPromptText("Përshkrimi i fotos së selektuar...");
        pershkrimTextArea.setWrapText(true);

        ruajPershkrimBtn = new Button("Ruaj Përshkrimin");
        ruajPershkrimBtn.setOnAction(e -> ruajPershkrimin());

        VBox pershkrimPane = new VBox(5, new Label("Përshkrimi i Fotos:"), pershkrimTextArea, ruajPershkrimBtn);
        pershkrimPane.setPadding(new Insets(10));
        pershkrimPane.setPrefWidth(300);

        BorderPane root = new BorderPane();
        root.setTop(butonaPane);
        root.setCenter(scrollPane);
        root.setRight(pershkrimPane);

        shtoFotoBtn.setOnAction(e -> shtoFoto());
        fshijFotoBtn.setOnAction(e -> fshijFoto());
        zoomInBtn.setOnAction(e -> ndryshoZoom(1.25));
        zoomOutBtn.setOnAction(e -> ndryshoZoom(0.8));
        rrotulloBtn.setOnAction(e -> rrotulloFoto());

        Scene scene = new Scene(root, 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void shtoFoto() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Zgjidh Foto");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Foto", "*.png", "*.jpg", "*.jpeg")
        );

        List<File> files = fileChooser.showOpenMultipleDialog(null);
        if (files != null) {
            for (File file : files) {
              
                Image img = new Image(file.toURI().toString(), 0, 0, true, false);

                ImageView iv = new ImageView(img);
                iv.setPreserveRatio(true);
                iv.setSmooth(false);
                iv.setUserData(0.0);

                Group grp = new Group(iv);

                iv.setOnMouseClicked(event -> {
                    if (event.getButton() == MouseButton.PRIMARY) {
                        fotoSelekutuar = iv;
                        pershkrimTextArea.setText(pershkrimeMap.getOrDefault(iv, ""));
                    }
                });

                listaFotove.add(iv);
                fotoPane.getChildren().add(grp);
            }
        }
    }

    private void ruajPershkrimin() {
        if (fotoSelekutuar != null) {
            pershkrimeMap.put(fotoSelekutuar, pershkrimTextArea.getText());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Përshkrimi u ruajt");
            alert.setContentText("Përshkrimi u ruajt me sukses për foton selektuar.");
            alert.showAndWait();
        }
    }

    private void fshijFoto() {
        if (!listaFotove.isEmpty()) {
            ImageView fundit = listaFotove.remove(listaFotove.size() - 1);
            fotoPane.getChildren().remove(fundit.getParent());
            pershkrimeMap.remove(fundit);
            if (fundit == fotoSelekutuar) {
                fotoSelekutuar = null;
                pershkrimTextArea.clear();
            }
        }
    }

    private void ndryshoZoom(double faktor) {
        for (ImageView iv : listaFotove) {
            iv.setScaleX(iv.getScaleX() * faktor);
            iv.setScaleY(iv.getScaleY() * faktor);
        }
    }

    private void rrotulloFoto() {
        for (ImageView iv : listaFotove) {
            double rot = (double) iv.getUserData() + 90;
            iv.setRotate(rot);
            iv.setUserData(rot);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
