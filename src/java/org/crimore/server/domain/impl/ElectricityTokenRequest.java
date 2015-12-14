/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.crimore.server.domain.impl;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.security.Security;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/**
 *
 * @author Chayanne
 */
public class ElectricityTokenRequest {

    private static Socket socket;
    private static Integer ref;
    private static int amount;
    private static int numberOfTokens;
    private static String meterNumber;
    private static String paymentType;

    static {
        try {
            String endPoint = "www.bizswitch.net";
            Integer portNumber = 8867;
            boolean useSSL = true;
            if (useSSL) {
                Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
                SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();

                ElectricityTokenRequest.socket = (SSLSocket) factory.createSocket(endPoint, portNumber);
            } else {
                ElectricityTokenRequest.socket = new Socket(endPoint, portNumber);
            }
        } catch (Exception e) {

        }
    }

    public ElectricityTokenRequest() {
    }

    static void processAndSendIPayMessage() throws IOException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss'Z'"); // TODO get proper formatting of timezone
        String ipayRequestXml = String.format("<ipayMsg client=\"ipay\" term=\"1\" seqNum=\"1\" time=\"%s\">"
                + "<elecMsg ver=\"2.3\">"
                + "<vendReq useAdv=\"false\">"
                + "<ref>%d</ref>"
                + "<amt cur=\"ZAR\">%d</amount>"
                + "<numTokens>%d</numberOfTokens>"
                + "<meter>%s</meter>"
                + "<payType>%s</payType>"
                + "</vendReq>"
                + "</elecMsg>"
                + "</ipayMsg>",
                sdf.format(new Date()), ElectricityTokenRequest.ref,
                ElectricityTokenRequest.amount,
                ElectricityTokenRequest.numberOfTokens,
                ElectricityTokenRequest.meterNumber,
                ElectricityTokenRequest.paymentType);

        OutputStream outputStream = ElectricityTokenRequest.socket.getOutputStream();
        Writer writer = new OutputStreamWriter(outputStream);
        writer.write(ipayRequestXml);
        writer.flush();
        ElectricityTokenRequest.socket.shutdownOutput();
    }

    public static String receiveAndProcessIPayMessage() throws IOException {

        processAndSendIPayMessage();
        StringBuffer stringBuffer = new StringBuffer();
        try {
            BufferedReader bufferdReader = new BufferedReader(new InputStreamReader(ElectricityTokenRequest.socket.getInputStream()));
            int charAsInt;
            while ((charAsInt = bufferdReader.read()) != -1) {
                stringBuffer.append((char) charAsInt);
            }

            bufferdReader.close();

            ElectricityTokenRequest.socket.close();
        } catch (IOException ex) {
        }
        return stringBuffer.toString();
    }

    public static List<String> getTokenAndProcessResponseMessage(Integer ref,
            int amount,
            int numberOfTokens,
            String meterNumber,
            String paymentType) {

        ElectricityTokenRequest.ref = ref;
        ElectricityTokenRequest.amount = amount;
        ElectricityTokenRequest.numberOfTokens = numberOfTokens;
        ElectricityTokenRequest.meterNumber = meterNumber;
        ElectricityTokenRequest.paymentType = paymentType;

        List<String> listOfTokens = new ArrayList<String>();
        try {
            String iPayMessageXml = receiveAndProcessIPayMessage();

            if (iPayMessageXml != null && !iPayMessageXml.isEmpty()) {
                Document iPayMessageXMLDoc = XMLParser.parse(iPayMessageXml);
                Node resNode = iPayMessageXMLDoc.getElementsByTagName("res").item(0);
                String code = ((Element) resNode).getAttribute("code");
                if (code.equals("elec000")) {
                    NodeList stdTokens = iPayMessageXMLDoc.getElementsByTagName("stdToken");

                    for (int i = 0; i < stdTokens.getLength(); i++) {
                        listOfTokens.add(stdTokens.item(i).getFirstChild().getNodeValue());
                    }
                } else {
                    // TODO handle response if not OK
                }
            }
        } catch (Exception e) {

        }
        return listOfTokens;
    }
}
