/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.crimore.server.domain;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.util.List;
import javax.persistence.TypedQuery;
import org.crimore.server.domain.impl.ElectricityService;

/**
 *
 * @author Chayanne
 */
public class ElectricityServiceImpl extends RemoteServiceServlet implements ElectricityService {

    private final javax.persistence.EntityManager entityManager = EntityManagerImpl.getEntityManager();

    @Override
    public ElecTrans createElecTransaction(ElecTrans elecTransaction) throws Exception {
//        ElecTrans elecTransaction = new ElecTrans(elecTransId, tokenNumber, amount, payTypeId, meterId);
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(elecTransaction);
            entityManager.getTransaction().commit();

        } catch (Exception e) {
            // TODO add error message  
            entityManager.getTransaction().rollback();
            return null;
        }
        return elecTransaction;
    }
    
    public Meter createMeter(Meter meter) throws Exception {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(meter);
            entityManager.getTransaction().commit();

        } catch (Exception e) {
            // TODO add error message  
            entityManager.getTransaction().rollback();
            return null;
        }
        return meter;
    }
    
    public PayType createPayType(PayType payType) throws Exception {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(payType);
            entityManager.getTransaction().commit();

        } catch (Exception e) {
            // TODO add error message  
            entityManager.getTransaction().rollback();
            return null;
        }
        return payType;
    }

    @Override
    public List<PayType> findAllPayTypes() {
        TypedQuery<PayType> query = entityManager.createNamedQuery("PayType.findAll", PayType.class);
        return java.util.Collections.unmodifiableList((List<PayType>) query.getResultList());
    }

    @Override
    public List<Meter> findAllMeterNumbers() {
        TypedQuery<Meter> query = entityManager.createNamedQuery("Meter.findAll", Meter.class);
        List<Meter> resultList = query.getResultList();
        return java.util.Collections.unmodifiableList((List<Meter>) resultList);
    }

    @Override
    public List<ElecTrans> findAllElecTrans(){         
        TypedQuery<ElecTrans> query = entityManager.createNamedQuery("ElecTrans.findAll", ElecTrans.class);
        return java.util.Collections.unmodifiableList((List<ElecTrans>) query.getResultList());
    }
}
