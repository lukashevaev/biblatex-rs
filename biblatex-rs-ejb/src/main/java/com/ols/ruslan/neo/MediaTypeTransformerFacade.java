package com.ols.ruslan.neo;

/**
 *  Интерфейс для EJB
 */
public interface MediaTypeTransformerFacade {
    byte[] transform(byte[] content, String encoding) throws Exception;
}
