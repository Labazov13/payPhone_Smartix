package com.example.payPhone.processors;

import com.example.payPhone.enttities.PaymentHistory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OperationProcessor {

    public List<String> convertToList(List<PaymentHistory> paymentHistoryList) {
        List<String> paymentDetails = new ArrayList<>();
        paymentHistoryList.forEach(paymentHistory -> paymentDetails.add("ID: " + paymentHistory.getId() + ", PHONE: "
                + paymentHistory.getUser().getUsername() + ", AMOUNT: " + paymentHistory.getAmount()
                + ", PAID AT: " + paymentHistory.getPaidAt()));
        return paymentDetails;
    }
}
