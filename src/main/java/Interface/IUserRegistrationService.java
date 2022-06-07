package Interface;


import com.bridgelabz.bookstoreapp.dto.ResponseDTO;
import com.bridgelabz.bookstoreapp.dto.UserDTO;
import com.bridgelabz.bookstoreapp.dto.UserLoginDTO;
import com.bridgelabz.bookstoreapp.entity.UserData;


public interface IUserRegistrationService {

    ResponseDTO registerUserInBookStore(UserDTO userDTO);

    ResponseDTO loginUser(UserLoginDTO userLoginDTO);

    ResponseDTO verifyEmailUsingOtp(Long otp);
}
