package software.engineering.upm.es.objetos;

import org.json.JSONException;
import org.json.JSONObject;

import software.engineering.upm.es.objetos.parceables.Productos;
import software.engineering.upm.es.objetos.parceables.Trasportista;

public class PedidoRecJAVA {

    private int id;

    private Actor actorOrigen;

    private Actor actorDestino;

    private Boolean necesitaTransportista;

    private Productos productos;

    private String mensaje;

    private int estado;

    private ImagenByte firmaRecogida;

    private OrigenOrdenes origenOrdenes;

    private int idPadre;

    private int idHijo;

    private Trasportista transportista;

    public PedidoRecJAVA(JSONObject pedidos) throws JSONException {

        this.id = pedidos.getInt("id");
        this.actorOrigen = new Actor(pedidos.getJSONObject("actorOrigen"));
        this.actorDestino = new Actor(pedidos.getJSONObject("actorDestino"));
        this.necesitaTransportista = pedidos.getBoolean("necesitaTransportista");
        this.productos = new Productos(new JSONObject(pedidos.getString("productos")));
        this.mensaje=pedidos.getString("mensaje");
        this.estado=pedidos.getInt("estado");
        this.origenOrdenes = origenOrdenes;

        this.firmaRecogida=firmaRecogida;
        this.idHijo=pedidos.getInt("idHijo");
        this.idPadre=pedidos.getInt("idPadre");
        this.transportista=transportista;



    }

    public void setTransportista(Trasportista transportista) {
        this.transportista = transportista;
    }

    public void setFirmaRecogida(ImagenByte cadenaImagen){
        this.firmaRecogida=cadenaImagen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Actor getActorOrigen() {
        return actorOrigen;
    }

    public void setActorOrigen(Actor actorOrigen) {
        this.actorOrigen = actorOrigen;
    }

    public Actor getActorDestino() {
        return actorDestino;
    }

    public void setActorDestino(Actor actorDestino) {
        this.actorDestino = actorDestino;
    }

    public Boolean getNecesitaTransportista() {
        return necesitaTransportista;
    }

    public void setNecesitaTransportista(Boolean necesitaTransportista) {
        this.necesitaTransportista = necesitaTransportista;
    }

    public Productos getProductos() {
        return productos;
    }

    public void setProductos(Productos productos) {
        this.productos = productos;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public ImagenByte getFirmaRecogida() {
        return firmaRecogida;
    }

    public int getIdPadre() {
        return idPadre;
    }

    public void setIdPadre(int idPadre) {
        this.idPadre = idPadre;
    }

    public int getIdHijo() {
        return idHijo;
    }

    public void setIdHijo(int idHijo) {
        this.idHijo = idHijo;
    }

    public Trasportista getTransportista() {
        return transportista;
    }

    @Override
    public String toString() {
        return "PedidoRecJAVA{}";
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("id", id);
        jo.put("actorOrigen", actorOrigen.toJSON());
         jo.put("actorDestino", actorDestino.toJSON());
        jo.put("necesitaTransportista", necesitaTransportista);

        jo.put("productos", productos);
        jo.put("mensaje", mensaje);
        jo.put("estado", estado);
        jo.put("firmaRecogida", firmaRecogida);
        jo.put("origenOrdenes", origenOrdenes.toJSON());
        jo.put("idPadre", idPadre);
        jo.put("idHijo", idHijo);
        jo.put("Transportista", transportista);

        return jo;
    }


}
