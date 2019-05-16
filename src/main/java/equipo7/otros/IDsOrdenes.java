package equipo7.otros;

public class IDsOrdenes {

    private int idOrdenAResponder;

    private int idOrdenRespuesta;

    public IDsOrdenes(int idOrdenAResponder, int idOrdenRespuesta) {
        this.idOrdenAResponder = idOrdenAResponder;
        this.idOrdenRespuesta = idOrdenRespuesta;
    }

    public int getIdOrdenAResponder() {
        return idOrdenAResponder;
    }

    public int getIdOrdenRespuesta() {
        return idOrdenRespuesta;
    }
}