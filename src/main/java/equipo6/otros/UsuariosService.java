package equipo6.otros;

import equipo5.dao.metodosCompany;
import equipo6.model.Actor;
import equipo6.model.CadenaActores;

//Esta es la clase a la que van a llamar el resto de grupos para hacer sus
//gestiones con respecto a los usuarios
public class UsuariosService{
	
	//Temporal
//	BBDDTemporal BBDD;
//	public void init(BBDDTemporal bd) {
//		this.BBDD=bd;
//	}
	public UsuariosService() {}
	

    //Funcion para logear usuarios
    //Obtiene la cadena de BBDD, y utiliza el metodo logIn de la cadena
    //TODO anton (habla con gonzalo si necesitas entender como funciona su clase modelo)
    public Actor logUsuario(Actor usuarioIntentaLogin) throws Exception{
    	try {
			Actor actor = metodosCompany.extraerCadenaActores().logeaUsuario(usuarioIntentaLogin);
			if (actor != null) {
					System.out.println("Login de actor : "+usuarioIntentaLogin.toString()+" correcto");
					return actor;
			} else {
				throw new Exception("\nFallo al logear actor : "+usuarioIntentaLogin.getNombreUsuario());
			}
    	}catch(Exception ex) {
    		System.err.println(ex.getMessage());
    		return new Actor();
    	}
   
    }

    
}
