package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service;
import java.util.Optional;

public interface GenericService<Object> {
    boolean elementIsPresent(Object element);
    String registerElement(Object element);
    void updateElement(Object element);
    Optional<Object> findElementById(String elementId);
}