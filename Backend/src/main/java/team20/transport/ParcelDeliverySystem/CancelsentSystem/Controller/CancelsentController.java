package team20.transport.ParcelDeliverySystem.CancelSentSystem.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import team20.transport.ParcelDeliverySystem.Entity.Employee;
import team20.transport.ParcelDeliverySystem.Repository.EmployeeRepository;

import team20.transport.ParcelDeliverySystem.PackagingSystem.Entity.Packaging;
import team20.transport.ParcelDeliverySystem.PackagingStateSystem.Repository.PackagingRepository;

import team20.transport.ParcelDeliverySystem.CancelSentSystem.Entity.Howtopay;
import team20.transport.ParcelDeliverySystem.CancelSentSystem.Repository.HowtopayRepository;

import team20.transport.ParcelDeliverySystem.CancelSentSystem.Entity.Senttoback;
import team20.transport.ParcelDeliverySystem.CancelSentSystem.Repository.SenttobackRepository;

import team20.transport.ParcelDeliverySystem.Entity.Status;
import team20.transport.ParcelDeliverySystem.Repository.StatusRepository;

import java.util.Map;

@RestController
@RequestMapping("/Cancelsent")
@CrossOrigin(origins = {"*"})
public class CancelsentController {
    @Autowired
    CancelsentRepository cancelsentRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    PackagingRepository packagingRepository;
    @Autowired
    StatusRepository statusRepository;
    @Autowired
    SenttobackRepository senttobackRepository;
    @Autowired
    HowtopayRepository howtopayRepository;


    @PostMapping("/addCancelsent")
    public Cancelsent addCancelsent(@RequestBody Map<String,Long> allParams){

        Employee createBy = employeeRepository.findById(allParams.get("employeeId")).get();
        Packaging packaging = packagingRepository.findById(allParams.get("packageId")).get();
        Status onStatus = statusRepository.findById(allParams.get("statusId")).get();
        Senttoback onSenttoback = senttobackRepository.findById(allParams.get("senttobackId")).get();
        Howtopay onHowtopay = howtopayRepository.findById(allParams.get("howtopayId")).get();

        Cancelsent newCancelsent = new Cancelsent();
        newCancelsent.setCreateBy(createBy);
        newCancelsent.setPackaging(packaging);
        newCancelsent.setOnStatus(onStatus);
        newCancelsent.setOnSenttoback(onSenttoback);
        newCancelsent.setOnHowtopay(onHowtopay);

        return cancelsentRepository.save(newCancelsent);
        //
    }
}