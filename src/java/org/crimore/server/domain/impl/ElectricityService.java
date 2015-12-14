/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.crimore.server.domain.impl;

import com.google.gwt.user.client.rpc.RemoteService;
import java.util.List;
import org.crimore.server.domain.ElecTrans;
import org.crimore.server.domain.Meter;
import org.crimore.server.domain.PayType;

/**
 *
 * @author Chayanne
 */
public interface ElectricityService extends RemoteService{

    public ElecTrans createElecTransaction(ElecTrans elecTrans) throws Exception ;   

    public List<ElecTrans> findAllElecTrans();

    public List<PayType> findAllPayTypes();
    
    public List<Meter> findAllMeterNumbers();
}
