package software.engineering.upm.es.objetos.parceables;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Productos implements Parcelable {

    private int cant_malta_palida;
    private int cant_malta_munich;
    private int cant_malta_negra;
    private int cant_malta_crystal;
    private int cant_malta_chocolate;
    private int cant_malta_caramelo;
    private int cant_malta_pilsner;
    private int cant_cebada_tostada;
    private int cant_lupulo_centenial;
    private int cant_lupulo_perle;
    private int cant_lupulo_tettnanger;
    private int cant_levadura_lager;
    private int cant_levadura_ale;
    private int cant_lotes_stout;
    private int cant_lotes_pilsner;
    private final String[] cervezas = new String[]{"Malta Pálida", "Malta Munich", "Malta Negra",
                                    "Malta Crystal", "Malta Chocolate", "Malta Caramelo",
                                    "Cebada", "Cebada Tostada", "Lupulo Centenial", "Cajas Stout", "Cajas Bisner"};

    public Productos(int cant_malta_base_palida,int cant_malta_munich,int malta_negra,int cant_malta_crystal,
                     int cant_malta_chocolate,int cant_malta_caramelo,int cant_malta_pilsner,int cant_cebada_tostada,
                     int cant_lupulo_centennial, int cant_lupulo_perle, int cant_lupulo_tettnanger,
                     int cant_levadura_lager,int cant_levadura_ale,int cant_lotes_stout,int lotes_pilsner) {

        this.cant_malta_palida = cant_malta_base_palida;
        this.cant_malta_munich = cant_malta_munich;
        this.cant_malta_negra = malta_negra;
        this.cant_malta_crystal = cant_malta_crystal;
        this.cant_malta_chocolate = cant_malta_chocolate;
        this.cant_malta_caramelo = cant_malta_caramelo;
        this.cant_malta_pilsner = cant_malta_pilsner;
        this.cant_cebada_tostada = cant_cebada_tostada;
        this.cant_lupulo_centenial = cant_lupulo_centennial;
        this.cant_lupulo_perle = cant_lupulo_perle;
        this.cant_lupulo_tettnanger = cant_lupulo_tettnanger;
        this.cant_levadura_lager = cant_levadura_lager;
        this.cant_levadura_ale = cant_levadura_ale;
        this.cant_lotes_stout = cant_lotes_stout;
        this.cant_lotes_pilsner = lotes_pilsner;
    }

    public Productos (JSONObject jsonObject) throws JSONException {
        cant_malta_palida = jsonObject.getInt("cant_malta_palida");
        cant_malta_munich = jsonObject.getInt("cant_malta_munich");
        cant_malta_negra = jsonObject.getInt("cant_malta_negra");
        cant_malta_crystal = jsonObject.getInt("cant_malta_crystal");
        cant_malta_chocolate = jsonObject.getInt("cant_malta_chocolate");
        cant_malta_caramelo = jsonObject.getInt("cant_malta_caramelo");
        //cant_cebada = jsonObject.getInt("cant_cebada");
        cant_cebada_tostada = jsonObject.getInt("cant_cebada_tostada");
        cant_lupulo_centenial = jsonObject.getInt("cant_lupulo_centenial");
        //cant_cajas_stout = jsonObject.getInt("cant_cajas_stout");
        //cant_cajas_bisner = jsonObject.getInt("cant_cajas_bisner");
    }

    public int getCant_malta_palida() {
        return cant_malta_palida;
    }

    public void setCant_malta_palida(int cant_malta_palida) {
        this.cant_malta_palida = cant_malta_palida;
    }

    public int getCant_malta_munich() {
        return cant_malta_munich;
    }

    public void setCant_malta_munich(int cant_malta_munich) {
        this.cant_malta_munich = cant_malta_munich;
    }

    public int getCant_malta_negra() {
        return cant_malta_negra;
    }

    public void setCant_malta_negra(int cant_malta_negra) {
        this.cant_malta_negra = cant_malta_negra;
    }

    public int getCant_malta_crystal() {
        return cant_malta_crystal;
    }

    public void setCant_malta_crystal(int cant_malta_crystal) {
        this.cant_malta_crystal = cant_malta_crystal;
    }

    public int getCant_malta_chocolate() {
        return cant_malta_chocolate;
    }

    public void setCant_malta_chocolate(int cant_malta_chocolate) {
        this.cant_malta_chocolate = cant_malta_chocolate;
    }

    public int getCant_malta_caramelo() {
        return cant_malta_caramelo;
    }

    public void setCant_malta_caramelo(int cant_malta_caramelo) {
        this.cant_malta_caramelo = cant_malta_caramelo;
    }

    public int getCant_malta_pilsner(){return cant_malta_pilsner;}

    public int getCant_cebada_tostada() {
        return cant_cebada_tostada;
    }

    public void setCant_cebada_tostada(int cant_cebada_tostada) {
        this.cant_cebada_tostada = cant_cebada_tostada;
    }

    public int getCant_lupulo_centenial() {
        return cant_lupulo_centenial;
    }

    public void setCant_lupulo_centenial(int cant_lupulo_centenial) {
        this.cant_lupulo_centenial = cant_lupulo_centenial;
    }

    public int getCant_lupulo_perle(){return cant_lupulo_perle;}

    public int getCant_lupulo_tettnanger(){return  cant_lupulo_tettnanger;}

    public int getCant_levadura_lager() {return  cant_levadura_lager;}

    public int getCant_levadura_ale() {return cant_levadura_ale;}


    public int getCant_lotes_stout() {
        return cant_lotes_stout;
    }

    public void setCant_lotes_stout(int cant_cajas_stout) {
        this.cant_lotes_stout = cant_cajas_stout;
    }

    public int getCant_cajas_bisner() {
        return cant_lotes_pilsner;
    }

    public void setCant_cajas_bisner(int cant_cajas_bisner) {
        this.cant_lotes_pilsner = cant_cajas_bisner;
    }

    public String[] getCervezas () {
        return cervezas;
    }

    protected Productos(Parcel in) {
        cant_malta_palida = in.readInt();
        cant_malta_munich = in.readInt();
        cant_malta_negra = in.readInt();
        cant_malta_crystal = in.readInt();
        cant_malta_chocolate = in.readInt();
        cant_malta_caramelo = in.readInt();
        //cant_cebada = in.readInt();
        cant_cebada_tostada = in.readInt();
        cant_lupulo_centenial = in.readInt();
        //cant_cajas_stout = in.readInt();
        //cant_cajas_bisner = in.readInt();
    }

    public static final Creator<Productos> CREATOR = new Creator<Productos>() {
        @Override
        public Productos createFromParcel(Parcel in) {
            return new Productos(in);
        }

        @Override
        public Productos[] newArray(int size) {
            return new Productos[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(cant_malta_palida);
        dest.writeInt(cant_malta_munich);
        dest.writeInt(cant_malta_negra);
        dest.writeInt(cant_malta_crystal);
        dest.writeInt(cant_malta_chocolate);
        dest.writeInt(cant_malta_caramelo);
        //dest.writeInt(cant_cebada);
        dest.writeInt(cant_cebada_tostada);
        dest.writeInt(cant_lupulo_centenial);
        //dest.writeInt(cant_cajas_stout);
        //dest.writeInt(cant_cajas_bisner);
    }
}
