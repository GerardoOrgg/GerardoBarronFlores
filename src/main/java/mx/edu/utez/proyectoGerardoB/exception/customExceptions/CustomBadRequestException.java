package mx.edu.utez.proyectoGerardoB.exception.customExceptions;


public class CustomBadRequestException extends RuntimeException{
    public CustomBadRequestException(String mensaje){
        super(mensaje);

    }
}
