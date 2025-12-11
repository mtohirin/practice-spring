package com.gentara.practice.springboot.order.controller;

import com.gentara.practice.springboot.order.model.OrderRequest;
import com.gentara.practice.springboot.order.model.OrderModel;
import com.gentara.practice.springboot.order.service.OrderService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class Controller {

    private final OrderService orderService;

    public Controller(OrderService orderService) {
        this.orderService = orderService;
    }

    // GET ALL ORDERS
    @GetMapping
    public ResponseEntity<List<OrderModel>> getAllOrders() {
        try {
            List<OrderModel> orders = orderService.getAll();
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // GET ORDER BY ID
    @GetMapping("/{id}")
    public ResponseEntity<OrderModel> getOrderById(@PathVariable String id) {
        try {
            OrderModel order = orderService.getById(id);
            return ResponseEntity.ok(order);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // GET ORDER BY ORDER NUMBER
    @GetMapping("/order-number/{orderNumber}")
    public ResponseEntity<OrderModel> getOrderByOrderNumber(@PathVariable String orderNumber) {
        try {
            OrderModel order = orderService.getByOrderNumber(orderNumber);
            return ResponseEntity.ok(order);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // CREATE NEW ORDER
    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest request) {
        try {
            OrderModel createdOrder = orderService.create(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to create order"));
        }
    }

    // UPDATE ORDER STATUS
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateOrderStatus(@PathVariable String id, @RequestBody Map<String, String> request) {
        try {
            String status = request.get("status");
            if (status == null || status.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "Status is required"));
            }
            OrderModel updatedOrder = orderService.updateStatus(id, status);
            return ResponseEntity.ok(updatedOrder);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to update order status"));
        }
    }

    // DELETE ORDER
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable String id) {
        try {
            orderService.delete(id);
            return ResponseEntity.ok().body(Map.of("message", "Order deleted successfully"));
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to delete order"));
        }
    }

    // GET ORDERS BY CUSTOMER
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<OrderModel>> getOrdersByCustomer(@PathVariable String customerId) {
        try {
            List<OrderModel> orders = orderService.getByCustomerId(customerId);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // GET ORDERS BY PRODUCT
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<OrderModel>> getOrdersByProduct(@PathVariable String productId) {
        try {
            List<OrderModel> orders = orderService.getByProductId(productId);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // GET ORDERS BY STATUS
    @GetMapping("/status/{status}")
    public ResponseEntity<?> getOrdersByStatus(@PathVariable String status) {
        try {
            List<OrderModel> orders = orderService.getByStatus(status);
            return ResponseEntity.ok(orders);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // GET ORDERS BY DATE RANGE
    @GetMapping("/date-range")
    public ResponseEntity<?> getOrdersByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        try {
            List<OrderModel> orders = orderService.getByDateRange(startDate, endDate);
            return ResponseEntity.ok(orders);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // GET ORDERS BY CUSTOMER AND STATUS
    @GetMapping("/customer/{customerId}/status/{status}")
    public ResponseEntity<?> getOrdersByCustomerAndStatus(
            @PathVariable String customerId,
            @PathVariable String status) {
        try {
            List<OrderModel> orders = orderService.getByCustomerAndStatus(customerId, status);
            return ResponseEntity.ok(orders);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // CONFIRM ORDER
    @PatchMapping("/{id}/confirm")
    public ResponseEntity<?> confirmOrder(@PathVariable String id) {
        try {
            OrderModel updatedOrder = orderService.confirmOrder(id);
            return ResponseEntity.ok(updatedOrder);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to confirm order"));
        }
    }

    // PROCESS ORDER
    @PatchMapping("/{id}/process")
    public ResponseEntity<?> processOrder(@PathVariable String id) {
        try {
            OrderModel updatedOrder = orderService.processOrder(id);
            return ResponseEntity.ok(updatedOrder);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to process order"));
        }
    }

    // COMPLETE ORDER
    @PatchMapping("/{id}/complete")
    public ResponseEntity<?> completeOrder(@PathVariable String id) {
        try {
            OrderModel updatedOrder = orderService.completeOrder(id);
            return ResponseEntity.ok(updatedOrder);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to complete order"));
        }
    }

    // CANCEL ORDER
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<?> cancelOrder(@PathVariable String id) {
        try {
            OrderModel updatedOrder = orderService.cancelOrder(id);
            return ResponseEntity.ok(updatedOrder);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to cancel order"));
        }
    }

    // GET TOTAL REVENUE
    @GetMapping("/revenue/total")
    public ResponseEntity<?> getTotalRevenue() {
        try {
            Double revenue = orderService.getTotalRevenue();
            return ResponseEntity.ok(Map.of("totalRevenue", revenue));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to get total revenue"));
        }
    }

    // GET ORDER COUNT BY STATUS
    @GetMapping("/count/status/{status}")
    public ResponseEntity<?> getOrderCountByStatus(@PathVariable String status) {
        try {
            Long count = orderService.getOrderCountByStatus(status);
            return ResponseEntity.ok(Map.of("count", count));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to get order count"));
        }
    }
}