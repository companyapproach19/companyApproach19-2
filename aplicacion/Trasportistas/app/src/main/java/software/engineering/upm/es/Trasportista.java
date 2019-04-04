package software.engineering.upm.es;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Trasportista implements Parcelable {

    private String nombre;
    private String empresa;
    private String fecha_recogida;
    private String fecha_entrega;

    public Trasportista (String nombre, String empresa, String fecha_recogida, String fecha_entrega) {
        this.nombre = nombre;
        this.empresa = empresa;
        this.fecha_recogida = fecha_recogida;
        this.fecha_entrega = fecha_entrega;
    }

    public Trasportista (JSONObject trasportista) throws JSONException {
        this.nombre = trasportista.getString("nombre");
        this.empresa = trasportista.getString("empresa");
        this.fecha_recogida = trasportista.getString("fecha_recogida");
        this.fecha_entrega = trasportista.getString("fecha_entrega");
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getFecha_recogida() {
        return fecha_recogida;
    }

    public void setFecha_recogida(String fecha_recogida) {
        this.fecha_recogida = fecha_recogida;
    }

    public String getFecha_entrega() {
        return fecha_entrega;
    }

    public void setFecha_entrega(String fecha_entrega) {
        this.fecha_entrega = fecha_entrega;
    }

    protected Trasportista(Parcel in) {
        nombre = in.readString();
        empresa = in.readString();
        fecha_recogida = in.readString();
        fecha_entrega = in.readString();
    }

    public static final Creator<Trasportista> CREATOR = new Creator<Trasportista>() {
        @Override
        public Trasportista createFromParcel(Parcel in) {
            return new Trasportista(in);
        }

        @Override
        public Trasportista[] newArray(int size) {
            return new Trasportista[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nombre);
        dest.writeString(empresa);
        dest.writeString(fecha_recogida);
        dest.writeString(fecha_entrega);
    }
}
