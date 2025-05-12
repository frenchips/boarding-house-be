package com.adk.kost.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Service
@Slf4j
public class CommonServiceImpl implements CommonService{

//    @Autowired
//    private JavaMailSender mailSender;

//    @Autowired
//    private String sendMail;

    @Autowired
    private RestTemplate restTemplate;


    @Autowired
    private String waToken;

    @Autowired
    private MessageUserService messageUserService;

//    @Autowired
//    private String secretKey;

    @Autowired
    private String apiWaHost;

    private static final Logger logger = Logger.getLogger(CommonServiceImpl.class.getName());

//    @Override
//    public String sendMail(String to, String subject, String body) {
//        MimeMessage mimeMessage
//                = mailSender.createMimeMessage();
//        MimeMessageHelper mimeMessageHelper;
//        try {
//
//            // Setting multipart as true for attachments to
//            // be send
//            mimeMessageHelper
//                    = new MimeMessageHelper(mimeMessage, true);
//            mimeMessageHelper.setFrom(sendMail);
//            mimeMessageHelper.setTo(to);
//            mimeMessageHelper.setSubject(subject);
//            mimeMessageHelper.setText(body);
//
//            mailSender.send(mimeMessage);
//
//            return "Success send mail";
//        }
//
//        // Catch block to handle MessagingException
//        catch (MessagingException e) {
//            // Display message when exception occurred
//            return "Error while sending mail!!!";
//        }
//
//    }


    public String sendMessageWa(String phone, String message) {
//        String uri = apiWaHost.concat("/api/send-message");
//        ResponseEntity<String> response = null;
//        try {
//
//            String point = UriComponentsBuilder.fromHttpUrl(uri)
//                    .queryParam("phone", phone)
//                    .queryParam("message", URLDecoder.decode(message, StandardCharsets.UTF_8.name()))
//                    .queryParam("token", generateToken())
//                    .encode().toUriString();
//
//            HttpHeaders headers = new HttpHeaders();
//
//            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//
//            HttpEntity entity = new HttpEntity(headers);
//
//            response = this.restTemplate.exchange(point, HttpMethod.GET, entity, String.class);
//
//            logger.info("ini response : " + response.getBody());
//    }catch(Exception e){
//
//    }
//      return response.getBody();

        String uri = apiWaHost.concat("/api/send-message");

        Map<String, Object> mapRequest = new HashMap<>();
        mapRequest.put("phone", phone);
        mapRequest.put("message", message);

        logger.info("ini adalah request : " + mapRequest);

        MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();
        form.setAll(mapRequest);

        logger.info("ini adalah form request : " + form);

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.MULTIPART_FORM_DATA);
        header.set("Authorization", waToken);

        logger.info("ini adalah header : " + header);

        HttpEntity<Object> entity = new HttpEntity<>(form, header);
        ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);

        return responseEntity.getBody();
    }

    private String generateToken(){
        StringBuilder builder = new StringBuilder();
        builder.append(waToken);


        return builder.toString();
    }
}
