package com.cydeo.controller;

import com.cydeo.dto.AddressDTO;
import com.cydeo.dto.ResponseWrapper;
import com.cydeo.dto.TeacherDTO;
import com.cydeo.service.AddressService;
import com.cydeo.service.ParentService;
import com.cydeo.service.StudentService;
import com.cydeo.service.TeacherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ApiController {
    private final TeacherService teacherService;
    private final StudentService studentService;
    private final ParentService parentService;
    private final AddressService addressService;

    public ApiController(TeacherService teacherService, StudentService studentService, ParentService parentService, AddressService addressService) {
        this.teacherService = teacherService;
        this.studentService = studentService;
        this.parentService = parentService;
        this.addressService = addressService;
    }


    @GetMapping("/teachers")
    public List<TeacherDTO> readAllTeachers(){

        return teacherService.findAll();
    }

    @GetMapping("/students")
    public ResponseEntity<ResponseWrapper> readAllStudents(){

        return ResponseEntity.ok(new ResponseWrapper("Students are successfully retrieved",studentService.findAll()));
    }

    @GetMapping("/parents")
    public ResponseEntity<ResponseWrapper> readAllParents(){

        ResponseWrapper responseWrapper=
                new ResponseWrapper(true,"Parent are successfully retrived", HttpStatus.OK.value(), parentService.findAll());

        return ResponseEntity.status(HttpStatus.OK).body(responseWrapper);
    }

    @GetMapping("/address/{id}")
    public ResponseEntity<ResponseWrapper> readAddress(@PathVariable("id") Long id) throws Exception {

        AddressDTO addressToReturn=addressService.findById(id);
        addressToReturn.setCurrentTemperature(addressService.getCurrentWeather(addressToReturn.getCity()).getCurrent().getTemperature());
        return ResponseEntity.ok(new ResponseWrapper("Address is succesfully retrived.",addressToReturn));
    }

    @PutMapping("/address/{id}")
    public AddressDTO updateAddress(@PathVariable("id") Long id
                     , @RequestBody AddressDTO addressDTO) throws Exception {

        addressDTO.setId(id);
        AddressDTO addressToReturn= addressService.update(addressDTO);
        addressToReturn.setCurrentTemperature(addressService.getCurrentWeather(addressToReturn.getCity()).getCurrent().getTemperature());
        return addressToReturn;
    }


}
