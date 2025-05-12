package com.adk.kost.service;


import ch.qos.logback.core.util.StringUtil;
import com.adk.kost.constant.Constants;
import com.adk.kost.dto.BookingDto;
import com.adk.kost.dto.OrderDto;
import com.adk.kost.dto.SubmissionDto;
import com.adk.kost.entity.*;
import com.adk.kost.exception.ValidateException;
import com.adk.kost.repository.*;
import com.adk.kost.request.OrderRequest.*;
import com.adk.kost.util.AES256Util;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.logging.Logger;


@Service
public class TransactionServiceImpl extends BaseServiceImpl implements TransactionService{

    @Autowired
    OrderRepository orderRepo;

    @Autowired
    PaymentRepository paymentRepo;

    @Autowired
    PersonRepository personRepo;

    @Autowired
    ParameterRepository parameterRepository;

    @Autowired
    RoomRepository roomRepo;

    @Autowired
    UserRepository userRepo;

    @Autowired
    CommonService commonService;

    @Autowired
    MessageUserService messageUserService;


    @Autowired
    UserRepository userRepository;

    private static final Logger logger = Logger.getLogger(TransactionServiceImpl.class.getName());


    private final Integer amountDp = 300000;

    public Order saveOrder(OrderSaveRequest request, String no) {

//        ValidateUtil.messageOrder(request);
        validateOrder(request);

        Long roomId = roomRepo.getById(no);
        Room room = roomRepo.findById(roomId).orElse(null);

        Order order = new Order();

//      Kamar yang belum di pesan
        if(room.getRoomStatus().equals("NOT_EXIST") && room.getRoomNo().equals(no)){

            order.setOrderName(request.getOrderName());

            order.setOrderRoomId(room);

            order.setOrderDate(new Date());
            order.setOrderStatus(request.getOrderStatus());
            order.setTotalPerson(request.getTotalPerson());
            this.createAudit(order, Constants.CS);
            order.setNoHp(request.getNoHp());


//            emailService.sendMail(request.getEmail(), "Cek", "Test Subject");

            List<Person> personList = new ArrayList<>();

            // Add Person
            for(int i = 0; i < request.getListPerson().size(); i++){
                PersonRequest personRequest = request.getListPerson().get(i);
                Person person = new Person();
                personList.add(this.savePerson(person, personRequest, order));
                order.setPersonList(personList);
            }
            // Finish Person


//            PEMBAYARAN DP
            if(!personList.isEmpty() && request.getOrderStatus().equals("DP")){
               Payment pay = new Payment();

               pay.setOrderId(order);
               pay.setPaymentDate(new Date());
               pay.setPaymentStatus(parameterRepository.findById("PAID_NOT_FULL").orElseThrow());
               pay.setTotalPaymentAmt(getAmountDp(room.getPrice(), amountDp));
               this.createAudit(pay, "admin");
               paymentRepo.save(pay);

            }
            // Update Status Room
            updateStatus(room);

//
//            ValidateUtil.callApi(request.getNoHandphone());

            if(order != null){
                sendWa(request.getNoHp(), generateUsernameAndPassword(request.getOrderName(), request.getNoHp()).getUsername(), generateRandomPassword(), 11111, 2222, "https://www.w3schools.com/");
            }

        } else {
            throw new ValidateException("Nomor kamar sudah terisi");
        }


        orderRepo.save(order);
        return order;
    }




    @Override
    public Payment savePayment(PaymentRequest request) {

//        ValidateUtil.messagePayment(request);



        UUID orderId = getOrderName(request.getOrderName());
        Order order = orderRepo.findById(orderId).orElse(null);

        Room room = room(order.getOrderRoomId().getRoomId().longValue());
        Integer countPay = total(room.getPrice(), request);

        UUID pay = getPaymentByName(request.getOrderName());

        Payment payment = null;

        // Pemesan baru membayar awal
        if(pay == null && order != null){
            payment = new Payment();
            payment.setOrderId(order);
//            payment.setPaymentAmt(request.getPriceRoom());
            if(countPay == 0){
                payment.setTotalPaymentAmt(countPay);
                payment.setPaymentStatus(parameterRepository.findById("PAID_FULL").orElseThrow());
                orderPay(orderId);
                this.createAudit(payment, "admin");
            } else {
                payment.setTotalPaymentAmt(countPay);
                payment.setPaymentStatus(parameterRepository.findById("PAID_NOT_FULL").orElseThrow());
            }


        }

        // Pemesan membayar kekurangan dan melakukan pembayaran pelunasan
        if(order != null && pay != null){
            payment = paymentRepo.findById(pay).orElse(null);
            payment.setOrderId(order);
//            payment.setPaymentAmt(request.getPriceRoom() + payment.getPaymentAmt());
            Integer paidOff = request.getPriceRoom() - payment.getTotalPaymentAmt() ;
            if(payment != null){
                if(paidOff == 0){
                    payment.setTotalPaymentAmt(paidOff);
                    payment.setPaymentStatus(parameterRepository.findById("PAID_FULL").orElseThrow());
                    orderPay(orderId);
                    this.updateAudit(payment, "admin");
                } else {
                    payment.setTotalPaymentAmt(countPay);
                    payment.setPaymentStatus(parameterRepository.findById("PAID_NOT_FULL").orElseThrow());
                }
            }
        }
        payment.setPaymentDate(new Date());

        this.createAudit(payment, "admin");
        return paymentRepo.save(payment);
    }


    @Override
    public List<OrderDto> findByRoomNoAndOrderStatus(HomeOrderRequest request) {

        List<Object[]> listData = orderRepo.findByRoomNoAndOrderStatus(request.getSearchVal());
        List<OrderDto> listOrder = new ArrayList<>();

        for(Object[] obj: listData){
            OrderDto dto = new OrderDto();
            dto.setRoomNo(obj[0] != null ? (String) obj[0] : null);
            dto.setRoomStatus(obj[1] != null ? (String) obj[1] : null);
            dto.setPrice(obj[2] != null ? (Integer) obj[2] : null);
//            String date = DateUtil.parseDate((Date) obj[1]);
//            dto.setRoomDate(date);
            dto.setOrderStatus(obj[3] != null ? (String) obj[3] : null);





            listOrder.add(dto);
        }

        return listOrder;
    }

    public List<BookingDto> findByRoomNoAndOrder(HomeOrderRequest request) {

        List<Object[]> listData = orderRepo.findByRoomNoAndOrder(request.getSearchVal());
        List<BookingDto> listOrder = new ArrayList<>();

        for(Object[] obj: listData){
            BookingDto dto = new BookingDto();
            dto.setRoomNo(obj[0] != null ? (String) obj[0] : null);
            dto.setRoomStatus(obj[1] != null ? (String) obj[1] : null);
            dto.setPrice(obj[2] != null ? (Integer) obj[2] : null);
            dto.setOrderStatus(obj[3] != null ? (String) obj[3] : null);
            dto.setOrderName(obj[4] != null ? (String) obj[4] : null);
            Date dt = obj[5] != null ? (Date) obj[5] : null;

            SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy, HH:mm", new Locale("id", "ID"));

            if(dt != null){
                String format = formatter.format(dt);
                dto.setOrderStr(format);
            }


            listOrder.add(dto);
        }
        return listOrder;
    }

    @Override
    public List<SubmissionDto> findSubmissionOrder(SubmissionRequest request) {
        List<Object[]> listData = orderRepo.findBySubmission(request.getSearchValue());
        List<SubmissionDto> listSubmission = new ArrayList<>();
        if(listData != null){
            for(Object[] obj : listData) {
                SubmissionDto dto = new SubmissionDto();
                dto.setRoomNo(obj[0] != null ? (String) obj[0] : null);
                dto.setPrice(obj[1] != null ? (Integer) obj[1] : null);
                dto.setOrderStatus(obj[2] != null ? (String) obj[2] : null);
                listSubmission.add(dto);
            }
        }

        return listSubmission;

    }


    @Override
    public BookingDto findByRoomNoTransaction(String no) {

        List<Object[]> obj = null;
        BookingDto dto = null;
        if(StringUtils.isEmpty(no)){
            throw new ValidateException("Nomor tidak boleh kosong");
        } else {
            Long roomNo = roomRepo.getById(no);
            if(roomNo == null){
                throw new ValidateException("Nomor kamar tidak ditemukan");
            }else {
                Room room = roomRepo.findById(roomNo).orElse(null);

                if (room != null){
                    obj = orderRepo.findByRoomNo(no);


                    if(obj != null){
                        for(Object[] objects : obj){
                            dto = new BookingDto();
                            dto.setRoomNo(objects[0] != null ? (String) objects[0] : null);
                            dto.setRoomStatus(objects[1] != null ? (String) objects[1] : null);
                            dto.setPrice(objects[2] != null ? (Integer) objects[2] : null);
                            dto.setOrderStatus(objects[3] != null ? (String) objects[3] : null);
                            dto.setOrderName(objects[4] != null ? (String) objects[4] : null);
                            Date dt = objects[5] != null ? (Date) objects[5] : null;

                            SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy, HH:mm", new Locale("id", "ID"));

                            if(dt != null){
                                String format = formatter.format(dt);
                                dto.setOrderStr(format);
                            }

                        }

                    }
//                    listObj = orderRepo.findByRoomNo(room.getRoomNo());
//
//                    if(!listObj.isEmpty() || listObj != null){
//                        for(Object [] obj : listObj){
//                            BookingDto dto = new BookingDto();
//                            dto.setRoomNo(obj[0] != null ? (String) obj[0] : null);
//                            dto.setRoomStatus(obj[1] != null ? (String) obj[1] : null);
//                            dto.setPrice(obj[2] != null ? (Integer) obj[2] : null);
//                            dto.setOrderStatus(obj[3] != null ? (String) obj[3] : null);
//                            dto.setOrderName(obj[4] != null ? (String) obj[4] : null);
//                            Date dt = obj[5] != null ? (Date) obj[5] : null;
//
//                            SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy, HH:mm", new Locale("id", "ID"));
//
//                            if(dt != null){
//                                String format = formatter.format(dt);
//                                dto.setOrderStr(format);
//                            }
//
//
//                            listOrder.add(dto);
//                        }
//                    } else {
//                        throw new ValidateException("Data tidak ditemukan");
//                    }
                }
            }
        }
        return dto;
    }

    public Person savePerson(Person person, PersonRequest request, Order order) {

        validatePerson(request);

        person.setFullName(request.getFullName());
        person.setGender(request.getGender());
        person.setNik(request.getNik());
        person.setOrderId(order);

        this.createAudit(person, "customer");
        return personRepo.save(person);
    }

    private Order orderPay (UUID id){
        Order order = orderRepo.findById(id).orElse(null);

        if(order != null){
            order.setOrderStatus("CASH");
            this.updateAudit(order, "Admin");
            orderRepo.save(order);
        }
        return order;
    }

    @Override
    public Order loadOrder(String no) {

        UUID orderId = getOrder(no);
        Order order = orderRepo.findById(orderId).orElse(null);
        return order;
    }


    private UUID getOrder(String orderName){
        return orderRepo.getByOrderId(orderName);
    }

    private UUID getOrderName(String orderName){
        return orderRepo.getOrderByName(orderName);
    }

    private UUID getPaymentByName(String name){
        return paymentRepo.getPaymentByName(name);
    }

    private Integer total(Integer count, PaymentRequest request){
        return  count -  request.getPriceRoom() ;
    }

    private Room room(Long id){
        return roomRepo.findById(id).orElse(null);
    }

    private String getRoomNo (String no){
        return orderRepo.getRoomNumber(no);
    }

    private Room updateStatus(Room room){
        room.setRoomStatus("IS_EXIST");
        this.updateAudit(room, "Admin");
        return roomRepo.save(room);
    }



    public Users generateUsernameAndPassword(String fullName, String nohp){
        Users users = new Users();

        users.setUsername(saveUsername(fullName));

        String pass = AES256Util.generateEncrypted(generateRandomPassword());
        users.setPassword(pass);
        users.setNoHp(nohp);

        users.setUserType(Constants.CS);
        this.createAudit(users, "admin");
        return userRepo.save(users);
    }

    private String generateRandom(){
        Random random = new Random();
        int boundedRandom = 1000 * random.nextInt(9000);
        String value = String.valueOf(boundedRandom);

        return value;
    }

    public String generateRandomPassword(){
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 6;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();

        return generatedString;
    }

    public String saveUsername(String fullName){

        String firstName;
        String username;
        if(fullName != null && !fullName.isEmpty()){
             firstName = fullName.split(" ")[0];
             username = userRepository.getUsername(firstName.toLowerCase());
             if(firstName.equals(username)){
                 return firstName + generateRandom();
             } else {
                 return firstName.toLowerCase();
             }
        } else {
            throw new ValidateException("Nama tidak valid");
        }

    }

    private Integer getAmountDp(Integer price, Integer dp ){
        Integer total;
        if(dp != 0){
            total = price - dp;
        } else {
            throw new ValidateException("Tidak boleh lebih kecil dari 0");
        }
        return total;
    }

    private String validatePerson(PersonRequest request){
        String message = "";
        if(StringUtils.isEmpty(request.getFullName())){
            message = "Nama tidak boleh kosong";
            throw new ValidateException(message);
        } else if (request.getFullName().length() >= 64){
            message = "Panjang nama tidak boleh lebih dari 64";
            throw new ValidateException(message);
        }

        if(StringUtils.isEmpty(request.getGender())){
            message = "Jenis kelamin tidak boleh kosong";
            throw new ValidateException(message);
        }

        if(StringUtils.isEmpty(request.getNik())){
            message = "Nomor Induk Kependudukan tidak boleh kosong";

            throw new ValidateException(message);
        } else if (request.getNik().length() >= 17){
            message = "Nomor Induk Kependudukan tidak boleh lebih dari 16";
            throw new ValidateException(message);
        }



        return message;
    }


    private String validateOrder(OrderSaveRequest request){
        String message = "";

        if(request.getOrderName().isEmpty()){
            message = "Nama pemesanan tidak boleh kosong";
            throw new ValidateException(message);
        } else if(request.getOrderStatus().isEmpty()){
            message = "Status pemesanan tidak boleh kosong";
            throw new ValidateException(message);
        }
        if (request.getTotalPerson() >= 1 && request.getTotalPerson() <= 2){
            if (request.getListPerson().isEmpty()){
                message = "Detail Data orang tidak boleh kosong";
                throw new ValidateException(message);
            }

            if(!request.getListPerson().isEmpty()){
                for(int i = 0; i < request.getListPerson().size(); i++){
                    PersonRequest requestPerson = request.getListPerson().get(i);
                    validatePerson(requestPerson);
                }
            }
        } else {
            message = String.format("Jumlah %d minimal 1 dan maksimal 2 orang", request.getTotalPerson());
            throw new ValidateException(message);
        }
        return message;
    }



    public String sendWa(String phone, String user, String pass, Integer no1, Integer no2, String to){
        return  commonService.sendMessageWa(phone, messageUserService.messageUser(user, pass, no1, no2, to));
    }
}
