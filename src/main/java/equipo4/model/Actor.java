package equipo4.model;
 import java.io.Serializable;

 public class Actor implements Serializable{
     static final long serialVersionUID=10L;
     private int id;
     private String nombreUsuario;
     private String passwordPlana;
     private String passwordSalt = null;
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
         this.passwordSalt="";
     }
     
     public Actor(int id, String nombreUsuario, String passwordSalt, String email, int tipoActor){
     	this.id = id;
         this.nombreUsuario = nombreUsuario;
         this.passwordSalt = passwordSalt;
         this.email = email;
         //this.usuarioPrevio = usuarioPrevio;
         this.tipoActor = tipoActor;
     }
}
