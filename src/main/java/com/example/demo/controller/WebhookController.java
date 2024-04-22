package com.example.demo.controller;

import com.example.demo.model.StripePayment;
import com.example.demo.repository.StripePaymentRepository;
import com.example.demo.repository.UserRepository;
import com.google.gson.Gson;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.*;
import com.stripe.net.Webhook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("webhook")
public class WebhookController {

    private static final Gson gson = new Gson();
    @Value("${stripe.webhook_key}")
    private String stripeKey;

    @Autowired
    private StripePaymentRepository stripePaymentRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<String> webhook(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) {
        Event event = null;
        try {
            event = Webhook.constructEvent(payload, sigHeader, stripeKey);
        } catch (SignatureVerificationException e) {
            System.out.println("Failed signature verification");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
        StripeObject stripeObject = null;

        if (dataObjectDeserializer.getObject().isPresent()) {
            stripeObject = dataObjectDeserializer.getObject().get();
        } else {
            // Deserialization failed, probably due to an API version mismatch.
            // Refer to the Javadoc documentation on `EventDataObjectDeserializer` for
            // instructions on how to handle this case, or return an error here.
        }

        switch (event.getType()) {
            case "payment_intent.succeeded" :
            case "charge.succeeded":
                try {
                    var charge = (Charge)stripeObject;
                    var stripePayment = new StripePayment();
                    stripePayment.setAmount(charge.getAmountCaptured() / 100);
                    stripePayment.setCurrency(charge.getCurrency());
                    String userId = charge.getMetadata().get("user_id");
                    var user = userRepository.findById(Integer.parseInt(userId)).get();
                    stripePayment.setUserEmail(user.getEmail());
                    stripePayment.setUserAddress(user.getCustomer().getAddress());
                    stripePaymentRepository.save(stripePayment);
                } catch (Exception e) {

                }
                break;
            case "payment_method.attached":
                // ...
                break;
            // ... handle other event types
            default:
                // Unexpected event type
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        System.out.println("Success");
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
}
