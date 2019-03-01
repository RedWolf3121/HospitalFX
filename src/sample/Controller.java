package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class Controller {

    File csvPath = new File("");
    private boolean listaCargada = false;
    private Hospital hospital = new Hospital();
    String ruta;
    private List<Pacient> pacientList = new ArrayList<>();

    String dniFilter;
    String nameFilter;
    String lastNameFilter;
    String ageFilter;
    double weightFilter;
    int heightFilter;

    @FXML
    public Button loadFile;
    public TextField path;
    public Button search;
    public RadioButton dni, name, lastName, age, weight, height;
    public TextField filter;

    @FXML
    TableView<Pacient> tablePacients;
    TableView<Pacient> filterView;
    private String csvFile = null;
    private List<Pacient> p = new ArrayList<>();
    private ObservableList<Pacient> data;


    @FXML

    public void initialize() {
        data = FXCollections.observableArrayList();
        if(csvFile == null) {
            loadFile.setText("Cargar CSV");
        }else {
            setTableView();
        }
    }

    public void CSV(ActionEvent event) {
        Node node = (Node) event.getSource();

        if(csvFile == null) {
            FileChooser fc = new FileChooser();
            fc.setTitle("Select csv file");
            File file = fc.showOpenDialog(null);
            csvFile = file.getAbsolutePath();
            setTableView();
            loadFile.setText("Loaded");
            ruta = csvPath.getAbsolutePath();
            path.setText(ruta);
        }else {
            loadFile.setText("File is loaded");
        }

    }
    private void setTableView() {
        TableColumn DNI = new TableColumn("DNI");
        TableColumn Nom = new TableColumn("Nom");
        TableColumn Cognoms = new TableColumn("Cognoms");
        TableColumn DataNaix = new TableColumn("Data de Naixament");
        TableColumn Genre = new TableColumn("Gènere");
        TableColumn Telefon = new TableColumn("Telèfon");
        TableColumn pes = new TableColumn("Pes");
        TableColumn Alçada = new TableColumn("Alçada");

        // COMPTE!!!! les propietats han de tenir getters i setters
        DNI.setCellValueFactory(new PropertyValueFactory<Pacient, String>("DNI"));
        Nom.setCellValueFactory(new PropertyValueFactory<Pacient, String>("Nom"));
        Cognoms.setCellValueFactory(new PropertyValueFactory<Pacient, String>("Cognoms"));
        DataNaix.setCellValueFactory(new PropertyValueFactory<Pacient, String>("DataNaixament"));
        Genre.setCellValueFactory(new PropertyValueFactory<Pacient, String>("genere"));
        Telefon.setCellValueFactory(new PropertyValueFactory<Pacient, String>("Telefon"));
        pes.setCellValueFactory(new PropertyValueFactory<Pacient, Float>("Pes"));
        Alçada.setCellValueFactory(new PropertyValueFactory<Pacient, Integer>("Alçada"));

        tablePacients.getColumns().addAll(DNI, Nom, Cognoms, DataNaix, Genre, Telefon, pes, Alçada);


        //data.add(new Pacient("111", "n", "co", LocalDate.of(2000, 12, 12), Persona.Genere.HOME, "55555", 5.4f, 100));
        loadData();
        data.addAll(p);
        tablePacients.setItems(data);

    }
    private void loadData() {
        Hospital hospital = new Hospital();
        p.addAll(hospital.loadPacients(csvFile));
    }

    public void bfilter() {
        if (dni.isSelected()) {
            dniFilter = filter.getText();
        } else if (name.isSelected()) {
            nameFilter = filter.getText();
        } else if (lastName.isSelected()) {
            lastNameFilter = filter.getText();
        } else if (age.isSelected()) {
            ageFilter = filter.getText();
        } else if (weight.isSelected()) {
            weightFilter = Double.parseDouble(filter.getText());
        } else if (height.isSelected()) {
            heightFilter = Integer.parseInt(filter.getText());
        }
    }

    public void searchtext(KeyEvent keyEvent) {
        data.clear();

        List<Pacient> pacients = pacientList.stream()
                .filter(pacient -> pacient.getNom().contains(nameFilter))
                .filter(pacient -> pacient.getCognoms().contains(lastNameFilter))
                .filter(pacient -> pacient.getDNI().contains(dniFilter))
                .collect(Collectors.toList());
        data.addAll(pacients);
        filterView.setItems(data);
    }

}
