/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.crimore.server.domain.impl;

/**
 *
 * @author Chayanne
 */
public interface ElectricityServiceAsync {

   	void createElecTransaction(org.crimore.server.domain.ElecTrans elecTrans, com.google.gwt.user.client.rpc.AsyncCallback<org.crimore.server.domain.ElecTrans> arg2);
	void findAllElecTrans(com.google.gwt.user.client.rpc.AsyncCallback<java.util.List<org.crimore.server.domain.ElecTrans>> arg1);
	void findAllMeterNumbers(com.google.gwt.user.client.rpc.AsyncCallback<java.util.List<org.crimore.server.domain.Meter>> arg1);
	void findAllPayTypes(com.google.gwt.user.client.rpc.AsyncCallback<java.util.List<org.crimore.server.domain.PayType>> arg1);
}
