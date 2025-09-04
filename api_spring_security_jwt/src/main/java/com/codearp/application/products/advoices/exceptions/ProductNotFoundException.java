package com.codearp.application.products.advoices.exceptions;

/**
 * Spring realice un rollback de la transacción ante una excepción, debes lanzar una RuntimeException o un Error (por defecto) o,
 * si quieres que se haga rollback con una excepción "checked" (que no sean RuntimeException),
 * debes especificarla usando el atributo rollbackFor de la anotación @Transactional.
 *
 * Por ejemplo, @Transactional(rollbackFor = Exception.class) hará que la transacción se revierta ante cualquier Exception.
 *
 */
public class ProductNotFoundException extends RuntimeException{

    public ProductNotFoundException(String msg){
        super(msg);
    }

    public ProductNotFoundException(String message, Throwable cause){
        super(message,cause);
    }

    public ProductNotFoundException(Throwable cause){
        super(cause);
    }
}
