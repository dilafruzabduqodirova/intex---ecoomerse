package com.example.demo.service;


import com.example.demo.entity.Customer;
import com.example.demo.payload.ApiResponse;
import com.example.demo.payload.CustomerDto;
import com.example.demo.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository ;

    /**
     * Bu yerda customer lar ni listini qaytaramiz .
     * @return customer
     */
    public List<Customer> getCustomers(){
        List<Customer> customers = customerRepository.findAll();
        return customers;
    }


    /**
     * Bu yerda customer lar ni id si bo'yicha qaytaramiz .
     * @param id Integer
     * @return Customer
     * Agar id orqali customer qaytarilmasa , demak biz null value qaytaramiz .
     */
    public Customer getCustomerById(Integer id){
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isPresent()){
            return optionalCustomer.get();
        }return null;
    }


    /**
     *Method customer lar ni qo'shish uchun xizmat qiladi .
     * @return ApiResponse beriladi , agar customer saqlansa .
     */
    public ApiResponse addCustomer(CustomerDto customerDto){
        boolean existsByPhoneNumber = customerRepository.existsByPhoneNumber(customerDto.getPhoneNumber());
        if (existsByPhoneNumber){
            return new ApiResponse("Bunday mijoz mavjud", false);
        }
        Customer customer = new Customer();
        customer.setPhoneNumber(customerDto.getPhoneNumber());
        customer.setFullName(customerDto.getFullName());
        customer.setAddress(customerDto.getAddress());
        customerRepository.save(customer);
        return new ApiResponse("Mijoz saqlandi ", true);
    }


    /**
     * Method customer ni taxrirlash uchun
     * @param id
     * @param customerDto
     * @return ApiResponse
     */
    public ApiResponse editCustomer(Integer id , CustomerDto customerDto){
        boolean existsByPhoneNumberAndIdNot = customerRepository.existsByPhoneNumberAndIdNot(customerDto.getPhoneNumber(), id);
        if (existsByPhoneNumberAndIdNot){
            return new ApiResponse("Kechirasiz , bunday mijoz mavjud ",false);
        }

        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (!optionalCustomer.isPresent()){
            return new ApiResponse("Bunday mijoz mavjud emas ",false);
        }

        Customer customer = optionalCustomer.get();
        customer.setFullName(customerDto.getFullName());
        customer.setAddress(customerDto.getAddress());
        customer.setPhoneNumber(customerDto.getPhoneNumber());

        customerRepository.save(customer);
        return new ApiResponse("Mjoz muvafiqatli tarzda taxrirlandi ",true);
    }

    public ApiResponse deleteCustomer(Integer id ){
        customerRepository.deleteById(id);
        return new ApiResponse("Mijoz mavafaqiyatli tarzda o'chirildi .",true);
    }





}
