package ma;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.time.LocalDate;

public class RezervimiFluturimit extends Application {
    private final String[][] fluturime = {
            {"Tiranë", "2025-06-10"}, {"Romë", "2025-06-11"}, {"Paris", "2025-06-12"}
    };
    private final String[] vendet = {"1A", "1B", "2A", "2B"};
    private final String[][] rezervime = new String[100][5];
    private int rezervimIndex = 0;

    TextField emriField = new TextField();
    TextField mbiemriField = new TextField();
    ComboBox<String> destinacioniBox = new ComboBox<>();
    DatePicker dataPicker = new DatePicker();
    ComboBox<String> vendBox = new ComboBox<>();
    Label mesazhLabel = new Label();
    TextField kerkoField = new TextField();
    ListView<String> historikList = new ListView<>();

    @Override
    public void start(Stage stage) {
        emriField.setPromptText("Emri");
        mbiemriField.setPromptText("Mbiemri");
        destinacioniBox.getItems().addAll("Tiranë", "Romë", "Paris");
        destinacioniBox.setPromptText("Zgjidh destinacionin");
        vendBox.setPromptText("Zgjidh vendin");

        Button kerkoFluturimeBtn = new Button("Kërko fluturime");
        kerkoFluturimeBtn.setOnAction(e -> kerkoVendet());

        Button rezervoBtn = new Button("Rezervo");
        rezervoBtn.setOnAction(e -> rezervo());

        Button shfaqHistorikBtn = new Button("Shfaq historikun");
        shfaqHistorikBtn.setOnAction(e -> shfaqHistorikun());

        VBox rezervimPane = new VBox(8,
                new Label("Emri:"), emriField,
                new Label("Mbiemri:"), mbiemriField,
                new Label("Destinacioni:"), destinacioniBox,
                new Label("Data e fluturimit:"), dataPicker,
                kerkoFluturimeBtn,
                new Label("Zgjidh vendin:"), vendBox,
                rezervoBtn, mesazhLabel,
                new Separator(),
                new Label("Kërko historikun sipas emrit:"),
                kerkoField, shfaqHistorikBtn, historikList
        );
        rezervimPane.setStyle("-fx-padding: 15;");
        Scene skena = new Scene(rezervimPane, 350, 600);
        stage.setTitle("Rezervimi i Fluturimit");
        stage.setScene(skena);
        stage.show();
    }

    private void kerkoVendet() {
        vendBox.getItems().clear();
        String dest = destinacioniBox.getValue();
        LocalDate data = dataPicker.getValue();
        if (dest == null || data == null) {
            mesazhLabel.setText("Zgjidhni destinacionin dhe datën.");
            return;
        }
        for (String vend : vendet) {
            if (!eshteRezervuar(dest, data.toString(), vend)) {
                vendBox.getItems().add(vend);
            }
        }
        mesazhLabel.setText("Vendet e lira të disponueshme.");
    }

    private void rezervo() {
        String emri = emriField.getText();
        String mbiemri = mbiemriField.getText();
        String dest = destinacioniBox.getValue();
        LocalDate data = dataPicker.getValue();
        String vendi = vendBox.getValue();

        if (emri.isEmpty() || mbiemri.isEmpty() || dest == null || data == null || vendi == null) {
            mesazhLabel.setText("Plotësoni të gjitha fushat!");
            return;
        }

        rezervime[rezervimIndex][0] = emri;
        rezervime[rezervimIndex][1] = mbiemri;
        rezervime[rezervimIndex][2] = dest;
        rezervime[rezervimIndex][3] = data.toString();
        rezervime[rezervimIndex][4] = vendi;
        rezervimIndex++;

        mesazhLabel.setText("Rezervimi u bë me sukses për " + emri + " " + mbiemri);
        vendBox.getItems().remove(vendi);
    }

    private boolean eshteRezervuar(String dest, String data, String vend) {
        for (int i = 0; i < rezervimIndex; i++) {
            if (rezervime[i][2].equals(dest) && rezervime[i][3].equals(data)
                    && rezervime[i][4].equals(vend)) {
                return true;
            }
        }
        return false;
    }

    private void shfaqHistorikun() {
        historikList.getItems().clear();
        String emerKerkuar = kerkoField.getText().toLowerCase();
        if (emerKerkuar.isEmpty()) return;

        for (int i = 0; i < rezervimIndex; i++) {
            if (rezervime[i][0].toLowerCase().equals(emerKerkuar)) {
                historikList.getItems().add(
                        rezervime[i][0] + " " + rezervime[i][1] + " -> " +
                                rezervime[i][2] + ", " + rezervime[i][3] +
                                ", Vendi: " + rezervime[i][4]
                );
            }
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
