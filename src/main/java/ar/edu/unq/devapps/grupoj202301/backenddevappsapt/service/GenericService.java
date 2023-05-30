package ar.edu.unq.devapps.grupoj202301.backenddevappsapt.service;

public interface GenericService<Object> {
    boolean elementIsPresent(Object element);
    String registerElement(Object element);
}