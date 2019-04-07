package equipo6.model;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.MessageDigest;
import java.util.Base64;

import com.google.gson.Gson;

import equipo7.model.OrdenTrazabilidad;

public class Bloque implements Serializable{
    private String hashPrevio;
    private int tipoBloque;
    private int numBloque;
    private int codLote;
    DatosContainer datos;
	private long timeStamp;
	private  int idCadena;

    //Constructor por defecto. Asignar tal cual
    //TODO anton
    public Bloque(String hashPrevio, int tipoBloque, int numBloque, int codLote, DatosContainer datos, int idCadena){
        this.hashPrevio = hashPrevio;
        this.tipoBloque = tipoBloque;
        this.numBloque = numBloque;
        this.codLote = codLote;
        this.datos = datos;
        this.idCadena = idCadena;
    }
    
    //Getters para todos los campos
    //TODO anton
    
    public  String getHashPrevio(){
        return this.hashPrevio;
    }
    
    public  int getTipoBloque(){
        return this.tipoBloque;
    }
    
    public  int getNumBloque(){
        return this.numBloque;
    }
    
    public  int getCodLote(){
        return this.codLote;
    }
    
    public  DatosContainer getDatos(){
        return this.datos;
    }
    
    public int getIdCadena() {
		return idCadena;
	}

	public void setIdCadena(int idCadena) {
		this.idCadena = idCadena;
	}
	
	
	
	public boolean is_super_blocke() 
	{
		return this.idCadena != -1;
	}

	public String getHashCode() {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");	        
            //Applies sha256 to our input, 
			String input=this.toBLOBString();
			byte[] hash = digest.digest(input.getBytes());	        
			StringBuffer hexString = new StringBuffer(); // This will contain hash as hexidecimal
			for (int i = 0; i < hash.length && i < 45; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if(hex.length() == 1) hexString.append('0');
				hexString.append(hex);
			}
			return hexString.toString().substring(0,40);
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	
	private String toBLOBString() throws IOException {
		Gson gson;
		gson = new Gson();
		Serializable o = this/*gson.toJson((this.datos))*/;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream( baos );
        oos.writeObject( o );
        oos.close();
        return Base64.getEncoder().encodeToString(baos.toByteArray()); 
    }

  public void setTimeStamp() {
		this.timeStamp=System.currentTimeMillis();
	}

public float getTimeStamp() {
	// TODO Auto-generated method stub
	return timeStamp;
}
}