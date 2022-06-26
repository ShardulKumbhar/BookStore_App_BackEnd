package com.bridgelabz.bookstoreapp.service;

import com.bridgelabz.bookstoreapp.dto.ResponseDTO;
import com.bridgelabz.bookstoreapp.dto.UserDTO;
import com.bridgelabz.bookstoreapp.dto.UserLoginDTO;
import com.bridgelabz.bookstoreapp.entity.UserData;
import com.bridgelabz.bookstoreapp.exception.UserException;
import com.bridgelabz.bookstoreapp.repository.UserRegistrationRepository;
import com.bridgelabz.bookstoreapp.util.OtpGenerator;
import com.bridgelabz.bookstoreapp.util.TokenGenerator;
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

//    @Autowired
//    private ModelMapper modelMapper;

    @Autowired
    private OtpGenerator otpGenerator;

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private TokenGenerator tokenGenerator;

    Long otp;

    /**
     * @param userDTO
     * @return registers the users in the database
     */
    @Override
    public UserData registerUserInBookStore(UserDTO userDTO) {
        UserData user = userRegistrationRepository.findUserDataByEmail(userDTO.getEmail());
        if (user == null) {//check for user exists
            //maps the userdto with userdata class
//            userData = modelMapper.map(userDTO, UserData.class);
            userData = new UserData(userDTO);
            userData.setCreatedDate(LocalDate.now());
            //encrypting the password using password encoder
            String epassword = bCryptPasswordEncoder.encode(userDTO.getPassword());
            userData.setPassword(epassword);
            System.out.println("password is " + epassword);
            userData = userRegistrationRepository.save(userData);
            System.out.println(userData);
            otp = generateOtpAndSendEmail(userData);
            return userData;
        } else {
            throw new UserException("User not Created because user with given email already exists",
                    UserException.ExceptionType.USER_ALREADY_PRESENT);
        }

    }

    /**
     * @param userData
     * @return creates the otp and sends the email
     */
    private Long generateOtpAndSendEmail(UserData userData) {
        long generatedOtp = otpGenerator.generateOTP();
        String requestUrl = "http://localhost:8080/bookstoreApi/verify/email/" + generatedOtp;
        System.out.println("the generated otp is " + generatedOtp);
        try {
            emailSenderService.sendEmail(
                    userData.getEmail(),
                    "Your Registration is successful",
                    requestUrl + "\n your generated otp is "
                            + generatedOtp +
                            " click on the link above to verify the user");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return generatedOtp;
    }

    /**
     * @param userLoginDTO
     * @return logins the user and gives access to bookstore
     */
    @Override
    public ResponseDTO loginUser(UserLoginDTO userLoginDTO) {
        System.out.println(userLoginDTO.getEmail());
        UserData userDataByEmail = userRegistrationRepository.findUserDataByEmail(userLoginDTO.getEmail());
        if (userDataByEmail == null) {
            throw new UserException("Enter registered Email", UserException.ExceptionType.EMAIL_NOT_FOUND);
        }
        if (userDataByEmail.getIsVerified()) {
            boolean isPassword = bCryptPasswordEncoder.matches(userLoginDTO.getPassword(),
                    userDataByEmail.getPassword());
            if (!isPassword) {
                throw new UserException("Invalid Password!!!Please Enter Correct Password",
                        UserException.ExceptionType.PASSWORD_INVALID);
            }
            String jwtToken = tokenGenerator.generateLoginToken(userDataByEmail);
            return new ResponseDTO("Logged in successfully", jwtToken);
        }
        otp = generateOtpAndSendEmail(userDataByEmail);
        throw new UserException("Please verify your email before proceeding",
                UserException.ExceptionType.EMAIL_NOT_FOUND);
    }

    /**
     * @param mailOtp
     * @return verifies the otp using mail
     */
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

    /**
     * @param id
     * @return
     * @Purpose to delete the user using his id
     */
    @Override
    public String deleteUserById(long id) {
        userData = findUserById(id);
        userRegistrationRepository.delete(userData);
        return userData.getEmail();
    }

    /**
     * @param id
     * @return returns the user details if found using id
     */
    @Override
    public UserData findUserById(long id) {
        return userRegistrationRepository.findById(id).get();
    }

    /**
     * @return returns all the users in the database
     */
    @Override
    public List<UserData> findAllUsers() {
        return userRegistrationRepository.findAll();
    }

    /**
     * @param id
     * @param userDTO
     * @return returns data and status updated
     */
    @Override
    public UserData updateUserbyId(Long id, UserDTO userDTO) {
        userData = userRegistrationRepository.findById(id).get();
        userData.updateUserData(userDTO);
        return userRegistrationRepository.save(userData);
    }

    /**
     * @param email to which password has to reset
     * @return returns message with email address
     * @purpose to generate reset password link
     */
    @Override
    public String resetPasswordLink(String email) {
//       UserData userData = userRegistrationRepository.findUserDataByEmail(email);
        UserData userData = userRegistrationRepository.findUserDataByEmailId(email)
                .orElseThrow(()->new UserException("Email Not found",UserException.ExceptionType.EMAIL_NOT_FOUND));
        if(userData == null){
            throw new UserException("Email Not found",UserException.ExceptionType.EMAIL_NOT_FOUND);
        }
        String token = tokenGenerator.generateLoginToken(userData);
        String urlToken = "Click on below link to Reset your Password \n" +
                "http://localhost:8080/bookstoreApi/reset/password/"+token+
                "\n The generated token is :  "+token;
        emailSenderService.sendEmail(userData.getEmail(),"Reset Password",urlToken);

        return "Reset Password Link Has Been Sent To Your Email Address : "+userData.getEmail();
    }

    @Override
    public String resetPassword(String enterPassword, String confirmPassword, String urlToken) {
        String userId = String.valueOf(tokenGenerator.decodeJWT(urlToken));
        System.out.println(userId);
        UserData userData = findUserById(Long.parseLong(userId));
        boolean isPassword = enterPassword.equals(confirmPassword);
        if(!isPassword){
            throw new UserException("Both passwords must be same ",UserException.ExceptionType.PASSWORD_INVALID);
        }
        String encodePassword = bCryptPasswordEncoder.encode(confirmPassword);
        userData.setPassword(encodePassword);
        userRegistrationRepository.save(userData);
        return "Password Has Been Reset";
    }


    public String updatePassword(String existingPassword, String newPassword, String urlToken) {
        String userId = String.valueOf(tokenGenerator.decodeJWT(urlToken));
        System.out.println(userId);
        UserData userData = findUserById(Long.parseLong(userId));
        boolean isPassword = bCryptPasswordEncoder.matches(existingPassword,userData.getPassword());
        if(!isPassword){
            throw new UserException("Enter correct existing password",UserException.ExceptionType.PASSWORD_INVALID);
        }
        String encodePassword = bCryptPasswordEncoder.encode(newPassword);
        userData.setPassword(encodePassword);
        userRegistrationRepository.save(userData);
        return "Password Has Been Reset";
    }
}
