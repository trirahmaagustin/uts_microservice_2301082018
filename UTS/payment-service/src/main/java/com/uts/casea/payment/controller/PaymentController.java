package com.uts.casea.payment.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.uts.casea.payment.model.Payment;
import com.uts.casea.payment.service.PaymentService;



@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private static final Logger log = LoggerFactory.getLogger(PaymentController.class);


   @Autowired
   private PaymentService paymentService;


   // Endpoint untuk mengambil semua payment
   @GetMapping
   public List<Payment> getAllPayments() {
    log.info("GET /api/payments accessed");
       return paymentService.getAll();
   }


   // Endpoint untuk mengambil payment berdasarkan id
   @GetMapping("/{id}")
   public ResponseEntity<Payment> getPaymentById(@PathVariable Long id) {
    log.info("GET /api/payments/{} accessed", id);
       return paymentService.getById(id)
               .map(payment -> ResponseEntity.ok().body(payment))
               .orElse(ResponseEntity.notFound().build());
   }


   // Endpoint membuat payment baru
   @PostMapping
   public Payment createPayment(@RequestBody Payment payment) {
    log.info("POST /api/payments accessed with body: {}", payment);
       return paymentService.createPayment(payment);
   }


   // Endpoint untuk update payment yang sudah ada
   @PutMapping("/{id}")
   public ResponseEntity<Payment> updatePayment(@PathVariable Long id, @RequestBody Payment paymentDetails) {
    log.info("PUT /api/payments/{} accessed with body: {}", id, paymentDetails);

       try {
           Payment updatedPayment = paymentService.updatePayment(id, paymentDetails);
           return ResponseEntity.ok(updatedPayment);
       } catch (RuntimeException e) {
        log.warn("PUT /api/payments/{} failed: {}", id, e.getMessage());
           return ResponseEntity.notFound().build();
       }
   }


   // Endpoint DELETE payment
   @DeleteMapping("/{id}")
   public ResponseEntity<?> deletePayment(@PathVariable Long id) {
    log.info("DELETE /api/payments/{} accessed", id);
    Map<String, String> response = new HashMap<>();
       try {
           paymentService.deletePayment(id);
           response.put("message", "Payment berhasil dihapus");
           return ResponseEntity.ok("Payment deleted successfully");
       } catch (RuntimeException e) {
        response.put("message", "Payment tidak ditemukan dengan id " + id);
        log.warn("DELETE /api/payments/{} failed: {}", id, e.getMessage());
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
       }
   }
}
