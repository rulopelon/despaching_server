package paneluser;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import dominio.Usuario;
import server.Fachada;
import util.Colores;
import util.Fuente;

/** Panel generico el cual mostrata un horario de un profesor */
public class PnlHorario extends JPanel{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    /** atributo que contiene el usuario al que pertenece el horario */
    private Usuario usuario;
    
    /** Constructor que llama al metodo {@link initComponents} */
    public PnlHorario(Usuario usuario,int dia){
        setUsuario(usuario);
        initComponents(dia);
    }

    /** metodo para establecer el usuario
     * @param usuario contiene el usuario al que el horario pertenece
     */
    public void setUsuario(Usuario usuario){
        this.usuario = usuario;
    }

    private void initComponents(int dia){
        this.setLayout(new GridLayout(7,2,5,5));    //Matriz de 7*2 con separacion de 5 en ambos sentidos
        
        /** Matriz de botones que contendra el horario del profesor */
        JButton btnHoras[][] = new JButton[7][2];
        for(int i=0; i<7; i++){
            for(int j=0; j<2; j++){
                int hora = 8+i+(j*7);   //formula para obtener las distintas horas del horario en funcion de j e i
                btnHoras[i][j] = new JButton(hora +":00 - "+(hora + 1)+":00");
                Colores.setBGVerde(btnHoras[i][j]);
                Fuente.setFuente(btnHoras[i][j]);
                this.add(btnHoras[i][j]);
            }
        }
        //hacemos que el horario sea dinámico
        this.setHorario(btnHoras,dia);
    }
    
    /** metodo para establecer el horario del profesor de manera dinamica
     * @param btnHoras es una matriz de botones que seran de un color especifico y se les cambiara el color
     */
    private void setHorario(JButton btnHoras[][],int dia){
        String horario= Fachada.getHorario(usuario.getId()); // se consigue el horario de la base de datos
        String[] clases = horario.split(";");  // se separa en dias de la semana
        if (dia > 4) // los sabados y domingos se pintan como un lunes pues no hay clases
            dia = 0;
         //se pinta el horario en funcion del horario
        String[] horas = clases[dia].split(","); // se separa las clases del dia
        int horafinant = 0; // varible que contiene la hora anterior a la clase con la que se comprara si hay solapamiento
        int minutosfinant = 0; // variable que contiene los minutos anteriores a la clase con la que se compara si hay solapamientos
        for (int i=1; i< horas.length ;i++){ // bucle que rrecore las clases del dia
            int horaini = Integer.valueOf(String.valueOf(horas[i].charAt(0)) + String.valueOf(horas[i].charAt(1))); // variable que almacena la hora de inicio de la clase
            int minutosini = Integer.valueOf(String.valueOf(horas[i].charAt(3)) + String.valueOf(horas[i].charAt(4))); // variable que almacena los minutos de inicio de la clase
            int horafin = Integer.valueOf(String.valueOf(horas[i].charAt(6)) + String.valueOf(horas[i].charAt(7))); // variable que almacena la hora de fin de la clase
            int minutosfin = Integer.valueOf(String.valueOf(horas[i].charAt(9)) + String.valueOf(horas[i].charAt(10))); // variable que almacena los minutos de fin de la clase
            if (minutosini == 0) // cambia el boton al color que le corresponde
                if (horaini-7 > 7 )
                    Colores.setBGRojo(btnHoras[horaini -15][1]);
                else{
                    Colores.setBGRojo(btnHoras[horaini -8][0]);
                }
            else{
                if (horaini == horafinant && (minutosini == minutosfinant || minutosini == minutosfinant - 10))
                    if (horaini-7 > 7 ){
                        Colores.setBGRojo(btnHoras[horaini -15][1]);
                        if (horafin-7 > 7 ){
                            Colores.setBGAmarillo(btnHoras[horafin -15][1]);
                        }
                        else{
                            Colores.setBGAmarillo(btnHoras[horafin -8][0]);
                        }
                    }
                    else{
                        Colores.setBGRojo(btnHoras[horaini -8][0]);
                        if (horafin-7 > 7 ){
                            Colores.setBGAmarillo(btnHoras[horafin -15][1]);
                        }
                        else{
                            Colores.setBGAmarillo(btnHoras[horafin -8][0]);
                        }
                    }
                else{
                    if (horaini-7 > 7 ){
                        Colores.setBGAmarillo(btnHoras[horaini -15][1]);
                        if (horafin-7 > 7 ){
                            Colores.setBGAmarillo(btnHoras[horafin -15][1]);
                        }
                        else{
                            Colores.setBGAmarillo(btnHoras[horafin -8][0]);
                        }
                    }
                    else{
                        Colores.setBGAmarillo(btnHoras[horaini -8][0]);
                        if (horafin-7 > 7 ){
                            Colores.setBGAmarillo(btnHoras[horafin -15][1]);
                        }
                        else{
                            Colores.setBGAmarillo(btnHoras[horafin -8][0]);
                        }
                    }
                }
            }
            horafinant = horafin;
            minutosfinant = minutosfin; 
        } 
    }
}
