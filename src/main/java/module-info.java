module org {
    requires javafx.controls;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;
    opens org to com.fasterxml.jackson.databind;
    exports org;
}