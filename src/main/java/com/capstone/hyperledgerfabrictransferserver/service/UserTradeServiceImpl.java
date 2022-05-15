package com.capstone.hyperledgerfabrictransferserver.service;

import com.capstone.hyperledgerfabrictransferserver.aop.customException.IncorrectStudentIdException;
import com.capstone.hyperledgerfabrictransferserver.domain.User;
import com.capstone.hyperledgerfabrictransferserver.domain.UserTrade;
import com.capstone.hyperledgerfabrictransferserver.dto.UserTradeTransactionResponse;
import com.capstone.hyperledgerfabrictransferserver.dto.UserTransferRequest;
import com.capstone.hyperledgerfabrictransferserver.repository.UserRepository;
import com.capstone.hyperledgerfabrictransferserver.repository.UserTradeRepository;
import jdk.jshell.execution.LoaderDelegate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.json.Json;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.data.domain.Sort.Order.desc;

@RequiredArgsConstructor
@Service
public class UserTradeServiceImpl implements UserTradeService {

    private final UserService userService;
    private final UserRepository userRepository;

    private final UserTradeRepository userTradeRepository;

    @Override
    @Transactional

    public void transfer(HttpServletRequest httpServletRequest, UserTransferRequest transferRequest) {

        User sender = userService.getUserByJwtToken(httpServletRequest);
        User receiver = userRepository.findByStudentId(transferRequest.getStudentId())
                .orElseThrow(() -> new IncorrectStudentIdException("가입하지 않거나 잘못된 학번입니다"));


    }

    @Transactional
    public List<UserTradeTransactionResponse> transaction(HttpServletRequest httpServletRequest) {
//
//        UserTrade findUser = userTradeRepository.findBySender(userService.getUserByJwtToken(httpServletRequest))
//                .orElseThrow(() -> new IncorrectStudentIdException("가입하지 않거나 잘못된 학번입니다")); // 예외처리

        LocalDateTime localDateTime = LocalDateTime.now();

        List<UserTrade> findUser = userTradeRepository.findAll(Sort.by(Sort.Direction.ASC, "dateCreated"));
//        List<UserTrade> findUser = userTradeRepository.findAll(userService.getUserByJwtToken(httpServletRequest));
        List<UserTradeTransactionResponse> responses = new ArrayList<>();
        for (UserTrade userTrade : findUser) {
            responses.add(
                    UserTradeTransactionResponse.builder()
                            .sender(userTrade.getSender().getStudentId())
                            .receiver(userTrade.getReceiver().getStudentId())
                            .coinName(userTrade.getCoin().getName())
                            .dateCreated(userTrade.getDateCreated())
                            .build()
            );
        }
        return responses;

//        if (findUser != null) {
//            UserTrade user = UserTrade.of(
//                    findUser.getSender(),
//                    findUser.getReceiver(),
//                    findUser.getCoin(),
//                    findUser.getAmount()
//            );

//            return UserTradeTransactionResponse.builder()
//                    .sender(user.getSender().getId())
//                    .build();
//        }
//        return null;
    }
}
