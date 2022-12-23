package com.example.demo.controller;

import com.example.demo.entity.Customer;
import com.example.demo.payload.ApiResponse;
import com.example.demo.payload.CustomerDto;
import com.example.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CustomerController {

    @Autowired
    CustomerService customerService;

    //mehtod used by user whose role is admin
    /**
     * Bu yerda customer lar ni listini qaytaramiz .
     * @return customer
     */
    @RolesAllowed("admin")
    @GetMapping("/api/customers")
    public List<Customer> getCustomers(){
        List<Customer> customers = customerService.getCustomers();
        return customers;
    }

    //mehtod used by user whose role is user
    /**
     * Bu yerda customer lar ni id si bo'yicha qaytaramiz .
     * @param id Integer
     * @return Customer
     * Agar id orqali customer qaytarilmasa , demak biz null value qaytaramiz .
     */
    @RolesAllowed("user")
    @GetMapping("api/customers/{id}")
    public Customer getCustomer(@PathVariable Integer id){
        Customer customerById = customerService.getCustomerById(id);
        return customerById;
    }

    /**
     * Method Customer lar ni qo'shish uchun xizmat qiladi .
     * RequestBody annotatsiya si malumotlarni json ga o'tkazib beradi
     * @param customerDto
     * @return ApiResponse
     */
    @PostMapping("/api/customers")
    public ApiResponse addCustomer(@Valid @RequestBody CustomerDto customerDto){
        ApiResponse apiResponse = customerService.addCustomer(customerDto);
        return apiResponse;
    }


    /**
     * Method customer ni taxrirlash uchun
     * @param id
     * @param customerDto
     * @return ApiResponse
     */
    @PutMapping("/api/customers/{id}")
    public ApiResponse editCustomer(@PathVariable Integer id ,@Valid @RequestBody CustomerDto customerDto){
        ApiResponse apiResponse = customerService.editCustomer(id, customerDto);
        return apiResponse;
    }


    /**
     * Method customer ni id si orqali o'chirish uchun xizmat qiladi
     * @param id
     * @return ApiResponse
     */
    @DeleteMapping("/api/customers/{id}")
    public ApiResponse deleteCustomer(@PathVariable Integer id ){
        ApiResponse apiResponse = customerService.deleteCustomer(id);
        return apiResponse;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
