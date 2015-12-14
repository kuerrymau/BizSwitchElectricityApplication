/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.crimore.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import java.util.ArrayList;
import java.util.List;
import org.crimore.server.domain.impl.ElectricityServiceAsync;
import org.crimore.server.domain.ElecTrans;
import org.crimore.server.domain.Meter;
import org.crimore.server.domain.PayType;
import org.crimore.server.domain.impl.ElectricityService;
import org.crimore.server.domain.impl.ElectricityTokenRequest;

/**
 * Main entry point.
 *
 * @author Chayanne
 */
public class MainEntryPoint implements EntryPoint {

    private Meter myMeter = null;
    private PayType payType = null;

    private Grid tableOfElecTransactions = new Grid();
    final Grid mainGrid = new Grid(6, 2);
    private final ElectricityTopupMessages electricityTopupMessages = (ElectricityTopupMessages) GWT.create(ElectricityTopupMessages.class);

    private final ElectricityServiceAsync electricityService = (ElectricityServiceAsync) GWT.create(ElectricityService.class);
    boolean myBoo = false;

    /**
     * Creates a new instance of MainEntryPoint
     */
    public MainEntryPoint() {
    }

    /**
     * The entry point method, called automatically by loading a module that
     * declares an implementing class as an entry-point
     */
    @Override
    public void onModuleLoad() {
        String moduleBaseURL = GWT.getModuleBaseURL();
        moduleBaseURL = moduleBaseURL.substring(0, moduleBaseURL.indexOf("org.crimore.Main") - 1); // remove unwanted stuff from URL
        String newURL = moduleBaseURL + "/";

        final ServiceDefTarget electricityServiceURL = (ServiceDefTarget) electricityService;
        electricityServiceURL.setServiceEntryPoint(newURL);

        createMainPanel();
    }
    
    /**
     * 
     */
    private void createMainPanel() {

        final Label meterNumberListLabel = new Label(electricityTopupMessages.meterNumberListLabel());
        String bizSwitch_Electricity_Portal = "BizSwitch Electricity Portal";
        final Label mainLabel = new Label(bizSwitch_Electricity_Portal);
        mainLabel.addStyleName("h1_css");
        final ListBox meterNumberListBox = new ListBox();

        final Label paymentTypeListLabel = new Label(electricityTopupMessages.paymentTypeListLabel());
        final ListBox paymentTypeListBox = new ListBox();

        final Label amountTextBoxLabel = new Label(electricityTopupMessages.amountTextLabel());
        final TextBox amountTextBox = new TextBox();
 
        final Label numberOfTokensLabel = new Label(electricityTopupMessages.numberOfTokensLabel());
        final TextBox numberOfTokensTextBox = new TextBox();

        final Button submitButton = new Button(electricityTopupMessages.submitButton());

        Button  previousTransactions = new Button (electricityTopupMessages.previousTransactions()); // use hyperlink?

        final List<List<Meter>> listOfMeterTypes = findAllMeterTypes();
        if (listOfMeterTypes.size() > 0) {
            List<Meter> meters = listOfMeterTypes.get(0);

            if (meters != null && meters.size() > 0) {
                for (Meter meter : meters) {
                    meterNumberListBox.addItem(meter.getMeterNumber());
                }
            }
        }  
        
        final List<List<PayType>> listOfPaymentTypes = findAllPaymentTypes();
        if (listOfPaymentTypes.size() > 0) {
            List<PayType> payments = listOfPaymentTypes.get(0);
            if (payments != null && payments.size() > 0) {
                for (PayType myPayType : payments) {
                    paymentTypeListBox.addItem(myPayType.getName());
                }
            }
        } 

        mainGrid.setWidget(0, 0, mainLabel);
        
        mainGrid.setWidget(1, 0, meterNumberListLabel);
        mainGrid.setWidget(1, 1, meterNumberListBox);
        mainGrid.setWidget(2, 0, paymentTypeListLabel);
        mainGrid.setWidget(2, 1, paymentTypeListBox);

        mainGrid.setWidget(3, 0, amountTextBoxLabel);
        mainGrid.setWidget(3, 1, amountTextBox);
        mainGrid.setWidget(4, 0, numberOfTokensLabel);
        mainGrid.setWidget(4, 1, numberOfTokensTextBox);

        mainGrid.setWidget(5, 0, submitButton);
        mainGrid.setWidget(5, 1, previousTransactions);
        mainGrid.addStyleName("main_grid_css");

        RootPanel.get().add(mainGrid);

        submitButton.sinkEvents(Event.ONCLICK);
        previousTransactions.sinkEvents(Event.ONCLICK);

        submitButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                String amount = amountTextBox.getText();
                String numberOfTokens = numberOfTokensTextBox.getText();
                String regex = "\\d+";
                String payTypeItemText = paymentTypeListBox.getItemText(paymentTypeListBox.getSelectedIndex());
                String meterItemText = meterNumberListBox.getItemText(meterNumberListBox.getSelectedIndex());
                
                if (numberOfTokens.isEmpty()) { 
                    Window.alert(electricityTopupMessages.tokensEmptyPrompt());
                } else if (!numberOfTokens.matches(regex)) {
                    Window.alert(electricityTopupMessages.tokensInvalidNumberPrompt());
                }
                if (amount.isEmpty()) {
                    Window.alert(electricityTopupMessages.amountEmptyPrompt());
                } else if (!amount.matches(regex)) {
                    Window.alert(electricityTopupMessages.amountinvalidNumberPrompt());
                }
                getPayTypeFromList(payTypeItemText);
                getMeterFromList(meterItemText);
                
                List<String> electricityTokens = ElectricityTokenRequest.getTokenAndProcessResponseMessage(seudoRandomRefNumber(), Integer.parseInt(amount), Integer.parseInt(numberOfTokens), myMeter.getMeterNumber(), payType.getName());
                for (String electricityToken : electricityTokens) {
                    persistElectricityTransaction(new ElecTrans(null, Integer.parseInt(electricityToken), Integer.parseInt(amount), payType, myMeter));
                }
            }

            private void getMeterFromList(String meterItemText) { // get meter from dropdown meter name
                if (listOfMeterTypes.size() > 0) {
                    List<Meter> meters = listOfMeterTypes.get(0);
                    if (meters != null && meters.size() > 0) {
                        for (Meter meter : meters) {
                            if (meter.getMeterNumber().equals(meterItemText)) {
                                myMeter = meter;
                                break;
                            }
                        }
                    }
                }
            }

            private void getPayTypeFromList(String payTypeItemText) { // get payType from dropdown pay type
                if (listOfPaymentTypes.size() > 0) {
                    
                    List<PayType> payments = listOfPaymentTypes.get(0);
                    if (payments != null && payments.size() > 0) {
                        for (PayType newPayType : payments) {
                            if (newPayType.getName().equals(payTypeItemText)) {
                                payType = newPayType;
                                break;
                            }
                        }
                    }
                }
            }
        });

        previousTransactions.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                List<List<ElecTrans>> elecTransactions = findAllElecTransactions();
                List<ElecTrans> trans = null;
                if (elecTransactions != null && elecTransactions.size() > 0) {
                    trans = elecTransactions.get(0);
//                }
                    tableOfElecTransactions = createTableOfElecTransactions(trans);
                    if (null != tableOfElecTransactions) {
                        tableOfElecTransactions.setVisible(true);
                        RootPanel.get().add(tableOfElecTransactions); // TODO should not add grid to rootpanel, append to existing grid
                    }
                } 
            }
        });
    }

    /**
     * 
     * @return 
     * @comment util method to generate ref numbers
     */
    private int seudoRandomRefNumber() {
        return (int) (Math.random() * 1000000000);
    }

    /**
     * 
     * @param elecTranst 
     */
    private void persistElectricityTransaction(ElecTrans elecTranst) { // and refresh page
        electricityService.createElecTransaction(elecTranst, new AsyncCallback() {
            @Override
            public void onSuccess(Object result) {
                mainGrid.clear(); // TODO, how to reload page
                createMainPanel();
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
    }
    
    /**
     * 
     * @param listOfElectTransactions
     * @return 
     * TODO find out how to append grid cells to existing grid 
     */
    private Grid createTableOfElecTransactions(List<ElecTrans> listOfElectTransactions) {
        tableOfElecTransactions.clear(true);
        if (listOfElectTransactions != null && listOfElectTransactions.size() > 0) {
            tableOfElecTransactions = new Grid(listOfElectTransactions.size(), 5);
            
            Label trxNoLabel = new Label(electricityTopupMessages.transactionNumberLabel());
            Label tokenNumberLabel = new Label(electricityTopupMessages.tokenNumberLabel());
            Label amountLabel = new Label(electricityTopupMessages.totalAmountLabel());
            Label paymentLabel = new Label(electricityTopupMessages.paymentTypeLabel());
            Label meterNoLabel = new Label(electricityTopupMessages.meterNumberLabel());

//            trxNoLabel.addStyleName("transactions");
//            noOfTokensLabel.addStyleName("transactions");
//            amountLabel.addStyleName("transactions");
//            paymentLabel.addStyleName("transactions");
//            meterNoLabel.addStyleName("transactions");
            
            tableOfElecTransactions.setWidget(0, 0, trxNoLabel);
            tableOfElecTransactions.setWidget(0, 1, tokenNumberLabel);
            tableOfElecTransactions.setWidget(0, 2, amountLabel);
            tableOfElecTransactions.setWidget(0, 3, paymentLabel);
            tableOfElecTransactions.setWidget(0, 4, meterNoLabel);
            
            tableOfElecTransactions.addStyleName("transactions_css");

            HTML transactionNumberTextBox = new HTML();
            HTML numberOfTokensTextBox = new HTML();
            HTML totalAmountTextBox = new HTML();
            HTML paymentTypeTextBox = new HTML();
            HTML meterNumberTextBox = new HTML();
            
            transactionNumberTextBox.addStyleName("htmlBox");
            numberOfTokensTextBox.addStyleName("htmlBox");
            totalAmountTextBox.addStyleName("htmlBox");
            paymentTypeTextBox.addStyleName("htmlBox");
            meterNumberTextBox.addStyleName("htmlBox");
            
            int i = 1;
            for (ElecTrans elecTrans : listOfElectTransactions) {

                transactionNumberTextBox.setText(String.valueOf(elecTrans.getElecTransId()));
                numberOfTokensTextBox.setText(String.valueOf(elecTrans.getTokenNumber()));
                totalAmountTextBox.setText(String.valueOf(elecTrans.getAmount()));
                paymentTypeTextBox.setText(elecTrans.getPayType().getName());
                meterNumberTextBox.setText(elecTrans.getMeter().getMeterNumber());

                tableOfElecTransactions.setWidget(i, 0, transactionNumberTextBox);
                tableOfElecTransactions.setWidget(i, 1, numberOfTokensTextBox);
                tableOfElecTransactions.setWidget(i, 2, totalAmountTextBox);
                tableOfElecTransactions.setWidget(i, 3, paymentTypeTextBox);
                tableOfElecTransactions.setWidget(i, 4, meterNumberTextBox);
            }
            ++i;
        }
        return tableOfElecTransactions;
    }

    /**
     * 
     * @return 
     */
    private List<List<ElecTrans>> findAllElecTransactions() {
        final List<List<ElecTrans>> listOfElecTrans = new ArrayList<List<ElecTrans>>(); // TODO how to change return type to List<ElecTrans>
        electricityService.findAllElecTrans(new AsyncCallback() {
            @Override
            public void onSuccess(Object result) {
                List<ElecTrans> anotherListOfElecTrans = (List<ElecTrans>) result;
                listOfElecTrans.add(anotherListOfElecTrans);
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
        return listOfElecTrans;
    }

    /**
     * 
     * @return 
     */
    private List<List<PayType>> findAllPaymentTypes() {
        final List<List<PayType>> listOfPayTypes = new ArrayList<List<PayType>>();
        electricityService.findAllPayTypes(new AsyncCallback() {
            @Override
            public void onSuccess(Object result) {
                List<PayType> anotherListOfPayTypes = (List<PayType>) result;
                listOfPayTypes.add(anotherListOfPayTypes);
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
        return listOfPayTypes;
    }

    /**
     * 
     * @return 
     */
    private List<List<Meter>> findAllMeterTypes() {
        final List<List<Meter>> listOfMeters = new ArrayList<List<Meter>>();
        electricityService.findAllMeterNumbers(new AsyncCallback() {
            @Override
            public void onSuccess(Object result) {
                List<Meter> anotherListOfMeters = (List<Meter>) result;
                listOfMeters.add(anotherListOfMeters);
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
        return listOfMeters;
    }
}
