/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.com.syscenterlife.hint.autocomp;


public class ModeloDataAutocomplet{
    
    public String idx, nombreDysplay, otherData;

    public String getOtherData() {
        return otherData;
    }

    public void setOtherData(String otherData) {
        this.otherData = otherData;
    }

    public ModeloDataAutocomplet() {
    }

    public ModeloDataAutocomplet(String idx, String nombreDysplay) {
        this.idx = idx;
        this.nombreDysplay = nombreDysplay;
    }

    @Override
    public String toString() {
        return nombreDysplay;
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
    
    

}