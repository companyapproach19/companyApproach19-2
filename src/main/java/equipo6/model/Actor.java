package equipo6.model;
import java.io.Serializable;

public class Actor implements Serializable{
    static final long serialVersionUID=10L;
    private int id;
    private String nombreUsuario;
    private String passwordPlana;
    private String email = null;
    private int tipoActor = 0; //0->4 para Productor, Cooperativa, Transportista, Fabrica y Retailer


    public boolean actor_compare(Actor usuarioIntentaAcceder)
    {
        return (
                    this.nombreUsuario.equals(usuarioIntentaAcceder.nombreUsuario) &&
                    this.passwordPlana.equals(usuarioIntentaAcceder.passwordPlana)
                    /*Resto de condiciones que sean necesarias*/
                );
    }

    public Actor(){
        this.nombreUsuario="";
        this.email="";
        this.tipoActor=-1;
        this.passwordPlana="";
    }

    //Constructor utilizado por la vista para pasarnos los datos
    //TODO gonzalo

    public Actor(String nombreUsuario, String passwordPlana){
            this.nombreUsuario = nombreUsuario;
            this.passwordPlana = passwordPlana;
    }

    //Constructor usado por la BBDD para instanciar el objeto que nos va a devolver
    //TODO gonzalo

    public Actor(String nombreUsuario, String passwordPlana, String email, int tipoActor){
        this.nombreUsuario = nombreUsuario;
        this.passwordPlana = passwordPlana;
        this.email = email;
        this.tipoActor = tipoActor;
    }

    public Actor(String nombreUsuario, String passwordPlana, String email, int tipoActor,int vacio){
        this.nombreUsuario = nombreUsuario;
        this.passwordPlana = passwordPlana;
        this.email = email;
        this.tipoActor = tipoActor;
    }
    
    public Actor(int id, String nombreUsuario, String passwordPlana, String email, int tipoActor){
    	this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.passwordPlana = passwordPlana;
        this.email = email;
        this.tipoActor = tipoActor;
    }

    //GETTERS
    //TODO gonzalo

    public String getNombreUsuario()
    {
        return this.nombreUsuario;
    }

    public String getpasswordPlana()
    {
        return this.passwordPlana;
    }
    
    public String getpasswordSalt()
    {
        return this.passwordPlana;
    }

    public String getEmail()
    {
        return this.email;
    }

    public int getTipoActor()
    {
        return this.tipoActor;
    }
    
    private boolean isActorValido() {
    	boolean valido=true;
    	if(this.nombreUsuario==null || this.nombreUsuario=="")valido=false;
    	if(this.passwordPlana==null || this.passwordPlana=="")valido=false;
    	if(this.email==null || this.email=="")valido=false;
    	
    	return valido;
    }


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setPasswordPlana(String string) {
		this.passwordPlana=string;
		
	}
	
	public String toString() {
		
		return this.nombreUsuario;
		
	}
}