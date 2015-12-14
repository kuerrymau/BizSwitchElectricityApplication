/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.crimore.server.domain;

import java.util.List;
import org.crimore.server.domain.impl.ElectricityTokenRequest;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author Chayanne
 */
public class ElectricityServiceImplTest {

    static ElectricityServiceImpl dataSourceInstance = null;

    static {
        dataSourceInstance = new ElectricityServiceImpl();
    }

    public ElectricityServiceImplTest() {

    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     *
     * @throws Exception
     */
    @Test
//    @Ignore
    public void bizSwitchElectricityTokenRequest() throws Exception {

        Meter meter = createMeter();
        PayType payType = createPayType();

        List<String> tokens = ElectricityTokenRequest.getTokenAndProcessResponseMessage(1723839303, 11450, 2, meter.getMeterNumber(), payType.getName());
        for (String token : tokens) {
            dataSourceInstance.createElecTransaction(new ElecTrans(null, Integer.parseInt(token), 11450, payType, meter));
        }
    }

    /**
     * @throws java.lang.Exception
     */
    @Test
    @Ignore
    public void testCreateElecTrans() throws Exception {
        System.out.println("in createElecTrans");
        ElecTrans elecTransaction = createElecTrans();

        ElecTrans expResult = elecTransaction;
        ElecTrans result = dataSourceInstance.createElecTransaction(elecTransaction);
        System.out.println("result=" + result);
        assertEquals(expResult.getElecTransId(), result.getElecTransId());
    }

    /**
     *
     */
    @Ignore
    @Test
    public void testFindAllPayTypes() {
        System.out.println("in findAllPayTypes");
        List<PayType> result = dataSourceInstance.findAllPayTypes();
        for (PayType payType : result) {
            System.out.println("payType.name=" + payType.getName());
        }
        assertEquals(2, result.size());
    }

    /**
     *
     */
    @Ignore
    @Test
    public void testFindAllMeterNumbers() {
        System.out.println("in findAllMeterNumbers");
        List<Meter> result = dataSourceInstance.findAllMeterNumbers();
        for (Meter meter : result) {
            System.out.println("meter.number=" + meter.getMeterNumber());
        }
        assertEquals(2, result.size());
    }

    /**
     *
     */
    @Ignore
    @Test
    public void testFindAllElecTrans() {
        System.out.println("in findAllElecTrans");

        List<ElecTrans> result = dataSourceInstance.findAllElecTrans();
        assertEquals(2, result.size());
    }

    private static ElecTrans createElecTrans() {
        Meter meter = null;
        PayType payType = null;
        ElecTrans elecTrans = null;
        try {
            meter = createMeter();
            payType = createPayType();

        } catch (Exception ex) {
        }
        if (null != payType && null != meter) {

            elecTrans = new ElecTrans(null, seudoRandomRefNumber(), 5, payType, meter);
        }
        return elecTrans;
    }

    private static Meter createMeter() throws Exception {
        Meter meter = new Meter();
        meter.setAddress("new long address");
        int nextRandomNumber = seudoRandomRefNumber();
        meter.setMeterNumber(String.valueOf(nextRandomNumber));
        meter.setEnabled(1);
//        meter.setMeterId(1);

        return dataSourceInstance.createMeter(meter);
    }

    private static PayType createPayType() throws Exception {
        PayType payType = new PayType();
        payType.setDescription("new descriptions");
        payType.setName("Credit Card");
        payType.setEnabled(1);
//        payType.setPayTypeId(1);
        return dataSourceInstance.createPayType(payType);
    }

    private static int seudoRandomRefNumber() {
        return (int) (Math.random() * 1000000000);
    }
}
