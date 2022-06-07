package com.bridgelabz.bookstoreapp.service;

import Interface.IUserRegistrationService;
import com.bridgelabz.bookstoreapp.dto.ResponseDTO;
import com.bridgelabz.bookstoreapp.dto.UserDTO;
import com.bridgelabz.bookstoreapp.dto.UserLoginDTO;
import com.bridgelabz.bookstoreapp.entity.UserData;
import com.bridgelabz.bookstoreapp.repository.UserRegistrationRepository;
import com.bridgelabz.bookstoreapp.util.OtpGenerator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserRegistrationService implements IUserRegistrationService {

    @Autowired
    UserData userData;
    @Autowired
    private UserRegistrationRepository userRegistrationRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private OtpGenerator otpGenerator;

    @Autowired
    private EmailSenderService emailSenderService;

    Long otp;


    @Override
    public ResponseDTO registerUserInBookStore(UserDTO userDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        UserData user = userRegistrationRepository.findUserDataByEmail(userDTO.getEmail());
        if (user == null) {//check for user exists
            //maps the userdto with userdata class
            userData = modelMapper.map(userDTO, UserData.class);
            userData.setCreatedDate(LocalDate.now());
            //encrypting the password using password encoder
            String epassword = bCryptPasswordEncoder.encode(userDTO.getPassword());
            userData.setPassword(epassword);
            System.out.println("password is " + epassword);
            userData = userRegistrationRepository.save(userData);
            otp = otpGenerator.generateOTP();
            String requestUrl = "http://localhost:8080/bookstoreApi/verify/email/" + otp;
            System.out.println("the generated otp is " + otp);
            try {
                emailSenderService.sendEmail(
                        userDTO.getEmail(),
                        "Your Registration is successful",
                        requestUrl + "\n your generated otp is "
                                + otp +
                                " click on the link above to verify the user");
            } catch (Exception e) {
                e.printStackTrace();
            }
            responseDTO.setMessage("User Created successfully");
            responseDTO.setData(userData);
        } else {
            responseDTO.setMessage("user not created");
            responseDTO.setData("user with " + userDTO.getEmail() + " is already exists");
        }
        return responseDTO;
    }

    @Override
    public ResponseDTO loginUser(UserLoginDTO userLoginDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        List<UserData> userList = userRegistrationRepository.findAll();
        UserData userDataByEmail = userRegistrationRepository.findUserDataByEmail(userLoginDTO.getEmail());
        if (userList.contains(userDataByEmail)) {
            String password = userDataByEmail.getPassword();
            //checking for password encryption match with raw passowrd
            if (bCryptPasswordEncoder.matches(userLoginDTO.getPassword(), password)) {
                responseDTO.setMessage("login SuccessFul");
                responseDTO.setData(userDataByEmail);
                return responseDTO;
            } else {
                responseDTO.setMessage("Sorry! login is unsuccessful");
                responseDTO.setData("Wrong password");
                return responseDTO;
            }
        }
        return new ResponseDTO("User not found!", "Wrong email");
    }


    public ResponseDTO verifyEmailUsingOtp(Long mailOtp) {
        if (mailOtp.equals(otp) && userData.getIsVerified().equals(false)) {
            userData.setIsVerified(true);//setting verification to true after verification
            //update the userData with is verified value
            userRegistrationRepository.save(userData);
            return new ResponseDTO("otp verified", userData);
        } else if (userData.getIsVerified() && mailOtp.equals(otp)) {
            return new ResponseDTO("otp already verified no need to verify again", userData);
        }
        return new ResponseDTO("Invalid otp", "please enter correct otp");
    }
}
