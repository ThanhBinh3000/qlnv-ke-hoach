package com.tcdt.qlnvkhoach.entities;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.Configurable;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.id.IdentityGenerator;
import org.hibernate.id.factory.internal.DefaultIdentifierGeneratorFactory;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Properties;

public class CustomGenerator extends IdentityGenerator implements Configurable {
    private IdentifierGenerator defaultGenerator;

    public Serializable generate(SessionImplementor session, Object object) throws HibernateException {
        Long idValue = (Long)defaultGenerator.generate(session, object);
        //idValue will be assigned your entity id
        return idValue;
    }

    public void configure(Type type, Properties params, Dialect d) throws MappingException {
        DefaultIdentifierGeneratorFactory dd = new DefaultIdentifierGeneratorFactory();
        dd.setDialect(d);
        defaultGenerator = dd.createIdentifierGenerator("sequence", type, params);
    }

    @Override
    public RequestConfig getConfig() {
        return null;
    }
}
