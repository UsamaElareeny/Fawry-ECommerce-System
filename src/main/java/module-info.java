module com.example.fawryecommercesystem {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.fawryecommercesystem to javafx.fxml;
    exports com.example.fawryecommercesystem;
}