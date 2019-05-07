package software.engineering.upm.es.objetos;

import org.json.JSONException;
import org.json.JSONObject;

class Actor {

    private int id;

    private String nombreUsuario;

    private int tipoActor;


    public Actor(JSONObject pedidos) throws JSONException {

        this.id=pedidos.getInt("id");
        this.nombreUsuario=pedidos.getString("nombreUsuario");
        this.tipoActor=pedidos.getInt("tipoActor");

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public int getTipoActor() {
        return tipoActor;
    }

    public void setTipoActor(int tipoActor) {
        this.tipoActor = tipoActor;
    }
    public JSONObject toJSON() throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("id", id);
        jo.put("nombreUsuario", nombreUsuario);
        jo.put("tipoActor", tipoActor);

        return jo;
    }
}
