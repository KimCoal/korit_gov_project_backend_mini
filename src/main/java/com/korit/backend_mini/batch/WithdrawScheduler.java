package com.korit.backend_mini.batch;

import com.korit.backend_mini.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WithdrawScheduler {
    private final UserRepository userRepository;

    @Scheduled(cron = "0 0 0 * * ?")
    public void deleteExpiredUser() {
        userRepository.removeUser();
    }
}
