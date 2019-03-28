package equipo6.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CadenaActores implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Actor> lista_actores;


	//Constructor generico, inicializar lista
	//TODO gonzalo
	public CadenaActores()
	{
		this.lista_actores = new ArrayList<Actor>();
	}
	

	//comprueba si el objeto Actor
	private boolean is_user_val(Actor usuarioLogin)
	{
		return 
				(
						usuarioLogin.getpasswordPlana() != null &&
						usuarioLogin.getNombreUsuario() != null
						/*Resto de condiciones que sean necesaria para
                    que el usurio sea valido*/
						);
	}
	
	public List<Actor> getlista_actores() 
	{
		return this.lista_actores;
	}

	//retorna la longitud de la cadena

	public int length()
	{
		return this.lista_actores.size();
	}

	//Añadir nuevo actor a la cadena. Recuerda que el nuevo tiene que tener referencia
	//al ultimo de la lista.
	//Devuelve id de usuario
	//TODO gonzalo

	public int addActor(Actor nuevoActor) {

		try {
			if(nuevoActor != null) {
				this.lista_actores.add(nuevoActor);
				return nuevoActor.getId()/*id del usuario*/;
			}
			else 
			{
				throw new Exception("Error : Actor nulo");
			}
		}	
		catch (Exception errorActor) {
			System.err.println(errorActor.getMessage());
			return -1;
		}
	}


	//Método que llama al metodo logMe del ultimo Actor de la lista, y reenvia lo que le
	//devuelva, y en caso de no ser un usurario valido segun las condiciones definida en
	//is_user_val se genera una excepcion generia.

	public Actor logeaUsuario(Actor usuarioLogin) throws Exception{
		
		Actor actor_login;
		
		actor_login = null;
		
		if(!is_user_val(usuarioLogin))
		{
			throw new Exception("Error al logear usuario, usuario no valido");
		}
		
				
		for (Actor actor : this.lista_actores) {
			if(actor.actor_compare(usuarioLogin)) 
			{
				actor_login = actor;
				break;
			}
				
		}
		return actor_login;
	}


}
