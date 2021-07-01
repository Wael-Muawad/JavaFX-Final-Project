package Models;

import java.time.LocalDate;
import java.time.Month;
import javafx.scene.control.DatePicker;

public class Course implements Comparable<Course> {

    private int IntAttribute;
    private String StringAttribute;
    private double DoubleAttribute;
    private DatePicker LocalDateAttribute;

    private Course(int IntAttribute, String StringAttribute, double DoubleAttribute, DatePicker LocalDateAttribute) {
        this.IntAttribute = IntAttribute;
        this.StringAttribute = StringAttribute;
        this.DoubleAttribute = DoubleAttribute;
        this.LocalDateAttribute = LocalDateAttribute;
    }

    public static Course GetNew(int IntAttribute) {
        return new Course(IntAttribute, "", 0.0, new DatePicker(LocalDate.of(2020, Month.MAY, 15)));
    }

    public int getIntAttribute() {
        return IntAttribute;
    }

    public void setIntAttribute(int IntAttribute) {
        this.IntAttribute = IntAttribute;
    }

    public String getStringAttribute() {
        return StringAttribute;
    }

    public void setStringAttribute(String StringAttribute) {
        this.StringAttribute = StringAttribute;
    }

    public double getDoubleAttribute() {
        return DoubleAttribute;
    }

    public void setDoubleAttribute(double DoubleAttribute) {
        this.DoubleAttribute = DoubleAttribute;
    }

    public DatePicker getLocalDateAttribute() {
        return LocalDateAttribute;
    }

    public void setLocalDateAttribute(DatePicker LocalDateAttribute) {
        this.LocalDateAttribute = LocalDateAttribute;
    }

    @Override
    public String toString() {
        return "IntAttribute = " + IntAttribute;
    }

    @Override
    public int compareTo(Course o) {
        return this.StringAttribute.compareTo(o.getStringAttribute());
    }

}
