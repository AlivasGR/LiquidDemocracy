package gr.uoc.csd.hy359.liquid_democracy.model;

import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author Alivas
 */
public class Validator {

    public Input uid;
    public Input pass1;
    public Input pass2;
    public Input fnm;
    public Input lnm;
    public Input eml;
    public Input sex;
    public Input ctr;
    public Input city;
    public Input addr;
    public Input dob;
    public Input ocp;
    public Input intr;
    public Input gen;
    public ArrayList<Input> invalidFields;

    public Validator() {
        this.uid = new Input("username", "[A-Za-z]{8,}");
        this.pass1 = new Input("password", "(?=^.{8,10}$)(?=.*[^a-zA-Z0-9])(?=.*[a-zA-Z])(?=.*[0-9]).*$");
        this.pass2 = new Input("password2", "(?=^.{8,10}$)(?=.*[^a-zA-Z0-9])(?=.*[a-zA-Z])(?=.*[0-9]).*$");
        this.fnm = new Input("firstname", "[\\w]{0,20}");
        this.lnm = new Input("lastname", "[\\w]{4,20}");
        this.eml = new Input("email", ".+@.+(\\..+)+");
        this.sex = new Input("gender");
        this.ctr = new Input("country");
        this.addr = new Input("address");
        this.city = new Input("city", "[\\w]{2,20}");
        this.dob = new Input("bday", "([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))");
        this.ocp = new Input("occupation", "[\\w]{2,20}");
        this.intr = new Input("interests", ".{0,100}");
        this.gen = new Input("general", ".{0,100}");
        this.invalidFields = new ArrayList<>();
    }

    public boolean getValidity() {
        boolean flag = true;
        this.invalidFields = new ArrayList<>();
        if (!this.uid.getValidity()) {
            flag = false;
            this.invalidFields.add(this.uid);
        }
        if (!this.pass1.getValidity()) {
            flag = false;
            this.invalidFields.add(this.pass1);
        }
        if (!this.pass2.getValidity()) {
            flag = false;
            this.invalidFields.add(this.pass2);
        }
        if (!this.pass1.getValue().equals(this.pass2.getValue())) {
            flag = false;
            this.invalidFields.add(new Input("pm"));
        }
        if (!this.fnm.getValidity()) {
            flag = false;
            this.invalidFields.add(this.fnm);
        }
        if (!this.lnm.getValidity()) {
            flag = false;
            this.invalidFields.add(this.lnm);
        }
        if (!this.eml.getValidity()) {
            flag = false;
            this.invalidFields.add(this.eml);
        }
        if (!this.addr.getValidity()) {
            flag = false;
            this.invalidFields.add(this.addr);
        }
        if (!this.city.getValidity()) {
            flag = false;
            this.invalidFields.add(this.city);
        }
        if (!this.intr.getValidity()) {
            flag = false;
            this.invalidFields.add(this.intr);
        }
        if (!this.gen.getValidity()) {
            flag = false;
            this.invalidFields.add(this.gen);
        }
        try {
            if (!this.dob.getValidity() || !validateOver18(dob.getValue())) {
                flag = false;
                this.invalidFields.add(this.dob);
            }
        } catch (IllegalArgumentException e) {
            flag = false;
            this.invalidFields.add(this.dob);
        }
        return flag;
    }

    private boolean validateOver18(String date) {
        int years = Integer.parseInt(date.substring(0, 4));
        int months = Integer.parseInt(date.substring(5, 7));
        int days = Integer.parseInt(date.substring(8, 10));
        Calendar today = Calendar.getInstance();
        Calendar birthDate = Calendar.getInstance();
        birthDate.set(years, months - 1, days, 0, 0);
        if (birthDate.after(today)) {
            throw new IllegalArgumentException("You don't exist yet");
        }
        int todayYear = today.get(Calendar.YEAR);
        int birthDateYear = birthDate.get(Calendar.YEAR);
        int todayDayOfYear = today.get(Calendar.DAY_OF_YEAR);
        int birthDateDayOfYear = birthDate.get(Calendar.DAY_OF_YEAR);
        int todayMonth = today.get(Calendar.MONTH);
        int birthDateMonth = birthDate.get(Calendar.MONTH);
        int todayDayOfMonth = today.get(Calendar.DAY_OF_MONTH);
        int birthDateDayOfMonth = birthDate.get(Calendar.DAY_OF_MONTH);
        int age = todayYear - birthDateYear;

        // If birth date is greater than todays date (after 2 days adjustment of leap year) then decrement age one year
        if ((birthDateDayOfYear - todayDayOfYear > 3) || (birthDateMonth > todayMonth)) {
            age--;

            // If birth date and todays date are of same month and birth day of month is greater than todays day of month then decrement age
        } else if ((birthDateMonth == todayMonth) && (birthDateDayOfMonth > todayDayOfMonth)) {
            age--;
        }
        return age >= 18;
    }

}
