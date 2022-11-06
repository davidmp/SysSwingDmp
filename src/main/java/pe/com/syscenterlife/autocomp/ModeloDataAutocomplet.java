
package pe.com.syscenterlife.autocomp;

/**
 * Clase Principal para AutoComplete
 *
 * @see <br>
 * Acepta las siguientes opciones:TIPE_DISPLAY=
 * <ol><li>ID</li><li>NAME</li><li>OTHER</li>
 * </ol>
 *
 * @see <a href = "https://github.com/davidmp" />Aqui Github</a>
 * 
 */
public class ModeloDataAutocomplet{
    
    public String idx, nombreDysplay, otherData;
    public static String TIPE_DISPLAY="";//ID, NAME, OTHER

    public ModeloDataAutocomplet() {
    }
    


    public ModeloDataAutocomplet(String idx, String nombreDysplay) {        
        this.idx = idx;
        this.nombreDysplay = nombreDysplay;
    }    
   
    public ModeloDataAutocomplet( String idx, String nombreDysplay, String otherData) {        
        this.idx = idx;
        this.nombreDysplay = nombreDysplay;
        this.otherData = otherData;
    }    
    
    public String getOtherData() {
        return otherData;
    }

    public void setOtherData(String otherData) {
        this.otherData = otherData;
    }

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getNombreDysplay() {
        return nombreDysplay;
    }

    public void setNombreDysplay(String nombreDysplay) {
        this.nombreDysplay = nombreDysplay;
    }
    
    @Override
    public String toString() {
         switch (TIPE_DISPLAY) {
            case "ID": return idx;
            case "OTHER": return otherData;
            default: return nombreDysplay;
        }
    }    

}