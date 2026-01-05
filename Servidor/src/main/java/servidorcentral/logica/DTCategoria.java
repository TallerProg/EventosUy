	package servidorcentral.logica;
	
	import jakarta.xml.bind.annotation.XmlAccessType;
	import jakarta.xml.bind.annotation.XmlAccessorType;
	
	@XmlAccessorType(XmlAccessType.FIELD)
	public class DTCategoria{
		private String nombre;
		
		public DTCategoria() {
		}
	
		public DTCategoria(String nombre) {
			this.nombre = nombre;
			
		}
	
		public String getNombre() {
			return nombre;
		}
		
		public void setNombre(String nombre) {
			this.nombre = nombre;
		}
	}