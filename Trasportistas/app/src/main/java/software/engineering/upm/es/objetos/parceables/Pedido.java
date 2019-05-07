package software.engineering.upm.es.objetos.parceables;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by MATRIX on 31/10/16.
 */

public class Pedido implements Parcelable {


    private Trasportista trasportista;

    private int id;

    private Boolean firmadoRecogida;

    private Boolean firmadoEntrega;

    private Productos productos;

    public Pedido (Trasportista trasportista, int id, boolean firmadoRecogida, boolean firmadoEntrega, Productos productos) {
        this.trasportista = trasportista;
        this.id = id;
        this.firmadoRecogida = firmadoRecogida;
        this.firmadoEntrega = firmadoEntrega;
        this.productos = productos;
    }

    public Pedido (int id){
        this.id = id;
        this.trasportista = new Trasportista("Pepito", "Software", "","");
        this.firmadoEntrega = false;
        this.firmadoRecogida = false;
        this.productos = new Productos(3,3,3,3,3,3,3,3,3,3,4);
    }

    public Pedido (JSONObject pedidos) throws JSONException {

        this.id = pedidos.getInt("id");
        this.firmadoEntrega = pedidos.getBoolean("firmadoEntrega");
        this.firmadoRecogida = pedidos.getBoolean("firmadoRecogida");

        this.productos = new Productos(new JSONObject(pedidos.getString("productos")));

        this.trasportista = new Trasportista(new JSONObject(pedidos.getString("trasportista")));

    }

    public Trasportista getTrasportista() {
        return trasportista;
    }

    public void setTrasportista(Trasportista trasportista) {
        this.trasportista = trasportista;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boolean getFirmadoRecogida() {
        return firmadoRecogida;
    }

    public void setFirmadoRecogida(Boolean firmadoRecogida) {
        this.firmadoRecogida = firmadoRecogida;
    }

    public Boolean getFirmadoEntrega() {
        return firmadoEntrega;
    }

    public void setFirmadoEntrega(Boolean firmadoEntrega) {
        this.firmadoEntrega = firmadoEntrega;
    }

    public Productos getProductos() {
        return productos;
    }

    public void setProductos(Productos productos) {
        this.productos = productos;
    }

    protected Pedido(Parcel in) {
        id = in.readInt();
        byte tmpFirmadoRecogida = in.readByte();
        firmadoRecogida = tmpFirmadoRecogida == 0 ? null : tmpFirmadoRecogida == 1;
        byte tmpFirmadoEntrega = in.readByte();
        firmadoEntrega = tmpFirmadoEntrega == 0 ? null : tmpFirmadoEntrega == 1;
    }

    public static final Creator<Pedido> CREATOR = new Creator<Pedido>() {
        @Override
        public Pedido createFromParcel(Parcel in) {
            return new Pedido(in);
        }

        @Override
        public Pedido[] newArray(int size) {
            return new Pedido[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeByte((byte) (firmadoRecogida == null ? 0 : firmadoRecogida ? 1 : 2));
        dest.writeByte((byte) (firmadoEntrega == null ? 0 : firmadoEntrega ? 1 : 2));
    }
}
