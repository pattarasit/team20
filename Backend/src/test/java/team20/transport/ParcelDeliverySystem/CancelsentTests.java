package team20.transport.ParcelDeliverySystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import team20.transport.ParcelDeliverySystem.CancelsentSystem.Entity.Cancelsent;
import team20.transport.ParcelDeliverySystem.CancelsentSystem.Entity.Howtopay;
import team20.transport.ParcelDeliverySystem.CancelsentSystem.Entity.Senttoback;
import team20.transport.ParcelDeliverySystem.CancelsentSystem.Repository.CancelsentRepository;
import team20.transport.ParcelDeliverySystem.CancelsentSystem.Repository.HowtopayRepository;
import team20.transport.ParcelDeliverySystem.CancelsentSystem.Repository.SenttobackRepository;
import team20.transport.ParcelDeliverySystem.Entity.Employee;
import team20.transport.ParcelDeliverySystem.Entity.Station;
import team20.transport.ParcelDeliverySystem.Entity.Status;
import team20.transport.ParcelDeliverySystem.MemberCustomerSystem.Entity.MemberCustomer;
import team20.transport.ParcelDeliverySystem.MemberCustomerSystem.Repository.MemberCustomerRepository;
import team20.transport.ParcelDeliverySystem.PackagingSystem.Entity.Packaging;
import team20.transport.ParcelDeliverySystem.PackagingSystem.Repository.PackagingRepository;
import team20.transport.ParcelDeliverySystem.Repository.EmployeeRepository;
import team20.transport.ParcelDeliverySystem.Repository.StationRepository;
import team20.transport.ParcelDeliverySystem.Repository.StatusRepository;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class CancelsentTests {
    @Autowired
    CancelsentRepository cancelsentRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    PackagingRepository packagingRepository;
    @Autowired
    StatusRepository statusRepository;
    @Autowired
    StationRepository stationRepository;
    @Autowired
    SenttobackRepository senttobackRepository;
    @Autowired
    HowtopayRepository howtopayRepository;
    @Autowired
    MemberCustomerRepository memberCustomerRepository;
    

    Validator validator;

    @BeforeEach
    public void setup() {
        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void b6021405_testCorrectDataInput() {

        Senttoback senttoback = new Senttoback();
        senttoback.setName("sentback Test");
        senttoback = senttobackRepository.saveAndFlush(senttoback);

        Howtopay howtopay = new Howtopay();
        howtopay.setName("howpay Test");
        howtopay = howtopayRepository.saveAndFlush(howtopay);

        Employee employee = new Employee();
        employee.setName("B6021405");
        employee.setEmail("B6021405@g.sut.ac.th");
        employee = employeeRepository.saveAndFlush(employee);

        Status status = new Status();
        status.setName("test status");
        status = statusRepository.saveAndFlush(status);

        Station station = new Station();
        station.setName("test station");
        station = stationRepository.saveAndFlush(station);

        MemberCustomer memberCustomer = new MemberCustomer();
        memberCustomer.setMemName("mem Test");
        memberCustomer.setTel("0987654321");
        memberCustomer.setCreateBy(employee);
        memberCustomer = memberCustomerRepository.saveAndFlush(memberCustomer);

        Packaging packaging = new Packaging();
        packaging.setCode("T2001234");
        packaging.setSentBy(memberCustomer);
        packaging.setAtStation(station);
        packaging.setCreateBy(employee);
        packaging = packagingRepository.saveAndFlush(packaging);
        
        Cancelsent cancelsent = new Cancelsent();
        cancelsent.setName("CN01234");
        cancelsent.setCreateBy(employee);
        cancelsent.setOnPackageing(packaging);
        cancelsent.setOnStatus(status);
        cancelsent.setOnSenttoback(senttoback);
        cancelsent.setOnHowtopay(howtopay);

        cancelsent = cancelsentRepository.saveAndFlush(cancelsent);

        final Cancelsent found = cancelsentRepository.findById(cancelsent.getId()).get();

        assertEquals(("CN01234"), found.getName());
        assertEquals(employee, found.getCreateBy());
        assertEquals(packaging, found.getOnPackageing());
        assertEquals(status, found.getOnStatus());
        assertEquals(senttoback, found.getOnSenttoback());
        assertEquals(howtopay, found.getOnHowtopay());

    }

    @Test
    void b6021405_nameMustNotBeNull() {
        Senttoback senttoback = new Senttoback();
        senttoback.setName("sentback Test");
        senttoback = senttobackRepository.saveAndFlush(senttoback);

        Howtopay howtopay = new Howtopay();
        howtopay.setName("howpay Test");
        howtopay = howtopayRepository.saveAndFlush(howtopay);

        Employee employee = new Employee();
        employee.setName("B6021405");
        employee.setEmail("B6021405@g.sut.ac.th");
        employee = employeeRepository.saveAndFlush(employee);

        Status status = new Status();
        status.setName("test status");
        status = statusRepository.saveAndFlush(status);

        Station station = new Station();
        station.setName("test station");
        station = stationRepository.saveAndFlush(station);

        MemberCustomer memberCustomer = new MemberCustomer();
        memberCustomer.setMemName("mem Test");
        memberCustomer.setTel("0987654321");
        memberCustomer.setCreateBy(employee);
        memberCustomer = memberCustomerRepository.saveAndFlush(memberCustomer);

        Packaging packaging = new Packaging();
        packaging.setCode("T2001234");
        packaging.setSentBy(memberCustomer);
        packaging.setAtStation(station);
        packaging.setCreateBy(employee);
        packaging = packagingRepository.saveAndFlush(packaging);
        
        Cancelsent cancelsent = new Cancelsent();
        cancelsent.setName(null);
        cancelsent.setCreateBy(employee);
        cancelsent.setOnPackageing(packaging);
        cancelsent.setOnStatus(status);
        cancelsent.setOnSenttoback(senttoback);
        cancelsent.setOnHowtopay(howtopay);

        final Set<ConstraintViolation<Cancelsent>> result = validator.validate(cancelsent);

        // ต้องมี 1 error เท่านั้น
        assertEquals(1, result.size());

        // error message ตรงชนิด และถูก field
        final ConstraintViolation<Cancelsent> v = result.iterator().next();
        assertEquals("must not be null", v.getMessage());
        assertEquals("name", v.getPropertyPath().toString());

    }

    @Test
    void b6021405_StatusMustNotBeNull() {
        Senttoback senttoback = new Senttoback();
        senttoback.setName("sentback Test");
        senttoback = senttobackRepository.saveAndFlush(senttoback);

        Howtopay howtopay = new Howtopay();
        howtopay.setName("howpay Test");
        howtopay = howtopayRepository.saveAndFlush(howtopay);

        Employee employee = new Employee();
        employee.setName("B6021405");
        employee.setEmail("B6021405@g.sut.ac.th");
        employee = employeeRepository.saveAndFlush(employee);

        Status status = new Status();
        status.setName("test status");
        status = statusRepository.saveAndFlush(status);

        Station station = new Station();
        station.setName("test station");
        station = stationRepository.saveAndFlush(station);

        MemberCustomer memberCustomer = new MemberCustomer();
        memberCustomer.setMemName("mem Test");
        memberCustomer.setTel("0987654321");
        memberCustomer.setCreateBy(employee);
        memberCustomer = memberCustomerRepository.saveAndFlush(memberCustomer);

        Packaging packaging = new Packaging();
        packaging.setCode("T2001234");
        packaging.setSentBy(memberCustomer);
        packaging.setAtStation(station);
        packaging.setCreateBy(employee);
        packaging = packagingRepository.saveAndFlush(packaging);
        
        Cancelsent cancelsent = new Cancelsent();
        cancelsent.setName("CN01234");
        cancelsent.setCreateBy(employee);
        cancelsent.setOnPackageing(packaging);
        cancelsent.setOnStatus(null);
        cancelsent.setOnSenttoback(senttoback);
        cancelsent.setOnHowtopay(howtopay);

        final Set<ConstraintViolation<Cancelsent>> result = validator.validate(cancelsent);

        // ต้องมี 1 error เท่านั้น
        assertEquals(1, result.size());

        // error message ตรงชนิด และถูก field
        final ConstraintViolation<Cancelsent> v = result.iterator().next();
        assertEquals("must not be null", v.getMessage());
        assertEquals("status", v.getPropertyPath().toString());

    }

    @Test
    void b6021405_HowtopayMustNotBeNull() {
        Senttoback senttoback = new Senttoback();
        senttoback.setName("sentback Test");
        senttoback = senttobackRepository.saveAndFlush(senttoback);
        Howtopay howtopay = new Howtopay();
        howtopay.setName("howpay Test");
        howtopay = howtopayRepository.saveAndFlush(howtopay);

        Employee employee = new Employee();
        employee.setName("B6021405");
        employee.setEmail("B6021405@g.sut.ac.th");
        employee = employeeRepository.saveAndFlush(employee);

        Status status = new Status();
        status.setName("test status");
        status = statusRepository.saveAndFlush(status);

        Station station = new Station();
        station.setName("test station");
        station = stationRepository.saveAndFlush(station);

        MemberCustomer memberCustomer = new MemberCustomer();
        memberCustomer.setMemName("mem Test");
        memberCustomer.setTel("0987654321");
        memberCustomer.setCreateBy(employee);
        memberCustomer = memberCustomerRepository.saveAndFlush(memberCustomer);

        Packaging packaging = new Packaging();
        packaging.setCode("T2001234");
        packaging.setSentBy(memberCustomer);
        packaging.setAtStation(station);
        packaging.setCreateBy(employee);
        packaging = packagingRepository.saveAndFlush(packaging);
        
        Cancelsent cancelsent = new Cancelsent();
        cancelsent.setName("CN01234");
        cancelsent.setCreateBy(employee);
        cancelsent.setOnPackageing(packaging);
        cancelsent.setOnStatus(status);
        cancelsent.setOnSenttoback(senttoback);
        cancelsent.setOnHowtopay(null);

        final Set<ConstraintViolation<Cancelsent>> result = validator.validate(cancelsent);

        // ต้องมี 1 error เท่านั้น
        assertEquals(1, result.size());

        // error message ตรงชนิด และถูก field
        final ConstraintViolation<Cancelsent> v = result.iterator().next();
        assertEquals("must not be null", v.getMessage());
        assertEquals("howtopay", v.getPropertyPath().toString());

    }

    @Test
    void b6021405_SenttobackMustNotBeNull() {
        Senttoback senttoback = new Senttoback();
        senttoback.setName("sentback Test");
        senttoback = senttobackRepository.saveAndFlush(senttoback);
        Howtopay howtopay = new Howtopay();
        howtopay.setName("howpay Test");
        howtopay = howtopayRepository.saveAndFlush(howtopay);

        Employee employee = new Employee();
        employee.setName("B6021405");
        employee.setEmail("B6021405@g.sut.ac.th");
        employee = employeeRepository.saveAndFlush(employee);

        Status status = new Status();
        status.setName("test status");
        status = statusRepository.saveAndFlush(status);

        Station station = new Station();
        station.setName("test station");
        station = stationRepository.saveAndFlush(station);

        MemberCustomer memberCustomer = new MemberCustomer();
        memberCustomer.setMemName("mem Test");
        memberCustomer.setTel("0987654321");
        memberCustomer.setCreateBy(employee);
        memberCustomer = memberCustomerRepository.saveAndFlush(memberCustomer);

        Packaging packaging = new Packaging();
        packaging.setCode("T2001234");
        packaging.setSentBy(memberCustomer);
        packaging.setAtStation(station);
        packaging.setCreateBy(employee);
        packaging = packagingRepository.saveAndFlush(packaging);
        
        Cancelsent cancelsent = new Cancelsent();
        cancelsent.setName("CN01234");
        cancelsent.setCreateBy(employee);
        cancelsent.setOnPackageing(packaging);
        cancelsent.setOnStatus(status);
        cancelsent.setOnSenttoback(null);
        cancelsent.setOnHowtopay(howtopat);

        final Set<ConstraintViolation<Cancelsent>> result = validator.validate(cancelsent);

        // ต้องมี 1 error เท่านั้น
        assertEquals(1, result.size());

        // error message ตรงชนิด และถูก field
        final ConstraintViolation<Cancelsent> v = result.iterator().next();
        assertEquals("must not be null", v.getMessage());
        assertEquals("senttoback", v.getPropertyPath().toString());

    }

    @Test
    void b6021405_PackagingMustNotBeNull() {
        Senttoback senttoback = new Senttoback();
        senttoback.setName("sentback Test");
        senttoback = senttobackRepository.saveAndFlush(senttoback);
        Howtopay howtopay = new Howtopay();
        howtopay.setName("howpay Test");
        howtopay = howtopayRepository.saveAndFlush(howtopay);

        Employee employee = new Employee();
        employee.setName("B6021405");
        employee.setEmail("B6021405@g.sut.ac.th");
        employee = employeeRepository.saveAndFlush(employee);

        Status status = new Status();
        status.setName("test status");
        status = statusRepository.saveAndFlush(status);

        Station station = new Station();
        station.setName("test station");
        station = stationRepository.saveAndFlush(station);

        MemberCustomer memberCustomer = new MemberCustomer();
        memberCustomer.setMemName("mem Test");
        memberCustomer.setTel("0987654321");
        memberCustomer.setCreateBy(employee);
        memberCustomer = memberCustomerRepository.saveAndFlush(memberCustomer);

        Packaging packaging = new Packaging();
        packaging.setCode("T2001234");
        packaging.setSentBy(memberCustomer);
        packaging.setAtStation(station);
        packaging.setCreateBy(employee);
        packaging = packagingRepository.saveAndFlush(packaging);
        
        Cancelsent cancelsent = new Cancelsent();
        cancelsent.setName("CN01234");
        cancelsent.setCreateBy(employee);
        cancelsent.setOnPackageing(null);
        cancelsent.setOnStatus(status);
        cancelsent.setOnSenttoback(senttoback);
        cancelsent.setOnHowtopay(howtopay);

        final Set<ConstraintViolation<Cancelsent>> result = validator.validate(cancelsent);

        // ต้องมี 1 error เท่านั้น
        assertEquals(1, result.size());

        // error message ตรงชนิด และถูก field
        final ConstraintViolation<Cancelsent> v = result.iterator().next();
        assertEquals("must not be null", v.getMessage());
        assertEquals("packageing", v.getPropertyPath().toString());

    }

    @Test
    void b6021405_EmployeeMustNotBeNull() {
        Senttoback senttoback = new Senttoback();
        senttoback.setName("sentback Test");
        senttoback = senttobackRepository.saveAndFlush(senttoback);
        Howtopay howtopay = new Howtopay();
        howtopay.setName("howpay Test");
        howtopay = howtopayRepository.saveAndFlush(howtopay);

        Employee employee = new Employee();
        employee.setName("B6021405");
        employee.setEmail("B6021405@g.sut.ac.th");
        employee = employeeRepository.saveAndFlush(employee);

        Status status = new Status();
        status.setName("test status");
        status = statusRepository.saveAndFlush(status);

        Station station = new Station();
        station.setName("test station");
        station = stationRepository.saveAndFlush(station);

        MemberCustomer memberCustomer = new MemberCustomer();
        memberCustomer.setMemName("mem Test");
        memberCustomer.setTel("0987654321");
        memberCustomer.setCreateBy(employee);
        memberCustomer = memberCustomerRepository.saveAndFlush(memberCustomer);

        Packaging packaging = new Packaging();
        packaging.setCode("T2001234");
        packaging.setSentBy(memberCustomer);
        packaging.setAtStation(station);
        packaging.setCreateBy(employee);
        packaging = packagingRepository.saveAndFlush(packaging);
        
        Cancelsent cancelsent = new Cancelsent();
        cancelsent.setName("CN01234");
        cancelsent.setCreateBy(null);
        cancelsent.setOnPackageing(packaging);
        cancelsent.setOnStatus(status);
        cancelsent.setOnSenttoback(senttoback);
        cancelsent.setOnHowtopay(howtopat);

        final Set<ConstraintViolation<Cancelsent>> result = validator.validate(cancelsent);

        // ต้องมี 1 error เท่านั้น
        assertEquals(1, result.size());

        // error message ตรงชนิด และถูก field
        final ConstraintViolation<Cancelsent> v = result.iterator().next();
        assertEquals("must not be null", v.getMessage());
        assertEquals("employee", v.getPropertyPath().toString());

    }
    

}