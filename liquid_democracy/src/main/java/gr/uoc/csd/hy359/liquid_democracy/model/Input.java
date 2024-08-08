package gr.uoc.csd.hy359.liquid_democracy.model;

public class Input {

    private final String name;
    private String value;
    private final String pattern;
    private boolean isValid = false;

    public Input(String name, String value, String pattern) {
        this.name = name;
        this.value = value;
        this.pattern = pattern;
        this.setValidity(this.value.matches(this.pattern));
    }

    public Input(String name, String pattern) {
        this.name = name;
        this.value = "";
        this.pattern = pattern;
        this.setValidity(this.value.matches(this.pattern));
    }

    public Input(String name) {
        this.name = name;
        this.value = "";
        this.pattern = ".*";
        this.isValid = true;
    }

    public String getName() {
        return this.name;
    }

    public String getValue() {
        return this.value;
    }

    public boolean getValidity() {
        return this.isValid;
    }

    public void setValue(String value) {
        this.value = value;
        this.setValidity(this.value.matches(this.pattern));
    }

    private void setValidity(boolean flag) {
        this.isValid = flag;
    }

}
