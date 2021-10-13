package opActAcct.starBankbackend.repository;

import opActAcct.starBankbackend.repository.exception.DuplicateKeyException;
import opActAcct.starBankbackend.repository.exception.KeyDoesNotExistException;

/**
 * Todos los métodos contenidos aquí pueden ser implementados por infinitas bases de datos.
 */
public interface IImplementation {

    /**
     * Método para agregar información a un archivo.
     *
     * @param objectToWrite : Objeto que se copiará en el archivo.
     * @param fileName : Nombre del Archivo al que se le agregará información.
     * @throws DuplicateKeyException: Lanza la excepción cuando la clave primaria
     * del objeto que se quiere agregar ya existe en el archivo.
     */
    void add(Object objectToWrite, String fileName) throws DuplicateKeyException;

    /**
     * Método que actualiza la información de un objeto una llave ya existente.
     *
     * @param object : Objeto actualizado.
     * @param fileName : Nombre del Archivo al que se le agregará información.
     * @throws KeyDoesNotExistException: Clave de objeto que no existe en el archivo.
     */
    void update(Object object, String fileName) throws KeyDoesNotExistException;

    /**
     * Método de método para leer información del archivo.
     *
     * @param fileName : Nombre del archivo que se leerá
     * @return Retorna el objeto leído.
     */
    Object read(String fileName) ;

}
