package ca.ulaval.ima.tp2;

import android.os.Parcel;
import android.os.Parcelable;

public class IdentityParcelable implements Parcelable {
    public String firstName;
    public String lastName;
    public String date;
    public String sexe;
    public String prog;

    private IdentityParcelable(Parcel in) {
        date = in.readString();
        lastName = in.readString();
        firstName = in.readString();
        sexe = in.readString();
        prog = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public IdentityParcelable() {
        this.lastName = "Charpentier";
        this.firstName = "Nicolas";
        this.date = "1996-10-02";
        this.sexe = "Masculin";
        this.prog = "GEL";
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date);
        dest.writeString(lastName);
        dest.writeString(firstName);
        dest.writeString(sexe);
        dest.writeString(prog);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<IdentityParcelable> CREATOR = new Parcelable.Creator<IdentityParcelable>() {
        @Override
        public IdentityParcelable createFromParcel(Parcel in) {
            return new IdentityParcelable(in);
        }

        @Override
        public IdentityParcelable[] newArray(int size) {
            return new IdentityParcelable[size];
        }
    };
}