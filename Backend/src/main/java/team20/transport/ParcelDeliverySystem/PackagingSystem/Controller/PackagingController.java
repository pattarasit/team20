package team20.transport.ParcelDeliverySystem.PackagingSystem.Controller;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import team20.transport.ParcelDeliverySystem.Entity.Employee;
import team20.transport.ParcelDeliverySystem.Entity.Station;
import team20.transport.ParcelDeliverySystem.PackagingSystem.Repository.PackageTypeRepository;
import team20.transport.ParcelDeliverySystem.PackagingSystem.Repository.SendingTypeRepository;
import team20.transport.ParcelDeliverySystem.MemberCustomerSystem.Entity.MemberCustomer;
import team20.transport.ParcelDeliverySystem.MemberCustomerSystem.Repository.MemberCustomerRepository;
import team20.transport.ParcelDeliverySystem.PackagingSystem.Entity.PackageType;
import team20.transport.ParcelDeliverySystem.PackagingSystem.Entity.Packaging;
import team20.transport.ParcelDeliverySystem.PackagingSystem.Entity.SendingType;
import team20.transport.ParcelDeliverySystem.Repository.EmployeeRepository;
import team20.transport.ParcelDeliverySystem.Repository.StationRepository;
import team20.transport.ParcelDeliverySystem.PackagingSystem.Repository.PackagingRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/team20")
@CrossOrigin(origins = {"*"})
public class PackagingController {
    @Autowired
    StationRepository stationRepository;
    @Autowired
    PackagingRepository packagingRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    MemberCustomerRepository memberCustomerRepository;
    @Autowired
    PackageTypeRepository packageTypeRepository;
    @Autowired
    SendingTypeRepository sendingTypeRepository;

    @PostMapping("/addPackaging")
    public Packaging addPackaging(@RequestBody Map<String,String> allParams){
        MemberCustomer sentBy = memberCustomerRepository.findById(Long.valueOf(allParams.get("customerId"))).get();
        Employee createBy = employeeRepository.findById(Long.valueOf(allParams.get("employeeId"))).get();
        Station atStation = stationRepository.findById(Long.valueOf(allParams.get("stationId"))).get();
        PackageType ptype = packageTypeRepository.findById(Long.valueOf(allParams.get("pTypeId"))).get();
        SendingType stype = sendingTypeRepository.findById(Long.valueOf(allParams.get("sTypeId"))).get();


        String receiver = allParams.get("receiever");
        String place = allParams.get("place");
        Long volume = Long.valueOf(allParams.get("volume"));
        Long weight = Long.valueOf(allParams.get("weight"));
        Date pdate = new Date();

        Long countAllPackage = packagingRepository.count();

        String code = String.format("T20%05d",countAllPackage + 1);


        Packaging newPackaging = new Packaging();
        newPackaging.setSentBy(sentBy);
        newPackaging.setAtStation(atStation);
        newPackaging.setCode(code);
        newPackaging.setCreateBy(createBy);
        newPackaging.setPackageType(ptype);
        newPackaging.setSendingType(stype);
        newPackaging.setPackageDate(pdate);
        newPackaging.setPlace(place);
        newPackaging.setReciever(receiver);
        newPackaging.setVolume(volume);
        newPackaging.setWeight(weight);

        return packagingRepository.save(newPackaging);
    }

    @GetMapping("/findPackageByCode/{code}")
    public JSONObject findPackageByCode(@PathVariable String code){
        Packaging pac = packagingRepository.findPackageByCode(code);
        JSONObject json = new JSONObject();
        json.put("id",pac.getId());
        json.put("packaging_date",pac.getPackageDate());
        json.put("code",pac.getCode());
        json.put("place",pac.getPlace());
        json.put("receiever",pac.getReciever());
        json.put("volume",pac.getVolume());
        json.put("weight",pac.getWeight());
        json.put("station",pac.getAtStation());
        json.put("createBy",pac.getCreateBy());
        json.put("packageType",pac.getPackageType());
        json.put("sendingType",pac.getSendingType());
        json.put("sentBy",pac.getSentBy());
        json.put("haveShippingState", pac.getHaveShippingState());
        return json;
    }

    @GetMapping("/getAllPackage")
    public JSONArray getAllPackage() {
        JSONArray aa = new JSONArray();
        for(Packaging pac : packagingRepository.findAll()){
            JSONObject json = new JSONObject();
            json.put("id",pac.getId());
            json.put("packaging_date",pac.getPackageDate());
            json.put("place",pac.getPlace());
            json.put("code",pac.getCode());
            json.put("receiever",pac.getReciever());
            json.put("volume",pac.getVolume());
            json.put("weight",pac.getWeight());
            json.put("station",pac.getAtStation());
            json.put("createBy",pac.getCreateBy());
            json.put("packageType",pac.getPackageType());
            json.put("sendingType",pac.getSendingType());
            json.put("sentBy",pac.getSentBy());
            json.put("haveShippingState", pac.getHaveShippingState());
            aa.add(json);
        }
        return aa;
    }
}
