package frontdoorprivacy.controller;


import frontdoorprivacy.model.enterprise.Enterprise;
import frontdoorprivacy.model.enterprise.LoginEnterprise;
import frontdoorprivacy.model.user.LoginInfo;
import frontdoorprivacy.model.user.LoginOutput;
import frontdoorprivacy.service.enterprise.EnterpriseService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;

import java.util.HashMap;

@Controller
@RequestMapping
public class RegisterController {
    private Logger logger = LoggerFactory.getLogger(RegisterController.class);
    private static EnterpriseService enterpriseService;


    @Autowired
    public RegisterController(EnterpriseService enterpriseService) {
        this.enterpriseService = enterpriseService;
    }

    //회원가입
    @PostMapping("/register/company")
    public void create(@RequestBody Enterprise enterprise){
        logger.info(enterprise.getEnterpriseName());
        logger.info(enterprise.getEnterpriseNumber());
        logger.info(enterprise.getEnterpriseId());

        enterpriseService.createEnterprise(enterprise);

    }

    @PostMapping("/register/company/check")
    public ResponseEntity<?> check(@RequestBody HashMap<String,String> enterpriseID){
        String output;
        HashMap<String,String> returnvalue = new HashMap<>();
        output = enterpriseService.checkmultiple(enterpriseID.get("enterpriseId"));
//       logger.info(output);
        returnvalue.put("returnvalue",output);

       return ResponseEntity.ok(returnvalue);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginOutput> login(@RequestBody LoginInfo loginInfo){
        LoginOutput output = new LoginOutput();
        logger.info(loginInfo.getRole());
        logger.info(loginInfo.getUserId());
        logger.info(loginInfo.getPassword());


        if(loginInfo.getRole().equals("E")){
            logger.info("if문 입장");
            LoginEnterprise input = new LoginEnterprise();
            input.setUserId(loginInfo.getUserId());
            input.setPw(loginInfo.getPassword());
            output = enterpriseService.enLogin(input);

            logger.info(String.valueOf(output));
        }else if(loginInfo.getRole().equals("E")){
            //유저 로그인
        }
        return new ResponseEntity<>(output, HttpStatus.OK);
    }
}
