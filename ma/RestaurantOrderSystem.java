package ma;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class RestaurantOrderSystem extends Application {

    private Map<String, Integer> selectedItems = new LinkedHashMap<>();
    private double total = 0;
    private int tableNumber;
    private String loggedWaiter;
    private LocalDateTime orderDateTime;
    private double TAX = 0.20; // 20% TVSH

    enum Language { SQ, EN }
    private Language language = Language.SQ;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    // Tekste multi-language
    private Map<String, String> txtSQ = new HashMap<>();
    private Map<String, String> txtEN = new HashMap<>();

    @Override
    public void start(Stage stage) {
        initTexts();
        stage.setTitle("Restaurant System");
        showLoginPage(stage);
    }

    private void initTexts() {

        txtSQ.put("loginTitle", "Login Kamarieri");
        txtSQ.put("username", "Username");
        txtSQ.put("password", "Password");
        txtSQ.put("loginError", "Username ose Password gabim!");
        txtSQ.put("menuTitle", "Menuja e Restorantit");
        txtSQ.put("tablePrompt", "Numri i Tavolinës");
        txtSQ.put("confirmOrder", "Konfirmo Porosinë");
        txtSQ.put("invalidTable", "Numër tavoline i pavlefshëm!");
        txtSQ.put("noItem", "Zgjidh të paktën një artikull!");
        txtSQ.put("invoiceTitle", "Fatura");
        txtSQ.put("goPayment", "Shko te Pagesa");
        txtSQ.put("paymentTitle", "Mënyra e Pagesës");
        txtSQ.put("finish", "Përfundo");
        txtSQ.put("choosePayment", "Zgjidh mënyrën e pagesës!");
        txtSQ.put("success", "Porosia u dërgua me sukses!");
        txtSQ.put("cash", "Cash");
        txtSQ.put("card", "Card");
        txtSQ.put("subtotal", "Subtotal");
        txtSQ.put("tax", "TVSH 20%");
        txtSQ.put("total", "Totali");
        txtSQ.put("fiscalInvoice", "Fatura Fiskale");

        txtEN.put("loginTitle", "Waiter Login");
        txtEN.put("username", "Username");
        txtEN.put("password", "Password");
        txtEN.put("loginError", "Wrong username or password!");
        txtEN.put("menuTitle", "Restaurant Menu");
        txtEN.put("tablePrompt", "Table Number");
        txtEN.put("confirmOrder", "Confirm Order");
        txtEN.put("invalidTable", "Invalid table number!");
        txtEN.put("noItem", "Choose at least one item!");
        txtEN.put("invoiceTitle", "Invoice");
        txtEN.put("goPayment", "Go to Payment");
        txtEN.put("paymentTitle", "Payment Method");
        txtEN.put("finish", "Finish");
        txtEN.put("choosePayment", "Choose a payment method!");
        txtEN.put("success", "Order sent successfully!");
        txtEN.put("cash", "Cash");
        txtEN.put("card", "Card");
        txtEN.put("subtotal", "Subtotal");
        txtEN.put("tax", "Tax 20%");
        txtEN.put("total", "Total");
        txtEN.put("fiscalInvoice", "Fiscal Invoice");
    }

    private String txt(String key) {
        return language == Language.SQ ? txtSQ.get(key) : txtEN.get(key);
    }

    private void showLoginPage(Stage stage) {

        Label title = new Label(txt("loginTitle"));
        title.setStyle("-fx-font-size:18; -fx-font-weight:bold;");

        TextField userField = new TextField();
        userField.setPromptText(txt("username"));

        PasswordField passField = new PasswordField();
        passField.setPromptText(txt("password"));

        Button loginBtn = new Button("Login");
        loginBtn.setOnAction(e -> {
            if (userField.getText().equals("marjo") && passField.getText().equals("1234")) {
                loggedWaiter = userField.getText();
                showMenuPage(stage);
            } else {
                new Alert(Alert.AlertType.ERROR, txt("loginError")).showAndWait();
            }
        });

        VBox box = new VBox(12, title, userField, passField, loginBtn);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(20));

        stage.setScene(new Scene(new StackPane(box), 300, 250));
        stage.show();
    }

    private void showMenuPage(Stage stage) {

        Label title = new Label(txt("menuTitle"));
        title.setStyle("-fx-font-size:20; -fx-font-weight:bold;");

        TextField tableField = new TextField();
        tableField.setPromptText(txt("tablePrompt"));
        tableField.setMaxWidth(200);

        ComboBox<String> langBox = new ComboBox<>();
        langBox.getItems().addAll("Shqip", "English");
        langBox.setValue(language == Language.SQ ? "Shqip" : "English");
        langBox.setOnAction(e -> {
            language = langBox.getValue().equals("English") ? Language.EN : Language.SQ;
            showMenuPage(stage);
        });

        VBox layout = new VBox(15, title, langBox, tableField);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setPadding(new Insets(20));

        Map<String, Spinner<Integer>> spinners = new LinkedHashMap<>();
        Map<String, Double> prices = new LinkedHashMap<>();
        Map<String, Map<String, Double>> categories = new LinkedHashMap<>();

        Map<String, Double> sallata = new LinkedHashMap<>();
        sallata.put("Sallatë Jeshile", 300.0);
        sallata.put("Sallatë Greke", 350.0);
        sallata.put("Sallatë Caesar", 400.0);
        categories.put("🥗 Sallata", sallata);

        Map<String, Double> pasta = new LinkedHashMap<>();
        pasta.put("Linguini", 500.0);
        pasta.put("Spaghetti Bolognese", 550.0);
        pasta.put("Rizoto me Kërpudha", 480.0);
        categories.put("🍝 Pasta & Rizoto", pasta);

        Map<String, Double> kryesore = new LinkedHashMap<>();
        kryesore.put("Fileto Pule", 700.0);
        kryesore.put("Biftek Viçi", 900.0);
        kryesore.put("Kotoletë Derr", 750.0);
        categories.put("🍖 Pjata Kryesore", kryesore);

        Map<String, Double> pije = new LinkedHashMap<>();
        pije.put("Ujë", 100.0);
        pije.put("Coca-Cola", 150.0);
        pije.put("Fanta", 150.0);
        pije.put("Birrë", 250.0);
        categories.put("🥤 Pije", pije);

        Map<String, Double> embelsira = new LinkedHashMap<>();
        embelsira.put("Tiramisu", 300.0);
        embelsira.put("Cheesecake", 320.0);
        embelsira.put("Akullore", 250.0);
        categories.put("🍰 Ëmbëlsira", embelsira);

        categories.forEach((catName, items) -> {
            Label catLabel = new Label(catName);
            catLabel.setStyle("-fx-font-size:16; -fx-font-weight:bold;");

            VBox box = new VBox(8);
            box.setAlignment(Pos.CENTER);

            for (Map.Entry<String, Double> entry : items.entrySet()) {
                String item = entry.getKey();
                double price = entry.getValue();
                prices.put(item, price);

                Label lbl = new Label(item + " - " + price + " Lek");
                Spinner<Integer> sp = new Spinner<>(0, 20, 0);
                sp.setPrefWidth(80);

                HBox row = new HBox(10, lbl, sp);
                row.setAlignment(Pos.CENTER);

                spinners.put(item, sp);
                box.getChildren().add(row);
            }

            VBox wrapper = new VBox(6, catLabel, box);
            wrapper.setAlignment(Pos.CENTER);
            wrapper.setStyle("-fx-border-color:#ccc; -fx-padding:10;");

            layout.getChildren().add(wrapper);
        });

        Button confirmBtn = new Button(txt("confirmOrder"));
        confirmBtn.setOnAction(e -> {

            try { tableNumber = Integer.parseInt(tableField.getText()); }
            catch (NumberFormatException ex) {
                new Alert(Alert.AlertType.WARNING, txt("invalidTable")).showAndWait();
                return;
            }

            selectedItems.clear();
            total = 0;
            for (Map.Entry<String, Spinner<Integer>> entry : spinners.entrySet()) {
                int qty = entry.getValue().getValue();
                if (qty > 0) {
                    selectedItems.put(entry.getKey(), qty);
                    total += prices.get(entry.getKey()) * qty;
                }
            }

            if (selectedItems.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, txt("noItem")).showAndWait();
                return;
            }

            orderDateTime = LocalDateTime.now();
            showInvoicePage(stage);
        });

        layout.getChildren().add(confirmBtn);

        ScrollPane scroll = new ScrollPane(layout);
        scroll.setFitToWidth(true);

        stage.setScene(new Scene(new StackPane(scroll), 500, 700));
    }

    private void showInvoicePage(Stage stage) {

        TextArea area = new TextArea();
        area.setEditable(false);
        area.setMaxWidth(400);

        double subtotal = total;
        double taxAmount = subtotal * TAX;
        double grandTotal = subtotal + taxAmount;

        String fiscalNumber = "P" + System.currentTimeMillis();

        StringBuilder sb = new StringBuilder();
        sb.append(txt("invoiceTitle")).append("\n");
        sb.append("-------------------------\n");
        sb.append("Kamarieri: ").append(loggedWaiter).append("\n");
        sb.append("Tavolina: ").append(tableNumber).append("\n");
        sb.append("Data: ").append(orderDateTime.format(formatter)).append("\n\n");

        selectedItems.forEach((k, v) -> sb.append("- ").append(k).append(" x").append(v).append("\n"));

        sb.append("\n").append(txt("subtotal")).append(": ").append(subtotal).append(" Lek\n");
        sb.append(txt("tax")).append(": ").append(taxAmount).append(" Lek\n");
        sb.append(txt("total")).append(": ").append(grandTotal).append(" Lek\n");

        sb.append("\n---------- ").append(txt("fiscalInvoice")).append(" ----------\n");
        sb.append("Nr. Fature: ").append(fiscalNumber).append("\n");
        sb.append("Kamarieri: ").append(loggedWaiter).append("\n");
        sb.append("Tavolina: ").append(tableNumber).append("\n");
        sb.append("Data: ").append(orderDateTime.format(formatter)).append("\n");
        sb.append(txt("subtotal")).append(": ").append(subtotal).append(" Lek\n");
        sb.append(txt("tax")).append(": ").append(taxAmount).append(" Lek\n");
        sb.append(txt("total")).append(": ").append(grandTotal).append(" Lek\n");
        sb.append("----------------------------------\n");

        area.setText(sb.toString());

        Button payBtn = new Button(txt("goPayment"));
        payBtn.setOnAction(e -> showPaymentPage(stage, sb.toString()));

        VBox box = new VBox(12, new Label(txt("invoiceTitle")), area, payBtn);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(20));

        stage.setScene(new Scene(new StackPane(box), 450, 500));
    }

    private void showPaymentPage(Stage stage, String invoiceText) {

        ToggleGroup group = new ToggleGroup();
        RadioButton cash = new RadioButton(txt("cash"));
        RadioButton card = new RadioButton(txt("card"));
        cash.setToggleGroup(group);
        card.setToggleGroup(group);

        Button finishBtn = new Button(txt("finish"));
        finishBtn.setOnAction(e -> {
            if (group.getSelectedToggle() == null) {
                new Alert(Alert.AlertType.WARNING, txt("choosePayment")).showAndWait();
                return;
            }

            printInvoice(invoiceText);
            new Alert(Alert.AlertType.INFORMATION, txt("success")).showAndWait();
            showMenuPage(stage);
        });

        VBox box = new VBox(12, cash, card, finishBtn);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(20));

        stage.setScene(new Scene(new StackPane(box), 300, 250));
    }

    private void printInvoice(String text) {
        try {
            Files.write(Paths.get("fatura_tavolina_" + tableNumber + ".txt"), text.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
